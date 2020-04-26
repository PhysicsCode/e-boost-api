package org.physicscode.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    USER_NOT_FOUND                  ("User not found on database",                  HttpStatus.BAD_REQUEST),
    EMAIL_ALREADY_IN_USE            ("Email is already in use!",                    HttpStatus.INTERNAL_SERVER_ERROR),
    EMPTY_INFORMATION_FOR_USER      ("No information found for the user",           HttpStatus.NO_CONTENT ),
    INVALID_PICTURE_TYPE            ("Invalid provided picture type",               HttpStatus.BAD_REQUEST),
    MAXIMUM_GALLERY_SIZE_REACHED    ("Maximum gallery size of 8 have been reached", HttpStatus.BAD_REQUEST),
    INVALID_MEMBERSHIP_PROVIDED     ("The provided membership is not valid",        HttpStatus.BAD_REQUEST);

    private final String msg;
    private final HttpStatus httpStatus;
}
