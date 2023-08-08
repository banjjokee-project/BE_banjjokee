package com.project.BE_banjjokee.image;

import com.project.BE_banjjokee.dto.UploadImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ImageManager {

    public UploadImageDTO uploadImage(MultipartFile multipartFile, UUID uuid) throws IOException;

    public Map<String, String> uploadImages(List<MultipartFile> multipartFiles, UUID uuid) throws IOException;

    public void delete(String key);

    public String createKey(String fileName, UUID uuid);

}
