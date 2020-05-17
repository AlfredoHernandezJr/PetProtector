package edu.miracostacollege.cs134.petprotector;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.miracostacollege.cs134.petprotector.model.Pet;

public class PetDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        ImageView petDetailsImageView = findViewById(R.id.petDetailsImageView);
        TextView petDetailsNameTextView = findViewById(R.id.petDetailsNameTextView);
        TextView petDetailsDetailsTextView = findViewById(R.id.petDetailsDetailsTextView);
        TextView petDetailsPhoneTextView = findViewById(R.id.petDetailsPhoneTextView);

        Pet selectedPet = getIntent().getExtras().getParcelable("SelectedPet") ;

        petDetailsImageView.setImageURI(selectedPet.getImageUri()) ;
        petDetailsNameTextView.setText(selectedPet.getName()) ;
        petDetailsDetailsTextView.setText(selectedPet.getDetails());
        petDetailsPhoneTextView.setText(selectedPet.getPhone());
    }
}
