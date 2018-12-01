package com.munjzservice.api;

import com.munjzservice.balance.model.BalanceResponse;
import com.munjzservice.profile.model.Customer;
import com.munjzservice.login.model.LoginRequestModel;
import com.munjzservice.servicerequest.model.NotificationServiceModel;
import com.munjzservice.share.model.ShareRequest;
import com.munjzservice.share.model.ShareResponse;
import com.munjzservice.summary.model.Summary;
import com.munjzservice.tab.active.model.ActiveModel;
import com.munjzservice.tab.active.model.ServiceRequest;
import com.munjzservice.tab.active.model.StatusChange;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mac on 12/14/17.
 */

public interface APICall {

    @POST("logincheck")
    Observable<Response<Customer>> customerLogin(@Body LoginRequestModel loginRequestModel);

    @GET("list/{id}/{status}")
    Observable<Response<ActiveModel>> requestList(@Path("id") int id,@Path("status") String status,@Query("page") int page);

    @GET("statuschange/{id}/{userid}/{status}")
    Observable<Response<StatusChange>> changeStatus(@Path("id") int id,@Path("userid") int userid ,@Path("status") String status);

    @POST("update/{requestID}")
    Observable<Response<StatusChange>> updateServiceRequest(@Path("requestID") int requestID,@Body ServiceRequest serviceRequest);

    @GET("notificationservice/{requestid}")
    Observable<Response<NotificationServiceModel>> notificationServiceRequest(@Path("requestid") String requestid);

    @GET("balanceservice/{userid}")
    Observable<Response<BalanceResponse>> balance(@Path("userid") int userid);

    @POST("share-request")
    Observable<Response<ShareResponse>> share(@Body ShareRequest shareRequest);

    @GET("view-summary/{service_id}")
    Observable<Response<Summary>> summary(@Path("service_id") int service_id);

}
