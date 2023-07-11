package kea.alog.project.common.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PageDto<T> {

    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;
    private List<T> content;
}
