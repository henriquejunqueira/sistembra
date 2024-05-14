package com.cep.controller;

import com.cep.dao.ClienteDao;
import com.cep.model.Cliente;
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

public class AtivarClienteController implements Initializable, ICadastro {

    @FXML
    private TableView<Cliente> tabelaAtivarCliente;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button tfDesbloquear;
    @FXML
    private Button btnSair;

    Long id;
    private ClienteDao dao = new ClienteDao();
    private ObservableList<Cliente> observableList = FXCollections.observableArrayList();
    private List<Cliente> listaClientes;
    private Cliente clienteSelecionado = new Cliente();
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
    }    

    @FXML
    private void clicarTabela(MouseEvent event) {
        setCamposFormulario();
    }

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }

    @FXML
    private void excluirRegistro(ActionEvent event) {
        clienteSelecionado.setAtivo(true);
        dao.excluir(clienteSelecionado);
        //alerta.msgInformacao("Registro desbloqueado com sucesso");
        limparCamposFormulario();
        atualizarTabela();
    }

    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<Cliente, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Cliente, String> colunaDescricao = new TableColumn<>("DESCRIÇÃO");
        TableColumn<Cliente, LocalDate> colunaNascimento = new TableColumn<>("NASCIMENTO");
        TableColumn<Cliente, String> colunaEndereco = new TableColumn<>("ENDERECO");
        TableColumn<Cliente, Integer> colunaNumero = new TableColumn<>("NUMERO");
        TableColumn<Cliente, String> colunaComplemento = new TableColumn<>("COMPLEMENTO");
        TableColumn<Cliente, String> colunaBairro = new TableColumn<>("BAIRRO");
        TableColumn<Cliente, String> colunaCidade = new TableColumn<>("CIDADE");
        TableColumn<Cliente, Long> colunaCep = new TableColumn<>("CEP");
        TableColumn<Cliente, String> colunaUf = new TableColumn<>("UF");
        TableColumn<Cliente, Long> colunaTelefone = new TableColumn<>("TELEFONE");
        
        tabelaAtivarCliente.getColumns().addAll(colunaId, colunaDescricao, colunaNascimento, colunaEndereco, colunaNumero, colunaComplemento, colunaBairro, colunaCidade, colunaCep, colunaUf, colunaTelefone);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colunaNascimento.setCellValueFactory(new PropertyValueFactory("nascimento"));
        colunaEndereco.setCellValueFactory(new PropertyValueFactory("endereco"));
        colunaNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        colunaComplemento.setCellValueFactory(new PropertyValueFactory("complemento"));
        colunaBairro.setCellValueFactory(new PropertyValueFactory("bairro"));
        colunaCidade.setCellValueFactory(new PropertyValueFactory("cidade"));
        colunaCep.setCellValueFactory(new PropertyValueFactory("cep"));
        colunaUf.setCellValueFactory(new PropertyValueFactory("uf"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        
        tabelaAtivarCliente.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        // Definindo o formato da data para exibição na tabela
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        // Converte a data da tabela para o formato especificado
        colunaNascimento.setCellFactory(tc -> new TableCell<Cliente, LocalDate>(){
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
        listaClientes = dao.consultar(tfPesquisa.getText());
        
        for(Cliente c : listaClientes){
            if(c.getAtivo() == false){
                observableList.add(c);
            }
        }
        
        tabelaAtivarCliente.getItems().setAll(observableList);
        tabelaAtivarCliente.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        clienteSelecionado = tabelaAtivarCliente.getItems().get(tabelaAtivarCliente.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void limparCamposFormulario() {
        clienteSelecionado.setId(null);
    }

    @Override
    public void adicionarTooltip() {
        Tooltip ttPesquisar = new Tooltip("Digite o nome do cliente para pesquisar!");
        ttPesquisar.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisar);
    }
}
