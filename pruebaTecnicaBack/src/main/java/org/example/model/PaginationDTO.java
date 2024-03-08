package org.example.model;

import java.util.List;

public class PaginationDTO {
    private int page;

    private int limit;
    private String columnSort;

  

    private List<Filter> dataFilter;

    public int getPage() {
        return page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(final int limit) {
        this.limit = limit;
    }

    public List<Filter> getDataFilter() {
        return dataFilter;
    }

    public void setDataDilter(final List<Filter> dataFilter) {
        this.dataFilter = dataFilter;
    }
    public String getcolumnSort() {
        return columnSort;
    }

    public void setcolumnSort(final String column) {
        this.columnSort = column;
    }
}
