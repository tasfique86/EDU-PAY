package com.example.edupay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class F_Remove extends AppCompatActivity {

    EditText editTextCourse,editTextSemester;

    String Usession,URoll,UDepartment;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fremove);

        Usession=getIntent().getExtras().getString("Fsession");
        URoll=getIntent().getExtras().getString("FRoll");
        UDepartment=getIntent().getExtras().getString("FDepartment");



        editTextCourse=findViewById(R.id.textView_fremove_fee);
        editTextSemester=findViewById(R.id.textView_fremove_semester);
        button=findViewById(R.id.submit_fremove_buttun);



        button.setOnClickListener(v -> {
            String semester=editTextSemester.getText().toString();
            String course =editTextCourse.getText().toString();
            if(semester.isEmpty())
            {
                editTextSemester.setError("Enter semester no!!");
                editTextSemester.requestFocus();
                return;
            }else if(course.isEmpty())
            {
                editTextCourse.setError("Enter total course !!");
                editTextCourse.requestFocus();
                return;
            }
            Intent intent =new Intent(F_Remove.this,F_RemoveTotalfee.class);
            intent.putExtra("FFsemester",semester);
            intent.putExtra("FFcourse",course);
            intent.putExtra("FFDepartment",UDepartment);
            intent.putExtra("FFRoll",URoll);
            intent.putExtra("FFsession",Usession);
            startActivity(intent);

        });


    }
}