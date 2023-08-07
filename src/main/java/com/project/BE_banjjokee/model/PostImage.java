package com.project.BE_banjjokee.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("POST")
@NoArgsConstructor
public class PostImage extends Image{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public static PostImage createPostImage(String key, String url, Post post) {

        PostImage image = new PostImage();
        image.setKey(key);
        image.setPost(post);
        return image;

    }

    private void setPost(Post post) {
        if (post == null) {
            this.post = null;
            return;
        }

        this.post = post;
        post.getImages().add(this);

    }

    public void cut() {

        this.post = null;

    }

}
