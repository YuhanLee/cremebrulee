package plscuddleme.yuhanlee.cremebrulee;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by yuhanlee on 2018-04-15.
 */

public class ProfileFragment extends Fragment {
    private TextView editUserName;
    private String userFullName;
    Member member;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        member = ((Tabs)getActivity()).getMember();
        userFullName = member.getFullName();
        editUserName = (TextView) view.findViewById(R.id.edit_user_name);
        editUserName.setText(userFullName);
        return view;
    }

}