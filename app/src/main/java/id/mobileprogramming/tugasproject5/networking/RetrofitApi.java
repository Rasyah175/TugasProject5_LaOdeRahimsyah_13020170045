package id.mobileprogramming.tugasproject5.networking;

import id.mobileprogramming.tugasproject5.models.Account;
import id.mobileprogramming.tugasproject5.models.LoginStatus;
import id.mobileprogramming.tugasproject5.models.Pariwisata;
import id.mobileprogramming.tugasproject5.models.PariwisataStatus;
import id.mobileprogramming.tugasproject5.models.Status;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitApi {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginStatus> getLogin(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<Status> getRegister(@Field("username") String username, @Field("password") String password, @Field("nama_lengkap") String namalengkap, @Field("alamat") String alamat, @Field("kontak") String kontak);

    @FormUrlEncoded
    @POST("update.php")
    Call<Status> getUpdate(@Field("username") String username, @Field("password") String password, @Field("nama_lengkap") String namalengkap, @Field("alamat") String alamat, @Field("kontak") String kontak);

    @GET("pariwisata.php")
    Call<PariwisataStatus> getPariwisata();
}
