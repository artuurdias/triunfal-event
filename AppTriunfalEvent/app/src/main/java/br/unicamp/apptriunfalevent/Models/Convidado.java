package br.unicamp.apptriunfalevent.Models;

public class Convidado {
    private String idConvidado, nomeUsuario, idEvento;

    public Convidado(String idConvidado, String nomeUsuario, String idEvento) {
        this.idConvidado = idConvidado;
        this.nomeUsuario = nomeUsuario;
        this.idEvento = idEvento;
    }

    public String getIdConvidado() {
        return idConvidado;
    }

    public void setIdConvidado(String idConvidado) {
        this.idConvidado = idConvidado;
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
