


/*
            Name        :  Surpreet Singh
            Student ID  :  218663803
            Unit No.    :  SIT305

 */

package com.example.itubeapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import sqllitehelper.DatabaseHelper;
import sqllitehelper.UserData;

public class SignUpFragment extends Fragment {

    private static final int REQUEST_CODE_PHOTO_PERMISSION = 1;
    private static final int REQUEST_CODE_PHOTO_PICKER = 2;

    private ImageView profilePhoto;
    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText phoneNumberEditText;
    private Button createAccountButton;
    private DatabaseHelper databaseHelper;
    private Uri selectedImageUri;

    public SignUpFragment() {
        // Required empty public constructor
    }

    // Create a new instance of SignUpFragment
    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // Initialize UI elements
        profilePhoto = view.findViewById(R.id.imageView);
        fullNameEditText = view.findViewById(R.id.editTextPassword);
        usernameEditText = view.findViewById(R.id.editTextTextPersonName3);
        passwordEditText = view.findViewById(R.id.editTextTextPersonName4);
        confirmPasswordEditText = view.findViewById(R.id.editTextTextPersonName5);
        phoneNumberEditText = view.findViewById(R.id.editTextPhone);
        createAccountButton = view.findViewById(R.id.button3);
        databaseHelper = new DatabaseHelper(getActivity());

        // Set click listener for profile photo
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPhotoPermission();
            }
        });

        // Set click listener for create account button
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input values
                String fullName = fullNameEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert user data into the database
                long result = databaseHelper.insertUser(new UserData(fullName, username, password, phoneNumber));

                // Check if account creation was successful
                if (result == -1) {
                    Toast.makeText(getContext(), "Failed to create an account", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Account created successfully", Toast.LENGTH_SHORT).show();
                    // Redirect to HomeFragment
                    Fragment fragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.mainActivityLayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

    // Request permission to access external storage for photo selection
    private void requestPhotoPermission() {
        // Check if the permission to access external storage is granted
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_PHOTO_PERMISSION);
        } else {
            // Permission is already granted, open the photo picker
            openPhotoPicker();
        }
    }

    // Open the photo picker to select an image
    private void openPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PHOTO_PICKER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PHOTO_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, open the photo picker
                openPhotoPicker();
            } else {
                // Permission is denied, show a toast message
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO_PICKER && resultCode == Activity.RESULT_OK && data != null) {
            // Photo picker result is successful
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Set the selected image URI to the ImageView
                profilePhoto.setImageURI(selectedImageUri);
            }
        }
    }
}
