package polite.crime.connection;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceClient {
    private static ServiceClient instance;
    private Retrofit retrofit;
    private Map<String, Object> mClients = new HashMap<>();

    private ServiceClient() {
    }

    public static ServiceClient getInstance() {
        if (null == instance) {
            instance = new ServiceClient();
        }
        return instance;
    }

    public <T> T getClient(Class<T> clazz) {
        T client = null;
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .build();

            Retrofit retrofit = new Retrofit.Builder().
                    addConverterFactory(GsonConverterFactory.create())

                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

//                    .baseUrl("http://203.91.118.179:8324/JSONService.svc/")//wifi
//                    .baseUrl("http://10.10.20.43:8324/JSONService.svc/")//wifi
//                    .baseUrl("http://192.168.118.1:8324/JSONService.svc/")//data
                    .baseUrl("https://polite.mybluemix.net/")//data

                    .client(okHttpClient)
                    .build();
            if ((client = (T) mClients.get(clazz.getCanonicalName())) != null) {
                return client;
            }
            client = retrofit.create(clazz);
            mClients.put(clazz.getCanonicalName(), client);

        }
        return client;
    }


}
