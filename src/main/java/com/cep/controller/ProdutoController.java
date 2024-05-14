package com.cep.controller;

import com.cep.dao.ProdutoDao;
import com.cep.model.Produto;
import com.cep.util.Alerta;
import com.cep.util.ValidaCampos;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProdutoController implements Initializable, ICadastro {

    @FXML
    private TextField tfId;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnNovo;
    @FXML
    private TextField tfDescricao;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button btnExcluir;
    @FXML
    private DatePicker dpDataCadastro;
    @FXML
    private TextField tfQuantidadeEstoque;
    @FXML
    private TextField tfUnidadeInventariada;
    @FXML
    private TableView<Produto> tabelaProduto;
    @FXML
    private Button btnSair;
    @FXML
    private CheckBox ckProdutoAtivo;
    @FXML
    private TextField tfPrecoCusto;
    @FXML
    private TextField tfPorcentagem;
    @FXML
    private TextField tfPrecoVenda;
    
    Long id;
    private ProdutoDao dao = new ProdutoDao();
    private ObservableList<Produto> observableList = FXCollections.observableArrayList();
    private List<Produto> listaProdutos;
    private Produto produtoSelecionado = new Produto();
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        dpDataCadastro.setValue(LocalDate.now());
        ckProdutoAtivo.setSelected(true);
        adicionarTooltip();
    }    

    @FXML
    private void cadastrar(ActionEvent event) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
        Produto produto = new Produto();
        if(ValidaCampos.checarCampoVazio(tfDescricao, tfQuantidadeEstoque, tfUnidadeInventariada, tfPrecoCusto, tfPorcentagem, tfPrecoVenda)){
            if(produtoSelecionado.getId() != null){
                produto.setId(produtoSelecionado.getId());
            }
            produto.setDescricao(tfDescricao.getText());
            produto.setDataCadastro(dpDataCadastro.getValue());
            produto.setQuantidadeEstoque(Integer.parseInt(tfQuantidadeEstoque.getText()));
            produto.setUnidadeInventariada(tfUnidadeInventariada.getText());
            produto.setPreco_custo(Double.parseDouble(tfPrecoCusto.getText()));
            produto.setPorcentagem(Integer.parseInt(tfPorcentagem.getText()));
            produto.setPreco_venda(Double.parseDouble(tfPrecoVenda.getText()));
            produto.setAtivo(ckProdutoAtivo.isSelected());

            dao.salvar(produto);

            limparCamposFormulario();
            atualizarTabela();
        }
    }

    @FXML
    private void limparCamposFormulario(ActionEvent event) {
        limparCamposFormulario();
    }

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }

    @FXML
    private void excluir(ActionEvent event) {
        if(alerta.msgConfirmaExclusao(tfDescricao.getText())){
            produtoSelecionado.setAtivo(false);
            dao.excluir(produtoSelecionado);

            limparCamposFormulario();
            atualizarTabela();
        }

    }

    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<Produto, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Produto, String> colunaDescricao = new TableColumn<>("DESCRIÇÃO");
        TableColumn<Produto, LocalDate> colunaData = new TableColumn<>("DATA");
        TableColumn<Produto, Integer> colunaEstoque = new TableColumn<>("ESTOQUE");
        TableColumn<Produto, String> colunaUnidadeInventariada = new TableColumn<>("UNIDADE INVENTARIADA");
        TableColumn<Produto, Double> colunaPrecoCusto = new TableColumn<>("PREÇO CUSTO");
        TableColumn<Produto, Integer> colunaPorcentagem = new TableColumn<>("PORCENTAGEM");
        TableColumn<Produto, Double> colunaPrecoVenda = new TableColumn<>("PREÇO VENDA");
        
        tabelaProduto.getColumns().addAll(colunaId, colunaDescricao, colunaData, colunaEstoque, colunaUnidadeInventariada, colunaPrecoCusto, colunaPorcentagem, colunaPrecoVenda);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colunaData.setCellValueFactory(new PropertyValueFactory("dataCadastro"));
        colunaEstoque.setCellValueFactory(new PropertyValueFactory("quantidadeEstoque"));
        colunaUnidadeInventariada.setCellValueFactory(new PropertyValueFactory("unidadeInventariada"));
        colunaPrecoCusto.setCellValueFactory(new PropertyValueFactory("preco_custo"));
        colunaPorcentagem.setCellValueFactory(new PropertyValueFactory("porcentagem"));
        colunaPrecoVenda.setCellValueFactory(new PropertyValueFactory("preco_venda"));
        
        tabelaProduto.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Converte a data da tabela para o formato especificado
        colunaData.setCellFactory(tc -> new TableCell<Produto, LocalDate>(){
            @Override
            protected void updateItem(LocalDate data, boolean vazio) {
                super.updateItem(data, vazio);
                if(vazio){
                    setText(null);
                }else{
                    setText(formato.format(data));
                }
            }
        });
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        listaProdutos = dao.consultar(tfPesquisa.getText());
        
        for(Produto p : listaProdutos){
            if(p.getAtivo() == true){
                observableList.add(p);
            }
        }
        
        tabelaProduto.getItems().setAll(observableList);
        tabelaProduto.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        produtoSelecionado = tabelaProduto.getItems().get(tabelaProduto.getSelectionModel().getSelectedIndex());
        tfId.setText(Long.toString(produtoSelecionado.getId()));
        tfDescricao.setText(produtoSelecionado.getDescricao());
        dpDataCadastro.setValue(produtoSelecionado.getDataCadastro());
        tfQuantidadeEstoque.setText(Integer.toString(produtoSelecionado.getQuantidadeEstoque()));
        tfUnidadeInventariada.setText(produtoSelecionado.getUnidadeInventariada());
        tfPrecoCusto.setText(Double.toString(produtoSelecionado.getPreco_custo()));
        tfPorcentagem.setText(Integer.toString(produtoSelecionado.getPorcentagem()));
        tfPrecoVenda.setText(Double.toString(produtoSelecionado.getPreco_venda()));
        ckProdutoAtivo.setSelected(produtoSelecionado.getAtivo());
        
    }

    @Override
    public void limparCamposFormulario() {
        produtoSelecionado.setId(null);
        tfId.clear();
        tfDescricao.clear();
        tfDescricao.requestFocus();
        dpDataCadastro.setValue(LocalDate.now());
        tfQuantidadeEstoque.clear();
        tfUnidadeInventariada.clear();
        tfPrecoCusto.clear();
        tfPorcentagem.clear();
        tfPrecoVenda.clear();
        ckProdutoAtivo.setSelected(true);
    }

    @FXML
    private void clicarTabela(MouseEvent event) {
        setCamposFormulario();
    }

    @Override
    public void adicionarTooltip(){
        Tooltip ttId = new Tooltip("Campo Id não pode ser preenchido!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttDescricao = new Tooltip("Digite o nome do produto. Campo obrigatório!");
        ttDescricao.setFont(new Font("Arial", 14));
        tfDescricao.setTooltip(ttDescricao);
        
        Tooltip ttData = new Tooltip("Data de cadastro do produto. Campo obrigatório!");
        ttData.setFont(new Font("Arial", 14));
        dpDataCadastro.setTooltip(ttData);
        
        Tooltip ttEstoque = new Tooltip("Quantidade colocada no estoque. Campo obrigatório!");
        ttEstoque.setFont(new Font("Arial", 14));
        tfQuantidadeEstoque.setTooltip(ttEstoque);
        
        Tooltip ttUnidade = new Tooltip("Digite o tipo de unidade. Campo obrigatório!");
        ttUnidade.setFont(new Font("Arial", 14));
        tfUnidadeInventariada.setTooltip(ttUnidade);
        
        Tooltip ttPrecoCusto = new Tooltip("Digite o preço de custo do produto. Campo obrigatório!");
        ttPrecoCusto.setFont(new Font("Arial", 14));
        tfPrecoCusto.setTooltip(ttPrecoCusto);
        
        Tooltip ttPrecoVenda = new Tooltip("Digite o preço de venda do produto. Campo obrigatório!");
        ttPrecoVenda.setFont(new Font("Arial", 14));
        tfPrecoVenda.setTooltip(ttPrecoVenda);
        
        Tooltip ttPorcentagem = new Tooltip("Digite a porcentagem de lucro. Campo obrigatório!");
        ttPorcentagem.setFont(new Font("Arial", 14));
        tfPorcentagem.setTooltip(ttPorcentagem);
        
        Tooltip ttAtivo = new Tooltip("Necessário marcar para ativar o produto!");
        ttAtivo.setFont(new Font("Arial", 14));
        ckProdutoAtivo.setTooltip(ttAtivo);
        
        Tooltip ttPesquisar = new Tooltip("Digite o nome do produto para pesquisar!");
        ttPesquisar.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisar);
    }
    
}
