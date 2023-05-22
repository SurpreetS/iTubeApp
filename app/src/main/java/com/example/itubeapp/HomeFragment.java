


/*
            Name        :  Surpreet Singh
            Student ID  :  218663803
            Unit No.    :  SIT305

 */

package com.example.itubeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sqllitehelper.PlaylistData;
import sqllitehelper.PlaylistDatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button playButton = view.findViewById(R.id.playbutton);

        Button addToPlaylistButton = view.findViewById(R.id.addToPlaylistbutton);
        Button myPlaylistButton = view.findViewById(R.id.myPlaylistbutton);
        EditText url = view.findViewById(R.id.editTextURL);




        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl= url.getText().toString();
                Fragment fragment = VideoPlayerFragment.newInstance(videoUrl);
                // Start new transaction to replace current fragment with VideoPlayerFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainActivityLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        addToPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = url.getText().toString();

                // Create a new PlaylistData object with the video URL
                PlaylistData playlistData = new PlaylistData(videoUrl);

                // Insert the playlist data into the database
                PlaylistDatabaseHelper databaseHelper = new PlaylistDatabaseHelper(getActivity());
                long rowID = databaseHelper.insertLink(playlistData);

                if (rowID != -1) {
                    // Insertion successful
                    Toast.makeText(getActivity(), "Added to playlist!", Toast.LENGTH_SHORT).show();
                } else {
                    // Insertion failed
                    Toast.makeText(getActivity(), "Failed to add to playlist!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        myPlaylistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = MyPlaylistFragment.newInstance();
                // Start new transaction to replace current fragment with MyPlaylistFragment
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainActivityLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });




        return view;

    }
}