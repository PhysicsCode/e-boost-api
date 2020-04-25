package org.physicscode.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    USER_NOT_FOUND                  ("User not found on database",          HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_IN_USE            ("Email is already in use!",            HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_INFORMATION_FOR_USER      ("No information found for the user",   HttpStatus.NO_CONTENT );

    private final String msg;
    private final HttpStatus httpStatus;
}
