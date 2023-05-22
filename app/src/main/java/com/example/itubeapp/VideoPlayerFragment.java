


/*
            Name        :  Surpreet Singh
            Student ID  :  218663803
            Unit No.    :  SIT305

 */

package com.example.itubeapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPlayerFragment extends Fragment {

    // Argument key for the video URL
    private static final String ARG_PARAM1 = "param1";

    // Video URL parameter
    private String mParam1;

    public VideoPlayerFragment() {
        // Required empty public constructor
    }

    // Factory method to create a new instance of the fragment with a video URL parameter
    public static VideoPlayerFragment newInstance(String param1) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve the video URL from the arguments
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videp_player, container, false);

        // Find the WebView in the layout
        WebView webView = view.findViewById(R.id.webView);

        // Get the video URL from the arguments
        String videoUrl = mParam1;

        // Enable JavaScript in the WebView
        webView.getSettings().setJavaScriptEnabled(true);

        // Set a WebViewClient to handle page loading and JavaScript execution
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Execute JavaScript code to start playing the video
                view.loadUrl("javascript:player.playVideo()");
            }
        });

        // Load the HTML content with the video URL into the WebView
        webView.loadData("<html>" +
                        "<body>" +
                        "<iframe width=\"100%\" height=\"100%\" src=\""
                        + videoUrl + "?enablejsapi=1\" frameborder=\"0\" allowfullscreen>" +
                        "</iframe>" +
                        "</body>" +
                        "</html>",
                "text/html",
                "utf-8");

        return view;
    }
}
