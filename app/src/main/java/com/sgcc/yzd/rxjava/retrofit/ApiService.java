package com.sgcc.yzd.rxjava.retrofit;

import com.sgcc.yzd.rxjava.entity.ResponseData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @POST("token")
    @FormUrlEncoded
    Observable<ResponseData> getAccessToken(@Field("grant_type") String grantType , @Field("client_id") String apiKey, @Field("client_secret") String secretKey);
}
