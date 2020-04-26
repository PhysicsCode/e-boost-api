package org.physicscode.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.physicscode.domain.entity.membership.MembershipEntity;
import org.physicscode.dto.pojo.output.membership.MembershipPlanDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MembershipMapper {


    MembershipPlanDTO mapToMembershipHolder(MembershipEntity membershipEntity);
}
