package com.bit.microservices.mitra.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

@Component
public class ParserFactory {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(com.bit.microservices.utils.ParserFactory.class);
    @Autowired
    private ObjectMapper mapper;

    public ParserFactory() {
    }

    public Object getParsedValue(Object value, Class clazz, String entityType) throws Exception {
        try {
            String className = clazz.getName();
            Object valueParsed;
            if (!Objects.isNull(entityType) && entityType.toLowerCase().equals("date")) {
                OffsetDateTime temp = OffsetDateTime.parse(value.toString(), DateTimeFormatter.ISO_OFFSET_DATE_TIME).withOffsetSameInstant(ZoneOffset.UTC);
                valueParsed = temp.toLocalDateTime();
            } else {
                valueParsed = this.mapper.convertValue(value, clazz);
            }

            return valueParsed;
        } catch (Exception var7) {

            throw var7;
        }
    }

    public Class getClassConvertion(String type) {
        Class clazz = null;
        switch (type) {
            case "number":
                clazz = BigDecimal.class;
                break;
            case "date":
                clazz = Date.class;
                break;
            case "string":
                clazz = String.class;
                break;
            case "boolean":
                clazz = Boolean.class;
        }

        return clazz;
    }
}
