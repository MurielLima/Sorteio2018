/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.premioRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class CRUDPremioController implements Initializable {

    public PremioController controllerPai;
    @FXML
    private TextField txtFldDescricao;
    @FXML
    private TextField txtFldNome;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnConfirma;

    public void setCadastroController(PremioController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldNome.setText(controllerPai.premio.getNome());
        txtFldDescricao.setText(controllerPai.premio.getNome());
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldDescricao.setDisable(controllerPai.acao == EXCLUIR);
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.premio.setDescricao(txtFldDescricao.getText());
        controllerPai.premio.setNome(txtFldNome.getText());
        controllerPai.premio.setDisponivel(true);
        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    premioRepository.insert(controllerPai.premio);
                    break;
                case ALTERAR:
                    premioRepository.save(controllerPai.premio);
                    break;
                case EXCLUIR:
                    premioRepository.delete(controllerPai.premio);
                    break;
            }
            controllerPai.tblView.setItems(
                    FXCollections.observableList(premioRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            controllerPai.tblView.refresh();
            controllerPai.tblView.getSelectionModel().clearSelection();
            controllerPai.tblView.getSelectionModel().select(controllerPai.premio);
            anchorPane.getScene().getWindow().hide();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Cadastro de Pessoa");
            if (e.getMessage().contains("duplicate key")) {
                alert.setContentText("Código já cadastrado");
            } else {
                alert.setContentText(e.getMessage());
            }
            alert.showAndWait();
        }
    }

    @FXML
    private void btnCancelaClick() {
        anchorPane.getScene().getWindow().hide();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btnConfirma.disableProperty().bind(txtFldDescricao.textProperty().isEmpty().
                or(txtFldNome.textProperty().isEmpty()));
    }

}
