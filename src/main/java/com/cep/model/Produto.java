package com.cep.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


// <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
// #[regen=yes,id=DCE.69C253B2-B926-6D2B-A69A-539EBF880476]
// </editor-fold>
@Entity
@Table(name = "produto")
public class Produto {
    
    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.A3323A54-003A-2E86-549C-F17204102984]
    // </editor-fold> 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.2BE3EA0D-DC47-B02B-A36A-BDD1DCB9AA71]
    // </editor-fold>
    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.855EE6A7-6BE9-2343-ED9A-806AE891697B]
    // </editor-fold>
    @Column(name = "dataCadastro", length = 10, nullable = false)
    private LocalDate dataCadastro;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.8D58BFC7-B8AE-8272-9BCC-7097F7FAA77B]
    // </editor-fold>
    @Column(name = "quantidadeEstoque", length = 255, nullable = false)
    private int quantidadeEstoque;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.44A9F14A-317D-6B73-35DD-5013749674E5]
    // </editor-fold>
    @Column(name = "unidadeInventariada", length = 100, nullable = false)
    private String unidadeInventariada;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.4AB0290B-16B5-247E-7ADE-478076D8C871]
    // </editor-fold>
    @Column(name = "preco_custo", length = 100, nullable = false)
    private double preco_custo;
    
    @Column(name = "porcentagem", length = 100, nullable = false)
    private int porcentagem;
    
    @Column(name = "preco_venda", length = 100, nullable = false)
    private double preco_venda;
    
    @Column(name = "ativo", nullable = true)
    private Boolean ativo;

    // <editor-fold defaultstate="collapsed" desc=" UML Marker "> 
    // #[regen=yes,id=DCE.5874AE32-4B69-6561-C372-87B5832F226D]
    // </editor-fold> 
    public Produto () {
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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public String getUnidadeInventariada() {
        return unidadeInventariada;
    }

    public void setUnidadeInventariada(String unidadeInventariada) {
        this.unidadeInventariada = unidadeInventariada;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public double getPreco_custo() {
        return preco_custo;
    }

    public void setPreco_custo(double preco_custo) {
        this.preco_custo = preco_custo;
    }

    public int getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(int porcentagem) {
        this.porcentagem = porcentagem;
    }

    public double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    

    @Override
    public String toString(){
        return getDescricao();
    }

}

