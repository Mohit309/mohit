package com.example.mikkchatingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mikkchatingapp.Models.Users;
import com.example.mikkchatingapp.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

   ActivitySignUpBinding binding;
//    private FirebaseAuth mAuth;
//    FirebaseDatabase database;
//    ProgressDialog progressDialog;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        getSupportActionBar().hide();
//        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//
//        progressDialog = new ProgressDialog(SignUpActivity.this);
//
//
//        progressDialog.setTitle("Creating Account");
//        progressDialog.setMessage("We're creating your account");
//
//        binding.btnSignUp.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                progressDialog.show();
//                mAuth.createUserWithEmailAndPassword
//                        (binding.edtEmail.getText().toString() , binding.edtPassword2.getText().toString()).
//                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressDialog.dismiss();
//                                if (task.isSuccessful())
//                                {
//
//                                    Users user = new Users(binding.edtUsername.getText().toString(), binding.edtEmail.getText().toString(), binding.edtPassword2.getText().toString());
//
//                                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                    database.getReference().child("Users").child(id).setValue(user);
//
//
//                                    Toast.makeText(SignUpActivity.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
//                                }
//                                else
//                                {
//                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//            }
//        });
//
//        binding.tvSingIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//}

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAuth.createUserWithEmailAndPassword(binding.edtEmail.getText().toString(),
                        binding.edtPassword2.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    Users user = new Users(binding.edtUsername.getText().toString(),
                                            binding.edtEmail.getText().toString(), binding.edtPassword2.getText().toString());

                                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    database.getReference().child("Users").child(id).setValue(user);

                                    Toast.makeText(SignUpActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
            
        });


    }
}