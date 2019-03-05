package com.seproject.crowdfunder.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.seproject.crowdfunder.R;

import java.io.File;


public class ProfileActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    }

    public void closeClicked(View view) {
        super.onBackPressed();
    }

    public void EditProfileClicked(View view) {
    }

    public void editProfilePic(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
            Uri selectedImageUri = data.getData();
            // Get the path from the Uri
            final String path = getPathFromURI(selectedImageUri);
            if (path != null) {
                File f = new File(path);
                selectedImageUri = Uri.fromFile(f);
            }
            // Set the image in ImageView
            ImageView image = findViewById(R.id.profilePic);
            image.setImageURI(selectedImageUri);
        }

    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public void DeactivateAccountClicked(View view) {
        startActivity(new Intent(this, ConfirmingPassword.class));
    }

    public void requestHist(View view) {
        startActivity(new Intent(ProfileActivity.this, RequestHistoryActivity.class));
    }

    public void yourBookmarkClicked(View view) {
        startActivity(new Intent(this, Chandan_Bookmarks.class));
    }
}
