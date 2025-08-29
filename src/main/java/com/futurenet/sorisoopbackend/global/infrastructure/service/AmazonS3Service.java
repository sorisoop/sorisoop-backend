package com.futurenet.sorisoopbackend.global.infrastructure.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.futurenet.sorisoopbackend.global.infrastructure.service.exception.InfrastructureErrorCode;
import com.futurenet.sorisoopbackend.global.infrastructure.service.exception.InfrastructureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile file, String folder) {

        if (file == null || file.isEmpty()) {
            throw new InfrastructureException(InfrastructureErrorCode.IMAGE_FILE_NULL);
        }

        if (!isImage(file)) {
            throw new IllegalArgumentException("파일 형식 불일치");
        }

        try {
            String fileName = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));

            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new InfrastructureException(InfrastructureErrorCode.S3_FILE_UPLOAD_FAIL);
        }
    }

    public String uploadImage(InputStream inputStream, String folder, String extension) {
        try {
            String fileName = folder + "/" + UUID.randomUUID() + "." + extension;

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/" + extension);

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, metadata));

            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (Exception e) {
            throw new InfrastructureException(InfrastructureErrorCode.S3_FILE_UPLOAD_FAIL);
        }
    }

    private boolean isImage(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public String uploadAudio(MultipartFile file, String folder) {
        if (file.getContentType() == null || !file.getContentType().startsWith("audio/")) {
            throw new IllegalArgumentException("음성 파일만 업로드 가능합니다.");
        }
        try {
            String fileName = folder + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), metadata));
            return amazonS3.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new InfrastructureException(InfrastructureErrorCode.S3_FILE_UPLOAD_FAIL);
        }
    }

    public void deleteFile(String ttsUrl){
        try{
            String bucketName = extractBucketName(ttsUrl);
            String key = extractKey(ttsUrl);

            amazonS3.deleteObject(bucketName, key);
        }
        catch (Exception e){
            throw new InfrastructureException(InfrastructureErrorCode.S3_FILE_DELETE_FAIL);
        }
    }

    // Url에서 bucket, key 추출하는 유틸
    private String extractBucketName(String fileUrl) {
        String host = fileUrl.split("\\.s3")[0];
        return host.substring(host.lastIndexOf("/") + 1);
    }
    private String extractKey(String fileUrl) {
        return fileUrl.substring(fileUrl.indexOf(".amazonaws.com/") + 15);
    }
}
