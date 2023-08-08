package com.project.BE_banjjokee.dto;

import lombok.Data;

import java.util.List;

@Data
public class findPostsResponse {

    private List<findPostsDTO> posts;

    private boolean hasNextPage;

    public findPostsResponse(List<findPostsDTO> posts, int limit) {
        this.posts = posts;

        if (limit < posts.size()) {
            this.hasNextPage = true;
        }

        this.hasNextPage = false;
    }
}
