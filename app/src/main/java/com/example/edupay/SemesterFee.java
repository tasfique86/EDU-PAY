package com.example.edupay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class SemesterFee extends AppCompatActivity {
    private TextView textViewfee,textViewSemester;

    CountryCodePicker countryCodePicker;
    private EditText editTextTransectionId,editTextbkashNumber;
    private Button buttonsubmit;
    String semesterNo,userRoll,userDepartment,userSession,userHall,userTrans,bKashNum;
    DatabaseReference SemesterFeeReference,feeStudentreference;
    DatabaseReference transectionReference;
    FirebaseUser feefirebaseUser;
    FirebaseAuth authprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_semester_fee);


        countryCodePicker=findViewById(R.id.fee_countrycode);


        textViewfee=findViewById(R.id.textView_fee);
        textViewSemester=findViewById(R.id.textview_semesterNo);
        editTextTransectionId=findViewById(R.id.transection);
        buttonsubmit=findViewById(R.id.submit_buttun);
        editTextbkashNumber=findViewById(R.id.bkashnumber);


        countryCodePicker.registerCarrierNumberEditText(editTextbkashNumber);



        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            semesterNo=bundle.getString("semesterNo");
            textViewSemester.setText(semesterNo);
        }
        else {
            Toast.makeText(SemesterFee.this,"Erorr",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SemesterFee.this, SemesterPayment.class);
            startActivity(intent);
        }

        authprofile=FirebaseAuth.getInstance();
        feefirebaseUser =  authprofile.getCurrentUser();
        if(feefirebaseUser==null)
        {
            Toast.makeText(SemesterFee.this,"Something is Erorr !!, User details are not availave at the moment",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(SemesterFee.this, SemesterPayment.class);
            startActivity(intent);
            return;
        }


            String userID=  feefirebaseUser.getUid();
           // Toast.makeText(SemesterFee.this,userID,Toast.LENGTH_SHORT).show();

            feeStudentreference= FirebaseDatabase.getInstance().getReference("Registered Students");
            feeStudentreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    ReadwriteuserDetails readwriteuserDetails= snapshot.getValue(ReadwriteuserDetails.class);

                    if(readwriteuserDetails!=null)
                    {
                        userRoll = readwriteuserDetails.getRegistrationNo();
                        userDepartment =readwriteuserDetails.getDepartment();
                        userSession=readwriteuserDetails.getSession();
                        userHall=readwriteuserDetails.getHallname();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });







        SemesterFeeReference= FirebaseDatabase.getInstance().getReference("Semester Fee");
        SemesterFeeReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot data: snapshot.getChildren())
                {
                     FeeReadWrite feeReadWrite= data.getValue(FeeReadWrite.class);
                    if(semesterNo.equals("1st Semester") ){
                        textViewfee.setText(feeReadWrite.getS1());
                    }else if(semesterNo.equals("2nd Semester")) {
                        textViewfee.setText(feeReadWrite.getS2());
                    }
                    else if(semesterNo.equals("3rd Semester")){
                        textViewfee.setText(feeReadWrite.getS3());
                    }
                    else if(semesterNo.equals("4th Semester")){
                        textViewfee.setText(feeReadWrite.getS4());
                    }
                    else if(semesterNo.equals("5th Semester")){
                        textViewfee.setText(feeReadWrite.getS5());
                    }
                    else if(semesterNo.equals("6th Semester")){
                        textViewfee.setText(feeReadWrite.getS6());
                    }
                    else if(semesterNo.equals("7th Semester")){
                        textViewfee.setText(feeReadWrite.getS7());
                    }
                    else {
                        textViewfee.setText(feeReadWrite.getS8());
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTrans=editTextTransectionId.getText().toString();
                bKashNum=editTextbkashNumber.getText().toString();


                if(TextUtils.isEmpty(bKashNum)){
                    Toast.makeText(SemesterFee.this,"Please enter valib bkashNumber",Toast.LENGTH_SHORT).show();
                    editTextbkashNumber.setError("bkash Number is require");
                    return;

                }
                else if(TextUtils.isEmpty(userTrans)){
                    Toast.makeText(SemesterFee.this,"Please enter Transection Id",Toast.LENGTH_SHORT).show();
                   editTextTransectionId.setError("Transection Id is require");
                   return;
                }



                transectionReference= FirebaseDatabase.getInstance().getReference("Transection");
                Transactionfunction(transectionReference);

            }
        });
    }
    private void Transactionfunction( DatabaseReference transectionReference){

        String key=transectionReference.push().getKey();

        TransectionReadWrite readWriteAccount=new TransectionReadWrite(userRoll,userDepartment,userSession,userTrans,bKashNum,semesterNo);
        transectionReference.child(key).setValue(readWriteAccount);
        Toast.makeText(SemesterFee.this, "submit Succesfully", Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(SemesterFee.this,UserDashboard.class);
        startActivity(intent);
    }
}

