package org.tasks.appblogbt.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PagingDto {

    private int pageSize;
    private int pageNumber;
    private int countItems;
    private boolean hasNextPage;
    private boolean hasPreviousPage;

    public PagingDto(int pageSize, int pageNumber, int countItems) {
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.countItems = countItems;

        this.hasPreviousPage = pageNumber > 1;
        this.hasNextPage = pageNumber*pageSize < countItems;
    }

}
