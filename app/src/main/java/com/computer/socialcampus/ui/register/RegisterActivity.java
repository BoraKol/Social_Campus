package com.computer.socialcampus.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.computer.socialcampus.R;
import com.computer.socialcampus.data.model.LoggedInUser;
import com.computer.socialcampus.databinding.ActivityRegisterBinding;
import com.computer.socialcampus.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    public FirebaseAuth mAuth;
    public DatabaseReference usersDatabaseRef;
    public EditText firstNameEditText, lastNameEditText, usernameEditText, emailEditText, passwordEditText, collageEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
//        usersDatabaseRef = FirebaseDatabase.getInstance();


        //String yerine Login classlarından gelecek nesnelere dönüşecekler
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String collage = collageEditText.getText().toString();

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();

                            LoggedInUser newUser = new LoggedInUser(firstName, lastName, username, email,password,collage);
                            usersDatabaseRef.child(userId).setValue(newUser);

                            Toast.makeText(RegisterActivity.this, "Kayıt Başarılı!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Kayıt Başarısız! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

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