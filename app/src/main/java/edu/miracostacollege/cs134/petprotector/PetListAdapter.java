package edu.miracostacollege.cs134.petprotector;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import edu.miracostacollege.cs134.petprotector.model.Pet;

public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private List<Pet> mPetsList;
    private int mResourceId;



    /**
     * Creates a new <code>PetListAdapter</code> given a mContext, resource id and list of Pets.
     *
     * @param c The mContext for which the adapter is being used (typically an activity)
     * @param rId The resource id (typically the layout file name)
     * @param pets The list of Pets to display
     */
    public PetListAdapter(Context c, int rId, List<Pet> pets) {
        super(c, rId, pets);
        mContext = c;
        mResourceId = rId;
        mPetsList = pets;
    }

    /**
     * Gets the view associated with the layout.
     * @param pos The position of the Pet selected in the list.
     * @param convertView The converted view.
     * @param parent The parent - ArrayAdapter
     * @return The new view with all content set.
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent)
    {
        final Pet selectedPet = mPetsList.get(pos);

        LayoutInflater inflater =
                (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        LinearLayout petListLinearLayout =
                view.findViewById(R.id.petListLinearLayout);
        ImageView petListImageView =
                view.findViewById(R.id.petListImageView);
        TextView petListNameTextView =
                view.findViewById(R.id.petNameTextView);
        TextView petDetailsTextView =
                view.findViewById(R.id.petDetailsTextView);
        TextView petPhoneTextView  =
                view.findViewById(R.id.petPhoneTextView);

        petListLinearLayout.setTag(selectedPet);

        // For setting the image URI
        petListImageView.setImageURI(selectedPet.getImageUri());


        petListNameTextView.setText(selectedPet.getName());
        petDetailsTextView.setText(selectedPet.getDetails());
        if(selectedPet.getPhone().length() > 3)
        {
            petPhoneTextView.setText("(" +  selectedPet.getPhone().substring(0, 3) + ") " + selectedPet.getPhone().substring(3)) ;
        } else {
            petPhoneTextView.setText(selectedPet.getPhone()) ;
        }

        return view;
    }
}

