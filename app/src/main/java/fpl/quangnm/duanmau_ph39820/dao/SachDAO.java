package fpl.quangnm.duanmau_ph39820.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.data.DbHelper;
import fpl.quangnm.duanmau_ph39820.model.Sach;

public class SachDAO {
    private DbHelper dbHelper;
    public SachDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    // lay toan bo dau sach co trong thu virn
    public ArrayList<Sach> getDSDauSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = null;
        Cursor cursor = null;
        try {
            sqLiteDatabase = dbHelper.getReadableDatabase();
            cursor = sqLiteDatabase.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloai, lo.tenloai FROM SACH sc, LOAISACH lo WHERE sc.maloai = lo.maloai", null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu cần
        } finally {
            if (cursor != null) {
                cursor.close(); // Đảm bảo đóng Cursor sau khi sử dụng
            }
            if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close(); // Đảm bảo đóng SQLiteDatabase sau khi sử dụng
            }
        }
        return list;
    }

    public boolean themSachMoi(String tensach, int giatien, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach", tensach);
        contentValues.put("giathue", giatien);
        contentValues.put("maloai", maloai);

        long check = sqLiteDatabase.insert("SACH", null,contentValues);
        if (check == -1){
            return false;
        } return true;
    }

    public boolean capNhatThongTinSach(int masach,String tensach, int giathue, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tensach",tensach);
        contentValues.put("giathue",giathue);
        contentValues.put("maloai",maloai);
        long check = sqLiteDatabase.update("SACH",contentValues,"masach=?", new String[]{String.valueOf(masach)});
        if (check == -1){
            return false;
        }return true;
    }

    // 1xoa thanh cong xoa that bai khong dc phep xoa khi sach cp trong phieu muon
    public int xoaSach(int masach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE masach = ?", new String[]{String.valueOf(masach)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = sqLiteDatabase.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});
        if (check == -1){
            return 0;
        } else {
            return 1;
        }
    }

}
