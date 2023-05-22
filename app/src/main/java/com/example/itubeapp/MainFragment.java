


/*
            Name        :  Surpreet Singh
            Student ID  :  218663803
            Unit No.    :  SIT305

 */

package com.example.itubeapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sqllitehelper.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    //Declaring DatabaseHelper variable
    DatabaseHelper databaseHelper;

    public MainFragment() {
        // Required empty public constructor
    }

    // Factory method to create a new instance of the fragment
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Retrieve any arguments here
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Find the signup and login buttons and the username and password EditText fields
        Button signupButton = view.findViewById(R.id.button2);
        Button loginButton = view.findViewById(R.id.button);
        EditText userName = view.findViewById(R.id.editTextUserName);
        EditText passWord = view.findViewById(R.id.editTextPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of DatabaseHelper
                databaseHelper = new DatabaseHelper(getContext());

                // Check if the entered username and password exist in the database
                boolean result = databaseHelper.getUser(
                        userName.getText().toString(), passWord.getText().toString()
                );

                if (result) {
                    Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                    // Navigate to the HomeFragment after successful login
                    Fragment fragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainActivityLayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    Toast.makeText(getContext(), "User Does not Exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the SignUpFragment when the signup button is clicked
                Fragment fragment = SignUpFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.mainActivityLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
