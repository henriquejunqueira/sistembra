package com.cep.controller;

import com.cep.dao.PagamentoDao;
import com.cep.model.Pagamento;
import com.cep.util.Alerta;
import com.cep.util.ValidaCampos;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PagamentoController implements Initializable, ICadastro {

    @FXML
    private TextField tfId;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnNovo;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField tfFormaPagamento;
    @FXML
    private TableView<Pagamento> tabelaPagamento;
    @FXML
    private Button btnSair;
    @FXML
    private CheckBox ckPagamentoAtivo;
    
    Long id;
    private PagamentoDao dao = new PagamentoDao();
    private ObservableList<Pagamento> observableList = FXCollections.observableArrayList();
    private List<Pagamento> listaPagamentos;
    private Pagamento pagamentoSelecionado = new Pagamento();
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
        ckPagamentoAtivo.setSelected(true);
    }    

    @FXML
    private void cadastrarRegistro(ActionEvent event) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Pagamento pagamento = new Pagamento();
        if(ValidaCampos.checarCampoVazio(tfFormaPagamento)){
        
            if(pagamentoSelecionado.getId() != null){
                pagamento.setId(pagamentoSelecionado.getId());
            }
            pagamento.setTipo(tfFormaPagamento.getText());
            pagamento.setAtivo(ckPagamentoAtivo.isSelected());

            dao.salvar(pagamento);

            limparCamposFormulario();
            atualizarTabela();
        }
    }

    @FXML
    private void limparCamposFormulario(ActionEvent event) {
        limparCamposFormulario();
    }
    
    @FXML
    private void excluirRegistro(ActionEvent event) {
        if(alerta.msgConfirmaExclusao(tfFormaPagamento.getText())){
            pagamentoSelecionado.setAtivo(false);
            dao.excluir(pagamentoSelecionado);

            limparCamposFormulario();
            atualizarTabela();
        }
    }

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }
    
    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void clicarTabela(MouseEvent event) {
        setCamposFormulario();
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<Pagamento, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Pagamento, String> colunaTipo = new TableColumn<>("TIPO");
        
        tabelaPagamento.getColumns().addAll(colunaId, colunaTipo);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        
        tabelaPagamento.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        listaPagamentos = dao.consultar(tfPesquisa.getText());
        
        for(Pagamento p : listaPagamentos){
            if(p.getAtivo() == true){
                observableList.add(p);
            }
        }
        
        tabelaPagamento.getItems().setAll(observableList);
        tabelaPagamento.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        pagamentoSelecionado = tabelaPagamento.getItems().get(tabelaPagamento.getSelectionModel().getSelectedIndex());
        tfId.setText(Long.toString(pagamentoSelecionado.getId()));
        tfFormaPagamento.setText(pagamentoSelecionado.getTipo());
        ckPagamentoAtivo.setSelected(pagamentoSelecionado.getAtivo());
    }

    @Override
    public void limparCamposFormulario() {
        pagamentoSelecionado.setId(null);
        tfId.clear();
        tfFormaPagamento.clear();
        tfFormaPagamento.requestFocus();
        ckPagamentoAtivo.setSelected(true);
    }

    @Override
    public void adicionarTooltip(){
        Tooltip ttId = new Tooltip("Campo Id não pode ser preenchido!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttFormaPagamento = new Tooltip("Digite a forma de pagamento. Campo obrigatório!");
        ttFormaPagamento.setFont(new Font("Arial", 14));
        tfFormaPagamento.setTooltip(ttFormaPagamento);
        
        Tooltip ttAtivo = new Tooltip("Necessário marcar para ativar a forma de pagamento!");
        ttAtivo.setFont(new Font("Arial", 14));
        ckPagamentoAtivo.setTooltip(ttAtivo);
        
        Tooltip ttPesquisar = new Tooltip("Digite a forma de pagamento para pesquisar!");
        ttPesquisar.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisar);
    }

}
