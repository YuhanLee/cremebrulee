package plscuddleme.yuhanlee.cremebrulee;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yuhanlee on 2018-04-15.
 */

public class MessagingFragment extends Fragment {
    Member member;

    public static MessagingFragment newInstance() {
        MessagingFragment fragment = new MessagingFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        member = ((Tabs)getActivity()).getMember();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging, container, false);
        return view;
    }
}
