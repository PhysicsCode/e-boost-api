package org.physicscode.dto.pojo.provider.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ImageBucketResponseDTO {

    private String imageUrl;
    private String thumbImageUrl;
    private String mediumImageUrl;
    private String deleteUrl;

    @SuppressWarnings("unchecked")
    @JsonProperty("data")
    private void unpackNameFromNestedObject(Map<String, Object> data) {

        deleteUrl = (String) data.get("delete_url");
        imageUrl = (String) data.get("url");
        /*Map<String,String> image = (Map<String,String>) data.get("image");

        Map<String,String> medium = (Map<String,String>) data.get("medium");
        mediumImageUrl = medium.get("url");
        Map<String,String> thumb = (Map<String,String>) data.get("thumb");
        thumbImageUrl = thumb.get("url");
        *
         */
    }
}
