package plscuddleme.yuhanlee.cremebrulee;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yuhanlee on 2018-02-24.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextConfirmPassword;
    private TextView textViewLogin;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseUsers;
    private DatabaseReference databaseReference;

    private ProgressDialog registerDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail = (EditText) findViewById(R.id.sign_up_email);
        editTextPassword = (EditText) findViewById(R.id.sign_up_password);
        editTextConfirmPassword = (EditText) findViewById(R.id.confirm_sign_up_password);
        editTextFirstName = (EditText) findViewById(R.id.first_name);
        editTextLastName = (EditText) findViewById(R.id.last_name);

        buttonRegister = (Button) findViewById(R.id.button_register);
        textViewLogin = (TextView) findViewById(R.id.sign_in_here);
        registerDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //attaching listener to button
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_register:
                registerUser();
                break;

            case R.id.sign_in_here:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private void registerUser() {

        final String email = editTextEmail.getText().toString().trim();
        final String firstName = editTextFirstName.getText().toString().trim();
        final String lastName = editTextLastName.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "You're missing a field", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Your password must match",Toast.LENGTH_LONG).show();
            return;
        }

        registerDialog.setMessage("Registering User. Please wait...");
        registerDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String uuid = firebaseAuth.getCurrentUser().getUid();

                            Member newMember = new Member(uuid, email, firstName, lastName);
                            registerUserTodb(newMember, uuid);
                            finish();

                            Intent intent = new Intent(getApplicationContext(), Tabs.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignUpActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        registerDialog.dismiss();
    }

    private void registerUserTodb(Member newUser, String uuid) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseUsers = FirebaseDatabase.getInstance().getReference("Members");
        databaseUsers.child(uuid).setValue(newUser);

        Toast.makeText(SignUpActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
    }
}
