package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.CreatePostRequest;
import com.project.BE_banjjokee.dto.UserPostDTO;
import com.project.BE_banjjokee.dto.findPostDTO;
import com.project.BE_banjjokee.dto.findPostsDTO;
import com.project.BE_banjjokee.image.ImageManager;
import com.project.BE_banjjokee.model.Post;
import com.project.BE_banjjokee.model.PostImage;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.ImageRepository;
import com.project.BE_banjjokee.repository.PostRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    public List<findPostsDTO> findPosts(int offset, int limit) {
        List<Post> posts = postRepository.findPostsWithOffsetAndLimit(offset, limit + 3);
        List<findPostsDTO> dtos = new ArrayList<>();

        for (Post post : posts) {
            String url = null;
            if (!post.getImages().isEmpty()) {
                url = post.getImages().get(0).getUrl();
            }
            dtos.add(new findPostsDTO(post.getId(), url));
        }

        return dtos;
    }

    public findPostDTO findPost(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("게시글 존재하지 않습니다."));
        return new findPostDTO(post);
    }

    public List<UserPostDTO> findUserPosts(String email) {
        return postRepository.findByEmail(email).stream()
                .map(UserPostDTO::new)
                .toList();
    }

    public Long update(String email, Long id, String content, List<MultipartFile> images) throws IOException, RuntimeException {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));
        if (!isAuthorizedUser(email, post)) {
            throw new RuntimeException("잘못된 접근입니다.");
        }
        List<PostImage> removeImages = post.getImages();
        int size = removeImages.size();

        for (int i = size - 1; i >= 0; i--) {
            PostImage image = post.getImages().get(i);
            String key = image.getKey();

            post.removeImage(image);
            imageManager.delete(key);
        }

        post.change(content);

        Map<String, String> imageInfos = imageManager.uploadImages(images, post.getWriter().getUuid());

        for (String key : imageInfos.keySet()) {
            imageRepository.save(post.createImage(key, imageInfos.get(key)));
        }

        return post.getId();
    }

    private boolean isAuthorizedUser(String email, Post post) {
        return email.equals(post.getWriter().getEmail());
    }

}
