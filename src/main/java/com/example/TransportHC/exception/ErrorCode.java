package com.example.TransportHC.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {

    // ====== COMMON ======
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_INPUT_DATA(9001, "Invalid input data", HttpStatus.BAD_REQUEST),

    // ====== AUTH / SECURITY ======
    UNAUTHENTICATED(1000, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1001, "Unauthorized", HttpStatus.FORBIDDEN),
    TOKEN_INVALID(1002, "Token invalid or expired", HttpStatus.UNAUTHORIZED),

    // ====== USER ======
    USER_EXISTED(2001, "User already exists", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(2002, "User not found", HttpStatus.NOT_FOUND),
    INVALID_USERNAME_PASSWORD(2003, "Invalid username or password", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(2004, "Role not found", HttpStatus.BAD_REQUEST),

    // ====== TRUCK ======
    TRUCK_NOT_FOUND(3001, "Truck not found", HttpStatus.NOT_FOUND),
    TRUCK_EXISTED(3002, "Truck already exists", HttpStatus.BAD_REQUEST),
    TRUCK_NOT_AVAILABLE(3003, "Truck is not available", HttpStatus.BAD_REQUEST),

    // ====== ROUTE ======
    ROUTE_NOT_FOUND(3101, "Route not found", HttpStatus.NOT_FOUND),
    ROUTE_EXISTED(3102, "Route already exists", HttpStatus.BAD_REQUEST),

    // ====== SCHEDULE ======
    SCHEDULE_NOT_FOUND(4001, "Schedule not found", HttpStatus.NOT_FOUND),
    SCHEDULE_ALREADY_APPROVED(4002, "Schedule already approved", HttpStatus.BAD_REQUEST),
    INVALID_SCHEDULE_DATE(4003, "Invalid schedule date", HttpStatus.BAD_REQUEST),

    // ====== COST ======
    COST_NOT_FOUND(5001, "Cost not found", HttpStatus.NOT_FOUND),
    COST_ALREADY_APPROVED(5002, "Cost already approved", HttpStatus.BAD_REQUEST),

    // ====== PRODUCT / INVENTORY ======
    PRODUCT_NOT_FOUND(6001, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_EXISTED(6002, "Product already exists", HttpStatus.BAD_REQUEST),
    INVENTORY_NOT_FOUND(6003, "Inventory not found", HttpStatus.NOT_FOUND),
    INVENTORY_NOT_ENOUGH(6004, "Not enough product in inventory", HttpStatus.BAD_REQUEST),

    // ====== TRANSACTION ======
    TRANSACTION_NOT_FOUND(7001, "Transaction not found", HttpStatus.NOT_FOUND),
    TRANSACTION_DETAIL_NOT_FOUND(7002, "Transaction detail not found", HttpStatus.NOT_FOUND);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
