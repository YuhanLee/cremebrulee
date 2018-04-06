package plscuddleme.yuhanlee.cremebrulee;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tabs extends AppCompatActivity implements View.OnClickListener{
    private TextView editUserName;
    private String userFullName;
    private String firstName;
    private Button logOut;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ImageView imageActivity;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.tabContainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final String userId = firebaseUser.getUid();
        setUserContext(userId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @SuppressLint("StringFormatInvalid")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int tab = this.getArguments().getInt(ARG_SECTION_NUMBER);
            View rootView=null;
            TextView textView;
            switch (tab) {
                case 1:
                    rootView = inflater.inflate(R.layout.activity_profile, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(Integer.toString(tab));
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
                    textView = (TextView) rootView.findViewById(R.id.section_label);
                    textView.setText(getString(R.string.section_format, this.getArguments().getInt(ARG_SECTION_NUMBER)));
                    break;
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/Tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
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
      //  setContentView(R.layout.activity_profile);
        editUserName = (TextView) findViewById(R.id.edit_user_name);
        editUserName.setText(userFullName);
        editUserName.setOnClickListener(this);
        imageActivity = (ImageView) findViewById(R.id.interest_activity);
        imageActivity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //TODO: find a way to attach onclick listeners to the 4 cards
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

            case R.id.edit_user_name:
                editUserName();
                break;
        }
    }

    public void logOut() {
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

