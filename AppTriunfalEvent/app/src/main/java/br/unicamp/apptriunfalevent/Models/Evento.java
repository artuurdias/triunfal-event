package br.unicamp.apptriunfalevent.Models;

public class Evento {
    public String id, nome, tipo, data, local, organizador;

    public Evento(String id, String nome, String tipo, String data, String local, String organizador) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
        this.local = local;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getOrganizador() {
        return organizador;
    }

    public void setOrganizador(String organizador) {
        this.organizador = organizador;
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
        this.local = t.local;
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