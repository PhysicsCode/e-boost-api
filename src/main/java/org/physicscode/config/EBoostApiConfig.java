package org.physicscode.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.Clock;
import java.time.ZoneOffset;
import java.util.TimeZone;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@Configuration
public class EBoostApiConfig implements Jackson2ObjectMapperBuilderCustomizer {

    @Bean
    public Clock clock(){
        return Clock.systemUTC();
    }

    @Bean
    public String mongoDBUri(@Value("${spring.data.mongodb.uri}") String uri) {

        System.out.println(uri);
        return uri;
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        builder.propertyNamingStrategy(SNAKE_CASE);
        builder.serializationInclusion(NON_EMPTY);
        builder.timeZone(TimeZone.getTimeZone(ZoneOffset.UTC));
        builder.findModulesViaServiceLoader(true);
    }
}
