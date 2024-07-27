package com.example.lab5.select;

import com.example.lab5.SanPham;

public class SvrResponseSelect {
    private SanPham[] products ;
    private String message;

    public SanPham[] getProducts() {
        return products;
    }

    public String getMessage() {
        return message;
    }
}
