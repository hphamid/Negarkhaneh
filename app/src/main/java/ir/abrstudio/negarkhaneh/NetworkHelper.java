package ir.abrstudio.negarkhaneh;

import com.squareup.okhttp.OkHttpClient;

/**
 * Created by hamid on 7/26/16.
 *
 */

public class NetworkHelper {
    private static NetworkHelper _instance;

    private OkHttpClient client;
    private HttpAddressHelper addressHelper;

    private NetworkHelper(){

    }

    public static NetworkHelper getInstance(){
        if(_instance == null){
            _instance = new NetworkHelper();
        }
        return _instance;
    }

    public OkHttpClient getClient(){
        if(client == null){
            client = new OkHttpClient();
        }
        return client;
    }

    public HttpAddressHelper getAddressHelper(){
        if(addressHelper == null){
            addressHelper = new HttpAddressHelper();
        }
        return addressHelper;
    }
}
