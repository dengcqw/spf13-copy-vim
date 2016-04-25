package net.silentfrog.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.silentfrog.whoiswho.R;
import net.silentfrog.whoiswho.adapters.ProfileListAdapter;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<String> myDataset = new ArrayList();
        myDataset.add("Pippo");
        myDataset.add("Pluto");
        myDataset.add("Paperino");
        myDataset.add("Topolino");
        myDataset.add("Minnie");
        myDataset.add("Pippo");
        myDataset.add("Pluto");
        myDataset.add("Paperino");
        myDataset.add("Topolino");
        myDataset.add("Minnie");
        myDataset.add("Pippo");
        myDataset.add("Pluto");
        myDataset.add("Paperino");
        myDataset.add("Topolino");
        myDataset.add("Minnie");

        // specify an adapter (see also next example)
        mAdapter = new ProfileListAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
