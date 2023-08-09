package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.CreateCommentRequest;
import com.project.BE_banjjokee.dto.CreateCommentResponse;
import com.project.BE_banjjokee.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/api/v1/comment")
    public CreateCommentResponse createComment(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody CreateCommentRequest request) {
        Long id = commentService.createComment(userDetails.getUsername(), request);
        return new CreateCommentResponse(id);
    }
}
