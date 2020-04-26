package org.physicscode.domain.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Gallery {

    List<GalleryPictureHolder> galleryPictures = new ArrayList<>();
}
