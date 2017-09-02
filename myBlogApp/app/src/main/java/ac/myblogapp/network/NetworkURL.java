package ac.myblogapp.network;

/**
 * Created by Home on 29-Aug-17.
 */



public class NetworkURL {
    //change to server name(http://achy007mails.imad.hasura-app.io or IP address for external server
    public static final String BASE_URL = "http://192.168.1.19";
    public static final String LOGIN = "login";
    public static final String REGISTRATION = "new-user";
    public static final String GET_ARTICLE = "get-articles";

    //Todo: Find way to navigate to Article detail page
    public static final String ARTICLE_DETAIL = "article/:articleName";
}
