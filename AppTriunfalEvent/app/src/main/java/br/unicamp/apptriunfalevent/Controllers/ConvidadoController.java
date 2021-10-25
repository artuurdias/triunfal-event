package br.unicamp.apptriunfalevent.Controllers;

public class ConvidadoController {
    private String  idEvento, nomeUsuario;
    private int idConvidado;

    public ConvidadoController(int idConvidado, String nomeUsuario, String idEvento) {
        this.idEvento = idEvento;
        this.idConvidado = idConvidado;
        this.nomeUsuario = nomeUsuario;
    }

    public int getIdConvidado() {
        return idConvidado;
    }

    public void setIdConvidado(int idConvidado) {
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
