package com.example.edupay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.edupay.model.UserModel;
import com.example.edupay.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.StartupTime;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextregisterFullname,getEditTextregisterId,getEditTextregisterEmail,editTextregisterBoD,getEditTextregisterMobile,
            getEditTextregisterPwd,getEditTextregisterConfirmPwd,getEditTextregisterDepart,editTextfathername,editTextmothername,
            editTexthallname,editTextsession,editTextreligion,editTextnationality,editTextaddress;
    private ProgressBar progressBar;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonSelectedGender;
    private DatePickerDialog picker;
    UserModel userModel;
    private static final String TAG="RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toast.makeText(RegistrationActivity.this,"You can register now",Toast.LENGTH_SHORT).show();

        progressBar=findViewById(R.id.progressBar);

        editTextregisterFullname=findViewById(R.id.edittext_register_fullname);
        getEditTextregisterDepart=findViewById(R.id.edittext_register_department);
        getEditTextregisterId=findViewById(R.id.edittext_registrationNo);
        getEditTextregisterEmail=findViewById(R.id.edittext_register_email);
        getEditTextregisterMobile=findViewById(R.id.edittext_register_mobile);
        getEditTextregisterPwd=findViewById(R.id.edittext_register_password);
        getEditTextregisterConfirmPwd=findViewById(R.id.edittext_register_confirmpassword);
        editTextregisterBoD=findViewById(R.id.edittext_register_bod);
        editTextnationality=findViewById(R.id.edittext_Nationality);
        editTextaddress=findViewById(R.id.edittext_ParmenetAddress);

        editTextfathername=findViewById(R.id.edittext_father_name);
        editTextmothername=findViewById(R.id.edittext_mother_name);
        editTexthallname=findViewById(R.id.edittext_hall_name);
        editTextreligion=findViewById(R.id.edittext_Religion);
        editTextsession=findViewById(R.id.edittext_session);

        getEditTextregisterDepart.setText(getIntent().getExtras().getString("userDeparment"));
        getEditTextregisterId.setText(getIntent().getExtras().getString("userRoll"));


        radioGroupGender=findViewById(R.id.radiogroup_register_gender);
        radioGroupGender.clearCheck();

        editTextregisterBoD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month=calendar.get(Calendar.MONTH);
                int year=calendar.get(Calendar.YEAR);

                picker =new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextregisterBoD.setText((dayOfMonth+"/"+(month + 1)+"/"+year));
                    }
                },year,month,day);
                picker.show();
            }

        });


        Button buttonRegister=findViewById(R.id.button_register);
        Button buttonlogin=findViewById(R.id.button_reglogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegistrationActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderId=radioGroupGender.getCheckedRadioButtonId();

                radioButtonSelectedGender=findViewById(selectedGenderId);

                String textFullName= editTextregisterFullname.getText().toString();
                String textDepartname= getEditTextregisterDepart.getText().toString();
                String textEmail= getEditTextregisterEmail.getText().toString();
                String textMobile= getEditTextregisterMobile.getText().toString();
                String textregistration= getEditTextregisterId.getText().toString();
                String textPassword= getEditTextregisterPwd.getText().toString();
                String textConfirmPassword= getEditTextregisterConfirmPwd.getText().toString();
                String textBod= editTextregisterBoD.getText().toString();
                String hallname=editTexthallname.getText().toString();
                String mothername=editTextmothername.getText().toString();
                String fathername=editTextfathername.getText().toString();
                String session=editTextsession.getText().toString();
                String address=editTextaddress.getText().toString();
                String nationality=editTextnationality.getText().toString();
                String regional= editTextreligion.getText().toString();

                String textgender;



//                String mobileRegex="/(^(\\+88|0088)?(01){1}[56789]{1}(\\d){8})$/";
//                Matcher mobileMatcher;
//                Patterns mobilePattern = Patterns.concatGroups(mobileRegex);





                if(TextUtils.isEmpty(textFullName)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Full name",Toast.LENGTH_SHORT).show();
                    editTextregisterFullname.setError("Full name is require");
                    editTextregisterFullname.requestFocus();

                }
                else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Email",Toast.LENGTH_SHORT).show();
                    getEditTextregisterEmail.setError("Email is required");
                    getEditTextregisterEmail.requestFocus();

                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()){
                    Toast.makeText(RegistrationActivity.this,"Please enter a valid email address",Toast.LENGTH_SHORT).show();
                    getEditTextregisterEmail.setError("Valid email is required");
                    getEditTextregisterEmail.requestFocus();

                }
                else if(TextUtils.isEmpty(textregistration)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Id",Toast.LENGTH_SHORT).show();
                    getEditTextregisterId.setError("Idis required");
                    getEditTextregisterId.requestFocus();

                }else if(TextUtils.isEmpty(textBod)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Date of Birth",Toast.LENGTH_SHORT).show();
                    editTextregisterBoD.setError("Date of Birth is required");
                    editTextregisterBoD.requestFocus();

                }else if(TextUtils.isEmpty(textDepartname)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your department name",Toast.LENGTH_SHORT).show();
                    getEditTextregisterDepart.setError("Department name is require");
                    getEditTextregisterDepart.requestFocus();

                }
                else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Password",Toast.LENGTH_SHORT).show();
                    getEditTextregisterPwd.setError("Password is require");
                    getEditTextregisterPwd.requestFocus();

                }  else if(textPassword.length()<7){
                    Toast.makeText(RegistrationActivity.this,"Password should be at least 7 digits",Toast.LENGTH_SHORT).show();
                    getEditTextregisterPwd.setError("Password too weak");
                    getEditTextregisterPwd.requestFocus();

                }
                else if(TextUtils.isEmpty(textConfirmPassword)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Password ",Toast.LENGTH_SHORT).show();
                    getEditTextregisterConfirmPwd.setError("Confirm password is required");
                    getEditTextregisterConfirmPwd.requestFocus();

                }
                else if(!textPassword.equals(textConfirmPassword)){
                    Toast.makeText(RegistrationActivity.this,"Password is not match",Toast.LENGTH_SHORT).show();
                    getEditTextregisterConfirmPwd.setError("Same password is required");
                    getEditTextregisterConfirmPwd.requestFocus();

                    getEditTextregisterConfirmPwd.clearComposingText();
                    getEditTextregisterPwd.clearComposingText();

                }
                else if (radioGroupGender.getCheckedRadioButtonId()==-1) {
                    Toast.makeText(RegistrationActivity.this,"Plase select your gender",Toast.LENGTH_SHORT).show();
                    radioButtonSelectedGender.setError("Gender is required");
                    radioButtonSelectedGender.requestFocus();

                }
                else if(textMobile.length()!=11){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Mobile number",Toast.LENGTH_SHORT).show();
                    getEditTextregisterMobile.setError("Mobile number should be 11 digits");
                    getEditTextregisterMobile.requestFocus();

                }
                else if(TextUtils.isEmpty(textMobile)){
                    Toast.makeText(RegistrationActivity.this,"Please enter your Mobile number",Toast.LENGTH_SHORT).show();
                    getEditTextregisterMobile.setError("Mobile number is required");
                    getEditTextregisterMobile.requestFocus();

                }else if(!(textMobile.matches("^(?:\\+88|88)?(01[3-9]\\d{8})$"))){
                    // System.out.println("Invalid mobile number");
                    Toast.makeText(RegistrationActivity.this,"Invalid mobile number",Toast.LENGTH_SHORT).show();
                    getEditTextregisterMobile.setError("Valid mobile number is required");
                    getEditTextregisterMobile.requestFocus();
                }
                else {
                    textgender=radioButtonSelectedGender.getText().toString();
                    progressBar.setVisibility((View.VISIBLE));
                    registerUser(textEmail,textFullName,textDepartname,hallname,fathername,mothername,session,textregistration,regional,nationality,textMobile,textBod,textgender,address,textPassword);

                }

            }
        });

    }


    private void registerUser(String Email,String fullname,String department,String hallname,String fathernaem,String mothername,String session,String registrationNo,String religion,String nationality,String mobileNo,String dob,String gender,String address,String password){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){


                    FirebaseUser firebaseUser= auth.getCurrentUser();

                    UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder().setDisplayName(fullname).build();
                    firebaseUser.updateProfile(profileChangeRequest);

                    ReadwriteuserDetails writeuserDetails= new ReadwriteuserDetails(Email,fullname,department,hallname,fathernaem,mothername,session,registrationNo,religion,nationality,mobileNo,dob,gender,address);
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Students");

                    referenceProfile.child(firebaseUser.getUid()).setValue(writeuserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility((View.GONE));
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegistrationActivity.this,"User registered successfully.Please verify your email",Toast.LENGTH_SHORT).show();


                                userModel=new UserModel(mobileNo,fullname,department,registrationNo , Timestamp.now(), FirebaseUtil.currentUserId());

                            FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
//                                        Intent intent=new Intent(loginUserNameActivity.this,RegistrationActivity.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                        startActivity(intent);
                                    }
                                }
                            });



                            Intent intent=new Intent(RegistrationActivity.this,UserDashboard.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(RegistrationActivity.this,"User registered Unsuccessfully",Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);

                        }
                    });


                }
                else {
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        getEditTextregisterPwd.setError(("Your password is tooo weak.Kindly use a mix of alphabets,numbers and special characters"));
                        getEditTextregisterPwd.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        getEditTextregisterPwd.setError(("Your email is invlid or already in use.Kindly re-endter."));
                        getEditTextregisterPwd.requestFocus();

                    }catch (FirebaseAuthUserCollisionException e){
                        getEditTextregisterEmail.setError(("Your email is already registered with this email. Use Kindly another email."));
                        getEditTextregisterEmail.requestFocus();

                    }catch (Exception e){
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(RegistrationActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}