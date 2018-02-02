package com.taskmanager.spring.Domain;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchForm
{
    @NotEmpty
    private String searchstring;

    public String getSearchstring() {
        return searchstring;
    }

    public void setSearchstring(String searchstring) {
        this.searchstring = searchstring;
    }
}
