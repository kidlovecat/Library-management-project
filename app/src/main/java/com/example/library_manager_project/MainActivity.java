package com.example.library_manager_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.library_manager_project.Fragment.ChangePassFragment;
import com.example.library_manager_project.Fragment.DoanhThuFragment;
import com.example.library_manager_project.Fragment.PhieuMuonFragment;
import com.example.library_manager_project.Fragment.SachFragment;
import com.example.library_manager_project.Fragment.ThanhVienFragment;
import com.example.library_manager_project.Fragment.ThuThuFragment;
import com.example.library_manager_project.Fragment.TopFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    Toolbar toolbar;
    View mHeaderView;
    TextView edUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer= findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);

        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();

        ab.setHomeAsUpIndicator(R.drawable.menu); //sua
        ab.setDisplayHomeAsUpEnabled(true);

        Intent t = getIntent();
        String username = t.getStringExtra("user");

        NavigationView nv = findViewById(R.id.nvView);
        //show user in header
        mHeaderView = nv.getHeaderView(  0);
        edUser = mHeaderView.findViewById(R.id.txtUser);
        Intent i= getIntent();


        edUser.setText("Welcome "+"!");

        FragmentManager manager = getSupportFragmentManager();
        setTitle("Quản lý phiếu mượn");

        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
        manager.beginTransaction().replace(R.id.flContent, phieuMuonFragment).commit();

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_ThuThu){
                    if(username.equalsIgnoreCase("admin")) {
                        setTitle("Quản lý Thủ Thư");
                        ThuThuFragment thuThuFragment = new ThuThuFragment();
                        manager.beginTransaction().replace(R.id.flContent, thuThuFragment).commit();
                        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Chỉ có admin mới được vào", Toast.LENGTH_SHORT).show();
                    }
                }

                if(item.getItemId() == R.id.nav_PhieuMuon){
                    setTitle("Quản lý phiếu mượn");
                    PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
                    manager.beginTransaction().replace(R.id.flContent, phieuMuonFragment).commit();
                }

                if(item.getItemId() == R.id.nav_Sach ){
                    setTitle("Quản lý sách");
                    SachFragment sachFragment = new SachFragment();
                    manager.beginTransaction().replace(R.id.flContent, sachFragment).commit();
                }
                if(item.getItemId() == R.id.nav_ThanhVien){
                    setTitle("Quản lý thành viên");
                    ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                    manager.beginTransaction().replace(R.id.flContent,thanhVienFragment).commit();
                }

                if(item.getItemId() == R.id.sub_Top){
                    setTitle("Top 10 sách cho thuê nhiều nhất");
                    TopFragment topFragment = new TopFragment();
                    manager.beginTransaction().replace(R.id.flContent, topFragment).commit();
                }
                if(item.getItemId() == R.id.sub_DoanhThu){
                    setTitle("Thống kê doanh thu");
                    DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                    manager.beginTransaction().replace(R.id.flContent, doanhThuFragment).commit();
                }

                if(item.getItemId() == R.id.sub_Pass){
                    setTitle("Thay đổi mật khẩu");
                    ChangePassFragment changePassFragment = new ChangePassFragment();
                    manager.beginTransaction().replace(R.id.flContent,changePassFragment).commit();
                }
                if(item.getItemId() == R.id.sub_Logout){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    finish();
                }

                drawer.closeDrawers();
                return false;
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            drawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }
}