package com.example.ayatbahaa.waveapp.database;

import android.os.Parcel;

public class Orphan extends Person {
    private boolean mIsStudyingCurrently;
    private int mEducationLevel;
    private String mHobbies;
    private String mSkills;

    public Orphan(){

    }

    protected Orphan(Parcel in) {
        super(in);
        mEducationLevel = in.readInt();
        mIsStudyingCurrently = in.readByte() != 0;
        mHobbies = in.readString();
        mSkills = in.readString();
    }

    public static final Creator<Orphan> CREATOR = new Creator<Orphan>() {
        @Override
        public Orphan createFromParcel(Parcel in) {
            return new Orphan(in);
        }

        @Override
        public Orphan[] newArray(int size) {
            return new Orphan[size];
        }
    };


    public boolean isStudyingCurrently() { // firestore will read they key as studyingCurrently so we have to add annotation
        return mIsStudyingCurrently;
    }

    public void setStudyingCurrently(boolean studyingCurrently) {
        mIsStudyingCurrently = studyingCurrently;
    }


    public int getEducationLevel() {
        return mEducationLevel;
    }

    public void setEducationLevel(int educationLevel) {
        mEducationLevel =  educationLevel;
    }


    public String getHobbies() {
        return mHobbies;
    }

    public void setHobbies(String hobbies) {
        mHobbies = hobbies;
    }

    public String getSkills() {
        return mSkills;
    }

    public void setSkills(String skills) {
        mSkills = skills;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(mEducationLevel);
        parcel.writeByte((byte) (mIsStudyingCurrently ? 1 : 0));
        parcel.writeString(mHobbies);
        parcel.writeString(mSkills);
    }
}
