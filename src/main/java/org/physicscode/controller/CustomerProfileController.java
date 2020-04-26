package org.physicscode.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.physicscode.constants.PicType;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.dto.pojo.output.CustomerUserProfileDTO;
import org.physicscode.dto.pojo.output.FreelancerUserProfileDTO;
import org.physicscode.dto.pojo.output.error.ErrorDTO;
import org.physicscode.service.ProfileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/profile")
public class CustomerProfileController {

    private final ProfileService profileService;

    @ApiOperation(value = "Retrieves profile for customers",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = CustomerUserProfileDTO.class, message = "Customer Profile retrieved successfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @GetMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CustomerUserProfileDTO>> retrieveUserProfileCustomer(@ApiIgnore @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser) {

        return profileService.retrieveCustomerProfile(eBoostAuthenticationUser.getUserId())
                .map(ResponseEntity::ok);
    }

}
