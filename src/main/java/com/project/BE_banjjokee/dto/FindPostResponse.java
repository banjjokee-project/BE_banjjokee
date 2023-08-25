package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Post;
import com.project.BE_banjjokee.model.PostImage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FindPostResponse {

    private Long postId;

    private String content;

    private int likes;

    private int views;

    private List<String> imageUrls;

    private String writer;

    private List<CommentDTO> comments;

    public FindPostResponse(FindPostDTO post, List<CommentDTO> comments) {
        this.postId = post.getPostId();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.views = post.getViews();
        this.writer = post.getWriter();
        this.imageUrls = post.getImageUrls();
        this.comments = comments;
    }

}
