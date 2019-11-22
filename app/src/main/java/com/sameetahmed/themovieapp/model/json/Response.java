package com.sameetahmed.themovieapp.model.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("results")
    private List<Result> resultList;


    public List<Result> getResultList() {
        return resultList;
    }

    public void setResultList(List<Result> resultList) {
        this.resultList = resultList;
    }
}
