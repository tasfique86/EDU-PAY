package com.example.edupay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import java.util.Objects;


public class MainActivity extends AppCompatActivity {


    TextInputEditText editTextEmail,editTextPassword;
    Button loinButton;
    FirebaseAuth mAuth;
    //   FirebaseUser mUser;
    ProgressBar progressBar;
    TextView registerNow;
    private static final String TAG="LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //getSupportActionBar().setTitle("Firebase App");
        editTextEmail=findViewById(R.id.loginEmail);
        editTextPassword=findViewById(R.id.loginpassword);
        loinButton=findViewById(R.id.loginButton);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);
        registerNow=findViewById(R.id.registerNow);

//        ImageView imageViewshowhide =findViewById(R.id.imageview_show_hide);
//        imageViewshowhide.setImageResource(R.drawable.ic_hide_pwd);
//        imageViewshowhide.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(editTextPassword.getTransformationMethod().equals((HideReturnsTransformationMethod.getInstance()))){
//                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    imageViewshowhide.setImageResource(R.drawable.ic_hide_pwd);
//                }else {
//                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    imageViewshowhide.setImageResource(R.drawable.ic_show_pwd);
//                }
//            }
//        });

        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), loginPhoneNumber.class);
                startActivity(intent);
                finish();
            }
        });

        loinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email= String.valueOf(editTextEmail.getText());
                password= String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email))
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"Please your enter Valid email ",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"Please enter Valid email ",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(password))
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"Enter Password ",Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Login Successfully",
                                            Toast.LENGTH_SHORT).show();

                                Intent intent=new Intent(MainActivity.this,UserDashboard.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                                } else {
                                    try {
                                        throw task.getException();
                                    }catch (FirebaseAuthInvalidUserException e)
                                    {
                                        editTextEmail.setError("User dose not exists or is no longer valid. Please register again.");
                                        editTextEmail.requestFocus();
                                    }
                                    catch (FirebaseAuthInvalidCredentialsException e)
                                    {
                                        editTextPassword.setError("Validation credentials.Kindly, check and re-enter.");
                                        editTextPassword.requestFocus();
                                    }
                                    catch (Exception e)
                                    {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        });

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            Toast.makeText(MainActivity.this,"Already Logged In",Toast.LENGTH_SHORT).show();

            startActivity(new Intent( MainActivity.this,UserDashboard.class));
            finish();

        }else {
            Toast.makeText(MainActivity.this,"You can Login Or Register now",Toast.LENGTH_SHORT).show();
        }
    }
}