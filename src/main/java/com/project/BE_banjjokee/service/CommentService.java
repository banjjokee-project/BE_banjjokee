package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.*;
import com.project.BE_banjjokee.exception.*;
import com.project.BE_banjjokee.model.Comment;
import com.project.BE_banjjokee.model.Post;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.CommentRepository;
import com.project.BE_banjjokee.repository.PostRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public CreateCommentDTO createComment(String email, CreateCommentRequest request) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(ErrorCode.U001));
        Post post = postRepository.findById(request.getPostId()).orElseThrow(() -> new PostNotFoundException(ErrorCode.P001));
        Comment parentComment = null;
        Set<UUID> uuids = new HashSet<>();

        if (request.getParentId() != null) {
            parentComment = commentRepository.findById(request.getParentId()).orElseThrow(() -> new CommentNotFoundException(ErrorCode.C001));

            if (!post.getComments().contains(parentComment)) {
                throw new CommentInvalidRequestException(ErrorCode.C004);
            }

            uuids.add(parentComment.getWriter().getUuid());
        }

        Comment comment = user.createComment(post, parentComment, request.getContent());
        commentRepository.save(comment);
        uuids.add(post.getWriter().getUuid());

        return new CreateCommentDTO(post.getId(), uuids);
    }

    public List<CommentDTO> findPostComments(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        Map<Comment, List<CommentDTO>> commentHierarchy = new HashMap<>();

        for (Comment comment : comments) {
            Comment parent = comment.getParent();
            List<CommentDTO> commentDTOS = commentHierarchy.get(parent);
            CommentDTO commentDTO = new CommentDTO(comment);

            if (commentDTOS == null) {
                commentDTOS = new ArrayList<>();
                commentHierarchy.put(parent, commentDTOS);
            }

            if (parent.getId() != comment.getId()) {
                commentDTOS.add(commentDTO);
            }
        }

        List<CommentDTO> dtos = new ArrayList<>();

        for (Comment parent : commentHierarchy.keySet()) {
            List<CommentDTO> children = commentHierarchy.get(parent);
            CommentDTO parentDTO = new CommentDTO(parent);

            parentDTO.changeChildren(children);
            dtos.add(parentDTO);
        }

        dtos.sort(Comparator.comparing(CommentDTO::getCommentId));

        return dtos;
    }

    public List<UserCommentDTO> findUserComments(String email) {
        List<Comment> comments = commentRepository.findAllByEmail(email);
        return comments.stream()
                .map(UserCommentDTO::new)
                .toList();
    }

    @Transactional
    public Long updateComment(String email, UpdateCommentRequest request) throws RuntimeException {
        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(() -> new CommentNotFoundException(ErrorCode.C001));

        if (!isValidUser(comment, email)) {
            throw new UnauthorizedException(ErrorCode.C002);
        }

        comment.change(request.getContent());
        return comment.getPost().getId();
    }

    @Transactional
    public Long deleteComment(String email, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException(ErrorCode.C001));
        Long postId = comment.getParent().getId();

        if (isValidUser(comment, email)) {
            throw new UnauthorizedException(ErrorCode.C002);
        }

        comment.getWriter().removeComment(comment); // user 객체를 이용하여 comment 삭제
        return postId;
    }

    private boolean isValidUser(Comment comment, String email) {
        return comment.getWriter().getEmail().equals(email);
    }

}
