package sjj.permission.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by SJJ on 2017/7/30.
 */

public class Permission implements Serializable,Parcelable{
    public final String name;
    public final boolean granted;
    public final boolean shouldShowRequestPermissionRationale;

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRationale) {
        this.name = name;
        this.granted = granted;
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }

    protected Permission(Parcel in) {
        name = in.readString();
        granted = in.readByte() != 0;
        shouldShowRequestPermissionRationale = in.readByte() != 0;
    }

    public static final Creator<Permission> CREATOR = new Creator<Permission>() {
        @Override
        public Permission createFromParcel(Parcel in) {
            return new Permission(in);
        }

        @Override
        public Permission[] newArray(int size) {
            return new Permission[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (granted ? 1 : 0));
        dest.writeByte((byte) (shouldShowRequestPermissionRationale ? 1 : 0));
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", granted=" + granted +
                ", shouldShowRequestPermissionRationale=" + shouldShowRequestPermissionRationale +
                '}';
    }
}
