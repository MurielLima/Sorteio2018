package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.pessoaRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Pessoa;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class PessoaController implements Initializable {

    public char acao;
    public Pessoa pessoa;
    @FXML
    public TableView<Pessoa> tblView;
    @FXML
    private MaterialDesignIconView btnIncluir;
    @FXML
    private MaterialDesignIconView btnAlterar;
    @FXML
    private MaterialDesignIconView btnExcluir;

    @FXML
    private TextField txtFldPesquisar;
    @FXML
    private MaterialDesignIconView btnPesquisar;
    @FXML
    private MenuItem mnIncluir;
    @FXML
    private MenuItem mnAlterar;
    @FXML
    private MenuItem mnExcluir;
    @FXML
    private MenuItem mnDisciplinas;

    @FXML
    private void acIncluir() {
        acao = INCLUIR;
        pessoa = new Pessoa();
        showCRUD();
    }

    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        pessoa = pessoaRepository.findByIdPessoa(tblView.getSelectionModel().getSelectedItem().getIdPessoa());
        showCRUD();
    }

    @FXML
    private void acExcluir() {
        acao = EXCLUIR;
        pessoa = pessoaRepository.findByIdPessoa(tblView.getSelectionModel().getSelectedItem().getIdPessoa());
        showCRUD();
    }

    @FXML
    private void acPesquisar() {
        tblView.setItems(FXCollections.observableList(
                pessoaRepository.findByNomeLikeIgnoreCase(txtFldPesquisar.getText())));
    }

    @FXML
    private void acLimpar() {
        txtFldPesquisar.setText("");
        tblView.setItems(
                FXCollections.observableList(pessoaRepository.findAll(new Sort(new Sort.Order("nome")))));
    }

    private void showCRUD() {
        String cena = "/fxml/CRUDPessoa.fxml";
        XPopOver popOver = null;

        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Pessoa", null);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Pessoa", null);
                break;
            case EXCLUIR:
                popOver = new XPopOver(cena, "Exclusão de Pessoa", btnExcluir);
                break;
        }
        CRUDPessoaController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tblView.setItems(
                FXCollections.observableList(pessoaRepository.findAll(new Sort(new Sort.Order("nome")))));
        btnAlterar.visibleProperty().bind(
                Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        btnExcluir.visibleProperty().bind(btnAlterar.visibleProperty());
        mnAlterar.visibleProperty().bind(btnAlterar.visibleProperty());
        mnExcluir.visibleProperty().bind(btnAlterar.visibleProperty());
        btnPesquisar.disableProperty().bind(txtFldPesquisar.textProperty().isEmpty());
    }

}
