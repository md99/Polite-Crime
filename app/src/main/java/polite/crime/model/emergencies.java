package polite.crime.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 7/5/2017.
 */

public class emergencies implements Parcelable {

    public emergencies() {
    }

    protected emergencies(Parcel in) {
        lat = in.readDouble();
        lon = in.readDouble();
        date = in.readString();
        w3w = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(date);
        dest.writeString(w3w);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<emergencies> CREATOR = new Creator<emergencies>() {
        @Override
        public emergencies createFromParcel(Parcel in) {
            return new emergencies(in);
        }

        @Override
        public emergencies[] newArray(int size) {
            return new emergencies[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getW3w() {
        return w3w;
    }

    public void setW3w(String w3w) {
        this.w3w = w3w;
    }

    private double lat;
    private double lon;
    private String date;
    private String w3w;
}
