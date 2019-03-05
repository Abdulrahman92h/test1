package com.example.ayatbahaa.waveapp.database;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;

import java.util.List;

public class Person implements Parcelable {
    private int mAge;
    private String mDocumentId;
    private String mName;
    private String mBirthday;
    private String mPlaceOfBirth;
    private String mEducation;
    private String mDescription;
    private String mGender;
    private String pictureUrl;
    private Uri pictureUri;
    private boolean mIsMarried;
    private String mDiseases;

    @Exclude
    public int getAge() {
        return mAge;
    }

    private void setAge(String birthday) {
        String[] parts= birthday.split("/");

        LocalDate bd= LocalDate.of(
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[0]));

        Period age= bd.until(LocalDate.now());
        mAge= age.getYears();
    }

    public String getDocumentId() {
        return mDocumentId;
    }

    public void setDocumentId(String documentId) {
        mDocumentId = documentId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
        if (birthday != null){
            setAge(birthday);
        }
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
    }

    public String getEducation() {
        return mEducation;
    }

    public void setEducation(String education) {
        mEducation = education;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Uri getPictureUri() {
        return pictureUri;
    }

    public void setPictureUri(Uri pictureUri) {
        this.pictureUri = pictureUri;
    }

    public boolean isMarried() {
        return mIsMarried;
    }

    public void setMarried(boolean married) {
        mIsMarried = married;
    }

    public String getDiseases() {
        return mDiseases;
    }

    public void setDiseases(String diseases) {
        mDiseases = diseases;
    }

    public Person(){

    }


    protected Person(Parcel in) {
        mAge = in.readInt();
        mDocumentId = in.readString();
        mName = in.readString();
        mBirthday = in.readString();
        mPlaceOfBirth = in.readString();
        mEducation = in.readString();
        mDescription = in.readString();
        mGender = in.readString();
        pictureUrl = in.readString();
        pictureUri = in.readParcelable(Uri.class.getClassLoader());
        mIsMarried = in.readByte() != 0;
        mDiseases = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mAge);
        dest.writeString(mDocumentId);
        dest.writeString(mName);
        dest.writeString(mBirthday);
        dest.writeString(mPlaceOfBirth);
        dest.writeString(mEducation);
        dest.writeString(mDescription);
        dest.writeString(mGender);
        dest.writeString(pictureUrl);
        dest.writeParcelable(pictureUri, flags);
        dest.writeByte((byte) (mIsMarried ? 1 : 0));
        dest.writeString(mDiseases);
    }
}
