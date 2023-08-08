package com.project.BE_banjjokee.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String content;

    private int likes;

    private int views;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", referencedColumnName = "uuid")
    private User writer;

    public Post(String content, User writer) {
        setContent(content);
        setWriter(writer);
    }

    private void setWriter(User user) {
        this.writer = user;
        writer.getPosts().add(this);
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setImages(List<PostImage> images) {
        this.images = images;
    }


    public void change(String content) {
        setContent(content);
    }

    public Image createImage(String key, String url) {
        return new PostImage(key, url, this);
    }

    public void removeImage(PostImage image) {
        images.remove(image);
        image.cut();
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.cutPost();
    }

}
