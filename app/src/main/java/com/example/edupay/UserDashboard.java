package com.example.edupay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.edupay.model.UserModel;
import com.example.edupay.utils.AndroidUtil;
import com.example.edupay.utils.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;


public class UserDashboard extends AppCompatActivity implements View.OnClickListener{

    CardView cardViewpayment,cardViewsetting,cardViewresult,cardViewdetails,cardViewlogout,cardViewremove;
    DatabaseReference Studentreference;
    FirebaseAuth authprofile;
    TextView textViewUsername,textViewUserId,textViewUserdepartment;
    String UserRoll,UserDepartment,UserName,Usersession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        cardViewdetails=findViewById(R.id.studentsDetailsCardView);
        cardViewlogout=findViewById(R.id.logoutCardView);
        cardViewresult=findViewById(R.id.resultCardView);
        cardViewremove=findViewById(R.id.removeCardView);
        cardViewsetting=findViewById(R.id.settingCardView);
        cardViewpayment=findViewById(R.id.paymentCardView);
        textViewUserId=findViewById(R.id.UserId_text);
        textViewUsername=findViewById(R.id.UserName_text);



        cardViewpayment.setOnClickListener(this);
        cardViewlogout.setOnClickListener(this);
        cardViewremove.setOnClickListener(this);
        cardViewsetting.setOnClickListener(this);
        cardViewresult.setOnClickListener(this);
        cardViewdetails.setOnClickListener(this);


        authprofile=FirebaseAuth.getInstance();

        FirebaseUser firebaseUser= authprofile.getCurrentUser();

        if(firebaseUser==null)
        {
            Toast.makeText(UserDashboard.this,"Something is Erorr !!, User details are not availave at the moment",Toast.LENGTH_SHORT).show();
        }
        else
        {
            showUserprofile(firebaseUser);
        }

        getFCMToken();
     //   Toast.makeText(UserDashboard.this, FirebaseUtil.currentUserId(),Toast.LENGTH_SHORT).show();








    }

    void getFCMToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token=task.getResult();
                FirebaseUtil.currentUserDetails().update("fcmToken",token);
            }
        });

    }




    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.paymentCardView){
            Intent intent=new Intent(UserDashboard.this,SemesterPayment.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.logoutCardView){

            authprofile.signOut();
            Toast.makeText(UserDashboard.this,"Log Out",Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(UserDashboard.this,MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
        else if(v.getId()==R.id.removeCardView){
            Intent intent=new Intent(UserDashboard.this,F_Remove.class);
            intent.putExtra("FDepartment",UserDepartment);
            intent.putExtra("FRoll",UserRoll);
            intent.putExtra("Fsession",Usersession);
            startActivity(intent);
        }
        else if(v.getId()==R.id.studentsDetailsCardView){
            Intent intent=new Intent(UserDashboard.this,StudentsDetails.class);
            startActivity(intent);

        }
        else if(v.getId()==R.id.settingCardView){
            Toast.makeText(UserDashboard.this,UserRoll,Toast.LENGTH_SHORT).show();
            if(UserRoll==null )
            {
                Toast.makeText(UserDashboard.this,"Something is Erorr",Toast.LENGTH_SHORT).show();
                return;
            }
//            String id="F4FXJiz90SSlYIZ2F26V9bbQGjx1";
//            String name="Admin",phone="01750413332";
//            String token="ehQZ4VmvTZSOUqZCor0OIF:APA91bEEh0bj33LdV3IYMHGLpHHyi1-Bm4lp8S_OEpjxuooRgi_tpmaSUk-1HbY1BRGB4WCn0xNZV4UQD_BkQ2t6q9A4RO71GOno0a4yoR5GfMZPyBkig_DHHirsfXGZSp5Fqz4K6y2m";
//

            UserModel adminModel=new UserModel();
            adminModel.setUserId("F4FXJiz90SSlYIZ2F26V9bbQGjx1");
            adminModel.setUsername("Admin");
            adminModel.setPhone("01750413332");
            adminModel.setFcmToken("ehQZ4VmvTZSOUqZCor0OIF:APA91bEEh0bj33LdV3IYMHGLpHHyi1-Bm4lp8S_OEpjxuooRgi_tpmaSUk-1HbY1BRGB4WCn0xNZV4UQD_BkQ2t6q9A4RO71GOno0a4yoR5GfMZPyBkig_DHHirsfXGZSp5Fqz4K6y2m");

            Intent intent=new Intent(UserDashboard.this,ChatActivity.class);
            AndroidUtil.passUserModelAsIntent(intent,adminModel);
            startActivity(intent);
        }
        else if(v.getId()==R.id.resultCardView){
           // Toast.makeText(UserDashboard.this,userRoll,Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(UserDashboard.this,Result.class);
            intent.putExtra("Department",UserDepartment);
            intent.putExtra("Roll",UserRoll);
            startActivity(intent);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    private void showUserprofile(FirebaseUser firebaseUser) {
        String userID=  firebaseUser.getUid();
      //  Toast.makeText(UserDashboard.this,userID,Toast.LENGTH_SHORT).show();

        Studentreference= FirebaseDatabase.getInstance().getReference("Registered Students");
        Studentreference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ReadwriteuserDetails readwriteuserDetails= snapshot.getValue(ReadwriteuserDetails.class);

                if(readwriteuserDetails!=null)
                {
                    UserRoll = readwriteuserDetails.getRegistrationNo();
                    UserDepartment =readwriteuserDetails.getDepartment();
                    UserName=readwriteuserDetails.getFullname();
                    Usersession=readwriteuserDetails.getSession();

                    textViewUsername.setText(UserName);
                    textViewUserId.setText(UserRoll+"( "+UserDepartment+" )");


                }
                else {
                    Toast.makeText(UserDashboard.this,"Find Nothing",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}