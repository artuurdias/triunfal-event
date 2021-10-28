package br.unicamp.apptriunfalevent.Models;

public class Dica {
    private int id;
    private String tipoEvento, conteudo;

    public Dica(int id, String tipoEvento, String conteudo) {
        this.id = id;
        this.tipoEvento = tipoEvento;
        this.conteudo = conteudo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
