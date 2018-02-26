package plscuddleme.yuhanlee.cremebrulee;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yuhanlee on 2018-02-24.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogin;
    private ProgressDialog registerDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextEmail = (EditText) findViewById(R.id.sign_up_email);
        editTextPassword = (EditText) findViewById(R.id.sign_up_password);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        textViewLogin = (TextView) findViewById(R.id.signInHere);
        registerDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        //attaching listener to button
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRegister:
                registerUser();
                break;

            case R.id.signInHere:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "You're missing a field", Toast.LENGTH_LONG).show();
            return;
        }

        registerDialog.setMessage("Registering User. Please wait...");
        registerDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                            finish();

                            Intent intent = new Intent(getApplicationContext(), CreateProfile.class);
                            intent.putExtra("RegisteredEmail", email);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        registerDialog.dismiss();
    }

}
