package kg.devcats.server.config;



import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "image-store")
public record ImageStorageConfig(String path) {}

