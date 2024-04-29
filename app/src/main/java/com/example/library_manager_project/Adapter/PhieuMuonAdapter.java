package com.example.library_manager_project.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library_manager_project.Dao.SachDAO;
import com.example.library_manager_project.Dao.ThanhVienDAO;
import com.example.library_manager_project.Fragment.DoanhThuFragment;
import com.example.library_manager_project.Fragment.PhieuMuonFragment;
import com.example.library_manager_project.Model.PhieuMuon;
import com.example.library_manager_project.Model.Sach;
import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    private Context context;
    PhieuMuonFragment fragment;
    DoanhThuFragment dtf;
    private ArrayList<PhieuMuon> lists;
    TextView tvMaPM, tvTenTV, tvTenSach, tvTienThue, tvNgay, tvTraSach;
    ImageView imgDel;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat(  "dd/MM/yyyy");
    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> lists) {
        super(context,  0,lists);
        this.context= context;
        this.lists=lists;
        this.fragment = fragment;
    }
    public PhieuMuonAdapter(@NonNull Context context, DoanhThuFragment fragment, ArrayList<PhieuMuon> lists) {
        super(context,  0,lists);
        this.context= context;
        this.lists=lists;
        this.dtf = fragment;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.phieu_muon_item,  null);
        }
        final PhieuMuon item = lists.get(position);
        if(item != null){
            tvMaPM = v.findViewById(R.id.tvMaPM);
            tvMaPM.setText("Mã phiếu: "+item.getMaPM());

            sachDAO = new SachDAO (context);
            Sach sach = sachDAO.getID (String.valueOf(item.getMaSach()));
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTenSach.setText("Tên sách: "+sach.getTenSach());

            thanhVienDAO = new ThanhVienDAO (context);
            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(item.getMaTV()));
            tvTenTV = v.findViewById(R.id.tvTenTV);
            tvTenTV.setText("Thành viên: "+thanhVien.getHoTen());
            tvTienThue = v.findViewById(R.id.tvTienThue);
            tvTienThue.setText( "Tiền thuê: "+item.getTienThue());
            tvNgay = v.findViewById(R.id.tvNgayPM);


            tvNgay.setText("Ngày thuê: " + sdf.format(item.getNgay()));
            tvTraSach = v.findViewById(R.id.tvTraSach);

            if (item.getTraSach()==1){
                tvTraSach.setTextColor(Color.BLUE);
                tvTraSach.setText("Đã trả sách");
            }else {
                tvTraSach.setTextColor(Color.RED);
                tvTraSach.setText("Chưa trả sách");
            }
            imgDel = v.findViewById(R.id.imgDeleteLS);
        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoa(String.valueOf(item.getMaPM()));
            }
        });

        return v;
    }

}
