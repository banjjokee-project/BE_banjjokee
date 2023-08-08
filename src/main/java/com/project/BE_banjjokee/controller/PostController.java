package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.CreatePostRequest;
import com.project.BE_banjjokee.dto.CreatePostResponse;
import com.project.BE_banjjokee.dto.findPostsDTO;
import com.project.BE_banjjokee.dto.findPostsResponse;
import com.project.BE_banjjokee.image.ImageManager;
import com.project.BE_banjjokee.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final ImageManager imageManager;

    @PostMapping("/api/v1/post")
    public CreatePostResponse createPost(@AuthenticationPrincipal UserDetails userDetails,
                                         @ModelAttribute CreatePostRequest request) throws IOException {
        Long postId = postService.createPost(userDetails.getUsername(), request);
        return new CreatePostResponse(postId);
    }

}
