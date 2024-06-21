package com.example.edupay;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edupay.model.AuthReadWrite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class loginPhoneNumber extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText editTextphone,editTextregistrationNo,editTextdepartment;
    Button buttonsendOTP;
    ProgressBar progressBar;


    DatabaseReference databaseReferenceResult;
  //  String registratID,department;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone_number);

        countryCodePicker=findViewById(R.id.login_countrycode);
        editTextphone=findViewById(R.id.login_modile_number);
        buttonsendOTP=findViewById(R.id.send_otp_btn);
        progressBar=findViewById(R.id.login_progressbar);
        editTextdepartment=findViewById(R.id.login_user_department);
        editTextregistrationNo=findViewById(R.id.login_user_registrationNo);




        progressBar.setVisibility(View.GONE);

        countryCodePicker.registerCarrierNumberEditText(editTextphone);
        buttonsendOTP.setOnClickListener((v)->{

            int find=0;

            String registratID=editTextregistrationNo.getText().toString();
            String department=editTextdepartment.getText().toString();
            String mobile=editTextphone.getText().toString();

            if(!countryCodePicker.isValidFullNumber()){
                editTextphone.setError("Phone number is not valid");
             return;
            }
            else if(TextUtils.isEmpty(registratID)){
                editTextregistrationNo.setError("Registration Id is not valid");
                return;
            }
            else if(TextUtils.isEmpty(department)){
                editTextdepartment.setError("Department is not valid");
                return;
            }

            databaseReferenceResult= FirebaseDatabase.getInstance().getReference("Auth Students");

            //Toast.makeText(loginPhoneNumber.this,mobile+" <> "+registratID,Toast.LENGTH_SHORT).show();
                databaseReferenceResult.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot data: snapshot.getChildren())
                        {
                            AuthReadWrite authReadWrite = data.getValue(AuthReadWrite.class);


                            String sR=authReadWrite.getRegistrationID(),sM=authReadWrite.getReg_Mobile();
                      //       Toast.makeText(loginPhoneNumber.this,sR+"+"+sM,Toast.LENGTH_SHORT).show();
                            if(sR.equals(registratID)&&sM.equals(mobile))
                            {
                                Intent intent=new Intent(loginPhoneNumber.this,loginOtp.class);
                                intent.putExtra("userRoll",registratID);
                                intent.putExtra("userDeparment",department);
                                intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
                                startActivity(intent);
                            }

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(loginPhoneNumber.this,"Erorr",Toast.LENGTH_SHORT).show();
                    }
                });


//            if(find==0) {
//                editTextphone.setError("Phone number is not valid");
//                Toast.makeText(loginPhoneNumber.this, "Invalid Mobile or RegistratonId", Toast.LENGTH_SHORT).show();
//                return;
//            }


        });
    }
}


