package br.unicamp.apptriunfalevent.APIconfig;

import java.util.List;

import br.unicamp.apptriunfalevent.Models.Convidado;
import br.unicamp.apptriunfalevent.Models.Convite;
import br.unicamp.apptriunfalevent.Models.Dica;
import br.unicamp.apptriunfalevent.Models.Evento;
import br.unicamp.apptriunfalevent.Models.Feriado;
import br.unicamp.apptriunfalevent.Models.Lembrete;
import br.unicamp.apptriunfalevent.Models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Service {

    /******************* CRUD USUARIOS *******************/
    @GET("/usuario")
    Call<List<Usuario>> getUsuarios();

    @GET("api/usuario/{username}")
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

    @GET("api/evento/{id}")
    Call<Evento> getEvento(@Path("id") String id);

    @POST("/api/evento")
    Call<Evento> postEvento(@Body Evento event);

    @PUT("/api/evento/{id}")
    Call<Evento> putEvento(@Path("id") String id, @Body Evento event);

    @DELETE("/api/evento/{id}")
    Call<Evento> deleteEvento(@Path("id") String id);
    
    @GET("/api/usuario/{username}/eventos")
    Call<List<Evento>> getUserEvents(@Path("username") String username);


    /******************* CRUD CONVIDADOS *******************/
    @GET("/api/convidado")
    Call<List<Convidado>> getConvidados();

    @GET("/api/convidado/{nomeUsuario}/{idEvento}")
    Call<Convidado> getConvidado(@Path("nomeUsuario") String nomeUsuario, @Path("idEvento") String idEvento);

    @POST("/api/convidado/")
    Call<Convidado> postConvidado(@Body Convidado convidado);

    @PUT("/api/convidado/{nomeUsuario}/{idEvento}")
    Call<Convidado> putConvidado(@Path("nomeUsuario") String nomeUsuario, @Path("idEvento") String idEvento, @Body Convidado convidado);

    @DELETE("/api/convidado/{nomeUsuario}/{idEvento}")
    Call<Convidado> deleteConvidado(@Path("nomeUsuario") String nomeUsuario, @Path("idEvento") String idEvento);


    /******************* CRUD LEMBRETES *******************/
    @GET("/lembrete")
    Call<List<Lembrete>> getLembretes();

    @GET("/api/convidado/{usuario}/{data}")
    Call<Lembrete> getLembrete(@Path("usuario") String usuario, @Path("data") String data);

    @POST("/api/convidado")
    Call<Lembrete> postLembrete(@Body Lembrete lembrete);

    @PUT("/api/convidado/{usuario}/{data}")
    Call<Lembrete> putLembrete(@Path("usuario") String usuario, @Path("data") String data, @Body Lembrete lembrete);

    @DELETE("/api/convidado/{usuario}/{data}")
    Call<Lembrete> deleteLembrete(@Path("usuario") String usuario, @Path("data") String data);


    /******************* CRUD DICAS *******************/
    @GET("/dica")
    Call<List<Dica>> getDicas();

    @GET("/api/dica/{tipoEvento}/{conteudo}")
    Call<Dica> getDica(@Path("tipoEvento") String tipoEvento, @Path("conteudo") String conteudo);

    @POST("/api/dica/")
    Call<Dica> postDica(@Body Dica dica);

    @PUT("/api/dica/{tipoEvento}/{conteudo}")
    Call<Dica> putDica(@Path("tipoEvento") String tipoEvento, @Path("conteudo") String conteudo, @Body Dica dica);

    @DELETE("/api/dica/{tipoEvento}/{conteudo}")
    Call<Dica> deleteDica(@Path("tipoEvento") String tipoEvento, @Path("conteudo") String conteudo);


    /******************* CRUD FERIADOS *******************/
    @GET("/feriado")
    Call<List<Feriado>> getFeriados();

    @GET("/api/feriado/{nome}")
    Call<Feriado> getFeriado(@Path("nome") String nome);

    @POST("/api/feriado/")
    Call<Feriado> postFeriado(@Body Feriado feriado);

    @PUT("/api/feriado/{nome}")
    Call<Feriado> putFeriado(@Path("nome") String nome, @Body Feriado feriado);

    @DELETE("/api/feriado/{nome}")
    Call<Feriado> deleteFeriado(@Path("nome") String nome);
    

    /******************* CRUD CONVITES *******************/
    @GET("api/convite")
    Call<List<Convite>> getConvites();

    @GET("/api/convite/{nomeUsuario}/{idEvento}")
    Call<Convite> getConvite(@Path("nomeUsuario") String nomeUsuario, @Path("idEvento") String idEvento);

    @POST("/api/convite/")
    Call<Convite> postConvite(@Body Convite convite);

    @PUT("/api/convite/{nomeUsuario}/{idEvento}")
    Call<Convite> putConvite(@Path("nomeUsuario") String nomeUsuario, @Path("idEvento") String idEvento, @Body Convite convite);

    @DELETE("/api/convite/{nomeUsuario}/{idEvento}")
    Call<Convite> deleteConvites(@Path("nomeUsuario") String nomeUsuario, @Path("idEvento") String idEvento);
}
