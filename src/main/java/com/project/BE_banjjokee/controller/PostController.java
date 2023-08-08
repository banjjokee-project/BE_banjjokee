package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.*;
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

    @GetMapping("/api/v1/post")
    public findPostsResponse findPosts(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "limit", defaultValue = "21") int limit) {
        List<findPostsDTO> posts = postService.findPosts(offset, limit);
        return new findPostsResponse(posts, limit);
    }

    @GetMapping("/api/v1/post/{postId}")
    public findPostDTO findPost(@PathVariable long postId) {
        return postService.findPost(postId);
    }

    @GetMapping("/api/v1/post-user")
    public FindUserPostsResponse findUserPosts(@AuthenticationPrincipal UserDetails userDetails) {
        return new FindUserPostsResponse(postService.findUserPosts(userDetails.getUsername()));
    }

}
