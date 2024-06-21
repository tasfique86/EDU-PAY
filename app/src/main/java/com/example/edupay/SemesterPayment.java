package com.example.edupay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SemesterPayment extends AppCompatActivity implements View.OnClickListener {

   private CardView cardViewsemester1,cardViewsemester2,cardViewsemester3,cardViewsemester4,cardViewsemester5,cardViewsemester6,cardViewsemester7,cardViewsemester8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_payment);

        cardViewsemester1=findViewById(R.id.semesterCard1);
        cardViewsemester2=findViewById(R.id.semesterCard2);
        cardViewsemester3=findViewById(R.id.semesterCard3);
        cardViewsemester4=findViewById(R.id.semesterCard4);
        cardViewsemester5=findViewById(R.id.semesterCard5);
        cardViewsemester6=findViewById(R.id.semesterCard6);
        cardViewsemester7=findViewById(R.id.semesterCard7);
        cardViewsemester8=findViewById(R.id.semesterCard8);

        cardViewsemester1.setOnClickListener(this);
        cardViewsemester2.setOnClickListener(this);
        cardViewsemester3.setOnClickListener(this);
        cardViewsemester4.setOnClickListener(this);
        cardViewsemester5.setOnClickListener(this);
        cardViewsemester6.setOnClickListener(this);
        cardViewsemester7.setOnClickListener(this);
        cardViewsemester8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.semesterCard1)
        {
          //  Toast.makeText(SemesterPayment.this,"1 yes",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(SemesterPayment.this,SemesterFee.class);
            intent.putExtra("semesterNo","1st Semester");
            startActivity(intent);
        }
        else if(v.getId()==R.id.semesterCard2)
        {
           // Toast.makeText(SemesterPayment.this,"1 yes",Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(SemesterPayment.this,SemesterFee.class);
            intent.putExtra("semesterNo","2nd Semester");
            startActivity(intent);
        }
        else if(v.getId()==R.id.semesterCard3)
        {
            Intent intent= new Intent(SemesterPayment.this,SemesterFee.class);
            intent.putExtra("semesterNo","3rd Semester");
            startActivity(intent);
        }
        else if(v.getId()==R.id.semesterCard4)
        {
            Intent intent= new Intent(SemesterPayment.this,SemesterFee.class);
            intent.putExtra("semesterNo","4th Semester");
            startActivity(intent);
        }
        else if(v.getId()==R.id.semesterCard5)
        {
            Intent intent= new Intent(SemesterPayment.this, SemesterFee.class);
            intent.putExtra("semesterNo","5th Semester");
            startActivity(intent);
        }
        else if(v.getId()==R.id.semesterCard6)
        {
            Intent intent= new Intent(SemesterPayment.this,SemesterFee.class);
            intent.putExtra("semesterNo","6th Semester");
            startActivity(intent);
        }
        else if(v.getId()==R.id.semesterCard7)
        {
            Intent intent= new Intent(SemesterPayment.this,SemesterFee.class);
            intent.putExtra("semesterNo","7th Semester");
            startActivity(intent);
        }
        else if(v.getId()==R.id.semesterCard8)
        {
            Intent intent= new Intent(SemesterPayment.this,SemesterFee.class);
            intent.putExtra("semesterNo","8th Semester");
            startActivity(intent);
        }

    }
}