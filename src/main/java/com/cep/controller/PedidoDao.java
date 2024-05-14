package com.cep.controller;

import com.cep.dao.Conexao;
import com.cep.model.Pedido;
import com.cep.util.Alerta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

class PedidoDao {
    Alerta alerta = new Alerta();
    public void salvar(Pedido pedido) {        
        try {
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();            
            session.save(pedido);    
            session.getTransaction().commit();  
            System.out.println("apos commit: "+pedido.getId());
            session.close();
        } catch (Exception erro) {
            System.out.println("Ocorreu o erro: " + erro);
        }
    }
    
    public void excluir(Pedido pedido) {
        try {
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(pedido);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro excluído com sucesso");
        } catch (Exception erro) {
            alerta.msgInformacao("Ocorreu o erro ao tentar excluir: " + erro);
        }
    }
    
    public List<Pedido> consultar(String descricao){
        List<Pedido> lista = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        
        if(descricao.length() == 0){
            lista = session.createQuery(" from Pedido ").getResultList();
        }else{
            lista = session.createQuery(" from Pedido p where p.descricao like "+"'"+descricao+"%'").getResultList();
        }
        
        session.getTransaction().commit();
        session.close();
        
        return lista;
    }
}
