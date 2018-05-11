package plscuddleme.yuhanlee.cremebrulee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;


import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth firebaseAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewJoinNow;
    private Button buttonSignIn;
    private ProgressDialog signInDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        textViewJoinNow = (TextView) findViewById(R.id.join_now);
        buttonSignIn = (Button) findViewById(R.id.sign_in);
        signInDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewJoinNow.setOnClickListener(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                userLogIn();
                break;

            case R.id.join_now:
                gotoSignUp();
                break;
        }
    }


    private void userLogIn() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please Enter an Email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter an Email", Toast.LENGTH_LONG).show();
            return;
        }

        signInDialog.setMessage("Signing in. Please wait...");
        signInDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        signInDialog.dismiss();
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), Tabs.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Sign in unsuccessful: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void gotoSignUp() {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }
}

