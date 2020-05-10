package com.example.api_rest_call;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AutoService {

    /**
     * Metodo abstracto para utilizar HTTP.GET
     * @return
     */

    @GET("app/api/read")
    Call<List<Auto>> getAutos();


    @GET("app/api/read/{id}")
    Call<Auto> getAuto(@Path("id") String id);


    @POST("app/api/create")
    Call<Void> crearAuto(@Body Auto auto);

    @FormUrlEncoded
    @PUT("app/api/update/{id}")
    Call<Void> modificarAuto(@Path("id") String id,
                             @Field("marca") String marca,
                             @Field("modelo") String modelo);

    @DELETE("app/api/delete/{id}")
    Call<Void> eliminarAuto(@Path("id") String id);



}
