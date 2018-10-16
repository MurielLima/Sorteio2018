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
import static config.DAO.premioRepository;
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
import model.Premio;
import model.Premio;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class PremioController implements Initializable {

    public char acao;
    public Premio premio;
    @FXML
    public TableView<Premio> tblView;
    @FXML
    private MaterialDesignIconView btnIncluir;
    @FXML
    private MaterialDesignIconView btnAlterar;
    @FXML
    private MaterialDesignIconView btnExcluir;
    @FXML
    private MaterialDesignIconView btnDisciplinas;
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
        premio = new Premio();
        showCRUD();
    }

    @FXML
    private void acAlterar() {
        acao = ALTERAR;
        premio = tblView.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    @FXML
    private void acExcluir() {
        acao = EXCLUIR;
        premio = tblView.getSelectionModel().getSelectedItem();
        showCRUD();
    }

    @FXML
    private void acPesquisar() {
        tblView.setItems(FXCollections.observableList(
                premioRepository.findByNomeLikeIgnoreCase(txtFldPesquisar.getText())));
    }

    @FXML
    private void acLimpar() {
        txtFldPesquisar.setText("");
        tblView.setItems(
                FXCollections.observableList(premioRepository.findAll(new Sort(new Sort.Order("nome")))));
    }

    private void showCRUD() {
        String cena = "/fxml/CRUDPremio.fxml";
        XPopOver popOver = null;

        switch (acao) {
            case INCLUIR:
                popOver = new XPopOver(cena, "Inclusão de Prêmio", null);
                break;
            case ALTERAR:
                popOver = new XPopOver(cena, "Alteração de Prêmio", null);
                break;
            case EXCLUIR:
                popOver = new XPopOver(cena, "Exclusão de Prêmio", btnExcluir);
                break;
        }
        CRUDPremioController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
             tblView.setItems(
                FXCollections.observableList(premioRepository.findAll(new Sort(new Sort.Order("nome")))));
        btnAlterar.visibleProperty().bind(
                Bindings.isEmpty((tblView.getSelectionModel().getSelectedItems())).not());
        btnExcluir.visibleProperty().bind(btnAlterar.visibleProperty());
        mnAlterar.visibleProperty().bind(btnAlterar.visibleProperty());
        mnExcluir.visibleProperty().bind(btnAlterar.visibleProperty());
        btnPesquisar.disableProperty().bind(txtFldPesquisar.textProperty().isEmpty());
    }

}
