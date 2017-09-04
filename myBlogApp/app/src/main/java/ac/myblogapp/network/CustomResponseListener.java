package ac.myblogapp.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

import ac.myblogapp.model.ErrorResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 04-Sep-17.
 */

public abstract class CustomResponseListener<T> implements Callback<T> {
    public abstract void onSuccessfulResponse(T response);

    public abstract void onFailedResponse(ErrorResponse errorResponse);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailedResponse(new ErrorResponse("DISASTER, SOMETHING FAILED"));

    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if(response.isSuccessful()){
            onSuccessfulResponse(response.body());
        } else {
            try {
                String errorMessage = response.errorBody().string();
                try{
                    ErrorResponse errorResponse = new Gson().fromJson(errorMessage, ErrorResponse.class);
                    onFailedResponse(errorResponse);
                } catch (JsonSyntaxException jsonException){
                    jsonException.printStackTrace();
                    onFailedResponse(new ErrorResponse("Something went wrong"));
                }
            } catch (IOException e) {
                e.printStackTrace();
                onFailedResponse(new ErrorResponse("Something went wrong"));
            }
        }


    }
}
