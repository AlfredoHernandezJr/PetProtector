package edu.miracostacollege.cs134.petprotector;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.miracostacollege.cs134.petprotector.model.DBHelper;
import edu.miracostacollege.cs134.petprotector.model.Pet;

public class MainActivity extends AppCompatActivity {

    private ImageView petImageView ;
    private Uri currentImage ;

    private List<Pet> mPets ;
    private DBHelper mDB ;
    private ListView mPetListView ;
    private PetListAdapter mPetListAdapter ;

    private long petId = 1 ;

    // Arbitrary constant.
    public static final int RESULT_LOAD_IMAGE = 101 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire up the view with the layout.
        petImageView = findViewById(R.id.petImageView) ;
        currentImage = getUriToResource(this, R.drawable.none) ;

        // Assign the petImageView to the currentImage (1 LINE OF OF CODE!!!)
        petImageView.setImageURI(currentImage) ;

        /** Details and Management of adding Pets*/
        mDB = new DBHelper(this);

        mPets = mDB.getAllPets() ;

        mPetListAdapter = new PetListAdapter(this, R.layout.pet_list_item, mPets);
        mPetListView = findViewById(R.id.petListView);
        mPetListView.setAdapter(mPetListAdapter);
    }

    /**
     * Adds new pets to the
     * @param v Current pet
     */
    public void addPet(View v)
    {
        EditText nameEditText = findViewById(R.id.petNameEditText) ;
        EditText detailsEditText = findViewById(R.id.petDetailsEditText) ;
        EditText phoneEditText = findViewById(R.id.petPhoneEditText) ;

        String name = nameEditText.getText().toString() ;
        String details = detailsEditText.getText().toString() ;
        String phone = phoneEditText.getText().toString() ;
        if(currentImage == null || TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Name, details, and phone of the pet must be provided.", Toast.LENGTH_LONG);
            return ;
        }

        Pet newPet = new Pet(petId++, name, details, phone, currentImage) ;

        mDB.addPet(newPet) ;
        mPetListAdapter.add(newPet) ;
        nameEditText.setText("") ;
        detailsEditText.setText("") ;
        phoneEditText.setText("") ;
    }

    public void viewPetDetails(View v)
    {
        Pet selectedPet = (Pet) v.getTag() ;
        Intent detailsIntent = new Intent(this, PetDetailsActivity.class) ;
        detailsIntent.putExtra("SelectedPet", selectedPet) ;
        startActivity(detailsIntent) ;
    }

    // Make a helper method to construct a Uri in the form
    // android.resource://packagename/resource type/resource name
    public static Uri getUriToResource(Context context, int resId)
    {
        // Let's get a reference to all the resources in our app:
        Resources res = context.getResources() ;

        // Use ContentResolver to always have the valid schema.
        String uriString = ContentResolver.SCHEME_ANDROID_RESOURCE
                            + "://" + res.getResourcePackageName(resId)
                            + "/" + res.getResourceTypeName(resId)
                            + "/" + res.getResourceEntryName(resId) ;

        // Convert the String into a Uri object.
        return Uri.parse(uriString) ;
    }

    // Method that responds to the click of the PetImageView.
    public void selectPetImage(View v)
    {
        /** Permissions requesting */

        // To request permissions from the user, let's see what
        // they have enabled already.
        List<String> permsReqList = new ArrayList<>() ;

        // Make up request code for permissions, code is arbitrary.
        int permsRequestCode = 8 ;

        // Permissions: Camera, Read Ext Storage, Write External Storage
        // Permissions are integer codes (GRANTED or not)
        int haveCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ;
        int haveReadPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ;
        int haveWritePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ;


        if(haveCameraPerm != PackageManager.PERMISSION_GRANTED)
            permsReqList.add(Manifest.permission.CAMERA) ;

        if(haveReadPerm != PackageManager.PERMISSION_GRANTED)
            permsReqList.add(Manifest.permission.READ_EXTERNAL_STORAGE) ;

        if(haveWritePerm != PackageManager.PERMISSION_GRANTED)
            permsReqList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE) ;


        // If the size of the list > 0, we have some permissions to request.
        if(permsReqList.size() > 0)
        {
            // Convert the List into an array for requesting.
            String[] perms = new String[permsReqList.size()] ;
            permsReqList.toArray(perms) ;

            // Request the permissions.
            ActivityCompat.requestPermissions(this, perms, permsRequestCode);
        }

        /** Changing the petImageView */

        // Check to ensure that all 3 permissions have been granted.
        if(haveCameraPerm == PackageManager.PERMISSION_GRANTED
                && haveReadPerm == PackageManager.PERMISSION_GRANTED
                && haveWritePerm == PackageManager.PERMISSION_GRANTED)
        {
            // Open the image gallery on the phone!

            // Make an intent to pick an image from gallery.
            Intent intent = new Intent(Intent.ACTION_PICK) ;
            intent.setType("image/*") ;
            startActivityForResult(intent, RESULT_LOAD_IMAGE) ;
        }
        // User has NOT enabled permissions!
        else
        {
            Toast.makeText(this,
                    "App requires access to external storage. Please enable permissions",
                    Toast.LENGTH_LONG).show() ;
        }
    }

    // Override a method called onActivityResult (after user has picked a picture!)
    // ctrl + o => override menu

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // This method is called after firing an intent for a result
        // Identify that we're loading an image
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK)
        {
            currentImage = data.getData() ;
            petImageView.setImageURI(currentImage) ;
        }
    }
}
