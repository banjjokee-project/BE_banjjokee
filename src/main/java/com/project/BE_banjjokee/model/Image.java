package com.project.BE_banjjokee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@NoArgsConstructor
@ToString(of = {"id", "key", "url"})
public class Image extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    @Column(name = "image_key")
    private String key;

    private String url;

    protected void setKey(String key) {
        this.key = key;
    }

    protected void setUrl(String url) {
        this.url = url;
    }
}
