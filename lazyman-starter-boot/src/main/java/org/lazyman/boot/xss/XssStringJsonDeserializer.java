package org.lazyman.boot.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

public class XssStringJsonDeserializer extends JsonDeserializer<String> {
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String source = jsonParser.getText().trim();
        return StringEscapeUtils.escapeHtml4(source);
    }
}
