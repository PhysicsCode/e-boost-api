package org.physicscode.service.client;

import org.physicscode.dto.pojo.provider.response.ImageBucketResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "assetstore-client",
        url = "${assetstore.client.url}"
)
public interface AssetStoreClient {

    @PostMapping(path = "/1/upload")
    Mono<ImageBucketResponseDTO> uploadImage(@RequestParam("key") String key, @RequestParam("image") MultipartFile image);
}
