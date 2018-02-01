package lix5.ushare;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {
    private AppCompatButton b1;
    private EditText ed1,ed2;
    private TextView b2;

    private FirebaseAuth mAuth; //instance of FirebaseAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);

        b1 = (AppCompatButton) findViewById(R.id.button);
        b2 = (TextView)findViewById(R.id.button2);

        b1.setOnClickListener((v) -> {  //login
            if (loginCheck(ed1.getText().toString(), ed2.getText().toString())){
                signIn(ed1.getText().toString(), ed2.getText().toString());
            }
        });

        b2.setOnClickListener((v) -> {  //register
            startActivity(new Intent(LoginActivity.this, RegActivity.class));
            finish();
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn(String email, String password){
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        // Sign in success, update UI with the signed-in user's information
                        if(checkIfEmailVerified()){
                            Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Email is not verified, please check your email",Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            finish();
                            Intent intent = getIntent();
                            startActivity(intent);
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean checkIfEmailVerified(){
        FirebaseUser user = mAuth.getCurrentUser();
        return user.isEmailVerified();
    }

    private boolean loginCheck(String email_, String pw_){
        boolean haveError = false;

        if (TextUtils.isEmpty(email_)){
            ed1.setError("Please enter your email");
            ed1.requestFocus();
            haveError = true;
        }

        if (TextUtils.isEmpty(pw_)) {
            ed2.setError("Please enter your password");
            ed2.requestFocus();
            haveError = true;
        }
        return !haveError;
    }
}