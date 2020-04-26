package org.physicscode.domain.entity;

import lombok.Data;

@Data
public class GalleryPictureHolder {

    private Integer imageId;
    private String imageUrl;
    private String thumbUrl;
    private String mediumUrl;
    private String deleteUrl;
}
