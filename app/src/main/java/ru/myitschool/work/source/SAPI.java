package ru.myitschool.work.source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import ru.myitschool.work.domain.IsExist;
import ru.myitschool.work.domain.User;

public interface SAPI {

    @GET("api/{<LOGIN>}/info")
    Call<User> getUser(@Path("<LOGIN>") String login);

    @GET("api/{<LOGIN>}/auth")
    Call<IsExist> isExist(@Path("<LOGIN>") String login);

    //@PATCH("api/{<LOGIN>}/open")
    //Call<T> openDoor(@Path("<Login>") String)
}
