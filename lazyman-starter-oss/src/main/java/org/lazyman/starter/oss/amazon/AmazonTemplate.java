package org.lazyman.starter.oss.amazon;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.common.constant.StringPool;
import org.lazyman.starter.oss.IOssRule;
import org.lazyman.starter.oss.OssFile;
import org.lazyman.starter.oss.OssProperties;
import org.lazyman.starter.oss.OssTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class AmazonTemplate implements OssTemplate {
    private final IOssRule iOssRule;
    private final AmazonS3 amazonS3;
    private final OssProperties ossProperties;

    @Override
    public void makeBucket(String bucketName) {
        bucketName = iOssRule.bucketName(bucketName);
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket((bucketName));
        }
    }

    @Override
    public void removeBucket(String bucketName) {
        try {
            bucketName = iOssRule.bucketName(bucketName);
            ObjectListing objectListing = amazonS3.listObjects(bucketName);
            while (true) {
                for (Iterator<?> iterator =
                     objectListing.getObjectSummaries().iterator();
                     iterator.hasNext(); ) {
                    S3ObjectSummary summary = (S3ObjectSummary) iterator.next();
                    amazonS3.deleteObject(bucketName, summary.getKey());
                }
                if (objectListing.isTruncated()) {
                    objectListing = amazonS3.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
                VersionListing versionListing = amazonS3.listVersions(
                        new ListVersionsRequest().withBucketName(bucketName));
                while (true) {
                    for (Iterator<?> iterator =
                         versionListing.getVersionSummaries().iterator();
                         iterator.hasNext(); ) {
                        S3VersionSummary vs = (S3VersionSummary) iterator.next();
                        amazonS3.deleteVersion(
                                bucketName, vs.getKey(), vs.getVersionId());
                    }
                    if (versionListing.isTruncated()) {
                        versionListing = amazonS3.listNextBatchOfVersions(
                                versionListing);
                    } else {
                        break;
                    }
                }
                amazonS3.deleteBucket(bucketName);
            }
        } catch (Exception e) {
            log.error("删除存储桶异常", e);
        }
    }

    @Override
    public boolean bucketExists(String bucketName) {
        bucketName = iOssRule.bucketName(bucketName);
        return amazonS3.doesBucketExistV2(bucketName);
    }

    @Override
    public void copyFile(String bucketName, String fileName, String destBucketName) {
        copyFile(bucketName, fileName, destBucketName, fileName);
    }

    @Override
    public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
        bucketName = iOssRule.bucketName(bucketName);
        destBucketName = iOssRule.bucketName(destBucketName);
        amazonS3.copyObject(bucketName, fileName, destBucketName, destBucketName);
    }

    @Override
    public OssFile statFile(String fileName) {
        return statFile(ossProperties.getBucketName(), fileName);
    }

    @Override
    public OssFile statFile(String bucketName, String fileName) {
        bucketName = iOssRule.bucketName(bucketName);
        S3Object s3Object = amazonS3.getObject(bucketName, fileName);
        OssFile ossFile = new OssFile();
        ossFile.setFileName(StringUtils.isEmpty(s3Object.getKey()) ? fileName : s3Object.getKey());
        ossFile.setLink(fileLink(ossFile.getFileName()));
        ossFile.setHash(String.valueOf(s3Object.hashCode()));
        ossFile.setLength(s3Object.getObjectMetadata().getContentLength());
        ossFile.setPutTime(s3Object.getObjectMetadata().getRestoreExpirationTime());
        ossFile.setContentType(s3Object.getObjectMetadata().getContentType());
        return ossFile;
    }

    @Override
    public String filePath(String fileName) {
        return filePath(ossProperties.getBucketName(), fileName);
    }

    @Override
    public String filePath(String bucketName, String fileName) {
        return iOssRule.bucketName(bucketName).concat(StringPool.SLASH).concat(fileName);
    }

    @Override
    public String fileLink(String fileName) {
        return fileLink(ossProperties.getBucketName(), fileName);
    }

    @Override
    public String fileLink(String bucketName, String fileName) {
        bucketName = iOssRule.bucketName(bucketName);
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(DateUtil.offsetDay(DateUtil.date(), 1));
        URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        String link = url.toString();
        if (link.contains(StringPool.QUESTION_MARK)) {
            link = link.substring(0, link.indexOf(StringPool.QUESTION_MARK));
        }
        return link;
    }

    @Override
    public OssFile putFile(MultipartFile file) throws Exception {
        return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file.getInputStream(), file.getContentType());
    }

    @Override
    public OssFile putFile(String fileName, MultipartFile file) throws Exception {
        return putFile(ossProperties.getBucketName(), fileName, file.getInputStream(), file.getContentType());
    }

    @Override
    public OssFile putFile(String bucketName, String fileName, MultipartFile file) throws Exception {
        return putFile(bucketName, fileName, file.getInputStream(), file.getContentType());
    }

    @Override
    public OssFile putFile(String fileName, InputStream stream, String contentType) throws Exception {
        return putFile(ossProperties.getBucketName(), fileName, stream, contentType);
    }

    @Override
    public OssFile putFile(String bucketName, String fileName, InputStream stream, String contentType) throws Exception {
        return put(bucketName, fileName, stream, Convert.toLong(stream.available()), contentType);
    }

    private OssFile put(String bucketName, String fileName, InputStream stream, Long size, String contentType) throws Exception {
        makeBucket(bucketName);
        bucketName = iOssRule.bucketName(bucketName);
        String originalName = fileName;
        fileName = iOssRule.fileName(fileName);
        byte[] bytes = IOUtils.toByteArray(stream);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        objectMetadata.setContentType(contentType);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        // 上传
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, byteArrayInputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult putObjectResult = amazonS3.putObject(putObjectRequest);
        OssFile file = new OssFile();
        if (ObjectUtil.isNotNull(putObjectResult)) {
            file.setHash(putObjectResult.getContentMd5());
        }
        file.setOriginalName(originalName);
        file.setFileName(fileName);
        file.setDomain(ossProperties.getEndpoint());
        file.setLink(fileLink(bucketName, fileName));
        file.setLength(size);
        file.setContentType(contentType);
        file.setPutTime(DateUtil.date().toJdkDate());
        return file;
    }

    @Override
    public void removeFile(String fileName) {
        removeFile(ossProperties.getBucketName(), fileName);
    }

    @Override
    public void removeFile(String bucketName, String fileName) {
        bucketName = iOssRule.bucketName(bucketName);
        amazonS3.deleteObject(bucketName, fileName);
    }

    @Override
    public void removeFiles(List<String> fileNames) {
        removeFiles(ossProperties.getBucketName(), fileNames);
    }

    @Override
    public void removeFiles(String bucketName, List<String> fileNames) {
        bucketName = iOssRule.bucketName(bucketName);
        DeleteObjectsRequest dor = new DeleteObjectsRequest(bucketName).withKeys(fileNames.toArray(new String[fileNames.size()]));
        amazonS3.deleteObjects(dor);
    }
}
