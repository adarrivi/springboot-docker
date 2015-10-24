package io.adarrivi.springboot.ws.request;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonDateSerializer extends JsonSerializer<LocalDate> {

    static final DateTimeFormatter YYYY_MM_DD_HHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS");

    @Override
    public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider provider) throws IOException {
        String dateString = date.format(YYYY_MM_DD_HHMMSS);
        generator.writeString(dateString);
    }
}
