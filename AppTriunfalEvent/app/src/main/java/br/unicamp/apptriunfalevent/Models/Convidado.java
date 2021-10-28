package br.unicamp.apptriunfalevent.Models;

public class Convidado {
    private String  idEvento, nomeUsuario;

    public Convidado(String nomeUsuario, String idEvento) {
        this.idEvento = idEvento;
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(String idEvento) {
        this.idEvento = idEvento;
    }
}
