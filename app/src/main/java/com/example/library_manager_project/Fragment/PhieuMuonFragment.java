package com.example.library_manager_project.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import com.example.library_manager_project.Adapter.PhieuMuonAdapter;
import com.example.library_manager_project.Adapter.SachSpinnerAdapter;
import com.example.library_manager_project.Adapter.ThanhVienSpinnerAdapter;
import com.example.library_manager_project.Dao.PhieuMuonDAO;
import com.example.library_manager_project.Dao.SachDAO;
import com.example.library_manager_project.Dao.ThanhVienDAO;
import com.example.library_manager_project.Model.PhieuMuon;
import com.example.library_manager_project.Model.Sach;
import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class PhieuMuonFragment extends Fragment {
    ListView lvPhieuMuon;
    ArrayList<PhieuMuon> list;
    static PhieuMuonDAO dao;
    PhieuMuonAdapter adapter;
    PhieuMuon item;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaPM, edTu, edDen;
    Button btSearch;
    Spinner spTV, spSach;

    TextView tvNgay, tvTienThue, tong;
    CheckBox chkTraSach;
    Button btnSave, btnCancel;

    ThanhVienSpinnerAdapter thanhVienSpinnerAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int maThanhVien;

    SachSpinnerAdapter sachSpinnerAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int maSach, tienThue;
    int positionTV, positionSach;

    int mYear, mMonth, mDay;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phieu_muon_3, container, false);
        lvPhieuMuon = v.findViewById(R.id.lvPhieuMuon);
        fab = v.findViewById(R.id.fab);
        edTu = v.findViewById(R.id.edTu);
        edDen = v.findViewById(R.id.edDen);
        btSearch = v.findViewById(R.id.search);
        tong = v.findViewById(R.id.tvTongPM);

        edTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTu, mYear, mMonth, mDay);
                d.show();
            }
        });

        edDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDen, mYear, mMonth, mDay);
                d.show();
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edTu.getText().toString();
                String denNgay = edDen.getText().toString();
                if(!tuNgay.isEmpty() &&  !denNgay.isEmpty())
                    capNhatLvSearch(tuNgay,denNgay);
                else{
                    Toast.makeText(getContext(), "Cần nhập đầy đủ thời gian", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(getActivity(), 0);
            }
        });


        lvPhieuMuon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialog(getActivity(),1);
            }
        });

        dao = new PhieuMuonDAO(getActivity());
        capNhatLv();
        return v;
    }


    DatePickerDialog.OnDateSetListener mDateTu = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar (mYear, mMonth, mDay);
            edTu.setText(sdf.format(c.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mDateDen = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar (mYear, mMonth, mDay);
            edDen.setText(sdf.format(c.getTime()));
        }
    };
    void capNhatLvSearch(String t, String d){
        list = (ArrayList<PhieuMuon>) dao.getPhieuMuonByDate(t,d);
        adapter=new PhieuMuonAdapter (getActivity(),  this, list);
        lvPhieuMuon.setAdapter(adapter);
        tong.setText(list.size()+" phiếu mượn");
    }
    void capNhatLv(){
        list = (ArrayList<PhieuMuon>) dao.getAll();
        adapter=new PhieuMuonAdapter (getActivity(),  this, list);
        lvPhieuMuon.setAdapter(adapter);
        tong.setText(list.size()+" phiếu mượn");
    }
    protected void openDialog(final Context context, final int type) {
        //custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.phieu_muon_dialog);
        edMaPM = dialog.findViewById(R.id.edMaPM);
        spTV = dialog.findViewById(R.id.spMaTV);
        spSach= dialog.findViewById(R.id.spMaSach);
        tvNgay = dialog.findViewById(R.id.tvNgay);
        tvTienThue = dialog.findViewById(R.id.tvTienThue);
        chkTraSach = dialog.findViewById(R.id.chkTraSach);
        btnCancel = dialog.findViewById(R.id.btnCancelPM);
        btnSave = dialog.findViewById(R.id.btnSavePM);
        edMaPM.setEnabled(false);

        //set ngay thue mac dinh la ngay hom nay
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        tvNgay.setText("Ngày thuê: " + sdf.format(new Date()));

        thanhVienDAO = new ThanhVienDAO (context);
        listThanhVien = new ArrayList<ThanhVien>();
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        thanhVienSpinnerAdapter = new ThanhVienSpinnerAdapter(context,listThanhVien);
        spTV.setAdapter(thanhVienSpinnerAdapter);

        spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maThanhVien = listThanhVien.get(position).getMaTV();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sachDAO = new SachDAO (context);
        listSach = new ArrayList<Sach>();
        listSach = (ArrayList<Sach>) sachDAO.getAll();
        sachSpinnerAdapter = new SachSpinnerAdapter(context, listSach);
        spSach.setAdapter(sachSpinnerAdapter);
        // Lay maLoaiSach
        spSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienThue = listSach.get(position).getGiaThue();
                tvTienThue.setText("Tiền thuê: "+tienThue);

            }
            @Override
            public void onNothingSelected (AdapterView<?> parent) {

            }
        });

        //edit data len form
        if (type != 0){
            edMaPM.setText(String.valueOf(item.getMaPM()));
            for (int i = 0; i < listThanhVien.size(); i++)
                if (item.getMaTV() == (listThanhVien.get(i).getMaTV())){
                    positionTV = i;
                }
            spTV.setSelection(positionTV);
            for (int i = 0; i < listSach.size(); i++)
                if (item.getMaSach() == (listSach.get(i).getMaSach())){
                    positionSach = i;
                }
            spSach.setSelection (positionSach);

            tvNgay.setText("Ngày thuê: "+sdf.format(item.getNgay()));
            tvTienThue.setText("Tiền thuê: "+item.getTienThue());
            if (item.getTraSach()==1){
                chkTraSach.setChecked(true);
            }else {
                chkTraSach.setChecked(false);
            }
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
                item = new PhieuMuon();
                item.setMaSach (maSach);
                item.setMaTV (maThanhVien);
                item.setNgay (new Date());
                item.setTienThue (tienThue);

                if (chkTraSach.isChecked()) {
                    item.setTraSach(1);
                } else {
                    item.setTraSach(0);
                }
                if (type == 0) {
                    //type = 0 (insert)
                    if (dao.insert(item) > 0) {
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //type =1 (update)
                    item.setMaPM
                            (Integer.parseInt(edMaPM.getText().toString()));
                    if (dao.update(item) > 0) {
                        Toast.makeText(context, "Sứa thành công", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(context, "Sứa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatLv();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void xoa (final String Id) {
        //Su dung Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc muốn xoá không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Goi function Delete
                dao.delete(Id);
                //cap nhat listview
                capNhatLv();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        builder.show();
    }
}