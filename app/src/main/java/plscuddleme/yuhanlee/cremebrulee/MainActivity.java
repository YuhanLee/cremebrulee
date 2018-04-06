package plscuddleme.yuhanlee.cremebrulee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Created by yuhanlee on 2018-02-24.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, Tabs.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {

    }

}



