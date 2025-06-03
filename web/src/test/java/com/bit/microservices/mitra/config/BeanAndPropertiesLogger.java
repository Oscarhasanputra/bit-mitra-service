package com.bit.microservices.mitra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import java.util.stream.StreamSupport;


@Component
@Profile("test")
public class BeanAndPropertiesLogger {

  private static final Logger logger = LoggerFactory.getLogger(BeanAndPropertiesLogger.class);

  @Bean
  public ApplicationRunner logProperties(Environment env) {
    return args -> {
      if (env instanceof ConfigurableEnvironment configurableEnv) {
        logger.info("### Loaded Application Properties ###");

        StreamSupport.stream(configurableEnv.getPropertySources().spliterator(), false)
            .filter(propertySource -> propertySource.getSource() instanceof java.util.Map)
            .forEach(propertySource -> {
              logger.info("Property Source: {}", propertySource.getName());
              ((java.util.Map<?, ?>) propertySource.getSource()).forEach((key, value) -> {
                if (value instanceof Supplier<?>) {
                  this.handleSupplier((Supplier<?>) value, key);
                }else {
                  logger.info("{} = {}", key, value);
                }
              });
            });

        logger.info("### System Properties ###");
        System.getProperties().forEach((key, value) -> logger.info("{} = {}", key, value));
      }
    };
  }

  public <T> void handleSupplier(Supplier<T> supplier, Object key) {
    if (supplier != null) {
      T value = supplier.get();  // Gets the value from the Supplier
      // Process or log the value
      logger.info("{} = {}", key, value);
    }
  }
}