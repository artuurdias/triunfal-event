package br.unicamp.apptriunfalevent.APIconfig;

import java.util.List;

import br.unicamp.apptriunfalevent.Models.*;
import retrofit2.Call;
import retrofit2.http.*;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {

    /******************* CRUD USUARIOS *******************/
    @GET("/api/usuario")
    Call<List<Usuario>> getUsuarios();

    @GET("/api/usuario/{username}")
    Call<Usuario> getUsuario(@Path("username") String username);

    @POST("/api/usuario/")
    Call<Usuario> postUsuario(@Body Usuario usuario);

    @PUT("/api/usuario/{username}")
    Call<Usuario> putUsuario(@Path("username") String username, @Body Usuario usuario);

    @DELETE("/api/usuario/{username}")
    Call<Usuario> deleteUsuario(@Path("username") String username);


    /******************* CRUD EVENTOS *******************/
    @GET("/api/evento")
    Call<List<Evento>> getEventos();

    @GET("/api/evento/user/{username}")
    Call<List<Evento>> getEventosPart(@Path("username") String username);

    @GET("/api/evento/org/{username}")
    Call<List<Evento>> getEventosOrg(@Path("username") String username);

    @GET("/api/evento/disp/{username}")
    Call<List<Evento>> getEventosDisp(@Path("username") String username);

    @GET("/api/evento/pass/{username}")
    Call<List<Evento>> getEventosPass(@Path("username") String username);

    @GET("/api/evento/{idEvento}")
    Call<Evento> getEvento(@Path("idEvento") String idEvento);

    @POST("/api/evento")
    Call<Evento> postEvento(@Body Evento event);

    @PUT("/api/evento/{id}")
    Call<Evento> putEvento(@Path("id") String id, @Body Evento event);

    @DELETE("/api/evento/{id}")
    Call<Evento> deleteEvento(@Path("id") String id);


    /******************* CRUD CONVIDADOS *******************/
    @GET("/api/convidado")
    Call<List<Convidado>> getConvidados();

    @GET("/api/convidado/list/{idEvento}")
    Call<List<Convidado>> getConvidadosEvento(@Path("idEvento") String idEvento);

    @POST("/api/convidado/")
    Call<Convidado> postConvidado(@Body Convidado convidado);

    @PUT("/api/convidado/{idConvidado}")
    Call<Convidado> putConvidado(@Path("idConvidado") String id, @Body Convidado convidado);

    @DELETE("/api/convidado/{nomeUsuario}/{idEvento}")
    Call<Convidado> deleteConvidado(@Path("nomeUsuario") String nomeUsuario, @Path("idEvento") String idEvento);


    /******************* CRUD LEMBRETES *******************/
    @GET("/lembrete")
    Call<List<Lembrete>> getLembretes();

    @GET("/api/convidado/{id}")
    Call<Lembrete> getLembrete(@Path("id") String id);

    @POST("/api/convidado")
    Call<Lembrete> postLembrete(@Body Lembrete lembrete);

    @PUT("/api/convidado/{id}")
    Call<Lembrete> putLembrete(@Path("id") String id, @Body Lembrete lembrete);

    @DELETE("/api/convidado/{id}")
    Call<Lembrete> deleteLembrete(@Path("id") String id);


    /******************* CRUD DICAS *******************/
    @GET("/api/dica")
    Call<List<Dica>> getDicas();

    @GET("/api/dica/{id}")
    Call<Dica> getDica(@Path("id") String id);

    @POST("/api/dica/")
    Call<Dica> postDica(@Body Dica dica);

    @PUT("/api/dica/{id}")
    Call<Dica> putDica(@Path("id") String id, @Body Dica dica);

    @DELETE("/api/dica/{id}")
    Call<Dica> deleteDica(@Path("id") String id);


    /******************* CRUD FERIADOS *******************/
    @GET("/feriado")
    Call<List<Feriado>> getFeriados();

    @GET("/api/feriado/{id}")
    Call<Feriado> getFeriado(@Path("id") String id);

    @POST("/api/feriado/")
    Call<Feriado> postFeriado(@Body Feriado feriado);

    @PUT("/api/feriado/{id}")
    Call<Feriado> putFeriado(@Path("id") String id, @Body Feriado feriado);

    @DELETE("/api/feriado/{id}")
    Call<Feriado> deleteFeriado(@Path("id") String id);

    /******************* CRUD CONVITES *******************/
    @GET("/api/convite")
    Call<List<Convite>> getConvites();

    @GET("/api/convite/user/{nomeUsuario}/")
    Call<List<Convite>> getConvitesUser(@Path("nomeUsuario") String nomeUsuario);



    @POST("/api/convite/")
    Call<Convite> postConvite(@Body Convite convite);

    @PUT("/api/convite/{id}")
    Call<Convite> putConvite(@Path("id") String id, @Body Convite convite);

    @DELETE("/api/convite/{username}/{idEvento}")
    Call<Convite> deleteConvite(@Path("username") String username, @Path("idEvento") String idEvento);


    /******************* CRUD TIPO DE EVENTO *******************/
    @GET("/api/tipoevento")
    Call<List<TipoEvento>> getTipoEventos();

    @POST("/api/tipoevento")
    Call<TipoEvento> postTipoEvento(@Body Evento event);

    @PUT("/api/tipoevento/{nome}")
    Call<TipoEvento> putTipoEvento(@Path("nome") String id, @Body Evento event);

    @DELETE("/api/tipoevento/{id}")
    Call<TipoEvento> deleteTipoEvento(@Path("nome") String id);


}
