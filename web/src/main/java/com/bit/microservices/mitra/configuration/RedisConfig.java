package com.bit.microservices.mitra.configuration;

import com.bit.microservices.configuration.CustomKeyGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.ClientOptions.DisconnectedBehavior;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import jakarta.annotation.PostConstruct;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableCaching
@EnableRedisRepositories
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

	@Value("${spring.profiles.active:default}")
	private String activeProfile;

    @Autowired
    private RedisProperties props;

    @Autowired
	private ObjectMapper mapper;

	@Value("${spring.cache.redis.key-prefix:user}")
	private String springCacheRedisKeyPrefix;

	@Value("${spring.cache.redis.use-key-prefix:true}")
	private boolean springCacheRedisUseKeyPrefix;

	private transient static CacheKeyPrefix cacheKeyPrefix;

	@PostConstruct
	private void onPostConstruct() {
	    if (springCacheRedisKeyPrefix != null) {
	        springCacheRedisKeyPrefix = springCacheRedisKeyPrefix.trim();
	    }
	    if (springCacheRedisUseKeyPrefix && springCacheRedisKeyPrefix != null
	        && !springCacheRedisKeyPrefix.isEmpty()) {
	        cacheKeyPrefix = CacheKeyPrefix.prefixed(springCacheRedisKeyPrefix);
	    } else {
	        cacheKeyPrefix = CacheKeyPrefix.simple();
	    }
	}

	@Bean("reactiveClusterConnectionFactory")
    @Primary
    ReactiveRedisConnectionFactory reactiveCacheConnectionFactory() {

	    if (props.getCluster() != null && props.getCluster().getNodes() != null && !props.getCluster().getNodes().isEmpty()) {
	        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(props.getCluster().getNodes());
	        redisClusterConfiguration.setMaxRedirects(3);

	        final SocketOptions socketOptions = SocketOptions.builder()
	                .connectTimeout(Duration.ofSeconds(30))
	                .tcpNoDelay(true)
	                .keepAlive(true)
	                .build();

	        final ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions.builder()
	                .dynamicRefreshSources(false)
	                .enablePeriodicRefresh()
	                .enableAllAdaptiveRefreshTriggers()
	                .closeStaleConnections(true)
	                .build();

	        ClusterClientOptions options = ClusterClientOptions.builder()
	                .autoReconnect(true)
	                .disconnectedBehavior(DisconnectedBehavior.DEFAULT)
	                .socketOptions(socketOptions)
	                .timeoutOptions(TimeoutOptions.enabled())
	                .topologyRefreshOptions(refreshOptions)
	                .build();

	        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
	                .commandTimeout(Duration.ofSeconds(30)).clientOptions(options).build();

	        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
	    } else {
	    	RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(props.getHost(), props.getPort());

            return new LettuceConnectionFactory(redisStandaloneConfiguration);
	    }

    }

    @Bean("defaultClusterConnectionFactory")
    RedisConnectionFactory elastiCacheConnectionFactory() {

        if (props.getCluster() != null && props.getCluster().getNodes() != null && !props.getCluster().getNodes().isEmpty()) {
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(props.getCluster().getNodes());
            redisClusterConfiguration.setMaxRedirects(3);

            final SocketOptions socketOptions = SocketOptions.builder()
                    .connectTimeout(Duration.ofSeconds(30))
                    .tcpNoDelay(true)
                    .keepAlive(true)
                    .build();

            final ClusterTopologyRefreshOptions refreshOptions = ClusterTopologyRefreshOptions.builder()
                    .dynamicRefreshSources(false)
                    .enablePeriodicRefresh()
                    .enableAllAdaptiveRefreshTriggers()
                    .closeStaleConnections(true)
                    .build();

            ClusterClientOptions options = ClusterClientOptions.builder()
                    .autoReconnect(true)
                    .disconnectedBehavior(DisconnectedBehavior.DEFAULT)
                    .socketOptions(socketOptions)
                    .timeoutOptions(TimeoutOptions.enabled())
                    .topologyRefreshOptions(refreshOptions)
                    .build();

            LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                    .commandTimeout(Duration.ofSeconds(30)).clientOptions(options).build();

            return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
        } else {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(props.getHost(), props.getPort());

            return new LettuceConnectionFactory(redisStandaloneConfiguration);
        }
    }

    @Bean("reactiveRedisTemplate")
    ReactiveRedisTemplate<String, Object> reactiveRedisTemplate(@Qualifier("reactiveClusterConnectionFactory") ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder = RedisSerializationContext.newSerializationContext(RedisSerializer.string());

        RedisSerializationContext<String, Object> context = builder.value(RedisSerializer.java()).build();

        return new ReactiveRedisTemplate<>(connectionFactory, context);
    }

    @Bean("redisTemplate")
    @Primary
    RedisTemplate<String, Object> redisTemplate(@Qualifier("defaultClusterConnectionFactory") RedisConnectionFactory connectionFactory) {

    	RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new Jackson2JsonRedisSerializer<String>(this.mapper, String.class));
		template.setValueSerializer(new Jackson2JsonRedisSerializer<String>(this.mapper, String.class));

		template.afterPropertiesSet();


        return template;
    }

	@Bean("eventRedisOperations")
	RedisOperations<String, String> eventRedisOperations(@Qualifier("defaultClusterConnectionFactory") RedisConnectionFactory connectionFactory) {

		final RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new Jackson2JsonRedisSerializer<String>(this.mapper, String.class));
		template.setValueSerializer(new Jackson2JsonRedisSerializer<String>(this.mapper, String.class));

		template.afterPropertiesSet();

		return template;
	}

	@Bean
	@Primary
	ReactiveRedisOperations<String, String> reactiveEventRedisOperations(@Qualifier("reactiveClusterConnectionFactory") ReactiveRedisConnectionFactory redisConnectionFactory) {

		return new ReactiveRedisTemplate<>(redisConnectionFactory,
				RedisSerializationContext.<String, String>newSerializationContext()
						.key(new StringRedisSerializer())
						.value(new Jackson2JsonRedisSerializer<String>(mapper, String.class))
						.hashKey(new StringRedisSerializer())
						.hashValue(new Jackson2JsonRedisSerializer<String>(mapper, String.class))
						.build());
	}


	@Bean
	RedisCacheConfiguration cacheConfiguration() {
		return createCacheConfiguration(props.getDefaultExpire());
	}

	@Bean
	CacheManager cacheManager(@Qualifier("defaultClusterConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();


		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.java()))
                .entryTtl(Duration.ofSeconds(7200));

		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(config)
				.withInitialCacheConfigurations(cacheConfigurations).build();
	}

	@Bean("redissonCacheManager")//(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    CacheManager redissonCacheManager(RedissonClient redissonClient) throws Exception {
        Map<String, CacheConfig> config = new HashMap<>();
        config.put("bit-user-l2", new CacheConfig(30000, 60000)); //2min TTL and 3min max idle time

		RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient);
        cacheManager.setTransactionAware(true);
        cacheManager.setCacheNames(List.of("bit-user-l2"));
        cacheManager.setConfig(config);

        cacheManager.afterPropertiesSet();

		return cacheManager;
    }
	
	@Bean//(destroyMethod = "shutdown")
	@ConditionalOnMissingBean
	RedissonClient redissonClient() throws Exception{
		Config config = Config.fromYAML(new ClassPathResource("/redisson-production.yml").getInputStream());
	    if (!activeProfile.equalsIgnoreCase("aws")) {
//	        config = Config.fromYAML(new ClassPathResource("/redisson-lsit.yml").getInputStream());
	        config = Config.fromYAML(new ClassPathResource("/redisson-"+activeProfile+".yml").getInputStream());
	    }

	    config.setCodec(new SerializationCodec());
//	    config.setCodec(new TypedJsonJacksonCodec(null, String.class));
	    config.setTransportMode(TransportMode.NIO);
	    config.setAddressResolverGroupFactory(new CustomDnsAddressResolverGroupFactory());

        return Redisson.create(config);
    }
	
	@Bean("customKeyGenerator")
	KeyGenerator keyGenerator() {
		return new CustomKeyGenerator();
	}

	private void configureSerializers(RedisTemplate<String, Object> redisTemplate) {
		 redisTemplate.setValueSerializer(RedisSerializer.string());
		 redisTemplate.setHashValueSerializer(RedisSerializer.string());
		redisTemplate.setValueSerializer(RedisSerializer.java());
		redisTemplate.setHashValueSerializer(RedisSerializer.java());
	}

	// // === this is for cacheable use ehcache (cache annotations) ===
	private RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
		return RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(SerializationPair.fromSerializer(RedisSerializer.string()))
                .serializeValuesWith(SerializationPair.fromSerializer(RedisSerializer.java()))
				.entryTtl(Duration.ofSeconds(timeoutInSeconds));
	}
}
