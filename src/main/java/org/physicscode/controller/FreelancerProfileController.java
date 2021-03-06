package org.physicscode.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.physicscode.constants.PicType;
import org.physicscode.domain.auth.EBoostAuthenticationUser;
import org.physicscode.dto.pojo.output.profile.FreelancerUserProfileDTO;
import org.physicscode.dto.pojo.output.profile.GalleryDTO;
import org.physicscode.dto.pojo.output.error.ErrorDTO;
import org.physicscode.service.GalleryService;
import org.physicscode.service.ProfileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/profile/freelancer")
public class FreelancerProfileController {

    private final ProfileService profileService;
    private final GalleryService galleryService;

    @ApiOperation(value = "Retrieves profile for freelancers",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = FreelancerUserProfileDTO.class, message = "Freelancer profile retrieved successfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<FreelancerUserProfileDTO>> retrieveUserProfileFreelance(@ApiIgnore @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser) {

        return profileService.retrieveFreelancerProfile(eBoostAuthenticationUser.getUserId())
                .map(ResponseEntity::ok);
    }

    @ApiOperation(value = "Updates profile for freelancers",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = FreelancerUserProfileDTO.class, message = "Freelancer profile updated successfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<FreelancerUserProfileDTO>> editFreelancerProfile(@ApiIgnore  @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser,
                                                                                @RequestBody FreelancerUserProfileDTO freelancerUserProfileDTO) {

        return profileService.editFreelancerProfile(eBoostAuthenticationUser.getUserId(), freelancerUserProfileDTO)
                .map(ResponseEntity::ok);
    }

    @ApiOperation(value = "Updates profile pics for freelancers",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = FreelancerUserProfileDTO.class, message = "Freelancer profile updated successfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @PutMapping(path = "/pic/{pic_type}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<FreelancerUserProfileDTO>> editFreelancerProfile(@ApiIgnore  @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser,
                                                                                @PathVariable("pic_type") String picType,
                                                                                @RequestPart("image") Mono<FilePart> picture) {

        return profileService.editProfileImage(eBoostAuthenticationUser.getUserId(), picture, PicType.reverseLookupPicType(picType))
                .map(ResponseEntity::ok);
    }

    @ApiOperation(value = "Put a new image over gallery pics for freelancers",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = GalleryDTO.class, message = "New picture added succesfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @PostMapping(path = "/gallery", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<GalleryDTO>> addImagesToGallery(@ApiIgnore  @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser,
                                                                                @RequestPart("image") Mono<FilePart> picture) {

        return galleryService.addImageToGallery(eBoostAuthenticationUser.getUserId(), picture)
                .map(ResponseEntity::ok);
    }

    @ApiOperation(value = "Retrieves gallery for a user",
            authorizations = @Authorization(value = "JWT"))
    @ApiResponses({
            @ApiResponse(code = 200, response = GalleryDTO.class, message = "Gallery retrieved successfully"),
            @ApiResponse(code = 204, message = "No data found - Shouldn't happen"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 400, response = ErrorDTO.class, message = "Client error"),
            @ApiResponse(code = 500, response = ErrorDTO.class, message = "Unexpected server side error")
    })
    @GetMapping(path = "/gallery", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<GalleryDTO>> retrieveGalleryImages(@ApiIgnore  @AuthenticationPrincipal EBoostAuthenticationUser eBoostAuthenticationUser) {

        return galleryService.retrieveGallery(eBoostAuthenticationUser.getUserId())
                .map(ResponseEntity::ok);
    }
}
