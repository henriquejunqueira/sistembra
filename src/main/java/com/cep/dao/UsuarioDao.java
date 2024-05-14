package com.cep.dao;

import com.cep.model.Usuario;
import com.cep.util.Alerta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class UsuarioDao {
    Alerta alerta = new Alerta();
    public void salvar(Usuario usuario){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(usuario);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro gravado com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar salvar: " + erro);
        }
    }
    
    public void excluir(Usuario usuario){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(usuario);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro excluído com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar excluir: " + erro);
        }
    }
    public List<Usuario> consultar(String descricao){
        List<Usuario> lista = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        
        if(descricao.length() == 0){
            lista = session.createQuery(" from Usuario ").getResultList();
        }else{
            lista = session.createQuery(" from Usuario u where u.descricao like "+"'"+descricao+"%'").getResultList();
        }
        
        session.getTransaction().commit();
        session.close();
        
        return lista;
    }
}
