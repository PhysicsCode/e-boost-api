package org.physicscode.service;

import lombok.RequiredArgsConstructor;
import org.physicscode.dto.pojo.provider.response.ImageBucketResponseDTO;
import org.physicscode.service.client.AssetStoreClient;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final AssetStoreClient assetStoreClient;
    private final WebClient webClient= WebClient.builder().build();

    public Mono<ImageBucketResponseDTO> uploadImage(FilePart picture) {

        return assetStoreClient.uploadImage(picture);
    }

    public void deleteImage(String url) {

        if (url == null) {
            return;
        }

        webClient.post()
                .uri(url)
                .retrieve()
                .toBodilessEntity()
                .subscribe();
    }
}
