package com.cep.controller;

import com.cep.dao.UsuarioDao;
import com.cep.model.Usuario;
import com.cep.util.ValidaCampos;
import com.cep.util.ValidaEmail;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CadastroPrevioUsuarioController implements Initializable, ICadastro {

    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnNovo;
    @FXML
    private Button btnSair;
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
    private TextField tfId;

    Long id;
    private UsuarioDao dao = new UsuarioDao();
    private ObservableList<Usuario> observableList = FXCollections.observableArrayList();
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSelecionado = new Usuario();
    private ObservableList<String> listaPermissoes = FXCollections.observableArrayList("Usuário");
    @FXML
    private PasswordField pfDicaSenha;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPermissoes.setItems(listaPermissoes);
        cbPermissoes.setValue("Usuário");
        adicionarTooltip();
    }    

    @FXML
    private void cadastrarRegistro(ActionEvent event) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
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
            usuario.setAtivo(false);
            usuario.setDicasenha(pfDicaSenha.getText());

            dao.salvar(usuario);
            limparCamposFormulario();
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

    @Override
    public void criarColunasTabela() {
        
    }

    @Override
    public void atualizarTabela() {
        
    }

    @Override
    public void setCamposFormulario() {
        
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
        pfSenha.clear();
        pfDicaSenha.clear();
    }

    @Override
    public void adicionarTooltip(){
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
        
        Tooltip ttDicaSenha = new Tooltip("Dica para recuperação de senha...");
        ttDicaSenha.setFont(new Font("Arial", 14));
        pfDicaSenha.setTooltip(ttDicaSenha);
    }
    
}
