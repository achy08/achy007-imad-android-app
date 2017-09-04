package ac.myblogapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 02-Sep-17.
 */

public class BlogEntry implements Parcelable {

    @SerializedName("id")
    Integer id;

    @SerializedName("title")
    String  title;

    @SerializedName("date")
    String date;

    @SerializedName("content")
    String content;


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(date);
        dest.writeString(content);
    }
    protected BlogEntry(Parcel in) {
        title = in.readString();
        date = in.readString();
        content = in.readString();
    }

    public static final Creator<BlogEntry> CREATOR = new Creator<BlogEntry>() {
        @Override
        public BlogEntry createFromParcel(Parcel in) {
            return new BlogEntry(in);
        }

        @Override
        public BlogEntry[] newArray(int size) {
            return new BlogEntry[size];
        }
    };

}
