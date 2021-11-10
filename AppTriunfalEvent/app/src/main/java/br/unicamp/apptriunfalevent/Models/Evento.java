package br.unicamp.apptriunfalevent.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Evento implements Serializable {
    private  @SerializedName("id") String id;
    private  @SerializedName("nome") String nome;
    private  @SerializedName("tipo") String tipo;
    private  @SerializedName("data") String data;
    private  @SerializedName("descricao") String descricao;
    private  @SerializedName("endereco") String endereco;
    private  @SerializedName("organizador") String organizador;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Evento(String id, String nome, String tipo, String data, String descricao , String endereco, String organizador) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
        this.endereco = endereco;
        this.descricao = descricao;
        this.organizador = organizador;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", data='" + data + '\'' +
                ", descricao='" + descricao + '\'' +
                ", endereco='" + endereco + '\'' +
                ", organizador='" + organizador + '\'' +
                '}';
    }

    public Evento (Evento t) throws Exception // construtor de cópia
    {
        // intanciar this.texto um vetor com o mesmo tamanho de t.texto
        // e copilar o conteúdo de t.texto para this.texto

        if (t == null)
            throw new Exception ("Modelo ausente");

        this.id = t.id;
        this.nome = t.nome;
        this.tipo = t.tipo;
        this.data = t.data;
        this.endereco = t.endereco;
        this.descricao = t.descricao;
        this.organizador = t.organizador;
    }

    @Override
    public Object clone ()
    {
        // retornar uma copia de this
        Evento ret = null;

        try
        {
            ret = new Evento (this);
        }
        catch(Exception erro) {
        }

        return ret;
    }


}