package org.lazyman.common.util;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.beust.jcommander.internal.Maps;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.CommonConstant;
import org.lazyman.common.constant.StringPool;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class WebUtils {
    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNull(requestAttributes)) {
            return null;
        }
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    public static HttpServletResponse getHttpResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (ObjectUtil.isNull(requestAttributes)) {
            return null;
        }
        HttpServletResponse response = requestAttributes.getResponse();
        return response;
    }

    public static Map<String, String> getHttpHeadersMap() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return Maps.newHashMap();
        }
        Map<String, String> headerMaps = new HashMap<>();
        for (Enumeration<String> enu = request.getHeaderNames(); enu.hasMoreElements(); ) {
            String name = enu.nextElement();
            headerMaps.put(name, request.getHeader(name));
        }
        return headerMaps;
    }

    public static String getRemoteIpAddr() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (StrUtil.isEmpty(ip) || StringPool.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ip) || StringPool.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isEmpty(ip) || StringPool.UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StrUtil.isEmpty(ip) || StringPool.UNKNOWN.equalsIgnoreCase(ip) || ip.startsWith("10.")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static boolean isAjaxRequest() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return false;
        }
        String xRequestedWith = request.getHeader("x-requested-with");
        return StrUtil.isNotEmpty(xRequestedWith) && CommonConstant.XML_HTTP_REQUEST.equalsIgnoreCase(xRequestedWith);
    }

    public static String getAccessToken() {
        HttpServletRequest request = WebUtils.getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (StrUtil.isEmpty(token)) {
            token = request.getParameter(CommonConstant.TOKEN);
        }
        return token;
    }

    public static String getClientRefererHost() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        String referer = request.getHeader(CommonConstant.REFERER);
        if (StrUtil.isEmpty(referer)) {
            return null;
        }
        try {
            URL url = new URL(referer);
            return url.getHost();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getClientOS() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        String userAgent = request.getHeader(CommonConstant.USER_AGENT);
        if (StrUtil.isBlank(userAgent)) {
            return null;
        }
        String os = null;
        if (userAgent.toLowerCase().indexOf("windows") >= 0) {
            os = "Windows";
        } else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
            os = "Mac";
        } else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
            os = "Unix";
        } else if (userAgent.toLowerCase().indexOf("android") >= 0) {
            os = "Android";
        } else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
            os = "IPhone";
        } else {
            os = "Unknown, more info: " + userAgent;
        }
        return os;
    }

    public static String getClientBrowser() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        String userAgent = request.getHeader(CommonConstant.USER_AGENT);
        if (StrUtil.isBlank(userAgent)) {
            return null;
        }
        String lowerCaseUserAgent = userAgent.toLowerCase();
        String browser = null;
        if (lowerCaseUserAgent.contains("edge")) {
            browser = (userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
        } else if (lowerCaseUserAgent.contains("msie")) {
            String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (lowerCaseUserAgent.contains("safari") && lowerCaseUserAgent.contains("version")) {
            browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                    + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (lowerCaseUserAgent.contains("opr") || lowerCaseUserAgent.contains("opera")) {
            if (lowerCaseUserAgent.contains("opera")) {
                browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                        + "-" + (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            } else if (lowerCaseUserAgent.contains("opr")) {
                browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                        .replace("OPR", "Opera");
            }

        } else if (lowerCaseUserAgent.contains("chrome")) {
            browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((lowerCaseUserAgent.indexOf("mozilla/7.0") > -1) || (lowerCaseUserAgent.indexOf("netscape6") != -1) ||
                (lowerCaseUserAgent.indexOf("mozilla/4.7") != -1) || (lowerCaseUserAgent.indexOf("mozilla/4.78") != -1) ||
                (lowerCaseUserAgent.indexOf("mozilla/4.08") != -1) || (lowerCaseUserAgent.indexOf("mozilla/3") != -1)) {
            browser = "Netscape-?";

        } else if (lowerCaseUserAgent.contains("firefox")) {
            browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (lowerCaseUserAgent.contains("rv")) {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser = "IE" + IEVersion.substring(0, IEVersion.length() - 1);
        } else {
            browser = "Unknown, more info: " + userAgent;
        }
        return browser;
    }

    public static String getClientFrom() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        return request.getHeader(CommonConstant.X_CLIENT_FROM);
    }

    public static Long getAppTenantId() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        return Convert.toLong(request.getHeader(CommonConstant.X_TENANT_ID));
    }

    public static Long getAppPackageId() {
        HttpServletRequest request = getHttpRequest();
        if (ObjectUtil.isNull(request)) {
            return null;
        }
        return Convert.toLong(request.getHeader(CommonConstant.X_PACKAGE_ID));
    }
}
