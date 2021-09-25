package br.unicamp.triunfalevent;

public class Evento {
    public String id, nome, tipo, data, local;

    public Evento(String id, String nome, String tipo, String data, String local) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.data = data;
        this.local = local;
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

}