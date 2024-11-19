package fpl.quangnm.duanmau_ph39820;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpl.quangnm.duanmau_ph39820.dao.ThuThuDAO;

public class DangNhapActivity extends AppCompatActivity {
    private EditText edtUser,edtPass;
    private Button btnLogIn,btnRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        edtPass = findViewById(R.id.edtPass);
        edtUser = findViewById(R.id.edtUser);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnRes = findViewById(R.id.btnRes);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user= edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if (thuThuDAO.checkDangNhap(user,pass)){
                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(DangNhapActivity.this, "User & Pass khong dung", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}