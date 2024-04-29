package com.example.library_manager_project.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.library_manager_project.Adapter.TopAdapter;
import com.example.library_manager_project.Dao.PhieuMuonDAO;
import com.example.library_manager_project.Model.Top;
import com.example.library_manager_project.R;

import java.util.ArrayList;

public class TopFragment extends Fragment {

    ListView lv;
    ArrayList<Top> list;
    TopAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_top_2, container, false);
        lv=v.findViewById(R.id.lvTop);
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO (getActivity());
        list = (ArrayList<Top>) phieuMuonDAO.getTop();
        adapter = new TopAdapter(getActivity(),  this, list);
        lv.setAdapter(adapter);
        return v;
    }
}