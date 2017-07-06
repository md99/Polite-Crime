package polite.crime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by admin on 7/5/2017.
 */

public class User implements Parcelable {
    public User() {
    }

    ;

    private String _id;
    private String _rev;
    private String name;
    private String password;
    private String email;
    private String mobile;
    private String relation;
    private String register;
    private contacts contact;
    private emergencies emergency;

    protected User(Parcel in) {
        _id = in.readString();
        _rev = in.readString();
        name = in.readString();
        password = in.readString();
        email = in.readString();
        mobile = in.readString();
        relation = in.readString();
        register = in.readString();
        contact = in.readParcelable(contacts.class.getClassLoader());
        emergency = in.readParcelable(emergencies.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(_id);
        dest.writeString(_rev);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(mobile);
        dest.writeString(relation);
        dest.writeString(register);
        dest.writeParcelable(contact, flags);
        dest.writeParcelable(emergency, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public contacts getContact() {
        return contact;
    }

    public void setContact(contacts contact) {
        this.contact = contact;
    }

    public emergencies getEmergency() {
        return emergency;
    }

    public void setEmergency(emergencies emergency) {
        this.emergency = emergency;
    }
}
