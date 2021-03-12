package org.lazyman.core.xss;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private static final Charset CHARSET = Charset.forName("UTF-8");
    private HttpServletRequest request;
    private final byte[] body;
    private String requestBody;

    public XssHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.request = request;
        this.requestBody = StreamUtils.copyToString(request.getInputStream(), CHARSET);
        this.body = requestBody.getBytes(CHARSET);
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public String getParameter(String name) {
        String value = request.getParameter(name);
        if (!StrUtil.isEmpty(value)) {
            value = StringEscapeUtils.escapeHtml4(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }
        for (int i = 0; i < parameterValues.length; i++) {
            String value = parameterValues[i];
            parameterValues[i] = StringEscapeUtils.escapeHtml4(value);
        }
        return parameterValues;
    }
}
