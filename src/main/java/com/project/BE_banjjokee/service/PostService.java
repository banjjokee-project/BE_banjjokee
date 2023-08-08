package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.CreatePostRequest;
import com.project.BE_banjjokee.dto.findPostsDTO;
import com.project.BE_banjjokee.image.ImageManager;
import com.project.BE_banjjokee.model.Post;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.ImageRepository;
import com.project.BE_banjjokee.repository.PostRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ImageRepository imageRepository;

    private final ImageManager imageManager;

    @Transactional
    public Long createPost(String email, CreatePostRequest request) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자"));

        Map<String, String> imageInfos = imageManager.uploadImages(request.getImages(), user.getUuid());
        Post post = user.createPost(request.getContent());
        postRepository.save(post);

        for (String key : imageInfos.keySet()) {
            imageRepository.save(post.createImage(key, imageInfos.get(key)));
        }

        return post.getId();
    }

}
