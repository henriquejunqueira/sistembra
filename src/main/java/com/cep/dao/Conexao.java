package com.cep.dao;

import com.cep.model.Cliente;
import com.cep.model.ItemPedido;
import com.cep.model.Login;
import com.cep.model.Pagamento;
import com.cep.model.Pedido;
import com.cep.model.Produto;
import com.cep.model.Recuperar;
import com.cep.model.Usuario;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Conexao {
    private static SessionFactory conexao = null;
    private static Configuration configuracao;
    
    private static SessionFactory buildSessionFactory(){
        //----> Objeto que armazena as configurações de conexão
        configuracao = new Configuration().configure();
        
        configuracao.setProperty("hibernate.connection.username", "root");
        configuracao.setProperty("hibernate.connection.password", "");
        
        configuracao.addPackage("com.cep.model").addAnnotatedClass(Usuario.class);
        configuracao.addPackage("com.cep.model").addAnnotatedClass(Cliente.class);
        configuracao.addPackage("com.cep.model").addAnnotatedClass(Produto.class);
        configuracao.addPackage("com.cep.model").addAnnotatedClass(Pedido.class);
        configuracao.addPackage("com.cep.model").addAnnotatedClass(ItemPedido.class);
        configuracao.addPackage("com.cep.model").addAnnotatedClass(Pagamento.class);
        configuracao.addPackage("com.cep.model").addAnnotatedClass(Login.class);
        configuracao.addPackage("com.cep.model").addAnnotatedClass(Recuperar.class);
        
        conexao = configuracao.buildSessionFactory();
        return conexao;
    }
    
    public static SessionFactory getSessionFactory(){
        if(conexao == null){
            conexao = buildSessionFactory();
        }
        return conexao;
    }
}
