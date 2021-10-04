package br.unicamp.apptriunfalevent.APIconfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {


    /******************* CRUD Usuarios ****************************/
    @GET("/usuario")
    Call<List<Usuario>> getUsuarios();

    @GET("api/usuario/{username}/")
    Call<Usuario> getUsuario(@Path("username") String username);

    @POST("/api/usuario/")
    Call<Usuario> postUsuario(@Body Usuario usuario);

    @PUT("/api/usuario/{username}")
    Call<Usuario> alterarUser(@Path("username") String username, @Body Usuario usuario);

/*
    @GET("/api/dog/get")
    Call<List<Dog>> getDog();

    @DELETE("/api/dog/delete/{id}")
    Call<Dog> excluirDog(@Path("id") String id);

    @POST("/api/dog/post")
    Call<Dog> incluirDog(@Body Dog dog);


*/


}