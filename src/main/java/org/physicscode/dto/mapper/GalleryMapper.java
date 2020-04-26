package org.physicscode.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.physicscode.domain.entity.CustomerUser;
import org.physicscode.domain.entity.FreelancerUser;
import org.physicscode.domain.entity.Gallery;
import org.physicscode.dto.pojo.output.CustomerUserProfileDTO;
import org.physicscode.dto.pojo.output.FreelancerUserProfileDTO;
import org.physicscode.dto.pojo.output.GalleryDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GalleryMapper {

    GalleryDTO mapToGalleryDTO(Gallery gallery);
}
