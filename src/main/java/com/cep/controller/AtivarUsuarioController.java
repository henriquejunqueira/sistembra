package com.cep.controller;

import com.cep.dao.UsuarioDao;
import com.cep.model.Usuario;
import com.cep.util.Alerta;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AtivarUsuarioController implements Initializable, ICadastro {

    @FXML
    private Button btnSair;
    @FXML
    private TextField tfPesquisa;
    @FXML
    private Button tfDesbloquear;
    @FXML
    private TableView<Usuario> tabelaAtivarUsuario;
    
    Long id;
    private UsuarioDao dao = new UsuarioDao();
    private ObservableList<Usuario> observableList = FXCollections.observableArrayList();
    private List<Usuario> listaUsuarios;
    private Usuario usuarioSelecionado = new Usuario();
    private ObservableList<String> listaPermissoes = FXCollections.observableArrayList("Administrador", "Usuário");
    private Alerta alerta = new Alerta();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        criarColunasTabela();
        atualizarTabela();
        adicionarTooltip();
        Usuario usuario = new Usuario();
        usuario.setAtivo(false);
    }   
    
    private void cadastrarRegistro(ActionEvent event) {
        Usuario usuario = new Usuario();
        if(usuarioSelecionado.getId() != null){
            usuario.setId(usuarioSelecionado.getId());
        }
        usuario.setAtivo(true);

        dao.salvar(usuario);
        
        limparCamposFormulario();
        atualizarTabela();
        
    }

    @FXML
    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }

    
    private void limparCamposFormulario(ActionEvent event) {
        limparCamposFormulario();
    }
    
    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void clicarTabela(MouseEvent event) {
        setCamposFormulario();
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<Usuario, Long> colunaId = new TableColumn<>("ID");
        TableColumn<Usuario, String> colunaDescricao = new TableColumn<>("DESCRIÇÃO");
        
        tabelaAtivarUsuario.getColumns().addAll(colunaId, colunaDescricao);
        
        colunaId.setCellValueFactory(new PropertyValueFactory("id"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory("descricao"));
        
        tabelaAtivarUsuario.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
    }

    @Override
    public void atualizarTabela() {
        observableList.clear();
        listaUsuarios = dao.consultar(tfPesquisa.getText());
        
        for(Usuario u : listaUsuarios){
            if(u.getAtivo() == false){
                observableList.add(u);
            }
        }
        
        tabelaAtivarUsuario.getItems().setAll(observableList);
        tabelaAtivarUsuario.getSelectionModel().selectFirst();
        
    }

    @Override
    public void setCamposFormulario() {
        usuarioSelecionado = tabelaAtivarUsuario.getItems().get(tabelaAtivarUsuario.getSelectionModel().getSelectedIndex());
    }

    @Override
    public void limparCamposFormulario() {
        usuarioSelecionado.setId(null);
    }

    @Override
    public void adicionarTooltip() {
        Tooltip ttPesquisar = new Tooltip("Digite o nome do usuário para pesquisar!");
        ttPesquisar.setFont(new Font("Arial", 14));
        tfPesquisa.setTooltip(ttPesquisar);
    }

    @FXML
    private void excluirRegistro(ActionEvent event) {
        usuarioSelecionado.setAtivo(true);
        dao.excluir(usuarioSelecionado);
        //alerta.msgInformacao("Registro desbloqueado com sucesso");
        
        
        limparCamposFormulario();
        atualizarTabela();
    }
}
