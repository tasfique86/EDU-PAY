package com.example.edupay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Result extends AppCompatActivity {
    private EditText editTextsemester1,editTextsemester2,editTextsemester3,editTextsemester4,editTextsemester5,editTextsemester6,
            editTextsemester7,editTextsemester8;


    public String resultRoll,resultdepartment;
    DatabaseReference databaseReferenceResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle bundle=getIntent().getExtras();

        resultRoll=bundle.getString("Roll");
        resultdepartment=bundle.getString("Department");
        if(resultRoll!=null && resultdepartment!=null)
        {
            Toast.makeText(Result.this,resultdepartment+resultRoll,Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(Result.this,"Something is Erorr",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Result.this,UserDashboard.class);
            startActivity(intent);
        }

        editTextsemester1=findViewById(R.id.Edittext_semester_result1);
        editTextsemester2=findViewById(R.id.Edittext_semester_result2);
        editTextsemester3=findViewById(R.id.Edittext_semester_result3);
        editTextsemester4=findViewById(R.id.Edittext_semester_result4);
        editTextsemester5=findViewById(R.id.Edittext_semester_result5);
        editTextsemester6=findViewById(R.id.Edittext_semester_result6);
        editTextsemester7=findViewById(R.id.Edittext_semester_result7);
        editTextsemester8=findViewById(R.id.Edittext_semester_result8);









        databaseReferenceResult= FirebaseDatabase.getInstance().getReference("Academic Result of CSE");


        databaseReferenceResult.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data: snapshot.getChildren())
                {
                    ResultReadWrit resultReadWrit= data.getValue(ResultReadWrit.class);


                    String sR=resultReadWrit.getRoll(),sD=resultReadWrit.getDepartment();
                   // Toast.makeText(Result.this,sR+"+"+sD,Toast.LENGTH_SHORT).show();
                    if(sR.equals(resultRoll))
                    {

                       // Toast.makeText(Result.this,"Find !!",Toast.LENGTH_SHORT).show();
                        editTextsemester1.setText(resultReadWrit.getS1());

                        editTextsemester2.setText(resultReadWrit.getS2());


                        editTextsemester3.setText(resultReadWrit.getS3());


                        editTextsemester4.setText(resultReadWrit.getS4());


                        editTextsemester5.setText(resultReadWrit.getS5());


                        editTextsemester6.setText(resultReadWrit.getS6());


                        editTextsemester7.setText(resultReadWrit.getS7());


                        editTextsemester8.setText(resultReadWrit.getS8());


                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Result.this,"Erorr",Toast.LENGTH_SHORT).show();
            }
        });


    }
}