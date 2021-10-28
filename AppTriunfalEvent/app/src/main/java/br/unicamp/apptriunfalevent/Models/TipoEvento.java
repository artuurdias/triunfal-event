package br.unicamp.apptriunfalevent.Models;

public class TipoEvento {

    String nome, definicao, exemplos;

    public TipoEvento(String nome, String definicao, String exemplos) {
        this.nome = nome;
        this.definicao = definicao;
        this.exemplos = exemplos;
    }

    @Override
    public String toString() {
        return "TipoEvento{" +
                "nome='" + nome + '\'' +
                ", definicao='" + definicao + '\'' +
                ", exemplos='" + exemplos + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDefinicao() {
        return definicao;
    }

    public void setDefinicao(String definicao) {
        this.definicao = definicao;
    }

    public String getExemplos() {
        return exemplos;
    }

    public void setExemplos(String exemplos) {
        this.exemplos = exemplos;
    }
}
