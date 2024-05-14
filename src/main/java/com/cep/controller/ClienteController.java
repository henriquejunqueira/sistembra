package com.cep.controller;

import com.cep.dao.ClienteDao;
import com.cep.model.Cliente;
import com.cep.util.Alerta;
import com.cep.util.MascaraCampo;
import com.cep.util.ValidaCampos;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClienteController implements Initializable, ICadastro{

    @FXML
    private Button btnSair;
    @FXML
    private TextField tfId;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnNovo;
    @FXML
    private TextField tfDescricao;
    @FXML
    private DatePicker dpNascimento;
    @FXML
    private TextField tfEndereco;
    @FXML
    private TextField tfComplemento;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfBairro;
    @FXML
    private TextField tfCidade;
    @FXML
    private TextField tfCep;
    @FXML
    private ComboBox<String> cbUf;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button btnExcluir;
    @FXML
    private TableView<Cliente> tabelaCliente;
    @FXML
    private CheckBox ckClienteAtivo;
    @FXML
    private Button btnDesbloquear;
    @FXML
    private TextField tfCpf;
    @FXML
    private TextField tfCnpj;
    
    Long id;
    private ClienteDao dao = new ClienteDao();
    private ObservableList<Cliente> observableList = FXCollections.observableArrayList();
    private List<Cliente> listaClientes;
    private Cliente clienteSelecionado = new Cliente();
    private ObservableList<String> listaUf = FXCollections.observableArrayList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbUf.setItems(listaUf);
        cbUf.setValue("MG");
        dpNascimento.setValue(LocalDate.now());
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
        MascaraCampo.mskCep(this.tfCep);
        MascaraCampo.mskTelefone(this.tfTelefone);
        MascaraCampo.mskCpf(this.tfCpf);
        MascaraCampo.mskCnpj(this.tfCnpj);
        ckClienteAtivo.setSelected(true);
    }    

    @FXML
    private void cadastrar(ActionEvent event) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Cliente cliente = new Cliente();
        if(ValidaCampos.checarCampoVazio(tfDescricao, dpNascimento, tfEndereco, tfNumero,tfBairro, tfCidade, tfCep, cbUf)){
            if(clienteSelecionado.getId() != null){
                cliente.setId(clienteSelecionado.getId());
            }
            cliente.setDescricao(tfDescricao.getText());
            cliente.setNascimento(dpNascimento.getValue());
            cliente.setEndereco(tfEndereco.getText());
            cliente.setNumero(Integer.parseInt(tfNumero.getText()));
            cliente.setComplemento(tfComplemento.getText());
            cliente.setBairro(tfBairro.getText());
            cliente.setCidade(tfCidade.getText());
            cliente.setCep(tfCep.getText());
            cliente.setUf(cbUf.getSelectionModel().getSelectedItem());
            cliente.setTelefone(tfTelefone.getText());
            cliente.setCpf(tfCpf.getText());
            cliente.setCnpj(tfCnpj.getText());
            cliente.setAtivo(ckClienteAtivo.isSelected());

            dao.salvar(cliente);

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
            clienteSelecionado.setAtivo(false);
            dao.excluir(clienteSelecionado);

            limparCamposFormulario();
            atualizarTabela();
        }
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
        TableColumn<Cliente, String> colunaCpf = new TableColumn<>("CPF");
        TableColumn<Cliente, String> colunaCnpj = new TableColumn<>("CNPJ");
        
        tabelaCliente.getColumns().addAll(colunaId, colunaDescricao, colunaNascimento, colunaEndereco, colunaNumero, colunaComplemento, colunaBairro, colunaCidade, colunaCep, colunaUf, colunaTelefone, colunaCpf, colunaCnpj);
        
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
        colunaCpf.setCellValueFactory(new PropertyValueFactory("cpf"));
        colunaCnpj.setCellValueFactory(new PropertyValueFactory("cnpj"));
        
        tabelaCliente.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
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
            if(c.getAtivo() == true){
                observableList.add(c);
            }
        }
        
        tabelaCliente.getItems().setAll(observableList);
        tabelaCliente.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        clienteSelecionado = tabelaCliente.getItems().get(tabelaCliente.getSelectionModel().getSelectedIndex());
        tfId.setText(Long.toString(clienteSelecionado.getId()));
        tfDescricao.setText(clienteSelecionado.getDescricao());
        dpNascimento.setValue(clienteSelecionado.getNascimento());
        tfEndereco.setText(clienteSelecionado.getEndereco());
        tfNumero.setText(Integer.toString(clienteSelecionado.getNumero()));
        tfComplemento.setText(clienteSelecionado.getComplemento());
        tfBairro.setText(clienteSelecionado.getBairro());
        tfCidade.setText(clienteSelecionado.getCidade());
        tfCep.setText(clienteSelecionado.getCep());
        cbUf.setValue(clienteSelecionado.getUf());
        tfTelefone.setText(clienteSelecionado.getTelefone());
        tfCpf.setText(clienteSelecionado.getCpf());
        tfCnpj.setText(clienteSelecionado.getCnpj());
        ckClienteAtivo.setSelected(clienteSelecionado.getAtivo());
        
    }

    @Override
    public void limparCamposFormulario() {
        clienteSelecionado.setId(null);
        tfId.clear();
        tfDescricao.clear();
        tfDescricao.requestFocus();
        dpNascimento.setValue(LocalDate.now());
        tfEndereco.clear();
        tfNumero.clear();
        tfComplemento.clear();
        tfBairro.clear();
        tfCidade.clear();
        tfCep.clear();
        cbUf.setValue("MG");
        tfTelefone.clear();
        tfCpf.clear();
        tfCnpj.clear();
        ckClienteAtivo.setSelected(true);
        
    }

    @Override
    public void adicionarTooltip() {
        Tooltip ttId = new Tooltip("Campo Id não pode ser preenchido!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttDescricao = new Tooltip("Digite o nome do cliente. Campo obrigatório!");
        ttDescricao.setFont(new Font("Arial", 14));
        tfDescricao.setTooltip(ttDescricao);
        
        Tooltip ttNascimento = new Tooltip("Data de nascimento do cliente...");
        ttNascimento.setFont(new Font("Arial", 14));
        dpNascimento.setTooltip(ttNascimento);
        
        Tooltip ttEndereco = new Tooltip("Digite o endereço do cliente. Campo obrigatório!");
        ttEndereco.setFont(new Font("Arial", 14));
        tfEndereco.setTooltip(ttEndereco);
        
        Tooltip ttNumero = new Tooltip("Número de residência do cliente. Campo obrigatório!");
        ttNumero.setFont(new Font("Arial", 14));
        tfNumero.setTooltip(ttNumero);
        
        Tooltip ttComplemento = new Tooltip("Complemento do cliente...");
        ttComplemento.setFont(new Font("Arial", 14));
        tfComplemento.setTooltip(ttComplemento);
        
        Tooltip ttBairro = new Tooltip("Digite o bairro do cliente. Campo obrigatório!");
        ttBairro.setFont(new Font("Arial", 14));
        tfBairro.setTooltip(ttBairro);
        
        Tooltip ttCidade = new Tooltip("Digite a cidade do cliente. Campo obrigatório!");
        ttCidade.setFont(new Font("Arial", 14));
        tfCidade.setTooltip(ttCidade);
        
        Tooltip ttCep = new Tooltip("Digite o cep. Campo obrigatório!");
        ttCep.setFont(new Font("Arial", 14));
        tfCep.setTooltip(ttCep);
        
        Tooltip ttUf = new Tooltip("Selecione o Estado. Campo obrigatório!");
        ttUf.setFont(new Font("Arial", 14));
        cbUf.setTooltip(ttUf);
        
        Tooltip ttTelefone = new Tooltip("Telefone do cliente...");
        ttTelefone.setFont(new Font("Arial", 14));
        tfTelefone.setTooltip(ttTelefone);
        
        Tooltip ttCpf = new Tooltip("Cpf do cliente...");
        ttCpf.setFont(new Font("Arial", 14));
        tfCpf.setTooltip(ttCpf);
        
        Tooltip ttCnpj = new Tooltip("Cnpj do cliente...");
        ttCnpj.setFont(new Font("Arial", 14));
        tfCnpj.setTooltip(ttCnpj);
        
        Tooltip ttAtivo = new Tooltip("Necessário marcar para ativar o cliente!");
        ttAtivo.setFont(new Font("Arial", 14));
        ckClienteAtivo.setTooltip(ttAtivo);
        
        Tooltip ttPesquisar = new Tooltip("Digite o nome do cliente para pesquisar!");
        ttPesquisar.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisar);
        
    }
    
    @FXML
    private void carregarJanela(ActionEvent event) throws IOException {
        abrirJanela("/fxml/ativarClienteView.fxml");
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
    
    private void abrirJanela(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        Image applicationIcon = new Image(getClass().getResourceAsStream("/icons/Shopping cart.png"));
        stage.getIcons().add(applicationIcon);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

}
