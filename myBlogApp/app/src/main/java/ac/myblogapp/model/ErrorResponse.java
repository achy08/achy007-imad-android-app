package ac.myblogapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Home on 29-Aug-17.
 */

public class ErrorResponse {
    @SerializedName("error")
    String error;

    public String getError() {
        return error;
    }
}
