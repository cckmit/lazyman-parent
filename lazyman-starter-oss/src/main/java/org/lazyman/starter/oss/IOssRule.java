package org.lazyman.starter.oss;

public interface IOssRule {
    String bucketName(String bucketName);

    String fileName(String originalFilename);
}
