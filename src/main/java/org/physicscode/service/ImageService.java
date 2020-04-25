package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.config.properties.AssetStoreProperties;
import org.physicscode.dto.pojo.provider.response.ImageBucketResponseDTO;
import org.physicscode.service.client.AssetStoreClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final AssetStoreClient assetStoreClient;
    private final AssetStoreProperties assetStoreProperties;
    private final WebClient webClient= WebClient.builder().build();

    public Mono<ImageBucketResponseDTO> uploadImage(MultipartFile picture) {

        return assetStoreClient.uploadImage(assetStoreProperties.getKey(), picture);
    }

    public void deleteImage(String url) {

        webClient.post()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }
}
