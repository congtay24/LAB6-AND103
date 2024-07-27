package com.example.lab5;

public class SanPham {
    private String MaSP,TenSP,MoTa;

    public SanPham() {
    }

    public SanPham(String maSP, String moTa, String tenSP) {
        MaSP = maSP;
        MoTa = moTa;
        TenSP = tenSP;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }
}
