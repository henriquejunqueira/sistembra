package com.cep.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cliente")
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @Column(name = "nascimento", length = 10, nullable = false)
    private LocalDate nascimento;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.F4FE2C1B-FCCD-ED02-0D39-A55DE9E416EB]
    // </editor-fold>
    @Column(name = "endereco", length = 200, nullable = false)
    private String endereco;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5BB1CF59-4A92-6E7E-AA6D-707C14E110BF]
    // </editor-fold>
    @Column(name = "numero", length = 6, nullable = false)
    private int numero;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.96BAD6D0-4E94-5EA5-F10E-13E4BA6352CB]
    // </editor-fold>
    @Column(name = "complemento", length = 10, nullable = true)
    private String complemento;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.09ECBA8D-E389-9E53-27AE-52428C6F3E97]
    // </editor-fold>
    @Column(name = "bairro", length = 100, nullable = false)
    private String bairro;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.CC7B8F18-D9B5-4CF1-3770-F8FFE5B3461B]
    // </editor-fold>
    @Column(name = "cidade", length = 100, nullable = false)
    private String cidade;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.56697BCD-9A40-FA4C-BFCB-C35C4B8CC1D7]
    // </editor-fold>
    @Column(name = "cep", length = 20, nullable = false)
    private String cep;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.387A77AF-06B5-6C2B-13D4-A6F74CFEB605]
    // </editor-fold>
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4D0F1B5C-9E03-925F-78EC-6EC659716156]
    // </editor-fold>
    @Column(name = "telefone", length = 20, nullable = true)
    private String telefone;
    
    @Column(name = "cpf", length = 15, nullable = true)
    private String cpf;
    
    @Column(name = "cnpj", length = 20, nullable = true)
    private String cnpj;
    
    @Column(name = "ativo", nullable = true)
    private Boolean ativo;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5AB8E452-138A-D395-0DA9-1E1638BCFA2B]
    // </editor-fold> 
    public Cliente () {
    }

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

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString(){
        return getDescricao();
    }
}

