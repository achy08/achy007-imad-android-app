package ac.myblogapp.network;

import java.util.List;

import ac.myblogapp.model.AuthenticationRequest;
import ac.myblogapp.model.BlogEntry;
import ac.myblogapp.model.MessageResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Home on 29-Aug-17.
 */

public interface API_interface {

    @POST(NetworkURL.LOGIN)
    Call<MessageResponse> login(@Body AuthenticationRequest body);

    @POST(NetworkURL.REGISTRATION)
    Call<MessageResponse> registration(@Body AuthenticationRequest body);

    @GET(NetworkURL.GET_ARTICLE)
    Call<List<BlogEntry>> getBlog();

    @GET(NetworkURL.ARTICLE_DETAIL)
    Call<List<BlogEntry>> getBlogDetail();
}
