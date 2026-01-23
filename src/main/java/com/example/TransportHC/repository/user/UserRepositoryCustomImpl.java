package com.example.TransportHC.repository.user;

import static com.example.TransportHC.entity.QRole.role;
import static com.example.TransportHC.entity.QUser.user;

import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.TransportHC.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @PostConstruct
    public void init() {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<User> findAllNonAdminUsers() {
        return queryFactory
                .selectDistinct(user)
                .from(user)
                .join(user.roles, role)
                .where(role.code.ne("ADMIN"))
                .fetch();
    }
}
