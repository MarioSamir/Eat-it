package com.example.eatit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText userNameEditText;
    private EditText emailEditText;
    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button signUpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        userNameEditText = findViewById(R.id.sign_up_user_name);
        emailEditText = findViewById(R.id.sign_up_email);
        phoneNumberEditText = findViewById(R.id.sign_up_phone_number);
        passwordEditText = findViewById(R.id.sign_up_password);
        confirmPasswordEditText = findViewById(R.id.sign_up_confirm_password);

        signUpBtn = findViewById(R.id.sign_up_btn_inside);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
                if(email.isEmpty()){
                    emailEditText.setError("Enter your Email");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailEditText.setError("Enter a vaild Email");
                    return;
                }
                if(password.isEmpty()){
                    passwordEditText.setError("Enter your password");
                    return;
                }
                if(password.length()<6) {
                    passwordEditText.setError("password must be 6 numbers at least");
                    return;
                }

                if(!password.equals(confirmPassword)){
                    confirmPasswordEditText.setError("Please confirm password");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"user create successfully",Toast.LENGTH_SHORT).show();

                            emailEditText.setText("");
                            passwordEditText.setText("");
                            confirmPasswordEditText.setText("");
                        }
                    }
                });

            }
        });
    }

}