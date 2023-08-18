package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.*;
import com.project.BE_banjjokee.model.Comment;
import com.project.BE_banjjokee.model.Post;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.CommentRepository;
import com.project.BE_banjjokee.repository.PostRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public CreateCommentDTO createComment(String email, CreateCommentRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));
        Comment parentComment = null;
        Set<UUID> uuids = new HashSet<>();

        if (request.getParentId() != null) {
            parentComment = commentRepository.findById(request.getParentId()).orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));

            if (!post.getComments().contains(parentComment)) {
                throw new RuntimeException("잘못된 접근");
            }

            uuids.add(parentComment.getWriter().getUuid());
        }

        Comment comment = user.createComment(post, parentComment, request.getContent());
        commentRepository.save(comment);
        uuids.add(post.getWriter().getUuid());

        return new CreateCommentDTO(post.getId(), uuids);
    }

    public List<PostCommentDTO> findPostComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(PostCommentDTO::new)
                .toList();
    }

    public List<UserCommentDTO> findUserComments(String email) {
        List<Comment> comments = commentRepository.findAllByEmail(email);
        return comments.stream()
                .map(UserCommentDTO::new)
                .toList();
    }

    @Transactional
    public Long updateComment(String email, UpdateCommentRequest request) throws RuntimeException {
        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(() -> new RuntimeException("잘못된 접근"));

        if (!isValidUser(comment, email)) {
            throw new RuntimeException("잘못된 접근");
        }

        comment.change(request.getContent());
        return comment.getParent().getId();
    }

    @Transactional
    public Long deleteComment(String email, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("잘못된 접근"));
        Long postId = comment.getParent().getId();

        if (isValidUser(comment, email)) {
            throw new RuntimeException("잘못된 접근");
        }

        comment.getWriter().removeComment(comment); // user 객체를 이용하여 comment 삭제
        return postId;
    }

    private boolean isValidUser(Comment comment, String email) {
        return comment.getWriter().getEmail().equals(email);
    }

}
