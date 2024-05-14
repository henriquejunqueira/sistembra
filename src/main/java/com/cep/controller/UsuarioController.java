package com.cep.controller;

import com.cep.dao.UsuarioDao;
import com.cep.model.Usuario;
import com.cep.util.Alerta;
import com.cep.util.ValidaCampos;
import com.cep.util.ValidaEmail;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.PasswordField;
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

public class UsuarioController implements Initializable, ICadastro {

    @FXML
    private Button btnSair;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnNovo;
    @FXML
    private TextField tfDescricao;
    @FXML
    private TextField tfEmail;
    @FXML
    private ComboBox<String> cbPermissoes;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private TableView<Usuario> tabelaUsuario;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField tfId;
    @FXML
    private CheckBox ckAtivo;
    @FXML
    private Button btnDesbloquearUsuario;
    @FXML
    private PasswordField pfDicaSenha;
    
    Long id;
    private UsuarioDao dao = new UsuarioDao();
    private ObservableList<Usuario> observableList = FXCollections.observableArrayList();
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSelecionado = new Usuario();
    private ObservableList<String> listaPermissoes = FXCollections.observableArrayList("Administrador", "Usuário");
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPermissoes.setItems(listaPermissoes);
        cbPermissoes.setValue("Usuário");
        ckAtivo.setSelected(true);
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
    }   
    
    @FXML
    private void cadastrarRegistro(ActionEvent event) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Usuario usuario = new Usuario();
        if(ValidaCampos.checarCampoVazio(tfDescricao, cbPermissoes, tfEmail, tfLogin, pfSenha, pfDicaSenha) && ValidaEmail.validate(tfEmail, tfEmail.getText())){
                    if(usuarioSelecionado.getId() != null){
                        usuario.setId(usuarioSelecionado.getId());
                    }
                    usuario.setDescricao(tfDescricao.getText());
                    usuario.setPermissoes(cbPermissoes.getSelectionModel().getSelectedItem());
                    usuario.setEmail(tfEmail.getText());
                    usuario.setLogin(tfLogin.getText());
                    usuario.setSenha(pfSenha.getText());
                    usuario.setAtivo(ckAtivo.isSelected());
                    usuario.setDicasenha(pfDicaSenha.getText());

                    dao.salvar(usuario);

                    limparCamposFormulario();
                    atualizarTabela();
        }
    }

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }

    @FXML
    private void excluirRegistro(ActionEvent event) {
        if(alerta.msgConfirmaExclusao(tfDescricao.getText())){
            usuarioSelecionado.setAtivo(false);
            dao.excluir(usuarioSelecionado);

            limparCamposFormulario();
            atualizarTabela();
        }
    }
    
    @FXML
    private void limparCamposFormulario(ActionEvent event) {
        limparCamposFormulario();
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
        TableColumn<Usuario, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Usuario, String> colunaDescricao = new TableColumn<>("DESCRIÇÃO");
        TableColumn<Usuario, String> colunaPermissoes = new TableColumn<>("PERMISSÕES");
        TableColumn<Usuario, String> colunaEmail = new TableColumn<>("EMAIL");
        TableColumn<Usuario, String> colunaLogin = new TableColumn<>("LOGIN");
        
        tabelaUsuario.getColumns().addAll(colunaId, colunaDescricao, colunaPermissoes, colunaEmail, colunaLogin);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        colunaPermissoes.setCellValueFactory(new PropertyValueFactory("permissoes"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colunaLogin.setCellValueFactory(new PropertyValueFactory("login"));
        
        tabelaUsuario.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        listaUsuarios = dao.consultar(tfPesquisa.getText());
        
        for(Usuario u : listaUsuarios){
            if(u.getAtivo() == true){
                observableList.add(u);
            }
        }
        tabelaUsuario.getItems().setAll(observableList);
        tabelaUsuario.getSelectionModel().selectFirst();
    }

    @Override
    public void setCamposFormulario() {
        usuarioSelecionado = tabelaUsuario.getItems().get(tabelaUsuario.getSelectionModel().getSelectedIndex());
        tfId.setText(Long.toString(usuarioSelecionado.getId()));
        tfDescricao.setText(usuarioSelecionado.getDescricao());
        tfEmail.setText(usuarioSelecionado.getEmail());
        tfLogin.setText(usuarioSelecionado.getLogin());
        cbPermissoes.setValue(usuarioSelecionado.getPermissoes());
        pfSenha.setText(usuarioSelecionado.getSenha());
        ckAtivo.setSelected(usuarioSelecionado.getAtivo());
        pfDicaSenha.setText(usuarioSelecionado.getDicasenha());
    }

    @Override
    public void limparCamposFormulario() {
        usuarioSelecionado.setId(null);
        tfId.clear();
        tfDescricao.clear();
        tfDescricao.requestFocus();
        tfEmail.clear();
        tfLogin.clear();
        cbPermissoes.setValue("Usuário");
        ckAtivo.setSelected(true);
        pfSenha.clear();
        pfDicaSenha.clear();
    }

    @Override
    public void adicionarTooltip() {
        Tooltip ttId = new Tooltip("Campo Id não pode ser preenchido!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttDescricao = new Tooltip("Digite o nome do usuário...");
        ttDescricao.setFont(new Font("Arial", 14));
        tfDescricao.setTooltip(ttDescricao);
        
        Tooltip ttPermissoes = new Tooltip("Selecione o nível de permissão do usuário...");
        ttPermissoes.setFont(new Font("Arial", 14));
        cbPermissoes.setTooltip(ttPermissoes);
        
        Tooltip ttEmail = new Tooltip("Email para recuperação de senha...");
        ttEmail.setFont(new Font("Arial", 14));
        tfEmail.setTooltip(ttEmail);
        
        Tooltip ttLogin = new Tooltip("Login para acessar o sistema...");
        ttLogin.setFont(new Font("Arial", 14));
        tfLogin.setTooltip(ttLogin);
        
        Tooltip ttSenha = new Tooltip("Senha para acessar o sistema...");
        ttSenha.setFont(new Font("Arial", 14));
        pfSenha.setTooltip(ttSenha);
        
        Tooltip ttAtivo = new Tooltip("Necessário marcar para ativar o usuário!");
        ttAtivo.setFont(new Font("Arial", 14));
        ckAtivo.setTooltip(ttAtivo);
        
        Tooltip ttDicaSenha = new Tooltip("Dica para recuperação de senha...");
        ttDicaSenha.setFont(new Font("Arial", 14));
        pfDicaSenha.setTooltip(ttDicaSenha);
        
        Tooltip ttPesquisar = new Tooltip("Digite o nome do usuário para pesquisar!");
        ttPesquisar.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisar);
        
    }

    @FXML
    private void carregarJanela(ActionEvent event) throws IOException {
        abrirJanela("/fxml/ativarUsuarioView.fxml");
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
