package com.p2ms.guideapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.p2ms.guideapp.keys.StaticData;
import com.p2ms.guideapp.session.LocalSessionStore;

public class SignInActivity extends AppCompatActivity {
    private TextInputEditText etEmail,etPass;
    private Button btnLogin;
    private TextView txtNavSignUp;

    private FirebaseAuth firebaseAuth;
    private String email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etEmail=findViewById(R.id.userID);
        etPass=findViewById(R.id.pass);
        btnLogin=findViewById(R.id.bttnSignIn);
        txtNavSignUp=findViewById(R.id.txtSignUp);

        firebaseAuth=FirebaseAuth.getInstance();

        //Navigate to Sign Up page
        txtNavSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,
                        MainActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //login code
                email=String.valueOf(etEmail.getText()).trim();
                pass=String.valueOf(etPass.getText()).trim();
                if(email.isEmpty()) etEmail.setError("Email can't be blank");
                else if(pass.isEmpty()) etPass.setError("Password can't be blank");
                else {
                    firebaseAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser fUser =firebaseAuth.getCurrentUser();
                                        boolean check= fUser.isEmailVerified();
                                        if(check){
                                            //email valid and verified
                                            Toast.makeText(SignInActivity.this,
                                                    "Signed in Successfully",
                                                    Toast.LENGTH_LONG).show();
                                            LocalSessionStore local=new LocalSessionStore(SignInActivity.this);
                                            local.storeData(StaticData.USER_ID,fUser.getUid());
                                            startActivity(new Intent(
                                                    SignInActivity.this, //source
                                                    HomeActivity.class                  //Destination
                                            ));
                                            SignInActivity.this.finish(); //Destroying sign in Activity
                                        }
                                        else{
                                            //if email not verified
                                            Toast.makeText(SignInActivity.this,
                                                    "Please verify email first",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        //email doesn't exist
                                        Toast.makeText(SignInActivity.this,
                                                "Sign in failed",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}