package com.project.BE_banjjokee.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreatePostRequest {

    private String content;

    private List<MultipartFile> images;

}
