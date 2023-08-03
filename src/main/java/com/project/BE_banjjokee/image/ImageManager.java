package com.project.BE_banjjokee.image;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageManager {

    public String uploadImage(MultipartFile multipartFile, UUID uuid) throws IOException;

    public List<String> uploadImages(List<MultipartFile> multipartFiles, UUID uuid) throws IOException;

    public void delete(UUID uuid, String fileName);

    public String createKey(UUID uuid, String fileName);

}
