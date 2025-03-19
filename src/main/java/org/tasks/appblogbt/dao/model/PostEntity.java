package org.tasks.appblogbt.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostEntity {

    private Long id;
    private String title;
    private String content;
    private Integer countLike;
    private String tags;
    private String imageType;
    private byte[] image;

}
