package org.physicscode.dto.pojo.output;

import lombok.Data;

@Data
public class GalleryImageHolderDTO {

    private Integer imageId;
    private String imageUrl;
    private String thumbUrl;
    private String mediumUrl;
}
