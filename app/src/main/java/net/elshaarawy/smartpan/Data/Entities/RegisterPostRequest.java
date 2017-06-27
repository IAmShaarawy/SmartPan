package net.elshaarawy.smartpan.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class RegisterPostRequest implements Parcelable {
    private String name,username,password,mail;
    private int gender;
    private String mobile;

    public RegisterPostRequest(String name, String username, String password, String mail, int gender, String mobile) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.gender = gender;
        this.mobile = mobile;
    }

    protected RegisterPostRequest(Parcel in) {
        name = in.readString();
        username = in.readString();
        password = in.readString();
        mail = in.readString();
        gender = in.readInt();
        mobile = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(mail);
        dest.writeInt(gender);
        dest.writeString(mobile);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegisterPostRequest> CREATOR = new Creator<RegisterPostRequest>() {
        @Override
        public RegisterPostRequest createFromParcel(Parcel in) {
            return new RegisterPostRequest(in);
        }

        @Override
        public RegisterPostRequest[] newArray(int size) {
            return new RegisterPostRequest[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
