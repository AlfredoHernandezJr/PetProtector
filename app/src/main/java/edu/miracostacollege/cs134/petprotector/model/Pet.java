package edu.miracostacollege.cs134.petprotector.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {

    // Parameters for a Pet object.
    private long mId ;
    private String mName ;
    private String mDetails ;
    private String mPhone ;
    private Uri mImageUri ;

    /**
     * Full Constructor
     * @param id unique id of the specified pet
     * @param name name of the pet
     * @param details details about the pet
     * @param phone phone number to call about the pet
     * @param imageUri image of the pet
     */
    public Pet(long id, String name, String details, String phone, Uri imageUri)
    {
        mId = id ;
        mName = name ;
        mDetails = details ;
        mPhone = phone ;
        mImageUri = imageUri ;
    }

    /**
     * Constructor without Id
     * @param name name of the pet
     * @param details details about the pet
     * @param phone phone number to call about the pet
     * @param imageUri image of the pet
     */
    public Pet(String name, String details, String phone, Uri imageUri)
    {
        mName = name ;
        mDetails = details ;
        mPhone = phone ;
        mImageUri = imageUri ;
    }

    /**
     * Returns the Pet's id
     * @return int id
     */
    public long getId() {
        return mId;
    }

    /**
     * Returns the name of the pet.
     * @return String of name
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets the name of the Pet
     * @param mName new name of Pet
     */
    public void setName(String mName) {
        this.mName = mName;
    }

    /**
     * Gets the details about the pet
     * @return String details of the Pet
     */
    public String getDetails() {
        return mDetails;
    }

    /**
     * Sets the details of a pet
     * @param mDetails new details of the Pet
     */
    public void setDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    /**
     * Returns the Phone number to call about the Pet to
     * @return String representation of the phone number
     */
    public String getPhone() {
        return mPhone;
    }

    /**
     * Sets the phone number related to the Pet
     * @param mPhone new phone related to the Pet
     */
    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    /**
     * Returns URI of the Pet
     * @return Image reference of the Pet
     */
    public Uri getImageUri() {
        return mImageUri;
    }

    /**
     * Sets the image of the Pet
     * @param mImageUri new image of the Pet
     */
    public void setImageUri(Uri mImageUri) {
        this.mImageUri = mImageUri;
    }

    /**
     * String representation of the Pet
     * @return String of Pet variables
     */
    @Override
    public String toString() {
        return "Pet{" +
                "mID=" + mId +
                ", mName='" + mName + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mImageUri=" + mImageUri +
                '}';
    }

    public Pet(Parcel parcel)
    {
        mId = parcel.readLong() ;
        mName = parcel.readString() ;
        mDetails = parcel.readString() ;
        mPhone = parcel.readString() ;

        mImageUri = Uri.parse(parcel.readString()) ;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId) ;
        dest.writeString(mName) ;
        dest.writeString(mDetails) ;
        dest.writeString(mPhone) ;
        dest.writeString(mImageUri.toString());
    }

    public static final Parcelable.Creator<Pet> CREATOR = new Parcelable.Creator<Pet>()
    {
        @Override
        public Pet createFromParcel(Parcel source) {
            return new Pet(source);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    } ;
}
