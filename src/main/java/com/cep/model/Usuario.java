package com.cep.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;
    @Column(name = "descricao", length = 100, nullable = false)
    protected String descricao;
    @Column(name = "permissoes", length = 20, nullable = false)
    protected String permissoes;
    @Column(name = "email", length = 100, nullable = false)
    protected String email;
    @Column(name = "login", length = 100, nullable = false)
    protected String login;
    @Column(name = "senha", length = 8, nullable = false)
    protected String senha;
    @Column(name = "ativo", nullable = true)
    private Boolean ativo;
    @Column(name = "dicasenha", length = 100, nullable = false)
    protected String dicasenha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(String permissoes) {
        this.permissoes = permissoes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getDicasenha() {
        return dicasenha;
    }

    public void setDicasenha(String dicasenha) {
        this.dicasenha = dicasenha;
    }

    @Override
    public String toString(){
        return getDescricao();
    }
    
}
