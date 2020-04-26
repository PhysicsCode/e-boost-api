package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.domain.entity.GalleryPictureHolder;
import org.physicscode.domain.repository.FreelancerRepository;
import org.physicscode.dto.mapper.GalleryMapper;
import org.physicscode.dto.pojo.output.GalleryDTO;
import org.physicscode.dto.pojo.provider.response.ImageBucketResponseDTO;
import org.physicscode.exception.ErrorCode;
import org.physicscode.exception.ServiceException;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GalleryService {

    private final FreelancerRepository freelancerRepository;
    private final ImageService imageService;
    private final GalleryMapper galleryMapper;

    public Mono<GalleryDTO> addImageToGallery(String userId, Mono<FilePart> picture) {

        return freelancerRepository.findByUserId(userId)
                .flatMap(user -> {
                    if (user.getGallery().getGalleryPictures().size() > 8) {
                        return Mono.error(new ServiceException(ErrorCode.MAXIMUM_GALLERY_SIZE_REACHED));
                    }
                    return Mono.zip(Mono.just(user), picture.flatMap(imageService::uploadImage));
                }).flatMap(tuple -> {
                    GalleryPictureHolder galleryPictureHolder = createGalleryPictureHolder(tuple.getT2(), tuple.getT1().getGallery().getGalleryPictures().size() + 1);
                    tuple.getT1().getGallery().getGalleryPictures().add(galleryPictureHolder);

                    return freelancerRepository.save(tuple.getT1());
                }).map(freelancerUser -> galleryMapper.mapToGalleryDTO(freelancerUser.getGallery()));
    }

    public Mono<GalleryDTO> retrieveGallery(String userId) {

        return freelancerRepository.findByUserId(userId)
                .map(freelancerUser -> galleryMapper.mapToGalleryDTO(freelancerUser.getGallery()));
    }

    private GalleryPictureHolder createGalleryPictureHolder(ImageBucketResponseDTO imageHolder, int number) {

        GalleryPictureHolder galleryPictureHolder = new GalleryPictureHolder();
        galleryPictureHolder.setImageId(number);
        galleryPictureHolder.setDeleteUrl(imageHolder.getDeleteUrl());
        galleryPictureHolder.setImageUrl(imageHolder.getImageUrl());
        galleryPictureHolder.setThumbUrl(imageHolder.getThumbImageUrl());
        galleryPictureHolder.setMediumUrl(imageHolder.getMediumImageUrl());

        return galleryPictureHolder;
    }
}


