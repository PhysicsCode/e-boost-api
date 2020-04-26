package org.physicscode.controller;

import lombok.RequiredArgsConstructor;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.dto.pojo.output.membership.MembershipPlanHolderDTO;
import org.physicscode.service.MembershipService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
