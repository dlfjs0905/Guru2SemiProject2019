package com.example.semiprojectsample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.db.FileDB;

public class LoginActivity extends AppCompatActivity {

    private EditText mEdtId, mEdtpw;
    CheckBox Auto_LogIn;
    SharedPreferences setting;
    SharedPreferences.Editor editor;
    private boolean saveLoginData;
    private String id;
    private String pwd;

    private SharedPreferences appData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEdtId = findViewById(R.id.edtId);
        mEdtpw = findViewById(R.id.edtPw);
        Auto_LogIn = findViewById(R.id.Auto_LogIn);
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnJoin = findViewById(R.id.btnJoin);

        btnLogin.setOnClickListener(mBtnLoginClick);
        btnJoin.setOnClickListener(mBtnJoinClick);

        setting = getSharedPreferences("setting", 0);
        editor= setting.edit();

        // 설정값 불러오기
        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        // 이전에 로그인 정보를 저장시킨 기록이 있다면
        if (saveLoginData) {
            mEdtId.setText(id);
            mEdtpw.setText(pwd);
            Auto_LogIn.setChecked(saveLoginData);
        }
    }//end onCreate()


    //로그인 버튼 클릭 이벤트
    private View.OnClickListener mBtnLoginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String memId = mEdtId.getText().toString();
            String memPw = mEdtpw.getText().toString();

            MemberBean memberBean = FileDB.getFindMember(LoginActivity.this, memId);
            if (memberBean == null) {
                Toast.makeText(LoginActivity.this, "해당 아이디는 가입되어있지 않습니다.", Toast.LENGTH_LONG).show();
                return;
            }
            //패스워드 비교
            if (TextUtils.equals(memberBean.memPw, memPw)) {
                FileDB.setLoginMember(LoginActivity.this, memberBean); //저장
                //비밀번호 일치
                save();
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                return;
            }
        }
    };

    //회원가입 버튼 클릭 이벤트
    private View.OnClickListener mBtnJoinClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent ii = new Intent(LoginActivity.this, CameraCapture2Activity.class);
            startActivity(ii);
        }
    };

    // 설정값을 저장하는 함수
    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", Auto_LogIn.isChecked());
        editor.putString("ID", mEdtId.getText().toString().trim());
        editor.putString("PWD", mEdtpw.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }
}
