package com.example.heightcalculator;

public class HeightInfo {
    private int id;
    private String name;
    private boolean ismale;
    private String fheight;
    private String mheight;
    private String sheight;

    public HeightInfo(){

    }
    public HeightInfo(int id, String name, boolean ismale, String fheight, String mheight, String sheight) {
        this.id = id;
        this.name = name;
        this.ismale = ismale;
        this.fheight = fheight;
        this.mheight = mheight;
        this.sheight = sheight;
    }

    @Override
    public String toString() {
        return "HeightInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ismale=" + ismale +
                ", fheight=" + fheight +
                ", mheight=" + mheight +
                ", sheight=" + sheight +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsmale() {
        return ismale;
    }

    public void setIsmale(boolean ismale) {
        this.ismale = ismale;
    }

    public String getFheight() {
        return fheight;
    }

    public void setFheight(String fheight) {
        this.fheight = fheight;
    }

    public String getMheight() {
        return mheight;
    }

    public void setMheight(String mheight) {
        this.mheight = mheight;
    }

    public String getSheight() {
        return sheight;
    }

    public void setSheight(String sheight) {
        this.sheight = sheight;
    }
}
