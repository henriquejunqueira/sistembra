package com.cep.dao;

import com.cep.model.Produto;
import com.cep.model.Usuario;
import com.cep.util.Alerta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class ProdutoDao {
    Alerta alerta = new Alerta();
    public void salvar(Produto produto){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(produto);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro gravado com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar salvar: " + erro);
        }
    }
    
    public void excluir(Produto produto){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(produto);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro excluído com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar excluir: " + erro);
        }
    }
    public List<Produto> consultar(String descricao){
        List<Produto> lista = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        
        if(descricao.length() == 0){
            lista = session.createQuery(" from Produto ").getResultList();
        }else{
            lista = session.createQuery( "from Produto p where p.descricao like "+"'"+descricao+"%'").getResultList();
        }
        
        session.getTransaction().commit();
        session.close();
        
        return lista;
    }
}
