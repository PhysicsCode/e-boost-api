package org.physicscode.domain.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.physicscode.domain.entity.membership.MembershipEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class FreelancerUser {

    @Id
    private ObjectId id;

    //Form based
    @Indexed(unique = true)
    private String userId;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;

    //Extra fields

    private String description;
    private String firstName;
    private String lastName;
    private ImageHolder profilePic = new ImageHolder();
    private ImageHolder profileBackPic = new ImageHolder();
    private List<String> tags;

    private String youtubeChannelLink;
    private String instagramProfile;

    //Meta

    private List<Recommendation> recommendations = new ArrayList<>();

    private Gallery gallery = new Gallery();

    private List<MembershipEntity> activeMemberships = new ArrayList<>();

}
