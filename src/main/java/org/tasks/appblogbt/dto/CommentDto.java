package org.tasks.appblogbt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentDto {

    private Long id;
    private String content;
    private Long postId;

}
