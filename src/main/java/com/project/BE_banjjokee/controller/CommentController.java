package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.*;
import com.project.BE_banjjokee.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/api/v1/comment")
    public CommentResponse createComment(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody CreateCommentRequest request) {
        Long id = commentService.createComment(userDetails.getUsername(), request);
        return new CommentResponse(id);
    }

    @GetMapping("api/v1/post-comments/{postId}")
    public PostCommentResponse findPostComments(@PathVariable Long postId) {
        List<PostCommentDTO> comments = commentService.findPostComments(postId);
        return new PostCommentResponse(postId, comments);
    }

    @GetMapping("/api/v1/user-comments")
    public UserCommentResponse findUserComments(@AuthenticationPrincipal UserDetails userDetails) {
        return new UserCommentResponse(commentService.findUserComments(userDetails.getUsername()));
    }

    @PutMapping("/api/v1/comment")
    public CommentResponse updateComment(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestBody UpdateCommentRequest request) {
        Long id = commentService.updateComment(userDetails.getUsername(), request);
        return new CommentResponse(id);
    }

    @DeleteMapping("/api/v1/comment/{commentId}")
    public CommentResponse deleteComment(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long commentId) {
        Long postId = commentService.deleteComment(userDetails.getUsername(), commentId);
        return new CommentResponse(postId);
    }

}
