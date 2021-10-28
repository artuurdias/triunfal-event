package br.unicamp.apptriunfalevent.Models;

public class Convite {

    private int id;
    private String data, mensagem, idEvento, nomeUsuario;

    public Convite(int id, String data, String mensagem, String idEvento, String nomeUsuario) {
        this.id = id;
        this.data = data;
        this.mensagem = mensagem;
        this.idEvento = idEvento;
        this.nomeUsuario = nomeUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }
}
