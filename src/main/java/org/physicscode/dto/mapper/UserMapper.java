package org.physicscode.dto.mapper;

import org.mapstruct.ReportingPolicy;
import org.physicscode.domain.entity.CustomerUser;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.mapstruct.Mapper;
import org.physicscode.domain.entity.FreelancerUser;
import org.physicscode.dto.pojo.input.EBoostUserCustomerRegisterDTO;
import org.physicscode.dto.pojo.input.EBoostUserFreelanceRegisterDTO;
import org.physicscode.dto.pojo.output.LoginFeedbackDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    LoginFeedbackDTO mapToLoginFeedback(EBoostAuthenticationUser eBoostAuthenticationUser);

    FreelancerUser mapToFreelancerUser(EBoostUserFreelanceRegisterDTO eBoostUserFreelanceRegisterDTO);

    CustomerUser mapToCustomerUser(EBoostUserCustomerRegisterDTO eBoostUserCustomerRegisterDTO);
}
