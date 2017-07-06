package polite.crime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by admin on 7/5/2017.
 */

public class Login implements Parcelable {


    public String logged;
    public Result result;

    public Login() {
    }

    protected Login(Parcel in) {
        logged = in.readString();
        result = in.readParcelable(Result.class.getClassLoader());
    }

    public static final Creator<Login> CREATOR = new Creator<Login>() {
        @Override
        public Login createFromParcel(Parcel in) {
            return new Login(in);
        }

        @Override
        public Login[] newArray(int size) {
            return new Login[size];
        }
    };

    public String getLogged() {
        return logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(logged);
        parcel.writeParcelable(result, i);
    }
}