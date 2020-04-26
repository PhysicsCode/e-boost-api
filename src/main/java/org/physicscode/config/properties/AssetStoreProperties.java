package org.physicscode.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "assetstore.client")
public class AssetStoreProperties {

    private String url;
    private String key;
}
