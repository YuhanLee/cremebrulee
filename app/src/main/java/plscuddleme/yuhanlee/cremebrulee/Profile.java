package plscuddleme.yuhanlee.cremebrulee;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by yuhanlee on 2018-02-24.
 */

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private TextView editUserName;
    private String userFullName;
    private String firstName;
    private Button logOut;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ImageView imageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            //Two cases: 1. just signed in 2. simply logged in

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                //is coming from sign up
                //TODO: make it call setUserContext like when you just log in
                setUserContext(extras.getString("uuid"));

            } else {
                //just loggin in
                firebaseUser = firebaseAuth.getCurrentUser();
                final String userId = firebaseUser.getUid();
                setUserContext(userId);
            }

        } else {
            finish();
            startActivity(new Intent(this, SignInActivity.class));
        }
    }


    private void setUserContext(final String userId) {
        // Get the logged in user information
        DatabaseReference databaseUsers;
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("Members");
        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String idFromDatabase;
                for (DataSnapshot Member : dataSnapshot.getChildren()) {
                    Member member = Member.getValue(Member.class);
                    idFromDatabase = member.getUuid();
                    if (userId.equals(idFromDatabase)) {
                        getMember(member);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getMember(Member member) {
        userFullName = member.getFullName();
        firstName = member.getFirstName();
        setView();
    }

    private void setView() {
        setContentView(R.layout.activity_profile);
        editUserName = (TextView) findViewById(R.id.edit_user_name);
        editUserName.setText(userFullName);
        editUserName.setOnClickListener(this);
        logOut = (Button) findViewById(R.id.log_out);
        logOut.setOnClickListener(this);
        imageActivity = (ImageView) findViewById(R.id.interest_activity);
        imageActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //TODO: find a way to attach onclick listeners to the 4 cards
        switch (v.getId()) {
            case R.id.log_out:
                logOut();
                break;

            case R.id.profile_activity:
                editProfileActivity();
                break;

            case R.id.interest_activity:
                editInterestActivity();

                break;

            case R.id.opinions_activity:
                editOpinionsActivity();
                break;

            case R.id.activities_activity:
                editActivitiesActivity(); 
                break;

            case R.id.edit_user_name:
                editUserName();
                break;
        }
    }

    private void logOut() {
        // Build and show dialog
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.logout_confirm, null);
        dialogBuilder.setView(dialogView);

        String logOutText = "Do you wish to log out?";
        TextView logoutText = (TextView) dialogView.findViewById(R.id.confirmLogout);
        logoutText.setText(logOutText);

        dialogBuilder.setTitle("Bye Bye " + firstName + " :'(");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        // Set variables
        final Button buttonCancel = (Button) dialogView.findViewById(R.id.cancelButton);
        final Button buttonConfirm = (Button) dialogView.findViewById(R.id.confirmButton);

        // Close dialog on cancel click
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        // When click confirm, sign out, exit smoothly and return to sign in page
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                b.dismiss();
                finish();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

    }

    private void editProfileActivity() {
        Toast.makeText(this, "profile", Toast.LENGTH_LONG).show();
    }

    private void editInterestActivity() {
        Toast.makeText(this, "Get Ready!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), PickInterest.class);
        startActivity(intent);
    }

    private void editOpinionsActivity() {
        Toast.makeText(this, "opinions", Toast.LENGTH_LONG).show();
    }

    private void editActivitiesActivity() {
        Toast.makeText(this, "activities", Toast.LENGTH_LONG).show();
    }

    private void editUserName(){
        Toast.makeText(this, "Should allow user to edit name", Toast.LENGTH_LONG).show();
    }
}
