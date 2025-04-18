package org.tasks.appblogbt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private Integer countLike;
    private String tags;
    private String imageType;
    private byte[] image;
    private String base64Image;

    private Integer countComment;

}
