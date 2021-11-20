package br.unicamp.apptriunfalevent.Models;

public class Convite {

    private String data, mensagem, idEvento, nomeUsuario;

    public Convite(String nomeUsuario, String idEvento,  String mensagem,String data  ) {
        this.data = data;
        this.mensagem = mensagem;
        this.idEvento = idEvento;
        this.nomeUsuario = nomeUsuario;
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
