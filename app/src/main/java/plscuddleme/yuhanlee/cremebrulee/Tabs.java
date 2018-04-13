package plscuddleme.yuhanlee.cremebrulee;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
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
import android.widget.FrameLayout;
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
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


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

    public static class PlaceholderFragment extends Fragment {
        private static final String TAB_NUMBER = "section_number";
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(TAB_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            int tab = this.getArguments().getInt(TAB_NUMBER);
            View rootView=null;
            switch (tab) {
                case 1:
                    rootView = inflater.inflate(R.layout.activity_profile, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.images, container, false);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.messaging, container, false);
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
        editUserName = (TextView) findViewById(R.id.edit_user_name);
        editUserName.setText(userFullName);
    }

    @Override
    public void onClick(View v) {
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
}

