package com.example.library_manager_project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private  static  final String DB_NAME = "Library.db";
    private  static final int DB_VERSION = 1;
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    static final String CREATE_TABLE_THU_THU = "CREATE TABLE ThuThu (\n" +
            "    maTT    TEXT PRIMARY KEY,\n" +
            "    hoTen   TEXT NOT NULL,\n" +
            "    matKhau TEXT NOT NULL\n" +
            ");\n";
    static final  String CREATE_TABLE_THANH_VIEN="CREATE TABLE ThanhVien (\n" +
            "    maTV    integer PRIMARY KEY autoincrement,\n" +
            "    hoTen   TEXT NOT NULL,\n" +
            "    namSinh TEXT NOT NULL\n" +
            ");\n";
    static final String CREATE_TABLE_LOAI_SACH="CREATE TABLE LoaiSach (\n" +
            "    maLoai  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    tenLoai TEXT    NOT NULL\n" +
            ");\n";
    static final String CREATE_TABLE_SACH="CREATE TABLE Sach (\n" +
            "    maSach  INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    tenSach TEXT    NOT NULL,\n" +
            "    giaThue INTEGER NOT NULL,\n" +
            "    maLoai  INTEGER REFERENCES LoaiSach (maloai) \n" +
            ");\n";

    static final String CREATE_TABLE_PHIEU_MUON="CREATE TABLE PhieuMuon (\n" +
            "    maPM     INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    maTT     TEXT    REFERENCES ThuThu (maTT),\n" +
            "    maTV     TEXT    REFERENCES ThanhVien (maTV),\n" +
            "    maSach   INTEGER REFERENCES Sach (masach),\n" +
            "    tienThue INTEGER NOT NULL,\n" +
            "    ngay     DATE    NOT NULL,\n" +
            "    traSach  INTEGER NOT NULL\n" +
            ");\n";
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_THU_THU);
        db.execSQL(CREATE_TABLE_PHIEU_MUON);
        db.execSQL(CREATE_TABLE_THANH_VIEN);
        db.execSQL(CREATE_TABLE_SACH);
        db.execSQL(CREATE_TABLE_LOAI_SACH);

        db.execSQL(Data_SQLite.INSERT_THU_THU);
        db.execSQL(Data_SQLite.INSERT_THANH_VIEN);
        db.execSQL(Data_SQLite.INSERT_PHIEU_MUON);
        db.execSQL(Data_SQLite.INSERT_SACH);
        db.execSQL(Data_SQLite.INSERT_LOAI_SACH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v, int v1) {
        String dropTableLoaiThuThu = "drop table if exists ThuThu";
        db.execSQL(dropTableLoaiThuThu);
        String dropTableThanhVien = "drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = "drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);
        String dropTableSach = "drop table if exists Sach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = "drop table if exists PhieuMuon";
        db.execSQL(dropTablePhieuMuon);

        onCreate(db);
    }
}
