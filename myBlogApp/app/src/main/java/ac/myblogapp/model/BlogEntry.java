package ac.myblogapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 02-Sep-17.
 */

public class BlogEntry {

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
}
