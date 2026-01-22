package com.example.TransportHC.service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.TransportHC.dto.request.LogoutRequest;
import com.example.TransportHC.dto.request.RefreshRequest;
import com.example.TransportHC.dto.response.AuthResponse;
import com.example.TransportHC.entity.InvalidToken;
import com.example.TransportHC.entity.Permission;
import com.example.TransportHC.entity.Role;
import com.example.TransportHC.entity.User;
import com.example.TransportHC.exception.AppException;
import com.example.TransportHC.exception.ErrorCode;
import com.example.TransportHC.repository.InvalidTokenRepository;
import com.example.TransportHC.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;
    InvalidTokenRepository invalidTokenRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        List<String> roles =
                user.getRoles().stream().map(Role::getCode).distinct().toList();

        List<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getCode)
                .distinct()
                .toList();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("viethung")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(10, ChronoUnit.HOURS)))
                .claim("roles", roles)
                .claim("permissions", permissions)
                .jwtID(UUID.randomUUID().toString())
                .build();

        JWSObject jwsObject = new JWSObject(header, new Payload(claimsSet.toJSONObject()));

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Cannot generate JWT", e);
        }
    }

    public void logout(LogoutRequest request) throws JOSEException, ParseException {
        var token = verifyToken(request.getToken());
        String uuid = token.getJWTClaimsSet().getJWTID();
        Date expiredTime = token.getJWTClaimsSet().getExpirationTime();

        InvalidToken invalidToken =
                InvalidToken.builder().token(uuid).expiryTime(expiredTime).build();

        invalidTokenRepository.save(invalidToken);
    }

    public SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.TOKEN_INVALID);

        return signedJWT;
    }

    public AuthResponse refreshToken(RefreshRequest request) throws JOSEException, ParseException {

        var signedJWT = verifyToken(request.getToken());

        String uuid = signedJWT.getJWTClaimsSet().getJWTID();
        Date expiredTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        InvalidToken invalidToken =
                InvalidToken.builder().token(uuid).expiryTime(expiredTime).build();

        invalidTokenRepository.save(invalidToken);

        String username = signedJWT.getJWTClaimsSet().getSubject();

        User user = userRepository
                .findUserByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        var token = generateToken(user);

        return AuthResponse.builder().token(token).build();
    }
}
