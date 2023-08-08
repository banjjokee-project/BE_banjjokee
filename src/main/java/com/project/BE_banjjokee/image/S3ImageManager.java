package com.project.BE_banjjokee.image;

import com.project.BE_banjjokee.dto.UploadImageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Uri;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@RequiredArgsConstructor
public class S3ImageManager implements ImageManager {

    private final S3Client s3;

    @Value("${s3.bucket}")
    private String bucketName;

    @Override
    public UploadImageDTO uploadImage(MultipartFile multipartFile, UUID uuid) throws IOException, S3Exception {
        String fileName = multipartFile.getOriginalFilename();
        String key = createKey(fileName, uuid);
        byte[] bytes = multipartFile.getBytes();
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3.putObject(putOb, RequestBody.fromBytes(bytes));
        return new UploadImageDTO(key, getUrl(key));
    }

    @Override
    public Map<String, String> uploadImages(List<MultipartFile> multipartFiles, UUID uuid) throws IOException {
        Map<String, String> keyAndUrl = new HashMap<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.isEmpty()) {
                continue;
            }
            UploadImageDTO imageDto = uploadImage(multipartFile, uuid);
            keyAndUrl.put(imageDto.getKey(), imageDto.getUrl());
        }

        return keyAndUrl;
    }

    private String getUrl(String key) throws S3Exception {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        URL url = s3.utilities().getUrl(request);
        return url.toString();
    }

    @Override
    public String createKey(String fileName, UUID uuid) {
        return new StringBuffer()
                .append(uuid.toString())
                .append("/")
                .append(fileName)
                .toString();
    }

    @Override
    public void delete(String key) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3.deleteObject(deleteObjectRequest);
    }
}
