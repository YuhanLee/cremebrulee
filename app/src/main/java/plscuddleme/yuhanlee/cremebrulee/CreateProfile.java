package plscuddleme.yuhanlee.cremebrulee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by yuhanlee on 2018-02-24.
 */

public class CreateProfile extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private EditText editUserNameHint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        editUserNameHint = (EditText) findViewById(R.id.edit_user_name);
        editUserNameHint.setOnFocusChangeListener(this);

    }

    @Override
    public void onClick(View v) {

        //TODO(UI1): Need to discuss with Eric how clicking on these should behave. Should we

        switch (v.getId()) {
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
        }
    }


    private void editProfileActivity() {
        Toast.makeText(this, "profile", Toast.LENGTH_LONG).show();
    }

    private void editInterestActivity() {
        Toast.makeText(this, "interests", Toast.LENGTH_LONG).show();
    }

    private void editOpinionsActivity() {
        Toast.makeText(this, "opinions", Toast.LENGTH_LONG).show();
    }

    private void editActivitiesActivity() {
        Toast.makeText(this, "activities", Toast.LENGTH_LONG).show();
    }





    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.edit_user_name:
                saveUserName(hasFocus);
                break;

        }
    }

    private void saveUserName(boolean hasFocus) {
        if (hasFocus) {
            editUserNameHint.setHint("Best Villain in History");
        } else {
          editUserNameHint.setHint("");

        }
    }
}
