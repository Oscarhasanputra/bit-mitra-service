package com.bit.microservices.mitra;

import com.bit.microservices.configuration.BaseEventListener;
import com.bit.microservices.configuration.CustomKeyGenerator;
import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.redis.impl.RedisAuthenticationRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.hypersistence.utils.spring.repository.BaseJpaRepositoryImpl;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.EnableRetry;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

@Import({
        LoadBalancerAutoConfiguration.class,
        BaseEventListener.class,
        CustomKeyGenerator.class,
        RedisAuthenticationRepository.class
})
@SpringBootApplication(exclude={ReactiveSecurityAutoConfiguration.class})

@EnableJpaRepositories(
        basePackages= {"com.bit.microservices.mitra.model.entity", "com.bit.microservices.mitra.repository"},
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class,
        repositoryBaseClass = BaseJpaRepositoryImpl.class)
@EnableJpaAuditing
@EnableRetry
@Slf4j
@EnableDiscoveryClient
@RefreshScope
public class Application {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    protected static final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHH");
    private static final String ECS_METADATA_URI_V4 = System.getenv("ECS_CONTAINER_METADATA_URI_V4");

    private static final String APP_VERSION = System.getenv("APP_VERSION");

    @Value("${spring.application.name:bit-mitra-service}")
    private String applicationName;

    @Value("${server.port:7006}")
    private int port;

    @Value("${spring.profiles.active:test}")
    private String activeProfile;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init(){
        Locale.setDefault(Locale.ENGLISH);
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Bean
    EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) throws JsonProcessingException {

        String version = LocalDateTime.now().format(format);
        if (APP_VERSION != null && !APP_VERSION.isBlank()) {
            StringTokenizer appVersionTokenizer = new StringTokenizer(APP_VERSION, ".");
            List<String> appVersions = new ArrayList<>();
            while (appVersionTokenizer.hasMoreElements()) {
                appVersions.add(appVersionTokenizer.nextToken());
            }

            if (!appVersions.isEmpty()) {
                version = String.join(".", appVersions.get(2), version);
                if (appVersions.size() > 3) {
                    version = String.join(".", appVersions.get(2), appVersions.get(3));
                }
            }
        }

        EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);

        config.setPreferIpAddress(true);
        config.setNonSecurePort(port);
        config.setAppname(applicationName);
        config.setIpAddress(config.getHostname());
        config.getMetadataMap().put("instanceId", config.getHostname()+":"+applicationName+":"+version+":"+port);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter();
        mapper.findAndRegisterModules();

        JavaTimeModule module = new JavaTimeModule();
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);

        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        module.addSerializer(LocalDateTime.class, localDateTimeSerializer);

        mapper.registerModule(module);

        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        String metadata = readEcsMetadata();

        if (metadata != null && !metadata.isBlank()) {

            log.info("{}{}", LINE_SEPARATOR, metadata);

            JsonNode metadatax = mapper.readTree(metadata);

            ArrayNode containers = (ArrayNode) metadatax.get("Containers");

            Iterator<JsonNode> containerx = containers.elements();

            JsonNode container = Stream.generate(() -> null)
                    .takeWhile(x -> containerx.hasNext())
                    .map(n -> containerx.next())
                    .filter(x-> {
                        String[] names = applicationName.split("-");
                        String containerName = names[0];
                        for (int i = 1; i < names.length-1; i++) {
                            containerName = String.join("-", containerName, names[i]);
                        }
                        if (activeProfile != null && !activeProfile.isBlank() && !activeProfile.equals("aws")) {
                            containerName = String.join("-", containerName, activeProfile);
                        }

                        return x.get("Name").asText().replace("[^a-zA-Z]", "").equalsIgnoreCase(containerName.replace("[^a-zA-Z]", ""));
                    })
                    .findFirst()
                    .orElse(null);

            if (container != null) {
                ArrayNode networks = (ArrayNode) container.get("Networks");

                Iterator<JsonNode> networkx = networks.elements();
                if (networkx.hasNext()) {
                    JsonNode network = networkx.next();

                    ArrayNode ips = (ArrayNode) network.get("IPv4Addresses");
                    Iterator<JsonNode> ipx = ips.elements();

                    if (ipx.hasNext()) {
                        JsonNode ipz = ipx.next();
                        String ipAddress = ipz.asText();

                        config.setIpAddress(ipAddress);
                        config.getMetadataMap().put("instanceId", ipAddress+":"+applicationName+":"+version+":"+port);
                    }
                }
            }
        }

        return config;

    }

    private String readEcsMetadata(){

        if (ECS_METADATA_URI_V4 != null) {
            URI uri = URI.create(String.join("/", ECS_METADATA_URI_V4,"task"));

            HttpRequest.Builder request = HttpRequest.newBuilder(uri)
                    .version(HttpClient.Version.HTTP_2)
                    .timeout(Duration.ofMinutes(5))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.ACCEPT, "*/*");

            request.GET();

            HttpClient.Builder client = HttpClient.newBuilder();

            try {

                return client.build().send(request.build(), HttpResponse.BodyHandlers.ofString()).body();

            } catch (IOException | InterruptedException e) {
                ExceptionPrinter print = new ExceptionPrinter(e);
                log.error(print.getMessage());
            }
        }

        return null;
    }
}