package org.physicscode.dto.pojo.output;

import lombok.Data;

import java.util.List;

@Data
public class GalleryDTO {

    List<GalleryImageHolderDTO> galleryPictures;
}
