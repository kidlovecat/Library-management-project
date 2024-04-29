package com.example.library_manager_project.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.library_manager_project.Model.ThanhVien;
import com.example.library_manager_project.Model.ThuThu;
import com.example.library_manager_project.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context){
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert (ThuThu obj) {
        ContentValues values = new ContentValues();
        values.put("maTT", obj.getMaTT());
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.insert(  "ThuThu", null, values);
    }

    public int updatePass (ThuThu obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.getHoTen());
        values.put("matKhau", obj.getMatKhau());
        return db.update("ThuThu", values, "maTT=?", new String[]{obj.getMaTT()});
    }

    public int update(ThuThu obj){
        ContentValues values = new ContentValues();

        values.put("hoTen", obj.getHoTen());
        values.put("MatKhau", obj.getMatKhau());

        return db.update("ThuThu", values, "maTT = ?", new String[]{obj.getMaTT()});
    }
    public int delete(String id) {
        return db.delete(  "ThuThu",  "maTT=?", new String[]{id});
    }

    @SuppressLint("Range")
    public List<ThuThu> getData(String sql, String...selectionArgs) {
        List<ThuThu> list = new ArrayList<ThuThu>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            ThuThu obj = new ThuThu();
            obj.setMaTT(c.getString(c.getColumnIndex("maTT")));
            obj.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            obj.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            list.add(obj);

        }
        return list;
    }
    public List<ThuThu> getAll() {
        String sql = "SELECT * FROM ThuThu";
        return getData(sql);
    }
    //get data theo id
    public ThuThu getID (String id) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }
    public int checkLogin(String id, String password) {
        String sql = "SELECT * FROM ThuThu WHERE maTT=? AND matkhau=?";
        List<ThuThu> list = getData(sql, id, password);
        if (list.size() == 0)
            return -1;
        return 1;
    }
}
