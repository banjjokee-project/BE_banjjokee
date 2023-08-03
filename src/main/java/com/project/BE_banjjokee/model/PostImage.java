package com.project.BE_banjjokee.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("POST")
@NoArgsConstructor
public class PostImage extends Image{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private Post post;

    public static PostImage createPostImage(String key, Post post) {

        PostImage image = new PostImage();
        image.setKey(key);
        image.setPost(post);
        return image;

    }

    private void setPost(Post post) {

        this.post = post;
        post.getImages().add(this);

    }

    public void cut() {

        this.post = null;

    }

}
