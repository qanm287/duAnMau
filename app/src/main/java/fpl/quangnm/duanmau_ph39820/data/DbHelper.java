package fpl.quangnm.duanmau_ph39820.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "DANGKYMONHOC", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // bang thu thu
        String dbThuTHu = "CREATE TABLE THUTHU (\n" +
                "    matt TEXT PRIMARY KEY,\n" +
                "    hoten TEXT,\n" +
                "    matkhau TEXT,\n" +
                "    loaitaikhoan TEXT\n" +
                ");\n";
        sqLiteDatabase.execSQL(dbThuTHu);

        // bang thanh vien
        String dbThanhVien = "CREATE TABLE THANHVIEN (\n" +
                "    matv INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    hoten TEXT,\n" +
                "    namsinh TEXT\n" +
                ");\n";
        sqLiteDatabase.execSQL(dbThanhVien);

        // bang Loai Sach
        String dbLoai = "CREATE TABLE LOAISACH (\n" +
                "    maloai INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    tenloai TEXT\n" +
                ");\n";
        sqLiteDatabase.execSQL(dbLoai);

        // bang sach
        String dbSach = "CREATE TABLE SACH (\n" +
                "    masach INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    tensach TEXT,\n" +
                "    giathue INTEGER,\n" +
                "    maloai INTEGER,\n" +
                "    FOREIGN KEY (maloai) REFERENCES LOAISACH(maloai)\n" +
                ");\n";
        sqLiteDatabase.execSQL(dbSach);

        // bang phieu muon
        String dbPhieuMuon = "CREATE TABLE PHIEUMUON (\n" +
                "    mapm INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    matv INTEGER REFERENCES THANHVIEN(matv),\n" +
                "    matt TEXT REFERENCES THUTHU(matt),\n" +
                "    masach INTEGER REFERENCES SACH(masach),\n" +
                "    ngay TEXT,\n" +
                "    trasach INTEGER,\n" +
                "    tienthue INTEGER\n" +
                ");\n";
        sqLiteDatabase.execSQL(dbPhieuMuon);
        // tra sach:  1 da tr   0 chua tra
        sqLiteDatabase.execSQL("INSERT INTO THANHVIEN VALUES(1,'Nguyễn Minh Quang','2004'),(2,'Bùi Xuân Huấn','2009')");
        sqLiteDatabase.execSQL("INSERT INTO LOAISACH VALUES(1,'Thieu Nhi'),(2,'Tinh Cam'),(3,'Sach Giao Khoa')");
        sqLiteDatabase.execSQL("INSERT INTO SACH VALUES(1,'Hay doi day',2500,1),(2,'Thang cuoi',1000,1),(3,'Lap trinh android',3500,3)");
        sqLiteDatabase.execSQL("INSERT INTO THUTHU VALUES('thuthu01','Nguyen Van Anh','123','Admin'),('thuthu02','Trần Văn Cường','1234','Thủ thư')");
        sqLiteDatabase.execSQL("INSERT INTO PHIEUMUON VALUES(1,1,'thuthu01',1,'2021/09/21',1,2500),(2,2,'thuthu01',3,'2022/09/21',0,2000),(3,2,'thuthu02',1,'2023/09/21',1,2000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1){
            sqLiteDatabase.execSQL("drop table if exists ThuThu");
            sqLiteDatabase.execSQL("drop table if exists ThanhVien");
            sqLiteDatabase.execSQL("drop table if exists LoaiSach");
            sqLiteDatabase.execSQL("drop table if exists Sach");
            sqLiteDatabase.execSQL("drop table if exists PhieuMuon");
            onCreate(sqLiteDatabase);
        }
    }
}
