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

import com.example.library_manager_project.Fragment.ThanhVienFragment;
import com.example.library_manager_project.Fragment.ThuThuFragment;
import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.Model.ThuThu;
import com.example.library_manager_project.R;

import java.util.ArrayList;

public class ThuThuAdapter extends ArrayAdapter<ThuThu> {
    private Context context;
    ThuThuFragment fragment;
    private ArrayList<ThuThu> lists;
    TextView tvMaTV, tvTenTV, tvNamSinh;
    ImageView imgDel;
    public ThuThuAdapter(@NonNull Context context, ThuThuFragment fragment, ArrayList<ThuThu> lists) {
        super(context, 0, lists);
        this.context = context;
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.thu_thu_item, null);
        }
        final ThuThu item = lists.get(position);
        if(item != null) {
            tvMaTV = v.findViewById(R.id.tvMaTT);
            tvMaTV.setText("Mã thủ thư: "+item.getMaTT());
            tvTenTV = v.findViewById(R.id.tvTenTT);
            tvTenTV.setText("Tên thành viên: "+item.getHoTen());
            imgDel=v.findViewById(R.id.imgDeleteLS);
        }

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaTT()));
            }
        });

        return v;
    }
}
