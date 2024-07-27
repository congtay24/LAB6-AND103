package com.example.lab5;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.example.lab5.delete.InterfaceDelete;
import com.example.lab5.select.InterfaceSelect;
import com.example.lab5.select.SvrResponseSelect;
import com.example.lab5.update.InterfaceUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LAB5Activity extends AppCompatActivity {
    EditText txt1,txt2,txt3;
    TextView tvKQ;
    Button btn1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab5);
        txt1=findViewById(R.id.lab51Txt1);
        txt2=findViewById(R.id.lab51Txt2);
        txt3=findViewById(R.id.lab51Txt3);
        tvKQ=findViewById(R.id.lab51TvKQ);
        btn1=findViewById(R.id.lab51Btn1);
        btn1.setOnClickListener(v->{
            //insertData(txt1,txt2,txt3,tvKQ);
            selectData();
            //DeleteData(txt1);
            //updateData(txt1,txt2,txt3,tvKQ);
        });
    }
    private void insertData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ) {
        SanPham s = new SanPham(txt1.getText().toString(),
                txt2.getText().toString(), txt3.getText().toString());

        // ipv4 ở nhà : 192.168.1.63
        // ipv4 trên trường : 10.24.42.249
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.63/000/20240720/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceInsertSanPham insertSanPham
                = retrofit.create(InterfaceInsertSanPham.class);

        Call<SvrResponseSanPham> call
                = insertSanPham.insertSanPham(s.getMaSP(),s.getTenSP(),s.getMoTa());

        call.enqueue(new Callback<SvrResponseSanPham>() {
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                    SvrResponseSanPham res = response.body();
                    tvKQ.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });
    }

    private void updateData(EditText txt1, EditText txt2, EditText txt3, TextView tvKQ){
        //B1. tao doi tuong chua du lieu
        SanPham s=new SanPham(txt1.getText().toString(),
                txt2.getText().toString(),txt3.getText().toString());
        //b2. tao doi tuong retrofit
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.63/000/20240720/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //b3. goi ham insert
        InterfaceUpdate updateSanPham
                =retrofit.create(InterfaceUpdate.class);
        //chuan bi ham
        Call<SvrResponseSanPham> call
                =updateSanPham.updateSanPham(s.getMaSP(),s.getTenSP(),s.getMoTa());
        //thuc thi ham
        call.enqueue(new Callback<SvrResponseSanPham>() {
            //thanh cong
            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res=response.body();
                tvKQ.setText(res.getMessage());
            }
            //that bai
            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });

    }

    private void DeleteData(EditText txt1){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.1.63/000/20240720/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceDelete deleteSanPham
                =retrofit.create(InterfaceDelete.class);

        Call<SvrResponseSanPham> call
                =deleteSanPham.deleteSanPham(txt1.getText().toString());

        call.enqueue(new Callback<SvrResponseSanPham>() {

            @Override
            public void onResponse(Call<SvrResponseSanPham> call, Response<SvrResponseSanPham> response) {
                SvrResponseSanPham res=response.body();
                tvKQ.setText(res.getMessage());
            }

            @Override
            public void onFailure(Call<SvrResponseSanPham> call, Throwable t) {
                tvKQ.setText(t.getMessage());
            }
        });

    }
    String strKQ = "";
    List<SanPham> ls;
    private void selectData(){
        strKQ="";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.63/000/20240720/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceSelect interfaceSelect = retrofit.create(InterfaceSelect.class);
        Call<SvrResponseSelect> call = interfaceSelect.selectSanPham();
        call.enqueue(new Callback<SvrResponseSelect>() {
            @Override
            public void onResponse(Call<SvrResponseSelect> call, Response<SvrResponseSelect> response) {
                SvrResponseSelect res=response.body();

                ls=new ArrayList<>(Arrays.asList(res.getProducts()));

                for(SanPham p: ls){
                    strKQ+="MaSP: "+p.getMaSP()+"; TenSP: "+p.getTenSP()+"; MoTa: "+p.getMaSP()+"\n";
                }
                tvKQ.setText(strKQ);
            }

            @Override
            public void onFailure(Call<SvrResponseSelect> call, Throwable throwable) {
                tvKQ.setText(throwable.getMessage());
            }
        });
    }
}