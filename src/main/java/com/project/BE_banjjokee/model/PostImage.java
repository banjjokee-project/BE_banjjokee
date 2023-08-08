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
    public PostImage(String key, String url, Post post) {
        setKey(key);
        setUrl(url);
        setPost(post);
    }

    private void setPost(Post post) {
        if (post != null) {
            this.post = post;
            post.getImages().add(this);
        }
    }

    public void cut() {
        this.post = null;
    }

}
