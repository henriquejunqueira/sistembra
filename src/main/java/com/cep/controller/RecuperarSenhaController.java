package com.cep.controller;

import com.cep.dao.Conexao;
import com.cep.model.Recuperar;
import com.cep.util.Alerta;
import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.hibernate.Session;

public class RecuperarSenhaController implements Initializable {

    @FXML
    private Button btnEnviar;
    @FXML
    private Button btnSair;
    @FXML
    private ComboBox<Recuperar> cbEmailRecuperacao;
    
    private ObservableList<Recuperar> obsListEmailUsuario = FXCollections.observableArrayList();
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        popularComboBoxEmailUsuario();
    }    

    @FXML
    private void enviarEmailRecuperacao(ActionEvent event) {
        
    }

    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
    
    private void popularComboBoxEmailUsuario(){
        List<Recuperar> list = new ArrayList<>();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createQuery(" from Recuperar").getResultList();
        session.getTransaction().commit();
        session.close();
        
        for(Recuperar r : list){
            obsListEmailUsuario.add(r);
            
        }
        cbEmailRecuperacao.setItems(obsListEmailUsuario);
    }
}
