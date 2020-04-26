package org.physicscode.dto.pojo.output.profile;

import lombok.Data;
import org.physicscode.domain.entity.Recommendation;

import java.util.List;

@Data
public class FreelancerUserProfileDTO {

    private String userId;
    private String username;
    private String email;

    private String description;
    private String firstName;
    private String lastName;
    private ImageHolderDTO profilePic;
    private ImageHolderDTO profileBackPic;
    private List<String> tags;

    private String youtubeChannelLink;
    private String instagramProfile;

    private List<Recommendation> recommendations;
}
