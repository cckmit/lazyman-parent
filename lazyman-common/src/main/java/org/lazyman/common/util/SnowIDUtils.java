package org.lazyman.common.util;


import java.net.InetAddress;

import static java.lang.Math.abs;

public class SnowIDUtils {
    private static final long TWEPOCH = 1288834974657L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATA_CENTER_ID_BITS = 5L;
    private static final long MAX_WORKER_ID = -1L ^ (-1L << (int) WORKER_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = -1L ^ (-1L << (int) DATA_CENTER_ID_BITS);
    private static final long SEQUENCE_BITS = 4L;

    private long workerId;
    private long dataCenterId;
    private long sequence = 0L;
    private long workerIdShift = SEQUENCE_BITS;
    private long dataCenterIdShift = SEQUENCE_BITS + WORKER_ID_BITS;
    private long timestampLeftShift = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    private long sequenceMask = -1L ^ (-1L << (int) SEQUENCE_BITS);
    private long lastTimestamp = -1L;

    private static volatile SnowIDUtils snowIdUtils = null;

    private SnowIDUtils(final long workerId) {
        this(workerId, 0);
    }

    private SnowIDUtils(long workerId, long dataCenterId) {
        this.workerId = abs(workerId % MAX_WORKER_ID);
        this.dataCenterId = abs(dataCenterId % MAX_DATA_CENTER_ID);
    }

    public static SnowIDUtils getInstance() {
        Integer hostHashCode = 0;
        try {
            if (InetAddress.getLocalHost().getHostAddress().hashCode() != 0) {
                hostHashCode = abs(InetAddress.getLocalHost().getHostAddress().hashCode());
            }
        } catch (Exception e) {
        }
        return SnowIDUtils.getInstance(hostHashCode);
    }

    public static SnowIDUtils getInstance(final long workerId) {
        if (snowIdUtils == null) {
            synchronized (SnowIDUtils.class) {
                if (snowIdUtils == null) {
                    snowIdUtils = new SnowIDUtils(workerId);
                }
            }
            return snowIdUtils;
        } else {
            return snowIdUtils;
        }
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    public synchronized Long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return ((timestamp - TWEPOCH) << (int) timestampLeftShift) | (dataCenterId << (int) dataCenterIdShift) | (workerId << (int) workerIdShift) | sequence;
    }
}
