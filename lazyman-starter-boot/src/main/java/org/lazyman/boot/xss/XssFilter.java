package org.lazyman.boot.xss;

import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.util.ThreadLocalUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        XssHttpServletRequestWrapper xssRequestWrapper = new XssHttpServletRequestWrapper(req);
        ThreadLocalUtils.setRequestBody(xssRequestWrapper.getRequestBody());
        chain.doFilter(xssRequestWrapper, response);
    }

    @Override
    public void destroy() {
    }
}
