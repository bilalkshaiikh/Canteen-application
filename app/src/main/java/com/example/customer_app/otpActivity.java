package com.example.customer_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class otpActivity extends AppCompatActivity {
    Button btn_verify;
    EditText GET_OTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_otp);

        btn_verify = (Button) findViewById(R.id.signUp);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), code_sent.class));
            }
        });
        Intent intent = getIntent();
        String mob = intent.getExtras().getString("phone");
        GET_OTP = findViewById(R.id.get_mob);
        GET_OTP.setText(mob);
    }
}
