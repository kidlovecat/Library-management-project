package com.example.library_manager_project.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.library_manager_project.Dao.LoaiSachDAO;
import com.example.library_manager_project.Fragment.SachFragment;
import com.example.library_manager_project.Model.LoaiSach;
import com.example.library_manager_project.Model.Sach;
import com.example.library_manager_project.R;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends ArrayAdapter<Sach> {
    Context context;
    SachFragment fragment;
    List<Sach> list;
    TextView tvMaSach, tvTenSach, tvGiaThue, tvLoai;
    ImageView imgDel;

    public SachAdapter(@NonNull Context context, @NonNull List<Sach> list, SachFragment fragment) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.sach_item, null);
        }
        final Sach item = list.get(position);
        if(item != null) {
            LoaiSachDAO LoaiSachDAO = new LoaiSachDAO (context);
            LoaiSach loaiSach = LoaiSachDAO.getID (String.valueOf(item.getMaLoai()));
            tvMaSach = v.findViewById(R.id.tvMaSach);
            tvMaSach.setText("Mã sách: "+item.getMaSach());
            tvTenSach = v.findViewById(R.id.tvTenSach);
            tvTenSach.setText("Tên sách: "+item.getTenSach());
            tvGiaThue = v.findViewById(R.id.tvGiaThue);
            tvGiaThue.setText("Giá thuê: "+item.getGiaThue());
            tvLoai = v.findViewById(R.id.tvLoai);
            tvLoai.setText("Loai sách: "+loaiSach.getTenLoai());

            imgDel = v.findViewById(R.id.imgDeleteLS);

        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaSach()));
            }
        });

        return v;
    }

}
