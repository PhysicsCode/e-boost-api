package org.physicscode.domain.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class FreelancerUser {

    @Indexed(unique = true)
    private String userId;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
}
