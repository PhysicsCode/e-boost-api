package org.physicscode.dto.mapper;

import org.mapstruct.Mapper;
import org.physicscode.domain.entity.CustomerUser;
import org.physicscode.domain.entity.FreelancerUser;
import org.physicscode.dto.pojo.output.CustomerUserProfileDTO;
import org.physicscode.dto.pojo.output.FreelancerUserProfileDTO;

@Mapper
public interface ProfileMapper {

     FreelancerUserProfileDTO mapToFreelancerProfileDTO(FreelancerUser freelancerUser);

    CustomerUserProfileDTO mapToCustomerProfile(CustomerUser customerUser);
}
