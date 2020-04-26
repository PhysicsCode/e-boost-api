package org.physicscode.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.dto.pojo.input.membership.MembershipTriggerDTO;
import org.physicscode.dto.pojo.output.error.ErrorDTO;
import org.physicscode.dto.pojo.output.membership.MembershipPlanHolderDTO;
import org.physicscode.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/membership/freelancer")
public class FreelanceMembershipsController {

    private final MembershipService membershipService;


    @ApiOperation(value = "Retrieves memberships for freelancers",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = MembershipPlanHolderDTO.class, message = "Freelancer memberships retrieved successfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @GetMapping
    public Mono<ResponseEntity<MembershipPlanHolderDTO>> retrieveMembershipPlans(@ApiIgnore @AuthenticationPrincipal EBoostAuthenticationUser user) {

        return membershipService.retrieveMembershipPlans(user.getUserId())
                .map(ResponseEntity::ok);
    }

    @ApiOperation(value = "Triggers the status of a freelancer membership",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = MembershipPlanHolderDTO.class, message = "Freelancer membership triggered successfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @PostMapping
    public Mono<ResponseEntity<MembershipPlanHolderDTO>> triggerMembershipPlans(@ApiIgnore @AuthenticationPrincipal EBoostAuthenticationUser user,
                                                                                 @RequestBody MembershipTriggerDTO membershipTriggerDTO) {

        return membershipService.triggerMembershipPlanStatus(user.getUserId(), membershipTriggerDTO.getTrigger(), membershipTriggerDTO.getMembershipId())
                .map(ResponseEntity::ok);
    }
}
