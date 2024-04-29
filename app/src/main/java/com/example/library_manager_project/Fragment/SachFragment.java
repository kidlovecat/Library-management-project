package com.example.library_manager_project.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_manager_project.Adapter.LoaiSachSpinnerAdapter;
import com.example.library_manager_project.Adapter.SachAdapter;
import com.example.library_manager_project.Dao.LoaiSachDAO;
import com.example.library_manager_project.Dao.SachDAO;
import com.example.library_manager_project.Model.LoaiSach;
import com.example.library_manager_project.Model.Sach;
import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {
    ListView lvSach;
    SachDAO sachDAO;
    SachAdapter adapter;
    Sach item;
    List<Sach> list;

    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaSach, edTenSach, edGiaThue;
    Spinner spinner;
    Button btnSave, btnCancel;
    TextView slsach;
    LoaiSachSpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDAO LoaisachDAO;
    LoaiSach loaiSach;
    int maLoaiSach, position;
    SearchView searchView;
    String searchQuery="";
    ArrayList<Sach> filterlist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sach_2, container, false);
        lvSach = v.findViewById(R.id.lvSach);
        fab = v.findViewById(R.id.fab);
        searchView = v.findViewById(R.id.searchSach);
        slsach = v.findViewById(R.id.tvSLSach);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });

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
        lvSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(), 1);
            }
        });
        sachDAO = new SachDAO(getActivity());
        capNhatLv();
        return v;
    }
    private void filter(String s){
        list = (ArrayList<Sach>) sachDAO.getAll();
        filterlist = new ArrayList<>();
        for(Sach i:list){
            if(i.getTenSach().toLowerCase().contains(s.toLowerCase())){
                filterlist.add(i);
            }
        }
        if(filterlist.isEmpty()){
            Toast.makeText(getContext(), "Không có dữ liệu khớp", Toast.LENGTH_SHORT).show();
        }else{
            capNhatLvSearch(filterlist);

        }
    }
    void capNhatLvSearch(ArrayList<Sach> l) {

        adapter = new SachAdapter(getActivity(), l, this);
        lvSach.setAdapter(adapter);
        slsach.setText("Số lượng sách: "+l.size());
    }
    void capNhatLv() {
        list = (List<Sach>) sachDAO.getAll();
        adapter = new SachAdapter(getActivity(), list, this);
        lvSach.setAdapter(adapter);
        slsach.setText("Số lượng sách: "+list.size());
    }
    public void xoa (final String Id){
//Su dung Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc muốn xoá không?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Goi function Delete
                        sachDAO.delete(Id);
                        capNhatLv();
                        if(searchQuery!="") {
                            filterlist = new ArrayList<>();
                            for (Sach i : list) {
                                if (i.getTenSach().toLowerCase().contains(searchQuery.toLowerCase())) {
                                    filterlist.add(i);
                                }
                            }
                            //cap nhat listview
                            capNhatLvSearch(filterlist);
                        }
                        dialog.cancel();
                    }
                });
        builder.setNegativeButton(
                "Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        builder.show();
    }
    protected void openDialog(final Context context, final int type) {
        //custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_sach_dialog);
        edMaSach = dialog.findViewById(R.id.edMaSach);
        edTenSach = dialog.findViewById(R.id.edTenSach);
        edGiaThue = dialog.findViewById(R.id.edGiaThue);
        spinner = dialog.findViewById(R.id.spLoaiSach);
        btnCancel = dialog.findViewById(R.id.btnCancelSach);
        btnSave = dialog.findViewById(R.id.btnSaveSach);
        listLoaiSach = new ArrayList<LoaiSach>();
        LoaisachDAO = new LoaiSachDAO (context);
        listLoaiSach = (ArrayList<LoaiSach>) LoaisachDAO.getAll();
        spinnerAdapter = new LoaiSachSpinnerAdapter(context, listLoaiSach);
        spinner.setAdapter(spinnerAdapter);
        // Lay maLoaiSach
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoaiSach = listLoaiSach.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent) {
            }
        });
        //kiem tra type insert 0 hay Update 1
        edMaSach.setEnabled(false);
        if (type != 0) {
            edMaSach.setText(String.valueOf(item.getMaSach()));
            edTenSach.setText(item.getTenSach());
            edGiaThue.setText(String.valueOf(item.getGiaThue()));
            for (int i = 0; i < listLoaiSach.size(); i++)
                if (item.getMaLoai() == (listLoaiSach.get(i).getMaLoai())) {
                    position = i;
                }
            Log.i("demo", "posSach " + position);
            spinner.setSelection(position);
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
                item = new Sach();
                item.setTenSach(edTenSach.getText().toString());
                item.setGiaThue(Integer.parseInt(edGiaThue.getText().toString()));
                item.setMaLoai(maLoaiSach);
                if (validate()>0) {
                    if (type == 0) {
                        //type = 0 (insert)
                        if (sachDAO.insert(item) > 0) {
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //type =1 (update)
                        item.setMaSach(Integer.parseInt(edMaSach.getText().toString()));
                        if (sachDAO.update(item) > 0) {
                            Toast.makeText(context, "Sứa thành công", Toast.LENGTH_SHORT).show();
                        } else {
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
        if (edTenSach.getText().length()==0 || edGiaThue.getText().length()==0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}