package com.example.dakane.platzigram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dakane.platzigram.view.ContainerActivity;
import com.example.dakane.platzigram.view.CreateAccountActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        final TextInputEditText etEmail = (TextInputEditText)findViewById(R.id.username);
        final TextInputEditText etPassword = (TextInputEditText)findViewById(R.id.password);

        Button btnLogin = (Button)findViewById(R.id.login);
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
                                if (!task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Fallo el login" + task.getException(), Toast.LENGTH_LONG).show();
                                }else{
                                    goClickMenu();
                                }
                            }
                        });

            }
        });

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
