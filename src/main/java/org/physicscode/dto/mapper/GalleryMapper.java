package org.physicscode.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.physicscode.domain.entity.Gallery;
import org.physicscode.dto.pojo.output.profile.GalleryDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GalleryMapper {

    GalleryDTO mapToGalleryDTO(Gallery gallery);
}
