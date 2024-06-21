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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

public class F_RemoveTotalfee extends AppCompatActivity {

    String Semester,Totalfee;
    private TextView textViewfee;
    CountryCodePicker countryCodePicker;
    private EditText editTextTransectionId,editTextbkashNumber;
    private Button buttonsubmit;

    DatabaseReference transectionReference;
    String semesterNo,userRoll,userDepartment,userSession,userHall,userTrans,bKashNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fremove_totalfee);

        Semester=getIntent().getExtras().getString("FFsemester");
        Totalfee=getIntent().getExtras().getString("FFcourse");
        userSession=getIntent().getExtras().getString("FFsession");
        userRoll=getIntent().getExtras().getString("FFRoll");
        userDepartment=getIntent().getExtras().getString("FFDepartment");

        float totalValue = Float.parseFloat(Totalfee);
        totalValue= (float) ((totalValue*300.0)+300);
        countryCodePicker=findViewById(R.id.fee_countrycode_remove);


        textViewfee=findViewById(R.id.textView_fee_remove);
        editTextTransectionId=findViewById(R.id.transection_remove);
        buttonsubmit=findViewById(R.id.submit_buttun_remove);
        editTextbkashNumber=findViewById(R.id.bkashnumbe_remove);


        String amount=String.valueOf(totalValue);
        textViewfee.setText(amount);



        countryCodePicker.registerCarrierNumberEditText(editTextbkashNumber);


        buttonsubmit.setOnClickListener(v -> {

            String semester=Semester;
            userTrans=editTextTransectionId.getText().toString();
            bKashNum=editTextbkashNumber.getText().toString();

            if(TextUtils.isEmpty(bKashNum)){
                Toast.makeText(F_RemoveTotalfee.this,"Please enter valib bkashNumber",Toast.LENGTH_SHORT).show();
                editTextbkashNumber.setError("bkash Number is require");
                return;

            }
            else if(TextUtils.isEmpty(userTrans)){
                Toast.makeText(F_RemoveTotalfee.this,"Please enter Transection Id",Toast.LENGTH_SHORT).show();
                editTextTransectionId.setError("Transection Id is require");
                return;
            }



            transectionReference= FirebaseDatabase.getInstance().getReference("Transection");
            //Transactionfunction(transectionReference);
            String key=transectionReference.push().getKey();

            TransectionReadWrite readWriteAccount=new TransectionReadWrite(userRoll,userDepartment,userSession,userTrans,bKashNum,Semester);
            transectionReference.child(key).setValue(readWriteAccount).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(F_RemoveTotalfee.this, "submit Succesfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(F_RemoveTotalfee.this,UserDashboard.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(F_RemoveTotalfee.this, "Something is error!!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


        });




    }
    private void Transactionfunction( DatabaseReference transectionReference){

        String key=transectionReference.push().getKey();

        TransectionReadWrite readWriteAccount=new TransectionReadWrite(userRoll,userDepartment,userSession,userTrans,bKashNum,Semester);
        transectionReference.child(key).setValue(readWriteAccount).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(F_RemoveTotalfee.this, "submit Succesfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(F_RemoveTotalfee.this,UserDashboard.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(F_RemoveTotalfee.this, "Something is error!!", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}