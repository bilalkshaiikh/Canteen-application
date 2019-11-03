package com.example.customer_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.customer_app.Model_User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sign_up extends AppCompatActivity {
    TextView alt;
    Button sign_sub;
    Toolbar toolbar;
    EditText FULL_NAME,MOBILE_NO,PASSWORD,EMAIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sign_up);

        alt = findViewById(R.id.already_acc);
        sign_sub =  findViewById(R.id.signUp);
        FULL_NAME = findViewById(R.id.fullname);
        MOBILE_NO = findViewById(R.id.Email);
        PASSWORD = findViewById(R.id.pass);
        EMAIL = findViewById(R.id.email);
        alt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        sign_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mdialog = new ProgressDialog(sign_up.this);
                mdialog.setMessage("Please wait...!");
                mdialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if user already exist
                        if(dataSnapshot.child(MOBILE_NO.getText().toString()).exists()){
                            mdialog.dismiss();
                            Toast.makeText(sign_up.this, "User already Exist!!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mdialog.dismiss();
                            User user = new User(FULL_NAME.getText().toString(),PASSWORD.getText().toString(),EMAIL.getText().toString());
                            table_user.child(MOBILE_NO.getText().toString()).setValue(user);
                            Toast.makeText(sign_up.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
