package polite.crime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 7/5/2017.
 */

public class contacts implements Parcelable {
    public contacts() {
    }

    ;

    protected contacts(Parcel in) {
        relation = in.readString();
        name = in.readString();
        mobile = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(relation);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<contacts> CREATOR = new Creator<contacts>() {
        @Override
        public contacts createFromParcel(Parcel in) {
            return new contacts(in);
        }

        @Override
        public contacts[] newArray(int size) {
            return new contacts[size];
        }
    };

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String relation;
    private String name;
    private String mobile;
    private String email;
}
