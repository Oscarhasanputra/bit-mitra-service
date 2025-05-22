package com.bit.microservices.mitra.configuration;

import com.bit.microservices.utils.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.core.jackson.ModelResolver;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Configuration
public class JacksonConfig {

	@Bean
	@Primary
	ObjectMapper objectMapper() {
		final ObjectMapper mapper = new ObjectMapper();
		
		mapper.writerWithDefaultPrettyPrinter();
		mapper.findAndRegisterModules();
		
		mapper.registerModule(pageJacksonModule());
        mapper.registerModule(sortJacksonModule());
        mapper.registerModule(jdk8JacksonModule());
        mapper.registerModule(javaTimeJacksonModule());
		
		mapper.setSerializationInclusion(Include.ALWAYS);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //harus ada ini biar bisa Pageable di feign

		return mapper;
	}


	@Bean
	@Primary
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
	    return new ModelResolver(objectMapper);
	}
	
	@Bean
    public PageJacksonModule pageJacksonModule() {
         return new PageJacksonModule();
    }
	
	@Bean
    public SortJacksonModule sortJacksonModule() {
         return new SortJacksonModule();
    }
	
	@Bean
    public Jdk8Module jdk8JacksonModule() {
         return new Jdk8Module();
    }
	
	@Bean
    public JavaTimeModule javaTimeJacksonModule() {
		JavaTimeModule module = new JavaTimeModule();
		
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
		
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		
		module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
		module.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer());
		
		return module;
    }
}
