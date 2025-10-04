package com.simonjamesrowe.model.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    public static com.fasterxml.jackson.databind.Module dateTimeModule() {
        SimpleModule bean = new SimpleModule();
        
        bean.addDeserializer(ZonedDateTime.class, new JsonDeserializer<ZonedDateTime>() {
            @Override
            public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) 
                throws IOException, JsonProcessingException {
                return ZonedDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            }
        });
        
        bean.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) 
                throws IOException, JsonProcessingException {
                return LocalDateTime.parse(jsonParser.getValueAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            }
        });
        
        bean.addSerializer(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
            @Override
            public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator,
                    SerializerProvider serializerProvider) 
                throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(zonedDateTime));
            }
        });
        
        bean.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator,
                    SerializerProvider serializerProvider) 
                throws IOException {
                jsonGenerator.writeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime));
            }
        });
        
        return bean;
    }

    @Bean
    public com.fasterxml.jackson.databind.Module jsonMapperJava8DateTimeModule() {
        return dateTimeModule();
    }
}