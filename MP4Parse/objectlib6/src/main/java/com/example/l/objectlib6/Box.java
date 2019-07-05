package com.example.l.objectlib6;

public class Box {
    private int startoffset;
    private int size;
    private String type;
    public Box(){}
    public Box(int startoffset,int size,String type){
        this.startoffset = startoffset;this.size = size;this.type = type;
    }

    public int getStartoffset() {
        return startoffset;
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public void setStartoffset(int startoffset) {
        this.startoffset = startoffset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setType(String type) {
        this.type = type;
    }
}
