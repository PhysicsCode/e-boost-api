package org.physicscode.domain.auth;

import lombok.Data;
import org.bson.types.ObjectId;
import org.physicscode.constants.UserType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Document
public class EBoostAuthenticationUser implements Authentication {

    @Id
    private ObjectId objectId;
    @Indexed(unique = true)
    private String userId;
    @Indexed(unique = true)
    private String username;
    private String password;
    @Indexed(unique = true)
    private String email;
    private List<GrantedAuthority> authorities;
    private Object details;
    private Object principal;
    private boolean authenticated;
    private UserType userType;

    public EBoostAuthenticationUser() {

    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
    public static EBoostAuthenticationUser buildAnonymous() {
        return new EBoostAuthenticationUser();
    }
}
