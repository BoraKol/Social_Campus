package com.computer.socialcampus.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.computer.socialcampus.R;
import com.computer.socialcampus.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        CheckBox acceptTermsCheckBox = findViewById(R.id.checkBox2);
        acceptTermsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Lütfen kaydolmak için kullanım koşullarını kabul edin.",Toast.LENGTH_LONG);
                }
            }
        });
    }
}