package fpl.quangnm.duanmau_ph39820.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpl.quangnm.duanmau_ph39820.data.DbHelper;
import fpl.quangnm.duanmau_ph39820.model.PhieuMuon;

public class PhieuMuonDAO {
    private DbHelper dbHelper;
    public PhieuMuonDAO(Context context){
        dbHelper = new DbHelper(context);
    }
    public ArrayList<PhieuMuon> getDSPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Truy vấn dữ liệu
            cursor = sqLiteDatabase.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue \n" +
                    "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc \n" +
                    "WHERE pm.matv = tv.matv AND pm.matt = tt.matt AND pm.masach = sc.masach ORDER BY pm.mapm DESC", null);

            // Kiểm tra nếu Cursor không rỗng
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Thêm dữ liệu vào danh sách
                    list.add(new PhieuMuon(
                            cursor.getInt(0),   // mapm
                            cursor.getInt(1),   // matv
                            cursor.getString(2),// hoten (ThanhVien)
                            cursor.getString(3),// matt
                            cursor.getString(4),// hoten (ThuThu)
                            cursor.getInt(5),   // masach
                            cursor.getString(6),// tensach
                            cursor.getString(7),// ngay
                            cursor.getInt(8),   // trasach
                            cursor.getInt(9)    // tienthue
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng Cursor sau khi sử dụng
            if (cursor != null) {
                cursor.close();
            }
        }

        return list;
    }

    public boolean thaydoiTrangThai(int mapm){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trasach", 1);
        long check = sqLiteDatabase.update("PHIEUMUON",contentValues,"mapm=?", new String[]{String.valueOf(mapm)});
        if (check == -1){
            return false;
        } else return true;
    }

    /*               mapm INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    matv INTEGER REFERENCES THANHVIEN(matv),\n" +
                "    matt TEXT REFERENCES THUTHU(matt),\n" +
                "    masach INTEGER REFERENCES SACH(masach),\n" +
                "    ngay TEXT,\n" +
                "    trasach INTEGER,\n" +
                "    tienthue INTEGER\n" +
     */
    public boolean themPhieuMuon(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
      //  contentValues.put("mapm",phieuMuon.getMapm());
        contentValues.put("matv",phieuMuon.getMatv());
        contentValues.put("matt",phieuMuon.getMatt());
        contentValues.put("masach",phieuMuon.getMasach());
        contentValues.put("ngay",phieuMuon.getNgay());
        contentValues.put("trasach",phieuMuon.getTrsach());
        contentValues.put("tienthue",phieuMuon.getTienthue());
        long check = sqLiteDatabase.insert("PHIEUMUON",null,contentValues);
        if (check == -1){
            return false;
        } else return true;
    }
}
