package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Comment;
import com.project.BE_banjjokee.model.Post;
import com.project.BE_banjjokee.model.PostImage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FindPostDTO {

    private Long postId;

    private String content;

    private int likes;

    private int views;

    private List<String> imageUrls;

    private String writer;

    public FindPostDTO(Post post) {
        this.postId = post.getId();
        this.content = post.getContent();
        this.likes = post.getLikes();
        this.views = post.getViews();
        this.writer = post.getWriter().getNickname();

        List<String> urls = new ArrayList<>();
        List<PostImage> images = post.getImages();
        for (PostImage image : images) {
            urls.add(image.getUrl());
        }
        this.imageUrls = urls;
    }

}
