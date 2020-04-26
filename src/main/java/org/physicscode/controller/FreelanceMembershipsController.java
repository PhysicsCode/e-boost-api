package org.physicscode.controller;

import lombok.RequiredArgsConstructor;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.dto.pojo.input.membership.MembershipTriggerDTO;
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

    @GetMapping
    public Mono<ResponseEntity<MembershipPlanHolderDTO>> retrieveMembershipPlans(@ApiIgnore @AuthenticationPrincipal EBoostAuthenticationUser user) {

        return membershipService.retrieveMembershipPlans(user.getUserId())
                .map(ResponseEntity::ok);

    }

    @PostMapping
    public Mono<ResponseEntity<MembershipPlanHolderDTO>> triggerMembershipPlans(@ApiIgnore @AuthenticationPrincipal EBoostAuthenticationUser user,
                                                                                 @RequestBody MembershipTriggerDTO membershipTriggerDTO) {

        return membershipService.triggerMembershipPlanStatus(user.getUserId(), membershipTriggerDTO.getTrigger(), membershipTriggerDTO.getMembershipId())
                .map(ResponseEntity::ok);

    }
}
