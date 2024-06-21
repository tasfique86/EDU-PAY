package com.example.edupay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class StudentsDetails extends AppCompatActivity {
    private TextView editTextregisterFullname,getEditTextregisterId,getEditTextregisterEmail,editTextregisterBoD,getEditTextregisterMobile,editTextgender,
           getEditTextregisterDepart,editTextfathername,editTextmothername, editTexthallname,editTextsession,editTextreligion,editTextnationality,editTextaddress;
    DatabaseReference Studentreference;
    FirebaseAuth authprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_details);


        editTextregisterFullname=findViewById(R.id.textView_UserDetails_studentname);
        getEditTextregisterDepart=findViewById(R.id.textView_UserDetails_Department);
        getEditTextregisterId=findViewById(R.id.textView_UserDetails_RegisterNo);
        getEditTextregisterEmail=findViewById(R.id.textView_UserDetails_email);
        getEditTextregisterMobile=findViewById(R.id.textView_UserDetails_mobile);

        editTextregisterBoD=findViewById(R.id.textView_UserDetails_dob);
        editTextnationality=findViewById(R.id.textView_UserDetails_Nationality);
        editTextaddress=findViewById(R.id.textView_UserDetails_ParmenetAddress);
        editTextfathername=findViewById(R.id.textView_UserDetails_Fathername);
        editTextmothername=findViewById(R.id.textView_UserDetails_Mothername);
        editTexthallname=findViewById(R.id.textView_UserDetails_Hallname);
        editTextreligion=findViewById(R.id.textView_UserDetails_Religion);
        editTextsession=findViewById(R.id.textView_UserDetails_session);
        editTextgender=findViewById(R.id.textView_UserDetails_Gender);



        authprofile=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser= authprofile.getCurrentUser();

        if(firebaseUser==null)
        {
            Toast.makeText(StudentsDetails.this,"Something is Erorr !!, User details are not availave at the moment",Toast.LENGTH_SHORT).show();
        }
        else
        {
            showUserprofile(firebaseUser);
        }






    }




    private void showUserprofile(FirebaseUser firebaseUser) {
        String userID=  firebaseUser.getUid();

        Studentreference= FirebaseDatabase.getInstance().getReference("Registered Students");
        Studentreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ReadwriteuserDetails readwriteuserDetails= snapshot.getValue(ReadwriteuserDetails.class);

                editTextregisterFullname.setText(readwriteuserDetails.getFullname());
                getEditTextregisterDepart.setText(readwriteuserDetails.getDepartment());
                getEditTextregisterEmail.setText(readwriteuserDetails.getEmail());
                getEditTextregisterId.setText(readwriteuserDetails.getRegistrationNo());
                getEditTextregisterMobile.setText(readwriteuserDetails.getMobileNo());
                editTexthallname.setText(readwriteuserDetails.getHallname());
                editTextsession.setText(readwriteuserDetails.getSession());
                editTextfathername.setText(readwriteuserDetails.getFathernaem());
                editTextmothername.setText(readwriteuserDetails.getMothername());
                editTextreligion.setText(readwriteuserDetails.getReligion());
                editTextgender.setText(readwriteuserDetails.getGender());
                editTextaddress.setText(readwriteuserDetails.getAddress());
                editTextregisterBoD.setText(readwriteuserDetails.getDob());
                editTextnationality.setText(readwriteuserDetails.getNationality());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}