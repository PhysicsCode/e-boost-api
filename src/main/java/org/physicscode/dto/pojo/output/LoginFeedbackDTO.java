package org.physicscode.dto.pojo.output;

import lombok.Data;
import org.physicscode.constants.UserType;

@Data
public class LoginFeedbackDTO {

    private String token;
    private UserType type;
}
