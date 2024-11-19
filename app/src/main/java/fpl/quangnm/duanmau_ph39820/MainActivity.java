package fpl.quangnm.duanmau_ph39820;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import fpl.quangnm.duanmau_ph39820.dao.ThuThuDAO;
import fpl.quangnm.duanmau_ph39820.fragment.QlLoaiSachFragment;
import fpl.quangnm.duanmau_ph39820.fragment.QlPhieuMuonFragment;
import fpl.quangnm.duanmau_ph39820.fragment.QlSachFragment;
import fpl.quangnm.duanmau_ph39820.fragment.QlThanhVienFragment;
import fpl.quangnm.duanmau_ph39820.fragment.ThongKeDoanhThuFragment;
import fpl.quangnm.duanmau_ph39820.fragment.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toobar);
        FrameLayout framlayout = findViewById(R.id.framlayout);
        NavigationView navigationView =findViewById(R.id.navgation);
        drawerLayout = findViewById(R.id.mainChinh);
        View headerLayout = navigationView.getHeaderView(0);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_headName = findViewById(R.id.tv_headName);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // hien thi nut menu
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                int id = menuItem.getItemId();
                if (id == R.id.nav_PhieuMuon) {
                    fragment = new QlPhieuMuonFragment();
                } else if (id == R.id.nav_LoaiSach) {
                    fragment = new QlLoaiSachFragment();
                } else if (id == R.id.sub_Pass) {
                    showDialogDoiMatKhau();
                }else if (id == R.id.sub_Top) {
                    fragment = new ThongKeTop10Fragment();
                }else if (id == R.id.nav_Sach) {
                    fragment = new QlSachFragment();
                }else if (id == R.id.sub_DoanhThu) {
                    fragment = new ThongKeDoanhThuFragment();
                } else if (id == R.id.nav_ThanhVien) {
                    fragment = new QlThanhVienFragment();
                } else if (id == R.id.sub_Logout) {
                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else {
                    fragment = new QlPhieuMuonFragment();
                }

                if (fragment != null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.framlayout, fragment)
                            .commit();
                    toolbar.setTitle(menuItem.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN",MODE_PRIVATE);
        String loaiTK = sharedPreferences.getString("loaitaikhoan","");
        if (!loaiTK.equals("Admin")){
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.sub_DoanhThu).setVisible(false);
            menu.findItem(R.id.sub_Top).setVisible(false);
        }
        String hoTen = sharedPreferences.getString("hoten","");
        tv_headName.setText("Xin chào "+hoTen);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
    private void showDialogDoiMatKhau(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Cap nhat",null)
                .setPositiveButton("Huy",null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau,null);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView tv_dmk = view.findViewById(R.id.tv_dmk);
        EditText edtpassold = view.findViewById(R.id.edtpassold);
        EditText edtpassnew = view.findViewById(R.id.edtpassnew);
        EditText edtcfpassnew = view.findViewById(R.id.edtcfpassnew);
        builder.setView(view);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpass = edtpassold.getText().toString();
                String newpass = edtpassnew.getText().toString();
                String repass = edtcfpassnew.getText().toString();
                // Kiểm tra nếu mật khẩu cũ và mật khẩu mới trùng nhau
                if (oldpass.equals(newpass)) {
                    Toast.makeText(MainActivity.this, "Mật khẩu mới không được trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
                } else if (!newpass.equals(repass)) {
                    // Kiểm tra nếu mật khẩu mới và xác nhận mật khẩu không khớp
                    Toast.makeText(MainActivity.this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                } else {
                    // Thực hiện cập nhật mật khẩu nếu các điều kiện đều hợp lệ
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    String matt = sharedPreferences.getString("matt", "");
                    ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                    boolean check = thuThuDAO.capNhatMatKhau(matt, oldpass, newpass);
                    if (check) {
                        Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "Cập nhật không thành công. Kiểm tra lại mật khẩu cũ.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}