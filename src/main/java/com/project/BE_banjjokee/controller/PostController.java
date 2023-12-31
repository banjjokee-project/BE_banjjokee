package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.*;
import com.project.BE_banjjokee.image.ImageManager;
import com.project.BE_banjjokee.service.CommentService;
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

    private final CommentService commentService;

    private final ImageManager imageManager;

    @PostMapping("/api/v1/post")
    public CreatePostResponse createPost(@AuthenticationPrincipal UserDetails userDetails,
                                         @ModelAttribute CreatePostRequest request) throws IOException {
        Long postId = postService.createPost(userDetails.getUsername(), request);
        return new CreatePostResponse(postId);
    }

    @GetMapping("/api/v1/post")
    public FindPostsResponse findPosts(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                       @RequestParam(name = "limit", defaultValue = "21") int limit) {
        List<FindPostsDTO> posts = postService.findPosts(offset, limit);
        return new FindPostsResponse(posts, limit);
    }

    @GetMapping("/api/v1/post/{postId}")
    public FindPostResponse findPost(@PathVariable long postId) {
        FindPostDTO post = postService.findPost(postId);
        List<CommentDTO> postComments = commentService.findPostComments(postId);
        return new FindPostResponse(post, postComments);
    }

    @GetMapping("/api/v1/post-user")
    public FindUserPostsResponse findUserPosts(@AuthenticationPrincipal UserDetails userDetails) {
        return new FindUserPostsResponse(postService.findUserPosts(userDetails.getUsername()));
    }

    @PutMapping("/api/v1/post/{postId}")
    public CreatePostResponse updatePost(@AuthenticationPrincipal UserDetails userDetails,
                                         @PathVariable Long postId,
                                         @ModelAttribute CreatePostRequest request) throws IOException {
        Long id = postService.update(userDetails.getUsername(), postId, request.getContent(), request.getImages());
        return new CreatePostResponse(id);
    }

    @DeleteMapping("/api/v1/post/{postId}")
    public DeletePostResponse deletePost(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long postId) {
        Long id = postService.delete(userDetails.getUsername(), postId);
        return new DeletePostResponse(id);
    }

}
