package com.cep.controller;

import com.cep.dao.Conexao;
import com.cep.dao.ItemPedidoDao;
import com.cep.dao.PopularComboBox;
import com.cep.dao.ProdutoDao;
import com.cep.model.Cliente;
import com.cep.model.ItemPedido;
import com.cep.model.Pagamento;
import com.cep.model.Pedido;
import com.cep.model.Produto;
import com.cep.model.Usuario;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.hibernate.Session;

public class PedidoController implements Initializable, ICadastro {

    @FXML
    private TextField tfId;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnSair;
    @FXML
    private DatePicker dpDataVenda;
    @FXML
    private ComboBox<Pagamento> cbPagamento;
    @FXML
    private ComboBox<Usuario> cbUsuario;
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private Label lblTotal;
    @FXML
    private ComboBox<Integer> cbParcelamento;
    @FXML
    private ComboBox<Integer> cbDesconto;
    @FXML
    private TextField tfDescricao;
    @FXML
    private TableView<ItemPedido> tabelaItens;
    @FXML
    private Button btnExcluir;
    @FXML
    private ComboBox<Produto> cbProduto;
    @FXML
    private TextField tfQuantidade;
    @FXML
    private TextField tfPreco;
    @FXML
    private Button btnAdicionar;
    
    private ItemPedido itemSelecionado = new ItemPedido();
    private List<ItemPedido> listaItensPedido = new ArrayList<>();
    private ObservableList<ItemPedido> obsItemPedido = FXCollections.observableArrayList();
    private Double valorTotal = 0.0;
    private Pedido pedidoSelecionado = new Pedido();
    private ObservableList<Pedido> observableList = FXCollections.observableArrayList();
    private List<Pedido> listaPedidos;
    private ObservableList<Integer> listaParcelamento = FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
    private ObservableList<Integer> listaDesconto = FXCollections.observableArrayList(0, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50);
    private ObservableList<Usuario> obsListUsuario = FXCollections.observableArrayList();
    private ObservableList<Cliente> obsListCliente = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dpDataVenda.setValue(LocalDate.now());
        cbParcelamento.setItems(listaParcelamento);
        cbParcelamento.setValue(1);
        cbDesconto.setItems(listaDesconto);
        cbDesconto.setValue(0);
        criarColunasTabela();
        adicionarTooltip();
        popularComboBoxUsuario();
        popularComboBoxCliente();
        PopularComboBox.popular(cbProduto, "Produto");
        PopularComboBox.popular(cbPagamento, "Pagamento");
    }

    @FXML
    private void adicionarItem(ActionEvent event) {
        Produto produtoSelecionado = (Produto) cbProduto.getSelectionModel().getSelectedItem();

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produtoSelecionado);
        itemPedido.setQuantidade(Integer.parseInt(tfQuantidade.getText()));
        itemPedido.setValorUnitario(itemPedido.getProduto().getPreco_venda());
        itemPedido.setTotalItem(itemPedido.getProduto().getPreco_venda()* itemPedido.getQuantidade());

        valorTotal = valorTotal + itemPedido.getTotalItem();
        //lblTotal.setText(String.valueOf(valorTotal));
        int desconto = (Integer)(cbDesconto.getSelectionModel().getSelectedItem());
        double totalDesconto;
        double totalParcelas;
        double totalFinal;
        if(desconto != 0){
            totalDesconto = valorTotal - (valorTotal * desconto / 100);
            lblTotal.setText(String.valueOf(totalDesconto));
        }else{
            lblTotal.setText(String.valueOf(valorTotal));
        }
        
        listaItensPedido.add(itemPedido);

        obsItemPedido = FXCollections.observableArrayList(listaItensPedido);

        tabelaItens.getItems().setAll(obsItemPedido);
    }

    @FXML
    private void removerItem(ActionEvent event) {
        if (!tabelaItens.getSelectionModel().isEmpty()) {
            itemSelecionado = tabelaItens.getItems().get(tabelaItens.getSelectionModel().getSelectedIndex());

            valorTotal = valorTotal - itemSelecionado.getValorUnitario() * itemSelecionado.getQuantidade();
            lblTotal.setText(String.valueOf(valorTotal));

            listaItensPedido.remove(itemSelecionado);
            obsItemPedido = FXCollections.observableArrayList(listaItensPedido);

            tabelaItens.getItems().setAll(obsItemPedido);
        } else {
            System.out.println("Selecione o item a ser excluído!");
        }
    }

    private void atualizarPreco(ActionEvent event) {
        tfPreco.setText(String.valueOf(cbProduto.getSelectionModel().getSelectedItem().getPreco_venda()));
    }

    @FXML
    private void finalizarPedido(ActionEvent event) {
        LocalDate data = dpDataVenda.getValue();
        Usuario usuario = cbUsuario.getSelectionModel().getSelectedItem();
        Cliente cliente = cbCliente.getSelectionModel().getSelectedItem();
        Pagamento pagamento = cbPagamento.getSelectionModel().getSelectedItem();
        Integer parcelamento = cbParcelamento.getSelectionModel().getSelectedItem();
        Integer desconto = cbDesconto.getSelectionModel().getSelectedItem();
        String descricao = tfDescricao.getText();
        Double total = Double.parseDouble(lblTotal.getText());
        
        Pedido pedido = new Pedido();
        pedido.setDataPedido(data);
        pedido.setUsuario(usuario);
        pedido.setCliente(cliente);
        pedido.setPagamento(pagamento);
        pedido.setParcelamento(parcelamento);
        pedido.setDesconto(desconto);
        pedido.setDescricao(descricao);
        pedido.setTotal(total);
        
        PedidoDao pedidoDao = new PedidoDao();
        pedidoDao.salvar(pedido);
        
        ItemPedidoDao itemPedidoDao = new ItemPedidoDao();
        for (ItemPedido itemPedido : listaItensPedido) {
            itemPedido.setPedido(pedido);
            itemPedidoDao.salvar(itemPedido);
            System.out.println("Pedido realizado com sucesso");
        }
        limparPedido();
        limparCamposFormulario();
    }

    private void limparPedido() {
        tabelaItens.getItems().clear();
        listaItensPedido.clear();
        obsItemPedido.clear();
        lblTotal.setText("0,00");
    }


    @FXML
    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void atualizarPrecoProduto(ActionEvent event) {
        tfPreco.setText(String.valueOf(cbProduto.getSelectionModel().getSelectedItem().getPreco_venda()));
    }

    @FXML
    private void clicarTabelaItens(MouseEvent event) {
        
    }


    private void pesquisarRegistro(KeyEvent event) {
        atualizarTabela();
    }

    private void cancelarVenda(ActionEvent event) {
        PedidoDao excluirDao = new PedidoDao();
        excluirDao.excluir(pedidoSelecionado);
        
        limparCamposFormulario();
        atualizarTabela();
    }

    @Override
    public void atualizarTabela() {
        
    }

    @Override
    public void setCamposFormulario() {
        
    }

    @Override
    public void limparCamposFormulario() {
        cbUsuario.setValue(null);
        cbCliente.setValue(null);
        dpDataVenda.setValue(LocalDate.now());
        cbProduto.setValue(null);
        cbPagamento.setValue(null);
        cbParcelamento.setValue(1);
        cbDesconto.setValue(0);
        tfQuantidade.clear();
        tfDescricao.clear();
        tfPreco.clear();
    }

    @Override
    public void adicionarTooltip() {
        Tooltip ttId = new Tooltip("Campo Id não pode ser preenchido!");
        ttId.setFont(new Font("Arial", 14));
        tfId.setTooltip(ttId);
        
        Tooltip ttUsuario = new Tooltip("Usuário que está efetuando a venda...");
        ttUsuario.setFont(new Font("Arial", 14));
        cbUsuario.setTooltip(ttUsuario);
        
        Tooltip ttCliente = new Tooltip("Cliente que está sendo atendido...");
        ttCliente.setFont(new Font("Arial", 14));
        cbCliente.setTooltip(ttCliente);
        
        Tooltip ttDataVenda = new Tooltip("Data que está efetuando a venda...");
        ttDataVenda.setFont(new Font("Arial", 14));
        dpDataVenda.setTooltip(ttDataVenda);
        
        Tooltip ttProduto = new Tooltip("Selecione os produtos...");
        ttProduto.setFont(new Font("Arial", 14));
        cbProduto.setTooltip(ttProduto);
        
        Tooltip ttPagamento = new Tooltip("Selecione a forma de pagamento...");
        ttPagamento.setFont(new Font("Arial", 14));
        cbPagamento.setTooltip(ttPagamento);
        
        Tooltip ttParcelamento = new Tooltip("Selecione 1 para pagamento à vista...");
        ttPagamento.setFont(new Font("Arial", 14));
        cbParcelamento.setTooltip(ttPagamento);
        
        Tooltip ttDesconto = new Tooltip("Selecione 0 para nenhum desconto...");
        ttDesconto.setFont(new Font("Arial", 14));
        cbDesconto.setTooltip(ttDesconto);
        
        Tooltip ttQuantidade = new Tooltip("Digite a quantidade do produto a ser comprado...");
        ttQuantidade.setFont(new Font("Arial", 14));
        tfQuantidade.setTooltip(ttQuantidade);
        
        Tooltip ttDescricao = new Tooltip("Digite uma descrição da venda. Ex: HD comprado por João Silva...");
        ttDescricao.setFont(new Font("Arial", 14));
        tfDescricao.setTooltip(ttDescricao);
        
        Tooltip ttPreco = new Tooltip("Campo preço não pode ser preenchido! Selecione o produto primeiro...");
        ttPreco.setFont(new Font("Arial", 14));
        tfPreco.setTooltip(ttPreco);
        
        Tooltip ttAdicionar = new Tooltip("Adiciona produtos a serem comprados...");
        ttAdicionar.setFont(new Font("Arial", 14));
        btnAdicionar.setTooltip(ttAdicionar);
        
        Tooltip ttExcluir = new Tooltip("Exclui produtos a serem comprados...");
        ttExcluir.setFont(new Font("Arial", 14));
        btnExcluir.setTooltip(ttExcluir);
        
        Tooltip ttSalvar = new Tooltip("Finaliza a venda...");
        ttSalvar.setFont(new Font("Arial", 14));
        btnSalvar.setTooltip(ttSalvar);
    }

    @Override
    public void criarColunasTabela() {
        TableColumn<ItemPedido, Produto> colunaProduto = new TableColumn<>("PRODUTO");
        TableColumn<ItemPedido, Double> colunaValor = new TableColumn<>("VALOR UNITÁRIO");
        TableColumn<ItemPedido, Integer> colunaQuantidade = new TableColumn<>("QUANTIDADE");
        TableColumn<ItemPedido, Double> colunaTotalItem = new TableColumn<>("TOTAL ITEM");

        tabelaItens.getColumns().addAll(colunaProduto, colunaQuantidade, colunaValor, colunaTotalItem);

        colunaProduto.setCellValueFactory(new PropertyValueFactory("produto"));
        colunaValor.setCellValueFactory(new PropertyValueFactory("valorUnitario"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory("quantidade"));
        colunaTotalItem.setCellValueFactory(new PropertyValueFactory("totalItem"));

        colunaProduto.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(0.5));
        colunaQuantidade.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(0.10));
        colunaValor.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(0.20));
        colunaTotalItem.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(0.20));
    }
    
    private void popularComboBoxUsuario(){
        List<Usuario> list = new ArrayList<>();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createQuery(" from Usuario").getResultList();
        session.getTransaction().commit();
        session.close();
        
        for(Usuario usuario : list){
            if(usuario.getAtivo() == true){
                obsListUsuario.add(usuario);
            }
        }
        cbUsuario.setItems(obsListUsuario);
    }
    
    private void popularComboBoxCliente(){
        List<Cliente> list = new ArrayList<>();
        Session session = Conexao.getSessionFactory().openSession();
        session.beginTransaction();
        list = session.createQuery(" from Cliente").getResultList();
        session.getTransaction().commit();
        session.close();
        
        for(Cliente cliente : list){
            if(cliente.getAtivo() == true){
                obsListCliente.add(cliente);
            }
        }
        cbCliente.setItems(obsListCliente);
    }
    
    private void atualizarEstoque(){
        Produto prod = new Produto();
        ItemPedido item = new ItemPedido();
        prod.getQuantidadeEstoque();
        int estoque = prod.getQuantidadeEstoque();
        int quantComprada = item.getQuantidade();
        float total = estoque - quantComprada;
        System.out.println("Total: " + total);
    }
}
