package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.model.PostImage;
import com.project.BE_banjjokee.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;

    public Long savePostImage(String key, String url) {
        PostImage image = PostImage.builder()
                .key(key)
                .url(url)
                .build();

        imageRepository.save(image);
        return image.getId();
    }

}
