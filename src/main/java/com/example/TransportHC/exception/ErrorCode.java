package com.example.TransportHC.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

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
    USER_NOT_AVAILABLE(2004, "User not available", HttpStatus.NOT_FOUND),
    USER_IS_NOT_DRIVER(2005, "User is not a driver", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(2005, "Role not found", HttpStatus.BAD_REQUEST),

    // ====== TRUCK ======
    TRUCK_NOT_FOUND(3001, "Truck not found", HttpStatus.NOT_FOUND),
    TRUCK_EXISTED(3002, "Truck already exists", HttpStatus.BAD_REQUEST),
    TRUCK_NOT_AVAILABLE(3003, "Truck is not available", HttpStatus.NOT_FOUND),

    // ====== ROUTE ======
    ROUTE_NOT_FOUND(3101, "Route not found", HttpStatus.NOT_FOUND),
    ROUTE_EXISTED(3102, "Route already exists", HttpStatus.BAD_REQUEST),

    // ====== SCHEDULE ======
    SCHEDULE_NOT_FOUND(4001, "Schedule not found", HttpStatus.NOT_FOUND),
    SCHEDULE_IS_PENDING(4002, "Schedule is pending...", HttpStatus.BAD_REQUEST),
    SCHEDULE_ALREADY_APPROVED(4003, "Schedule already approved", HttpStatus.BAD_REQUEST),
    SCHEDULE_ALREADY_DONE(4004, "Schedule already done", HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_IN_TRANSIT(4005, "Schedule not in transit", HttpStatus.BAD_REQUEST),
    INVALID_SCHEDULE_DATE(4005, "Invalid schedule date", HttpStatus.BAD_REQUEST),

    // ====== COST ======
    COST_NOT_FOUND(5001, "Cost not found", HttpStatus.NOT_FOUND),
    COST_ALREADY_APPROVED(5002, "Cost already approved", HttpStatus.BAD_REQUEST),
    COST_TYPE_NOT_FOUND(5003, "Cost type not found", HttpStatus.NOT_FOUND),

    // ====== PRODUCT / INVENTORY ======
    PRODUCT_NOT_FOUND(6001, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_EXISTED(6002, "Product already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(6003, "Product not found", HttpStatus.NOT_FOUND),
    INVENTORY_NOT_FOUND(6004, "Inventory not found", HttpStatus.NOT_FOUND),
    INVENTORY_NOT_ENOUGH(6005, "Not enough product in inventory", HttpStatus.BAD_REQUEST),

    // ====== TRANSACTION ======
    TRANSACTION_NOT_FOUND(7001, "Transaction not found", HttpStatus.NOT_FOUND),
    TRANSACTION_IS_PENDING(7002, "Transaction is pending...", HttpStatus.BAD_REQUEST),
    TRANSACTION_ALREADY_APPROVED(7003, "Transaction already approved", HttpStatus.BAD_REQUEST),
    TRANSACTION_ALREADY_DONE(7004, "Transaction already done", HttpStatus.BAD_REQUEST),
    TRANSACTION_ALREADY_USED(7005, "Transaction already used", HttpStatus.BAD_REQUEST),
    TRANSACTION_DETAIL_NOT_FOUND(7006, "Transaction detail not found", HttpStatus.NOT_FOUND),

    // ===== REPORT =====
    REPORT_NOT_FOUND(8001, "Report not found", HttpStatus.NOT_FOUND),
    REPORT_EXISTED(8002, "Report already exists", HttpStatus.BAD_REQUEST),
    REPORT_ALREADY_DONE(8003, "Report already done", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
