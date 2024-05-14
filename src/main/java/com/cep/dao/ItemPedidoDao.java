package com.cep.dao;

import com.cep.model.ItemPedido;
import org.hibernate.Session;

public class ItemPedidoDao {
        public void salvar(ItemPedido item) {        
        try {
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();            
            session.save(item);    
            session.getTransaction().commit();  
            session.close();
        } catch (Exception erro) {
            System.out.println("Ocorreu o erro: " + erro);
        }
    }
    
    public void excluir(ItemPedido item) {
        try {
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(item);
            session.getTransaction().commit();
            session.close();
            System.out.println("Registro excluído com sucesso");
        } catch (Exception erro) {
            System.out.println("Ocorreu o erro: " + erro);
        }
    }
    
}
