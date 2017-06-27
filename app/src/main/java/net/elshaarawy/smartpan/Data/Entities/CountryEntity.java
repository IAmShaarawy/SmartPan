package net.elshaarawy.smartpan.Data.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by elshaarawy on 25-Jun-17.
 */

public class CountryEntity implements Parcelable {
    private String name,alpha2Code,capital;

    public CountryEntity(String name, String alpha2Code, String capital) {
        this.name = name;
        this.alpha2Code = alpha2Code;
        this.capital = capital;
    }

    protected CountryEntity(Parcel in) {
        name = in.readString();
        alpha2Code = in.readString();
        capital = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(alpha2Code);
        dest.writeString(capital);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryEntity> CREATOR = new Creator<CountryEntity>() {
        @Override
        public CountryEntity createFromParcel(Parcel in) {
            return new CountryEntity(in);
        }

        @Override
        public CountryEntity[] newArray(int size) {
            return new CountryEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public void setAlpha2Code(String alpha2Code) {
        this.alpha2Code = alpha2Code;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }
}
