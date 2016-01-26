package com.strv.codemanager.entity;


import java.util.ArrayList;
import java.util.List;

public class ClassEntity {
    private String mName;
    private List<String> mMethodList = new ArrayList<>();

    public ClassEntity(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void addMethod(String methodName) {
        mMethodList.add(methodName);
    }

    public List<String> getMethodList() {
        return mMethodList;
    }
}
