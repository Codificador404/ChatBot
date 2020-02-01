package com.example.beast.chatbot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private String email, pass;
    private EditText ed1, ed2;
    private ProgressDialog probar;
    private FirebaseAuth firebaseauth;
    private FirebaseUser firebaseUser;
    private Button btn_signup;
    private ImageButton btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login_acitvity);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseauth = FirebaseAuth.getInstance();
        if (firebaseauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        btn_signin =  findViewById(R.id.but_signin);
        btn_signup =  findViewById(R.id.but_signup);

        probar = new ProgressDialog(this);

        ed1 = (EditText) findViewById(R.id.email1);
        ed2 = (EditText) findViewById(R.id.pass1);
        btn_signup.setOnClickListener(this);
        btn_signin.setOnClickListener(this);

    }

    public LoginActivity() {
        super();
    }

    @Override
    public void onClick(View v) {

        if (v == btn_signin) {
            //signin
            Log.v("user", "getting logged in with id and pass");
            email = ed1.getText().toString().trim();
            pass = ed2.getText().toString().trim();

            if (verification()) {

                probar.setMessage("Getting you logged in...");
                probar.show();
                firebaseauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        probar.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            firebaseUser = firebaseauth.getCurrentUser();

                            Log.v("user", "sign in successfully");
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            Toast.makeText(LoginActivity.this, "Getting you Logged in....", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(LoginActivity.this, "Not able to login ..Retry", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }
        if (v == btn_signup) {
            //signup
            Log.v("user", "creating user id");

            email = ed1.getText().toString().trim();
            pass = ed2.getText().toString().trim();

            if (verification()) {


                probar.setMessage("Registering user......");
                probar.show();

                firebaseauth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        probar.dismiss();
                        if (task.isSuccessful()) {


                            Toast.makeText(LoginActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            i.putExtra("EMAIL", email);
                            startActivity(i);

                        } else {

                            Toast.makeText(LoginActivity.this, "Could not registered.....  Please try again  ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        }


    }

    public boolean verification() {

        Log.v("user", "verification of id and pass");
        if (TextUtils.isEmpty(email)) {
            ed1.setError("Enter email");
            return false;
        }

        if (TextUtils.isEmpty(pass)) {
            ed2.setError("Enter pass");
            return false;
        }
        if (pass.length() < 6) {
            ed2.setError("Password should be at least 6 characters");

            return false;
        }
        return true;

    }


}
