package org.physicscode.domain.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class ImageHolder {

    @Field("image_url")
    private String imageUrl;
    @Field("thumb_url")
    private String thumbUrl;
    @Field("medium_url")
    private String mediumUrl;
    @Field("deletion_url")
    private String deletionUrl;
}
