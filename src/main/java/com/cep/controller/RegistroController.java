package com.cep.controller;

import com.cep.model.Cliente;
import com.cep.model.ItemPedido;
import com.cep.model.Pagamento;
import com.cep.model.Usuario;
import com.cep.model.Pedido;
import com.cep.util.Alerta;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegistroController implements Initializable, ICadastro {

    @FXML
    private TextField tfPesquisa;
    @FXML
    private TableView<Pedido> tabelaVendas;
    @FXML
    private Button btnImprimir;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnSair;
    @FXML
    private Label lblTotal;
    @FXML
    private Label lblId;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblCliente;
    @FXML
    private Label lblDataVenda;
    @FXML
    private Label lblPagamento;
    @FXML
    private Label lblParcelas;
    @FXML
    private Label lblDesconto;
    
    Long id;
    private PedidoDao dao = new PedidoDao();
    private ObservableList<Pedido> observableList = FXCollections.observableArrayList();
    private List<Pedido> listaVendas;
    private Pedido pedidoSelecionado = new Pedido();
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
        lblId.setText("");
        lblUsuario.setText("");
        lblCliente.setText("");
        lblDataVenda.setText("");
        lblPagamento.setText("");
        lblParcelas.setText("");
        lblDesconto.setText("");
        lblTotal.setText("R$0,00");
    }    


    @FXML
    private void clicarTabela(MouseEvent event) {
        setCamposFormulario();
    }

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<Pedido, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Pedido, Usuario> colunaUsuario = new TableColumn<>("USUARIO");
        TableColumn<Pedido, Cliente> colunaCliente = new TableColumn<>("CLIENTE");
        TableColumn<Pedido, LocalDate> colunaData = new TableColumn<>("DATA VENDA");
        TableColumn<Pedido, Pagamento> colunaPagamento = new TableColumn<>("PAGAMENTO");
        TableColumn<Pedido, Integer> colunaParcelamento = new TableColumn<>("PARCELAMENTO");
        TableColumn<Pedido, Integer> colunaDesconto = new TableColumn<>("DESCONTO");
        TableColumn<Pedido, String> colunaDescricao = new TableColumn<>("DESCRICAO");
        TableColumn<Pedido, Double> colunaTotal = new TableColumn<>("TOTAL");
        
        tabelaVendas.getColumns().addAll(colunaId, colunaUsuario, colunaCliente, colunaData, colunaPagamento, colunaParcelamento, colunaDesconto, colunaDescricao, colunaTotal);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaUsuario.setCellValueFactory(new PropertyValueFactory("usuario"));
        colunaCliente.setCellValueFactory(new PropertyValueFactory("cliente"));
        colunaData.setCellValueFactory(new PropertyValueFactory("dataPedido"));
        colunaPagamento.setCellValueFactory(new PropertyValueFactory("pagamento"));
        colunaParcelamento.setCellValueFactory(new PropertyValueFactory("parcelamento"));
        colunaDesconto.setCellValueFactory(new PropertyValueFactory("desconto"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colunaTotal.setCellValueFactory(new PropertyValueFactory("total"));
        
        tabelaVendas.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Converte a data da tabela para o formato especificado
        colunaData.setCellFactory(tc -> new TableCell<Pedido, LocalDate>(){
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
        listaVendas = dao.consultar(tfPesquisa.getText());
        
        for(Pedido p : listaVendas){
            if(p.getDataPedido().equals(LocalDate.now())){
                observableList.add(p);
            }
        }
        
        tabelaVendas.getItems().setAll(observableList);
        tabelaVendas.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        pedidoSelecionado = tabelaVendas.getItems().get(tabelaVendas.getSelectionModel().getSelectedIndex());
        lblId.setText(Long.toString(pedidoSelecionado.getId()));
        lblUsuario.setText(pedidoSelecionado.getUsuario().toString());
        lblCliente.setText(pedidoSelecionado.getCliente().toString());
        //lblDataVenda.setText(pedidoSelecionado.getDataPedido().toString());
        lblPagamento.setText(pedidoSelecionado.getPagamento().toString());
        lblParcelas.setText(Integer.toString(pedidoSelecionado.getParcelamento()));
        lblDesconto.setText(Integer.toString(pedidoSelecionado.getDesconto()));
        lblTotal.setText(String.format("R$%.2f", pedidoSelecionado.getTotal()));
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Converte a data da tabela para o formato especificado
        lblDataVenda.setText(formato.format(pedidoSelecionado.getDataPedido()));
    }

    @Override
    public void limparCamposFormulario() {
        lblId.setText("");
        lblUsuario.setText("");
        lblCliente.setText("");
        lblDataVenda.setText("");
        lblPagamento.setText("");
        lblParcelas.setText("");
        lblDesconto.setText("");
        lblTotal.setText("R$0,00");
    }

    @FXML
    private void imprimirRelatorio(ActionEvent event){
        /*URL url = getClass().getResource("/javafxmvc/relatorios/JAVAFXMVCRelatorioProdutos.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);*/
    }

    @Override
    public void adicionarTooltip() {
        Tooltip ttPesquisa = new Tooltip("Descrição do tipo de produto vendido. Ex: HD vendido para João da Silva");
        ttPesquisa.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisa);
    }

    @FXML
    private void cancelarVenda(ActionEvent event) {
        if(alerta.msgConfirmaExclusao(tfPesquisa.getText())){
            PedidoDao dao = new PedidoDao();
            dao.excluir(pedidoSelecionado);

            limparCamposFormulario();
            atualizarTabela();
        }
    }

    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    
}
