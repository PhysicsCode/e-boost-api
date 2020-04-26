package org.physicscode.domain.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class CustomerUser {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    private String userId;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
}
