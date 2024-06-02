package com.example.finalProject_meatector;

public class Version {

    private String code, isi;
    private boolean expandable;

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public Version(String code, String isi) {
        this.code = code;
        this.isi = isi;
        this.expandable = false;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    @Override
    public String toString() {
        return "Version{" +
                "code='" + code + '\'' +
                ", isi='" + isi + '\'' +
                '}';
    }
}
