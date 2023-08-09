package com.project.BE_banjjokee.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindUserPostsResponse {

    private List<UserPostDTO> posts;

    public FindUserPostsResponse(List<UserPostDTO> posts) {
        this.posts = posts;
    }

}
