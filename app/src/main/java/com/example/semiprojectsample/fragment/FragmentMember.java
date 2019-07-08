package com.example.semiprojectsample.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.activity.CameraCapture2Activity;
import com.example.semiprojectsample.activity.LoginActivity;
import com.example.semiprojectsample.activity.MainActivity;
import com.example.semiprojectsample.activity.NewMemoActivity;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.db.FileDB;

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Member;


public class FragmentMember extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member, container, false);

        ImageView imgProfile = view.findViewById(R.id.imgProfile);
        TextView txtMemId = view.findViewById(R.id.txtMemId);
        TextView txtMemName = view.findViewById(R.id.txtMemName);
        TextView txtMemPw = view.findViewById(R.id.txtMemPw);
        TextView txtMemDate = view.findViewById(R.id.txtMemDate);

        //파일 DB에서 가져온다.
        MemberBean memberBean = FileDB.getLoginMember(getActivity());

        imgProfile.setImageURI(Uri.fromFile(new File(memberBean.photoPath)));
        txtMemId.setText("ID : " + memberBean.memId);
        txtMemName.setText("이름 : " + memberBean.memName);
        txtMemPw.setText("비밀번호 : " + memberBean.memPw);
        txtMemDate.setText("가입한 날짜 : " + memberBean.memRegDate);


        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity()).setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?").setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();
            }
        });


        return view;
    }
}