package ac.myblogapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 29-Aug-17.
 */

public class MessageResponse {
    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }
}
