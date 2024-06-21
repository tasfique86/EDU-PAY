package com.example.edupay;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edupay.adapter.ChatRecyclerAdapter;
import com.example.edupay.model.ChatMessageModel;
import com.example.edupay.model.ChatRoomModel;
import com.example.edupay.model.UserModel;
import com.example.edupay.utils.AndroidUtil;
import com.example.edupay.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    UserModel otherUser,currentUser;

    ChatRecyclerAdapter adapter;



    ImageButton backBtn;
    TextView otherUsername;
    RecyclerView recyclerView;

    String chatroomId;
    ChatRoomModel chatroomModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        otherUser = AndroidUtil.getUserModelFromIntent(getIntent());


        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {

            currentUser=task.getResult().toObject(UserModel.class);

        });

        chatroomId= FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(),otherUser.getUserId());


        backBtn=findViewById(R.id.back_btn);
        otherUsername=findViewById(R.id.other_username);

        recyclerView=findViewById(R.id.chat_recyclerView);




        backBtn.setOnClickListener(v -> {
            onBackPressed();
        });
        otherUsername.setText(otherUser.getUsername());

        getOrCreateChatroomModel();

        setupChatRecyclerView();




    }

    void setupChatRecyclerView(){

        Query query= FirebaseUtil.getChatroomMessageReference(chatroomId)
                .orderBy("timestamp",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> option = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query,ChatMessageModel.class).build();


        adapter =new ChatRecyclerAdapter(option,getApplicationContext());
//        LinearLayoutManager manager=new LinearLayoutManager(this);
//        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });

    }





    void getOrCreateChatroomModel(){
        FirebaseUtil.getChatroomReference(chatroomId).get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               chatroomModel =task.getResult().toObject(ChatRoomModel.class);
               if(chatroomModel==null){
                   chatroomModel = new ChatRoomModel(
                     chatroomId,
                           Arrays.asList(FirebaseUtil.currentUserId(),otherUser.getUserId()) ,
                           Timestamp.now(),
                           ""
                   );
                   FirebaseUtil.getChatroomReference(chatroomId).set(chatroomModel);
               }
           }
        });
    }
//    void sendNotification(String message){
//
//        //current username,messagea,currentuserId,otherUserToken
//
//        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//                UserModel currentUser=task.getResult().toObject(UserModel.class);
//                try {
//                        JSONObject jsonObject =new JSONObject();
//
//                        JSONObject notificationObj= new JSONObject();
//                        notificationObj.put("title",currentUser.getUsername());
//                        notificationObj.put("boby",message);
//
//                        JSONObject dataObj =new JSONObject();
//                        dataObj.put("userId",currentUser.getUserId());
//
//                        jsonObject.put("notification",notificationObj);
//                        jsonObject.put("data",dataObj);
//                        jsonObject.put("to",otherUser.getFcmToken());
//
//                        callApi(jsonObject);
//                }catch (Exception e){
//
//                }
//            }
//        });
//
//    }
//    void callApi(JSONObject jsonObject){
//
//        MediaType JSON = MediaType.get("application/json");
//
//
//        OkHttpClient client = new OkHttpClient();
//        String url="http://fcm.googleapis.com/fcm/send";
//        RequestBody body = RequestBody.create(jsonObject.toString(), JSON);
//        Request request=new Request.Builder()
//                .url(url)
//                .post(body)
//                .header("Authorization","Bearer AAAAuUEf4uI:APA91bG3UahSxU4qfD3_OFAy8UVMKnCsHGmKByHCczSx814PpkPLUkr1WYly5stG_LHH55j9FbznnLiBPvTNxd4OZgTQKzQRPfPLjwt8KomgqNCMefWGCJZdDUKFASILqQ2gYsj7DB3d")
//                .build();
//        client.newCall(request);
//
//
//    }

//    void sendNotification(String message){
//
//        //current username, message, current userId, otherUserToken
//
//        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
//            if(task.isSuccessful()){
//                UserModel currentUser=task.getResult().toObject(UserModel.class);
//                try {
//                    JSONObject jsonObject = new JSONObject();
//
//                    JSONObject notificationObj = new JSONObject();
//                    notificationObj.put("title", currentUser.getUsername());
//                    notificationObj.put("body", message); // Fixed the typo in "body"
//
//                    JSONObject dataObj = new JSONObject();
//                    dataObj.put("userId", currentUser.getUserId());
//
//                    jsonObject.put("notification", notificationObj);
//                    jsonObject.put("data", dataObj);
//                    jsonObject.put("to", otherUser.getFcmToken());
//
//                    sendNotificationRequest(jsonObject);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }

//    void sendNotificationRequest(JSONObject jsonObject) {
//        String url = "YOUR_FCM_API_URL";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // Handle success response
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // Handle error response
//            }
//        });
//
//        // Add the request to the RequestQueue.
//        RequestQueue requestQueue = Volley.newRequestQueue(ChatActivity.this); // Pass your context here
//        requestQueue.add(jsonObjectRequest);
//    }

}