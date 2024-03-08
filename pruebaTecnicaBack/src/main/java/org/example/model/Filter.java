package org.example.model;

import org.example.enumModel.FilterType;

public class Filter {
    
    private String value;
    private String field;
    private FilterType filterType;

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(final FilterType filterType) {
        this.filterType = filterType;
    }

}
