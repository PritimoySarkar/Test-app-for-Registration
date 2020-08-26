package com.p2ms.guideapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.p2ms.guideapp.keys.StaticData;
import com.p2ms.guideapp.model.User;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextInputEditText etName, etEmail, etPhone, etPass, etConfPass;
    Button btnSignUp;

    private FirebaseAuth mAuth;
    private String name, email, phone, pass, confPass;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etContact);
        etPass = findViewById(R.id.etPassword);
        etConfPass = findViewById(R.id.etConfPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = String.valueOf(etName.getText()).trim();
                email = etEmail.getText().toString().trim();
                phone = String.valueOf(etPhone.getText()).trim();
                pass = String.valueOf(etPass.getText()).trim();
                confPass = String.valueOf(etConfPass.getText()).trim();

                if (name.isEmpty()) etName.setError("Name can't be blank");
                else if (email.isEmpty()) etEmail.setError("Email can't be blank");
                else if (phone.isEmpty()) etPhone.setError("Contact can't be blank");
                else if (pass.isEmpty()) etPass.setError("Password can't be blank");
                else if (confPass.isEmpty()) etConfPass.setError("Confirm Password can't be blank");
                else if (!pass.equals(confPass)) {
                    etPass.setError("Password didn't match");
                    //etConfPass.setError("Password didn't match");
                } else {
                    //Log.d("MainActivity","=>>>>>>>>>>>>>>>>>>>>>>else case=<<<<<<<<<<<<<<<<<");
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("MainActivity","else -> if");
                                String userID = mAuth.getUid();
                                user=new User(userID,name,email,phone,pass);
                                createProfile(user,"Signed up Successfully");
                                //mAuth.verifyPasswordResetCode()
                                //Toast.makeText(MainActivity.this, "Success " + userID, Toast.LENGTH_LONG).show();
                            } else {
                                Log.d("MainActivity","else -> else");
                                Toast.makeText(
                                        MainActivity.this,
                                        "Failed "+task.getException(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private void createProfile(User user, final String signed_up_successfully) {

        mAuth.getCurrentUser().sendEmailVerification();
        //Creating Instance
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Creating map to store data in hash Map format
        Map<String, String> newUser = new HashMap<>();
        newUser.put(StaticData.USER_NAME,user.getUserName());
        newUser.put(StaticData.USER_EMAIL,user.getUserEmail());
        newUser.put(StaticData.USER_CONTACT,user.getUserContact());
        newUser.put(StaticData.USER_PASS,user.getUserPass());
        db.collection("Users")
                .document(user.getUserId())
                .set(newUser)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this,
                                e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, signed_up_successfully,
                                Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this,
                                SignInActivity.class));
                        MainActivity.this.finish();
                    }
                });
        return;
    }
}