package com.project.BE_banjjokee.image;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ImageManager implements ImageManager {

    private final S3Client s3;

    @Value("${s3.bucket}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile multipartFile, UUID uuid) throws IOException, S3Exception {
        String fileName = multipartFile.getOriginalFilename();
        String key = createKey(fileName, uuid);
        byte[] bytes = multipartFile.getBytes();
        PutObjectRequest putOb = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3.putObject(putOb, RequestBody.fromBytes(bytes));
        return getUrl(key);
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> multipartFiles, UUID uuid) throws IOException {
        List<String> urls = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            if (multipartFile.isEmpty()) {
                continue;
            }

            urls.add(uploadImage(multipartFile, uuid));
        }

        return urls;
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
