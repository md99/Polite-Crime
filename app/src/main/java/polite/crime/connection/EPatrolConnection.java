package polite.crime.connection;


import org.apache.http.NameValuePair;

import java.util.List;

import okhttp3.ResponseBody;
import polite.crime.model.BasicResponse;
import polite.crime.model.CheckUser;
import polite.crime.model.Login;
import polite.crime.model.Save;
import polite.crime.model.User;
import polite.crime.model.contacts;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;


public interface EPatrolConnection {


//    @POST("CheckUser")
//    Observable<User> checkUser(@Body CheckUser checkUser);
//
//
//    @Streaming
//    @GET
//    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @GET("get-users")
    Observable<User> getUsers(@Body User user);

    @GET("get-contacts")
    Observable<BasicResponse<contacts>> getContacts(@Body User user);

    @POST("check-user")
    Observable<Login> checkUser(@Body CheckUser user);

    //
    @POST("register")
//    Observable<Save> register(@Body List<NameValuePair> requestVehicleTax);
    Observable<Save> register(@Body User user);    //

    @POST("emergency")
//    Observable<Save> register(@Body List<NameValuePair> requestVehicleTax);
    Observable<Save> emergency(@Body User user);

    @POST("add-contact")
//    Observable<Save> register(@Body List<NameValuePair> requestVehicleTax);
    Observable<Save> registerHuman(@Body User user);

//
//    //    Хүн   холболтийн хэсэг
//
//    @POST("GetCallInfosByPhone")
//    Observable<BasicResponse<CallDetailView>> GetCallInfosByPhone(@Body SearchRequest requestVehicleTax);
//
//    @POST("SaveCallInfoTimeStamp")
//    Observable<CallDetailView> SaveCallInfoTimeStamp(@Body SearchRequest requestVehicleTax);
//
//    @POST("SaveShiidverlelt")
//    Observable<CallDetailView> SaveShiidverlelt(@Body SearchRequest requestVehicleTax);
//
//    @POST("GetCallInfosByPromotion")
//    Observable<BasicResponse<CallDetailView>> GetCallInfosByPromotion(@Body SearchRequest requestVehicleTax);
//
//    @POST("GetAllZarlan")
//    Observable<BasicResponse<ECSNotification>> GetAllZarlan(@Body SearchRequest requestVehicleTax);
//
//    @POST("CheckZurchilHistory")
//    Observable<String> CheckZurchilHistory(@Body SearchRequest requestVehicleTax);
//
//    @POST("GetZarlanGroup")
//    Observable<BasicResponse<GroupNotification>> GetZarlanGroup(@Body SearchRequest requestVehicleTax);
//
//    @POST("SaveHugatsaaAldsan")
//    Observable<CallDetailView> SaveHugatsaaAldsan(@Body ECSLateResponse requestVehicleTax);
//
//    @POST("GetHugatsaaAldsanConst")
//    Observable<BasicResponse<ECSLateConst>> GetHugatsaaAldsanConst(@Body SearchRequest requestVehicleTax);
//
//    @POST("GetCallInfoDetail")
//    Observable<CallDetailView> GetCallInfoDetail(@Body SearchRequest indicate);
//
//    //M1100
//    @POST("GetECSConstData")
//    Observable<ECSItemTom> GetECSConstData(@Body SearchRequest requestVehicleTax);
//
//    @POST("CheckVersionECS")
//    Observable<String> CheckVersionECS(@Body CheckVersion version);
//
//    @POST("SaveM1100")
//    Observable<ECS> saveM1100(@Body ECS ecs);

}