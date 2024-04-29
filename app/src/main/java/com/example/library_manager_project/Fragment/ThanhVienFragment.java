package com.example.library_manager_project.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.library_manager_project.Adapter.ThanhVienAdapter;
import com.example.library_manager_project.Dao.ThanhVienDAO;
import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThanhVienFragment extends Fragment {

    ListView lvThanhVien;
    ArrayList<ThanhVien> list;
    static ThanhVienDAO dao;
    ThanhVienAdapter adapter;
    TextView tvSLThanhVien;
    ThanhVien item;
    FloatingActionButton fab;
    SearchView searchView;
    Dialog dialog;
    Button btnSave, btnCancel;
    EditText edMaTV, edTenTV, edNamSinh;
    ArrayList<ThanhVien> filterlist;
    String searchQuery="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_thanh_vien_2, container, false);

        lvThanhVien = v.findViewById(R.id.lvThanhVien);
        searchView = v.findViewById(R.id.searchThanhVien);
        fab = v.findViewById(R.id.fab);
        tvSLThanhVien = v.findViewById(R.id.tvSLThanhVien);

        dao = new ThanhVienDAO(getActivity());
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(),this,list);
        tvSLThanhVien.setText("Số lượng thành viên: "+list.size());
        lvThanhVien.setAdapter(adapter);



        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                filter(newText);
                return true;
            }

        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(),0);
            }
        });
        lvThanhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
            }
        });

        return  v;
    }
    private void filter(String s){
        list = (ArrayList<ThanhVien>) dao.getAll();
        filterlist = new ArrayList<>();
        for(ThanhVien i:list){
            if(i.getHoTen().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(i);
            }
        }
        if(filterlist.isEmpty()){
            Toast.makeText(getContext(), "Không có dữ liệu khớp", Toast.LENGTH_SHORT).show();
        }else{
            capNhatLvSearch(filterlist);

        }
    }
    void capNhatLvSearch(ArrayList<ThanhVien> l){
        adapter = new ThanhVienAdapter(getActivity(),this,l);
        lvThanhVien.setAdapter(adapter);
        tvSLThanhVien.setText("Số lượng thành viên: "+l.size());
    }
    void capNhatLv(){
        list = (ArrayList<ThanhVien>) dao.getAll();
        adapter = new ThanhVienAdapter(getActivity(),this,list);
        lvThanhVien.setAdapter(adapter);
        tvSLThanhVien.setText("Số lượng thành viên: "+list.size());
    }
    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc muốn xoá không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dao.delete(Id);
                        capNhatLv();
                        if(searchQuery!="") {
                            filterlist = new ArrayList<>();
                            for (ThanhVien i : list) {
                                if (i.getHoTen().toLowerCase().contains(searchQuery.toLowerCase())) {
                                    filterlist.add(i);
                                }
                            }
                            capNhatLvSearch(filterlist);
                        }
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }

    protected void openDialog(final Context context,final int type){
        dialog = new Dialog (context);
        dialog.setContentView(R.layout.add_thanh_vien_dialog);
        edMaTV = dialog.findViewById(R.id.edMaTV);
        edTenTV = dialog.findViewById(R.id.edTenTV);
        edNamSinh = dialog.findViewById(R.id.edNamSinh);
        btnCancel = dialog.findViewById(R.id.btnCacelTV);
        btnSave = dialog.findViewById(R.id.btnSaveTV);
        //kiem tra type insert 0 hay Update 1
        edMaTV.setEnabled(false);
        if (type != 0) {
            edMaTV.setText(String.valueOf(item.getMaTV()));
            edTenTV.setText(item.getHoTen());
            edNamSinh.setText(item.getNamSinh());
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new ThanhVien();
                item.setHoTen(edTenTV.getText().toString());
                item.setNamSinh(edNamSinh.getText().toString());
                if(validate()> 0){
                    if(type == 0){
                        //type = 0 (insert)
                        if (dao.insert(item)>0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                            //type =1 (update)
                            item.setMaTV(Integer.parseInt(edMaTV.getText().toString()));
                            if (dao.update(item)>0){
                                Toast.makeText(context,"Sứa thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Sứa thất bại", Toast.LENGTH_SHORT).show();
                            }
                    }
                    capNhatLv();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public int validate(){
        int check = 1;
        if (edTenTV.getText().length()==0 || edNamSinh.getText().length()==0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}