package com.example.library_manager_project.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;
    public ThanhVienDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    public  long insert (ThanhVien obj){
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());

        return db.insert("ThanhVien",null, values);
    }

    public int update(ThanhVien obj){
        ContentValues values = new ContentValues();

        values.put("hoTen", obj.getHoTen());
        values.put("namSinh", obj.getNamSinh());

        return db.update("ThanhVien", values, "maTV = ?", new String[]{String.valueOf(obj.getMaTV())});
    }

    public int delete(String id){
        return db.delete("ThanhVien", "maTV=?", new String[]{id});
    }
    @SuppressLint("Range")
    public List<ThanhVien> getData(String sql, String...selectionArgs) {
        List<ThanhVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            ThanhVien obj = new ThanhVien();
            obj.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setNamSinh (c.getString(c.getColumnIndex("namSinh")));
            list.add(obj);
        }
        return list;
    }
    public  List<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }

    public ThanhVien getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> list = getData(sql,id);
        return list.get(0);
    }

    public List<ThanhVien> getThanhVienByName(String s){
        String sql = "SELECT * FROM ThanhVien WHERE hoTen='%?%'";
        List<ThanhVien> list = getData(sql,s);
        return list;
    }
}
