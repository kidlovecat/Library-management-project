package com.example.library_manager_project.Fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library_manager_project.Adapter.DoanhThuAdapter;
import com.example.library_manager_project.Adapter.LoaiSachSpinnerAdapter;
import com.example.library_manager_project.Adapter.PhieuMuonAdapter;
import com.example.library_manager_project.Dao.LoaiSachDAO;
import com.example.library_manager_project.Dao.PhieuMuonDAO;
import com.example.library_manager_project.Dao.SachDAO;
import com.example.library_manager_project.Model.LoaiSach;
import com.example.library_manager_project.Model.PhieuMuon;
import com.example.library_manager_project.Model.Sach;
import com.example.library_manager_project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DoanhThuFragment extends Fragment {
    Button btnTuNgay, btnDenNgay, btnDoanhThu;
    EditText edTuNgay, edDenNgay;
    TextView tvDoanhThu;
    SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd");
    int mYear, mMonth, mDay;

    Spinner spLoaiSach;

    ListView lvPhieuMuon;

    PhieuMuonDAO phieuMuonDAO;
    ArrayList<PhieuMuon> list;
    DoanhThuAdapter adapter_doanhthu;
    LoaiSachDAO loaiSachDAO;
    ArrayList<LoaiSach> listLoaiSach;
    Sach sach;
    SachDAO sachDAO ;
    ArrayList<Sach> lsSach;
    int maLoaiSach, position;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_doanh_thu_3, container, false);

        edTuNgay = v.findViewById(R.id.edTuNgay);
        edDenNgay = v.findViewById(R.id.edDenNgay);
        tvDoanhThu = v.findViewById(R.id.tvDoanhThu);
        btnTuNgay = v.findViewById(R.id.btnTuNgay);
        btnDenNgay = v.findViewById(R.id.btnDenNgay);
        btnDoanhThu = v.findViewById(R.id.btnDoanhThu);

        spLoaiSach = v.findViewById(R.id.spLoaiSach);
        lvPhieuMuon = v.findViewById(R.id.lvPhieuMuon);


        listLoaiSach = new ArrayList<LoaiSach>();

        loaiSachDAO = new LoaiSachDAO (getActivity());
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        sachDAO = new SachDAO(getActivity());
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();



        String[] arr =new String[listLoaiSach.size()+1];
        arr[0]="Tất cả";
        for(int i = 0; i < listLoaiSach.size(); i++){
            arr[i+1] = listLoaiSach.get(i).getTenLoai();
        }
        spLoaiSach.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_sach_sp, arr));


        capNhatLv();
        tvDoanhThu.setText("Doanh Thu: "+tong(list)+" VND");

        spLoaiSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0)  {

                    listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();
                    for(LoaiSach i: listLoaiSach){
                        if(i.getTenLoai().equalsIgnoreCase(arr[position].toString())){
                            maLoaiSach = i.getMaLoai();
                            break;
                        }
                    }
                    capNhatLv_sp(String.valueOf(maLoaiSach));
//                    Toast.makeText(getActivity().getApplicationContext(), maLoaiSach+"", Toast.LENGTH_SHORT).show();

                }
                else{
                    if(edTuNgay.getText().toString().isEmpty() && edDenNgay.getText().toString().isEmpty()){
                        capNhatLv();
                    }
                    else{
                        capNhatLvBt();
                    }
                }
            }

            @Override
            public void onNothingSelected (AdapterView<?> parent) {

            }
        });

        btnTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateTuNgay, mYear, mMonth, mDay);
                d.show();
            }
        });

        btnDenNgay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog d = new DatePickerDialog(getActivity(), 0, mDateDenNgay, mYear, mMonth, mDay);
            d.show();
        }
        });

        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tuNgay = edTuNgay.getText().toString();
                String denNgay = edDenNgay.getText().toString();
                list = (ArrayList<PhieuMuon>) phieuMuonDAO.getPhieuMuonByDate(edTuNgay.getText().toString(),edDenNgay.getText().toString());
                capNhatLvBt();

            }
        });
        return v;
    }
    void capNhatLvBt(){
        adapter_doanhthu=new DoanhThuAdapter(getActivity(),  this, list);
        lvPhieuMuon.setAdapter(adapter_doanhthu);
        tvDoanhThu.setText("Doanh Thu: "+tong(list)+" VND");
    }
    void capNhatLv(){
        list = (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        adapter_doanhthu=new DoanhThuAdapter(getActivity(),  this, list);
        lvPhieuMuon.setAdapter(adapter_doanhthu);
        tvDoanhThu.setText("Doanh Thu: "+tong(list)+" VND");
    }
    void capNhatLv_sp(String maLoai){
        ArrayList<PhieuMuon> ls_sp = new ArrayList<>() ;
        lsSach = (ArrayList<Sach>) sachDAO.getSachByMaLoai(maLoai);
        ArrayList<PhieuMuon> lsPM ;
        if(edTuNgay.getText().toString().isEmpty() && edDenNgay.getText().toString().isEmpty()){
            lsPM = (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        }
        else{
            lsPM = list;
        }
        for(PhieuMuon i : lsPM){
            for(Sach j : lsSach){
                if(j.getMaSach() == i.getMaSach()){
                    ls_sp.add(i);
                }
            }

        }

        adapter_doanhthu=new DoanhThuAdapter(getActivity(),  this, ls_sp);
        lvPhieuMuon.setAdapter(adapter_doanhthu);
        tvDoanhThu.setText("Doanh Thu: "+tong(ls_sp)+" VND");

    }
    private int tong(List<PhieuMuon> ls){
        int t = 0;
        for(PhieuMuon i: ls){
            t+=i.getTienThue();
        }
        return t;
    }
    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear=year;
            mMonth=monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar (mYear, mMonth, mDay);
            edTuNgay.setText(sdf.format(c.getTime()));
        }
    };
    DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            GregorianCalendar c = new GregorianCalendar (mYear, mMonth, mDay);
            edDenNgay.setText(sdf.format(c.getTime()));
        }
    };
}