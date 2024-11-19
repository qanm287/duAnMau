package fpl.quangnm.duanmau_ph39820.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.data.DbHelper;
import fpl.quangnm.duanmau_ph39820.model.ThanhVien;

public class ThanhVienDAO {
    private DbHelper dbHelper;
    public ThanhVienDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public ArrayList<ThanhVien> getDSThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;

        try {
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close(); // Đảm bảo đóng con trỏ
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close(); // Đảm bảo đóng SQLiteDatabase
            }
        }

        return list;
    }
    public boolean themThanhVien(String hoten, String namsinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten",hoten);
        contentValues.put("namsinh",namsinh);
        long check = sqLiteDatabase.insert("THANHVIEN", null,contentValues);
        if (check == -1){
            return false;
        } return true;
    }
    public boolean capNhatThongTinThanhVien(int matv, String hoten, String namsinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoten",hoten);
        contentValues.put("namsinh",namsinh);
        long check = sqLiteDatabase.update("THANHVIEN", contentValues,"matv = ?", new String[]{ String.valueOf(matv)});
        if (check== -1){
            return false;
        } return true;
    }
    // int 1 xoa thanh cong   - 0: xoa that bai & -1 tim thay thanh vien dang co phieu muon nen k xoa dc
    public int xoaThongTinThanhVien(int matv){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE matv=?",new String[]{String.valueOf(matv)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = sqLiteDatabase.delete("THANHVIEN","matv=?", new String[]{String.valueOf(matv)});
        if (check == -1){
            return 0;
        }
        return 1;
    }
}
