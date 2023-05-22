


/*
            Name        :  Surpreet Singh
            Student ID  :  218663803
            Unit No.    :  SIT305

 */

package com.example.itubeapp;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import sqllitehelper.PlaylistData;
import sqllitehelper.PlaylistDatabaseHelper;

public class MyPlaylistFragment extends Fragment {

    public MyPlaylistFragment() {
        // Required empty public constructor
    }

    public static MyPlaylistFragment newInstance() {
        MyPlaylistFragment fragment = new MyPlaylistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_playlist, container, false);

        TextView playlistTextView = view.findViewById(R.id.playlisttextView);

        // Retrieve the playlist from the database
        PlaylistDatabaseHelper databaseHelper = new PlaylistDatabaseHelper(getActivity());
        List<PlaylistData> playlist = databaseHelper.getPlaylist();

        // Build the playlist text
        StringBuilder playlistText = new StringBuilder();
        for (int i = 0; i < playlist.size(); i++) {
            PlaylistData playlistData = playlist.get(i);
            String link = playlistData.getLink();

            // Append the playlist number and link with appropriate formatting
            playlistText.append("Video ").append(i + 1).append(": ").append(link).append("\n\n");
        }

        // Set the playlist text in the TextView
        playlistTextView.setText(playlistText.toString());

        // Set long-click listener on playlistTextView to enable copying the link
        playlistTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Get the link text
                String linkText = ((TextView) v).getText().toString();

                // Copy the link to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Playlist Link", linkText);
                clipboard.setPrimaryClip(clip);

                // Show a toast to indicate that the link has been copied
                Toast.makeText(getActivity(), "Link copied to clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        return view;
    }
}
