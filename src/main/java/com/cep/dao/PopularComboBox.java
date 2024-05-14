package com.cep.dao;

import com.cep.model.Produto;
import com.cep.model.Usuario;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.hibernate.Session;

public class PopularComboBox {

    public static void popular(ComboBox comboBox, String classe) {
        comboBox.getItems().clear();
        List<Produto> list = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createQuery(" from " + classe).getResultList();
        session.getTransaction().commit();
        session.close();
        
        ObservableList<Produto> obsProduto = FXCollections.observableArrayList();
        obsProduto = FXCollections.observableArrayList(list);
        comboBox.setItems(obsProduto);
    }
}
