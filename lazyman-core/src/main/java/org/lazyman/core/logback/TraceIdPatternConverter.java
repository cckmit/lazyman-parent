package org.lazyman.core.logback;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import cn.hutool.core.convert.Convert;
import org.lazyman.common.util.ThreadLocalUtils;

public class TraceIdPatternConverter extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent loggingEvent) {
        return Convert.toStr(ThreadLocalUtils.getTraceId());
    }
}
