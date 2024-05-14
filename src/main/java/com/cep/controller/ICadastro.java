package com.cep.controller;

public interface ICadastro {
    public abstract void criarColunasTabela();
    public abstract void atualizarTabela();
    public abstract void setCamposFormulario();
    public abstract void limparCamposFormulario();
    public abstract void adicionarTooltip();
}
