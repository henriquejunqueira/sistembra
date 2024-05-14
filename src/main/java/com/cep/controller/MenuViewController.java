package com.cep.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuViewController implements Initializable {

    @FXML
    private Button btnUsuario;
    @FXML
    private Button btnCliente;
    @FXML
    private Button btnProduto;
    @FXML
    private Button btnVenda;
    @FXML
    private Button btnPagamento;
    @FXML
    private Button btnSobre;
    @FXML
    private Button btnSair;
    @FXML
    private Button btnRelatorio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void carregarJanela(ActionEvent event) throws IOException {
        Button botao = (Button) event.getSource();
        
        switch(botao.getText()){
            case "Usuário":
                abrirJanela("/fxml/usuarioView.fxml");
                break;
            case "Cliente":
                abrirJanela("/fxml/clienteView.fxml");
                break;
            case "Produto":
                abrirJanela("/fxml/produtoView.fxml");
                break;
            case "Venda":
                abrirJanela("/fxml/vendaView.fxml");
                break;
            case "Pagamento":
                abrirJanela("/fxml/pagamentoView.fxml");
                break;
            case "Desbloquear":
                abrirJanela("/fxml/ativarUsuarioView.fxml");
                break;
            case "Relatório":
                abrirJanela("/fxml/registroView.fxml");
                break;
            case "Sobre":
                abrirJanela("/fxml/sobreView.fxml");
                break;
        }
    }

    @FXML
    private void fecharJanela(ActionEvent event) {
        System.exit(0);
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
