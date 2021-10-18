package br.unicamp.apptriunfalevent.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Usuario {
    private String username, nome, nascimento, email, senha;

    public Usuario(String username, String nome, String nascimento, String email, String senha) {
        this.username = username;
        this.nome = nome;
        this.nascimento = nascimento;
        this.email = email;
        this.senha = senha;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", nascimento='" + nascimento + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
