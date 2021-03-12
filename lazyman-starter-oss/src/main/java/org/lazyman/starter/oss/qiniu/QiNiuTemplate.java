package org.lazyman.starter.oss.qiniu;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.amazonaws.util.IOUtils;
import com.qiniu.cdn.CdnManager;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lazyman.starter.oss.IOssRule;
import org.lazyman.starter.oss.OssFile;
import org.lazyman.starter.oss.OssProperties;
import org.lazyman.starter.oss.OssTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@AllArgsConstructor
@Slf4j
public class QiNiuTemplate implements OssTemplate {
    private final IOssRule iOssRule;
    private final Auth auth;
    private final OssProperties ossProperties;

    @Override
    public void makeBucket(String bucketName) {

    }

    @Override
    public void removeBucket(String bucketName) {

    }

    @Override
    public boolean bucketExists(String bucketName) {
        return false;
    }

    @Override
    public void copyFile(String bucketName, String fileName, String destBucketName) {

    }

    @Override
    public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {

    }

    @Override
    public OssFile statFile(String fileName) {
        return null;
    }

    @Override
    public OssFile statFile(String bucketName, String fileName) {
        return null;
    }

    @Override
    public String filePath(String fileName) {
        return null;
    }

    @Override
    public String filePath(String bucketName, String fileName) {
        return null;
    }

    @Override
    public String fileLink(String fileName) {
        return fileLink(ossProperties.getBucketName(), fileName);
    }

    @Override
    public String fileLink(String bucketName, String fileName) {
        String baseUrl = String.format("%s/%s", ossProperties.getEndpoint(), fileName);
        CdnManager cdnManager = new CdnManager(auth);
        try {
            cdnManager.refreshUrls(new String[]{baseUrl});
        } catch (QiniuException e) {
            log.error("获取七牛文件链接失败", e);
        }
        return baseUrl;
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
        Configuration cfg = new Configuration(Zone.autoZone());
        UploadManager uploadManager = new UploadManager(cfg);
        bucketName = iOssRule.bucketName(bucketName);
        String originalName = fileName;
        fileName = iOssRule.fileName(fileName);
        String upToken = auth.uploadToken(bucketName, fileName);
        byte[] bytes = IOUtils.toByteArray(stream);
        uploadManager.put(bytes, fileName, upToken);
        OssFile file = new OssFile();
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
        Configuration cfg = new Configuration(Zone.autoZone());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(iOssRule.bucketName(bucketName), fileName);
        } catch (QiniuException e) {
            log.error("删除七牛文件失败", e);
        }
    }

    @Override
    public void removeFiles(List<String> fileNames) {
        removeFiles(ossProperties.getBucketName(), fileNames);
    }

    @Override
    public void removeFiles(String bucketName, List<String> fileNames) {
        if (CollectionUtil.isEmpty(fileNames)) {
            return;
        }
        for (String fileName : fileNames) {
            removeFile(bucketName, fileName);
        }
    }
}
