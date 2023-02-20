package com.project.stylebuyer.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.project.stylebuyer.R;
import com.project.stylebuyer.auth.LoginActivity;
import com.project.stylebuyer.firebase.Api;
import com.project.stylebuyer.firebase.Customer;
import java.util.Objects;


public class ProfileFragment extends Fragment {
    int LAUNCH_EDIT_ACTIVITY = 1;
    private Activity activity;
    private ImageView profile_image;
    private FloatingActionButton profile_FBTN_uploadImage;
    private LinearLayout profile_LL_editDetails, profile_LL_logout;
    private TextView profile_TV_name, profile_TV_email;
    private Api api;
    private Customer currentCustomer;

    public ProfileFragment(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(root);
        initVars();
        return root;
    }

    private void initVars() {
        api = new Api();
        FirebaseUser firebaseUser =  api.getCurrentUser();
        currentCustomer = new Customer()
                .setEmail(firebaseUser.getEmail())
                .setFullName(firebaseUser.getDisplayName())
                .setImgUrl(firebaseUser.getPhotoUrl());
        if(currentCustomer.getImgUrl() != null){
            Glide.with(activity).load(currentCustomer.getImgUrl()).into(profile_image);
        }

        profile_TV_name.setText(currentCustomer.getFullName());
        profile_TV_email.setText(currentCustomer.getEmail());
        profile_FBTN_uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        profile_LL_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.logout();
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

        profile_LL_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, AccountDetailsActivity.class);

                startActivityForResult(intent, LAUNCH_EDIT_ACTIVITY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_EDIT_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String name = data.getStringExtra("NAME");
                profile_TV_name.setText(name);
            }
        }
    }
    private void findViews(View view) {
        profile_FBTN_uploadImage = view.findViewById(R.id.profile_FBTN_uploadImage);
        profile_LL_editDetails = view.findViewById(R.id.profile_LL_editDetails);
        profile_LL_logout = view.findViewById(R.id.profile_LL_logout);
        profile_TV_email = view.findViewById(R.id.profile_TV_email);
        profile_TV_name = view.findViewById(R.id.profile_TV_name);
        profile_image = view.findViewById(R.id.profile_image);
    }

    public void uploadImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        someActivityResultLauncher.launch(photoPickerIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        Uri imageUri = Objects.requireNonNull(data).getData();
                        Glide.with(activity).load(imageUri).into(profile_image);
                        api.updateUserProfileImage(imageUri);
                    }else {
                        Toast.makeText(activity, "You haven't picked Image",Toast.LENGTH_LONG).show();
                    }
                }
            });
}