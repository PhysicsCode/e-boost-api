package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.constants.PicType;
import org.physicscode.domain.entity.FreelancerUser;
import org.physicscode.domain.entity.ImageHolder;
import org.physicscode.domain.repository.CustomerRepository;
import org.physicscode.domain.repository.FreelancerRepository;
import org.physicscode.dto.mapper.ProfileMapper;
import org.physicscode.dto.pojo.output.CustomerUserProfileDTO;
import org.physicscode.dto.pojo.output.FreelancerUserProfileDTO;
import org.physicscode.dto.pojo.provider.response.ImageBucketResponseDTO;
import org.physicscode.exception.ErrorCode;
import org.physicscode.exception.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final CustomerRepository customerRepository;
    private final FreelancerRepository freelancerRepository;
    private final ImageService imageService;
    private final ProfileMapper profileMapper;

    public Mono<FreelancerUserProfileDTO> retrieveFreelancerProfile(String userId) {

        return freelancerRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new ServiceException(ErrorCode.EMPTY_INFORMATION_FOR_USER)))
                .map(profileMapper::mapToFreelancerProfileDTO);
    }

    public Mono<CustomerUserProfileDTO> retrieveCustomerProfile(String userId) {

        return customerRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new ServiceException(ErrorCode.EMPTY_INFORMATION_FOR_USER)))
                .map(profileMapper::mapToCustomerProfile);
    }

    public Mono<FreelancerUserProfileDTO> editFreelancerProfile(String userId, FreelancerUserProfileDTO freelancerUserProfileDTO) {

        profileMapper.mapToFreelancerProfileEntity(freelancerUserProfileDTO);

        return freelancerRepository.findByUserId(userId)
                .switchIfEmpty(Mono.error(new ServiceException(ErrorCode.EMPTY_INFORMATION_FOR_USER)))
                .flatMap(currentEntity -> {

                    FreelancerUser tentativeEntity = profileMapper.mapToFreelancerProfileEntity(freelancerUserProfileDTO);
                    crossFields(currentEntity, tentativeEntity);

                    return freelancerRepository.save(currentEntity);
                })
                .map(profileMapper::mapToFreelancerProfileDTO);
    }

    public Mono<FreelancerUserProfileDTO> editProfileImage(String userId, MultipartFile picture, PicType picType) {

        AtomicReference<String> deletionUrl = new AtomicReference<>();

        return imageService.uploadImage(picture)
            .zipWith(freelancerRepository.findByUserId(userId))
            .flatMap(tuple -> {
                if (picType.equals(PicType.PROFILE)) {
                    deletionUrl.set(tuple.getT2().getProfilePic().getDeletionUrl());
                    setupImage(tuple.getT2().getProfilePic(), tuple.getT1());
                } else {
                    deletionUrl.set(tuple.getT2().getProfileBackPic().getDeletionUrl());
                    setupImage(tuple.getT2().getProfileBackPic(), tuple.getT1());
                }

                return freelancerRepository.save(tuple.getT2());

            }).map(profileMapper::mapToFreelancerProfileDTO)
                .doOnNext(o -> imageService.deleteImage(deletionUrl.get()));
    }

    private void setupImage(ImageHolder profileBackPic, ImageBucketResponseDTO imageHolder) {

        profileBackPic.setImageUrl(imageHolder.getImageUrl());
        profileBackPic.setMediumUrl(imageHolder.getMediumImageUrl());
        profileBackPic.setThumbUrl(imageHolder.getThumbImageUrl());
        profileBackPic.setDeletionUrl(imageHolder.getDeleteUrl());
    }


    private void crossFields(FreelancerUser currentEntity, FreelancerUser tentativeEntity) {

        Optional.ofNullable(tentativeEntity.getDescription()).ifPresent(currentEntity::setDescription);
        Optional.ofNullable(tentativeEntity.getFirstName()).ifPresent(currentEntity::setFirstName);
        Optional.ofNullable(tentativeEntity.getLastName()).ifPresent(currentEntity::setLastName);
        Optional.ofNullable(tentativeEntity.getYoutubeChannelLink()).ifPresent(currentEntity::setYoutubeChannelLink);
        Optional.ofNullable(tentativeEntity.getInstagramProfile()).ifPresent(currentEntity::setInstagramProfile);

        currentEntity.setTags(tentativeEntity.getTags());
    }

}
