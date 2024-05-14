package com.cep.dao;

import com.cep.model.Usuario;
import com.cep.util.Alerta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class LoginDao {
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
            session.delete(usuario);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro excluído com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar excluir: " + erro);
        }
    }
    public List<Usuario> consultar(String descricao, String senha){
        List<Usuario> lista = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        
       
            lista = session.createQuery("from Usuario l where l.login ="+"'"+descricao+"' and l.senha = '"+senha+"'").list();
            //lista = session.createQuery("from Usuario s where s.senha like"+"'"+descricao+"%'").list();
        
            session.getTransaction().commit();
        session.close();
        
        return lista;
    }
}
