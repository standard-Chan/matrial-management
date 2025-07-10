package com.springframework.mm.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleFKViolation(DataIntegrityViolationException ex) {
        if (ex.getRootCause() instanceof java.sql.SQLIntegrityConstraintViolationException) {
            String message = ex.getRootCause().getMessage();
            if (message.contains("a foreign key constraint fails")) {
                return ResponseEntity
                        .status(409) // 409 Conflict
                        .body("삭제할 수 없습니다: 관련된 하위 데이터가 존재합니다.");
            }
        }

        return ResponseEntity
                .status(400)
                .body("데이터 무결성 오류가 발생했습니다.");
    }
}