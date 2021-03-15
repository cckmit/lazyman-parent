package org.lazyman.boot.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

public class XssStringJsonSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (!StringUtils.isEmpty(s)) {
            jsonGenerator.writeString(StringEscapeUtils.escapeHtml4(s));
        }
    }
}
