package com.example.customer_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.customer_app.Model_User.User;
import com.example.customer_app.common.common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    ConstraintLayout constraintlayout;
    Button btn_login;
    EditText btn_num,btn_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.signUp);
        btn_num = findViewById(R.id.reg_num);
        btn_pass = findViewById(R.id.pass);
        constraintlayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final ProgressDialog mdialog = new ProgressDialog(login.this);
                mdialog.setMessage("Please wait...!");
                mdialog.show();
                      table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Check if user not exist in database
                            mdialog.dismiss();
                            if (dataSnapshot.child(btn_num.getText().toString()).exists()) {

                                User user = dataSnapshot.child(btn_num.getText().toString()).getValue(User.class);
                                if (user.getPassword().equals(btn_pass.getText().toString())) {
                                   Intent Homeintent = new Intent(getApplicationContext(),Home.class);
                                    common.current_user = user;
                                    startActivity(Homeintent);
                                    finish();

                                } else {
                                    //Toast.makeText(login.this, "Wrong Password!!", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(constraintlayout, "wrong password", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }else{
                                mdialog.dismiss();
                                //Toast.makeText(login.this, "User Doesn't Exist!", Toast.LENGTH_SHORT).show();
                                Snackbar snackbar = Snackbar
                                        .make(constraintlayout, "User cannot exist!!", Snackbar.LENGTH_LONG);
                                snackbar.show();
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
