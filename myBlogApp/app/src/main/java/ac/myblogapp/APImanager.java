package ac.myblogapp;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Home on 29-Aug-17.
 */

public class APImanager {
    private static API_interface apiInterface;
    private static void createAPI_interface(){

        //Create logs
        HttpLoggingInterceptor jenson = new HttpLoggingInterceptor();
        jenson.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Create Client
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(jenson)
                .build();


        //Connect to Web API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkURL.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(API_interface.class);
    }

    public static API_interface getApiInterface(){
        if (apiInterface == null){
            createAPI_interface();
        }
        return apiInterface;
    }


}
