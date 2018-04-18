package com.martin.matrix.entity;

public class FilterInfo {
    private String name;
    private float[] colorMatrix;

    public FilterInfo(String name, float[] colorMatrix) {
        this.name = name;
        this.colorMatrix = colorMatrix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float[] getColorMatrix() {
        return colorMatrix;
    }

    public void setColorMatrix(float[] colorMatrix) {
        this.colorMatrix = colorMatrix;
    }
}
