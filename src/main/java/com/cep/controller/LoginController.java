package com.cep.controller;

import com.cep.dao.Conexao;
import com.cep.dao.LoginDao;
import com.cep.dao.UsuarioDao;
import com.cep.model.Usuario;
import com.cep.util.Alerta;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import sun.net.www.protocol.http.HttpURLConnection;

public class LoginController implements Initializable{

    @FXML
    private TextField tfUsuario;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private Label lblMensagem;
    @FXML
    private Button btnSair;
    @FXML
    private Button btnEntrar;
    @FXML
    private Button btnRecuperarSenha;
    @FXML
    private Button btnCadastrarUsuario;
    
    Long id;
    private LoginDao dao = new LoginDao();
    private List<Usuario> listaUsuarios;
    private ObservableList<Usuario> observableList = FXCollections.observableArrayList();
    Usuario usu = new Usuario();
    private Alerta alerta = new Alerta();
    Task copyWorker;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Session session = Conexao.getSessionFactory().openSession();
    }
    
    @FXML
    private void sair(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void entrar(ActionEvent event) throws IOException {
        observableList.clear();
        listaUsuarios = dao.consultar(tfUsuario.getText(), pfSenha.getText());
        for(Usuario u : listaUsuarios){
            if(u.getAtivo() == true || tfUsuario.equals(u.getSenha())){
                observableList.add(u);
                String usuario = u.getLogin();
                String senha = u.getSenha();
                if(tfUsuario.getText().equals(u.getLogin()) && pfSenha.getText().equals(u.getSenha())){
                    Stage stage = (Stage) btnSair.getScene().getWindow();
                    abrirJanela("/fxml/menuView.fxml");
                    stage.close();
                }else{
                    alerta.msgInformacao("Login ou senha inválida!");
                }
            }
        }
    }

    @FXML
    private void recuperarSenha(ActionEvent event) throws IOException {
        
    }

    @FXML
    private void cadastrarUsuario(ActionEvent event) throws IOException {
        abrirJanela("/fxml/cadastroPrevioUsuarioView.fxml");
    }
    
    private void abrirJanela(String fxml) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        Image applicationIcon = new Image(getClass().getResourceAsStream("/icons/Shopping cart.png"));
        stage.getIcons().add(applicationIcon);
        stage.setMaximized(true);
        //stage.initModality(Modality.APPLICATION_MODAL);
        //progresso(stage);
        stage.show();
    }
}
