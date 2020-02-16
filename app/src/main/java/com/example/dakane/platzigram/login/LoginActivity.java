package com.example.dakane.platzigram.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dakane.platzigram.R;
import com.example.dakane.platzigram.view.ContainerActivity;
import com.example.dakane.platzigram.view.CreateAccountActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    TextView tvCreateAccount;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setElements();

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (email.equals("")){
                    Toast.makeText(LoginActivity.this, "Pon un email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (password.equals("")){
                    Toast.makeText(LoginActivity.this, "Pon un password", Toast.LENGTH_LONG).show();
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                FirebaseUser user = task.getResult().getUser();
                                SharedPreferences.Editor sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE).edit();
                                sharedPreferences.putString("email", user.getEmail());
                                sharedPreferences.commit();



                                if (!task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Fallo el login" + task.getException(), Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), user.getUid(), Toast.LENGTH_LONG).show();
                                    goClickMenu();
                                }
                            }
                        });

            }
        });

    }
    public void setElements(){
        etEmail = (TextInputEditText)findViewById(R.id.username);
        etPassword = (TextInputEditText)findViewById(R.id.password);
        btnLogin = (Button)findViewById(R.id.login);
        tvCreateAccount = (TextView) findViewById(R.id.createHere);
    }
    public void goClickAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
    public void goClickMenu(){
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }
}
