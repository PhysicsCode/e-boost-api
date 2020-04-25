package org.physicscode.exception;

import org.springframework.core.NestedRuntimeException;

public class UserNotFoundException extends NestedRuntimeException {

    public UserNotFoundException(String s) {
        super(s);
    }
}
