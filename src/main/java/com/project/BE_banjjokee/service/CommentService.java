package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.CreateCommentRequest;
import com.project.BE_banjjokee.model.Comment;
import com.project.BE_banjjokee.model.Post;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.CommentRepository;
import com.project.BE_banjjokee.repository.PostRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public Long createComment(String email, CreateCommentRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));
        Comment parentComment = null;

        if (request.getParentId() != null) {
            parentComment = commentRepository.findById(request.getParentId()).orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));
        }

        Comment comment = user.createComment(post, parentComment, request.getContent());
        commentRepository.save(comment);

        return comment.getId();
    }
}
