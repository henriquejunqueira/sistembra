package com.cep.dao;

import com.cep.model.Pagamento;
import com.cep.model.Usuario;
import com.cep.util.Alerta;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

public class PagamentoDao {
    Alerta alerta = new Alerta();
    public void salvar(Pagamento pagamento){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.merge(pagamento);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro gravado com sucesso");
            System.out.println();
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar salvar: " + erro);
            System.out.println();
        }
    }
    
    public void excluir(Pagamento pagamento){
        try{
            Session session = Conexao.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(pagamento);
            session.getTransaction().commit();
            session.close();
            alerta.msgInformacao("Registro excluído com sucesso");
        }catch(Exception erro){
            alerta.msgInformacao("Ocorreu o erro ao tentar excluir: " + erro);
        }
    }
    public List<Pagamento> consultar(String descricao){
        List<Pagamento> lista = new ArrayList();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        
        if(descricao.length() == 0){
            lista = session.createQuery(" from Pagamento ").getResultList();
        }else{
            lista = session.createQuery( " from Pagamento pa where pa.tipo like "+"'"+descricao+"%'").getResultList();
        }
        
        session.getTransaction().commit();
        session.close();
        
        return lista;
    }
}
