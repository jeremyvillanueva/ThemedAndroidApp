package nz.co.tsg.themedapp.ui.foldingtabbar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.ui.Branding;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class ChatsFragment extends Fragment {

    ThemedApplication mApp;
    Branding mBranding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        mApp = (ThemedApplication) getActivity().getApplication();
        mBranding = mApp.getmBranding();
        createUI(view);

        TextView chatTitle = (TextView) view.findViewById(R.id.chats_title);
        chatTitle.setTextColor(mBranding.getTextColorOverMainBackground());

        return view;
    }

    private void createUI(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_chats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ChatsAdapter());

    }
}
