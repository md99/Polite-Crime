package polite.crime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by admin on 7/5/2017.
 */

public class Result implements Parcelable {
    public Result(){}
    protected Result(Parcel in) {
        _id = in.readString();
        _rev = in.readString();
        isSave = in.readString();
        mobile = in.readString();
        register = in.readString();
        password = in.readString();
        contacts = in.createTypedArrayList(polite.crime.model.contacts.CREATOR);
        emergencies = in.createTypedArrayList(polite.crime.model.emergencies.CREATOR);
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
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

    public String getIsSave() {
        return isSave;
    }

    public void setIsSave(String isSave) {
        this.isSave = isSave;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<polite.crime.model.contacts> getContacts() {
        return contacts;
    }

    public void setContacts(List<polite.crime.model.contacts> contacts) {
        this.contacts = contacts;
    }

    public List<polite.crime.model.emergencies> getEmergencies() {
        return emergencies;
    }

    public void setEmergencies(List<polite.crime.model.emergencies> emergencies) {
        this.emergencies = emergencies;
    }

    private String _id;
    private String _rev;
    private String isSave;
    private String mobile;
    private String register;
    private String password;
    private List<contacts> contacts;
    private List<emergencies> emergencies;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(_rev);
        parcel.writeString(isSave);
        parcel.writeString(mobile);
        parcel.writeString(register);
        parcel.writeString(password);
        parcel.writeTypedList(contacts);
        parcel.writeTypedList(emergencies);
    }
}
