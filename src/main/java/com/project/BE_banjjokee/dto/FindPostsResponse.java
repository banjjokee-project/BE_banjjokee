package com.project.BE_banjjokee.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindPostsResponse {

    private List<FindPostsDTO> posts;

    private boolean hasNextPage;

    public FindPostsResponse(List<FindPostsDTO> posts, int limit) {
        this.posts = posts;

        if (limit < posts.size()) {
            this.hasNextPage = true;
        }

        this.hasNextPage = false;
    }
}
