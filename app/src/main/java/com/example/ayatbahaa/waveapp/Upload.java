package com.example.ayatbahaa.waveapp;

public class Upload {

    String mName1;
    String mImage1;
    String mName2;

    public Upload(){

    }

    public Upload(String mName1,String mImage1 ,String mName2){

        if (mName1.trim().equals("")){
            mName1="no name";
        }
        this.mName1=mName1;
        this.mImage1=mImage1;
        this.mName2=mName2;
    }


    public String getmName1() {
        return mName1;
    }

    public String getmImage1() {
        return mImage1;
    }

    public String getmName2() {
        return mName2;
    }
    public void setmName(String mName1) {
        this.mName1 = mName1;
    }


    public void setmName2(String mName2) {

        this.mName2 = mName2;
    }

    public void setmImage1(String mImage1) {
        this.mImage1 = mImage1;
    }

}
