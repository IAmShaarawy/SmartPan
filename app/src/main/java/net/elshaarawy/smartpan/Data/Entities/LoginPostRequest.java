package net.elshaarawy.smartpan.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class LoginPostRequest implements Parcelable {
    private String username,password;

    public LoginPostRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected LoginPostRequest(Parcel in) {
        username = in.readString();
        password = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoginPostRequest> CREATOR = new Creator<LoginPostRequest>() {
        @Override
        public LoginPostRequest createFromParcel(Parcel in) {
            return new LoginPostRequest(in);
        }

        @Override
        public LoginPostRequest[] newArray(int size) {
            return new LoginPostRequest[size];
        }
    };

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
}
