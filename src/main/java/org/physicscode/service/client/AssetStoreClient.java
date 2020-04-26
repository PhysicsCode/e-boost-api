package org.physicscode.service.client;

import org.physicscode.config.properties.AssetStoreProperties;
import org.physicscode.dto.pojo.provider.response.ImageBucketResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component

public class AssetStoreClient {

    private final AssetStoreProperties assetStoreProperties;
    private final WebClient webClient;

    public AssetStoreClient (AssetStoreProperties assetStoreProperties) {
        this.assetStoreProperties = assetStoreProperties;
        this.webClient = WebClient.builder().build();
    }

    @PostMapping(path = "/1/upload")
    public  Mono<ImageBucketResponseDTO> uploadImage(FilePart image) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("image", image);

        return webClient.post()
                .uri(assetStoreProperties.getUrl()+ "/1/upload?key=" + assetStoreProperties.getKey())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(ImageBucketResponseDTO.class);

    }
}
