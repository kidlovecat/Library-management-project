package com.example.library_manager_project.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_manager_project.Adapter.ThanhVienAdapter;
import com.example.library_manager_project.Adapter.ThuThuAdapter;
import com.example.library_manager_project.Dao.ThanhVienDAO;
import com.example.library_manager_project.Dao.ThuThuDAO;
import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.Model.ThuThu;
import com.example.library_manager_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ThuThuFragment extends Fragment {
    ListView lvThuThu;
    TextView tvSLThuThu;
    ArrayList<ThuThu> list;
    static ThuThuDAO dao;
    ThuThuAdapter adapter;
    ThuThu item;
    FloatingActionButton fab;
    SearchView searchView;
    Dialog dialog;
    Button btnSave, btnCancel;
    EditText edMaTT, edTenTT,  edPassword;
    ArrayList<ThuThu> filterlist;
    String searchQuery="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_thu_thu, container, false);

        lvThuThu = v.findViewById(R.id.lvThuThu);
        searchView = v.findViewById(R.id.searchThuThu);
        fab = v.findViewById(R.id.fab);
        tvSLThuThu = v.findViewById(R.id.tvSLThuThu);


        dao = new ThuThuDAO(getActivity());
        list = (ArrayList<ThuThu>) dao.getAll();
        adapter = new ThuThuAdapter(getActivity(),this,list);
        lvThuThu.setAdapter(adapter);
        tvSLThuThu.setText("Số lượng thủ thư: "+list.size());


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
        lvThuThu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
            }
        });

        return  v;
    }
    private void filter(String s){
        list = (ArrayList<ThuThu>) dao.getAll();
        filterlist = new ArrayList<>();
        for(ThuThu i:list){
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
    void capNhatLvSearch(ArrayList<ThuThu> l){
        adapter = new ThuThuAdapter(getActivity(),this,l);
        lvThuThu.setAdapter(adapter);
        tvSLThuThu.setText("Số lượng thủ thư: "+l.size());
    }
    void capNhatLv(){
        list = (ArrayList<ThuThu>) dao.getAll();
        adapter = new ThuThuAdapter(getActivity(),this,list);
        lvThuThu.setAdapter(adapter);
        tvSLThuThu.setText("Số lượng thủ thư: "+list.size());
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
                    for (ThuThu i : list) {
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

    protected void openDialog(final Context context, final int type){
        dialog = new Dialog (context);
        dialog.setContentView(R.layout.add_thu_thu_diaglog);
        edMaTT = dialog.findViewById(R.id.edMaTT);
        edTenTT = dialog.findViewById(R.id.edTenTT);

        edPassword = dialog.findViewById(R.id.edPassword);
        btnCancel = dialog.findViewById(R.id.btnCacelTT);
        btnSave = dialog.findViewById(R.id.btnSaveTT);
        //kiem tra type insert 0 hay Update 1
//        edMaTT.setEnabled(false);
        if (type != 0) {
            edMaTT.setText(item.getMaTT());
            edTenTT.setText(item.getHoTen());
            edPassword.setText(item.getMatKhau());

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
                item = new ThuThu();
                item.setHoTen(edTenTT.getText().toString());
                item.setMaTT(edMaTT.getText().toString());
                item.setMatKhau(edPassword.getText().toString());
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
                        item.setMaTT(edMaTT.getText().toString());
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
        if (edTenTT.getText().length()==0 || edMaTT.getText().length()==0 || edPassword.getText().length()==0) {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông ", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;

    }
}