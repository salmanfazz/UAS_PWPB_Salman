package com.example.uas_pwpb_salman;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable{
    private int id;
    private String datetime;
    private String title;
    private String detail;

    public Note(){

    }

    public Note(int id,String datetime, String title, String detail) {
        this.id = id;
        this.datetime = datetime;
        this.title = title;
        this.detail = detail;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getId(){
        return id;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.datetime);
        dest.writeString(this.title);
        dest.writeString(this.detail);
    }

    protected Note(Parcel in) {
        this.id = in.readInt();
        this.datetime = in.readString();
        this.title = in.readString();
        this.detail = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
