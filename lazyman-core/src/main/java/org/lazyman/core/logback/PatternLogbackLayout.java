package org.lazyman.core.logback;

import ch.qos.logback.classic.PatternLayout;

public class PatternLogbackLayout extends PatternLayout {
    static {
        defaultConverterMap.put("traceId", TraceIdPatternConverter.class.getName());
    }
}
