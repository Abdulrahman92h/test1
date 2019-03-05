package com.example.ayatbahaa.waveapp.database;

import android.net.Uri;
import android.os.Parcel;

/**
 * Created by abdulrahman helan on 03/10/2018.
 */

public class Elderly extends Person{
    private String mPhone;

    public Elderly(){

    }

    protected Elderly(Parcel in) {
        super(in);
        mPhone = in.readString();
    }

    public static final Creator<Elderly> CREATOR = new Creator<Elderly>() {
        @Override
        public Elderly createFromParcel(Parcel in) {
            return new Elderly(in);
        }

        @Override
        public Elderly[] newArray(int size) {
            return new Elderly[size];
        }
    };

    public String getPhone() {  return mPhone;    }

    public void setPhone(String mPhone) {  this.mPhone = mPhone;    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(mPhone);
    }
}
