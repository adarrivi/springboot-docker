package io.adarrivi.springboot.ws.request;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.IOException;
import java.time.LocalDate;

public class JsonDateDeserializer extends JsonDeserializer<LocalDate> {


    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        TextNode node = oc.readTree(jsonParser);
        return LocalDate.parse(node.textValue(), JsonDateSerializer.YYYY_MM_DD_HHMMSS);
    }
}