package org.tasks.appblogbt.dao.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentEntity {

    private Long id;
    private String content;
    private Long postId;

}
