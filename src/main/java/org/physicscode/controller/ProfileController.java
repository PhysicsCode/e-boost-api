package org.physicscode.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.dto.pojo.output.CustomerUserProfileDTO;
import org.physicscode.dto.pojo.output.FreelancerUserProfileDTO;
import org.physicscode.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/profile")
public class ProfileController {

    private final ProfileService profileService;

    @ApiOperation(value = "Retrieves profile for freelancers",
            authorizations = @Authorization(value = "JWT"))
    @GetMapping(path = "/freelancer")
    public Mono<ResponseEntity<FreelancerUserProfileDTO>> retrieveUserProfileFreelance(@ApiIgnore  @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser) {

        return profileService.retrieveFreelancerProfile(eBoostAuthenticationUser.getUserId())
                .map(ResponseEntity::ok);
    }

    @ApiOperation(value = "Retrieves profile for customers",
            authorizations = @Authorization(value = "JWT"))
    @GetMapping(path = "/customer")
    public Mono<ResponseEntity<CustomerUserProfileDTO>> retrieveUserProfileCustomer(@ApiIgnore @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser) {

        return profileService.retrieveCustomerProfile(eBoostAuthenticationUser.getUserId())
                .map(ResponseEntity::ok);
    }

}
