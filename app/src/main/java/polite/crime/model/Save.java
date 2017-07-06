package polite.crime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.List;

/**
 * Created by admin on 7/5/2017.
 */

public class Save implements Parcelable {
    protected Save(Parcel in) {
        isSave = in.readByte() != 0;
        result = in.readString();
        contactsList = in.createTypedArrayList(contacts.CREATOR);
    }

    public static final Creator<Save> CREATOR = new Creator<Save>() {
        @Override
        public Save createFromParcel(Parcel in) {
            return new Save(in);
        }

        @Override
        public Save[] newArray(int size) {
            return new Save[size];
        }
    };

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<contacts> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<contacts> contactsList) {
        this.contactsList = contactsList;
    }

    boolean isSave;
    String result;
    List<contacts> contactsList;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (isSave ? 1 : 0));
        parcel.writeString(result);
        parcel.writeTypedList(contactsList);
    }
}
