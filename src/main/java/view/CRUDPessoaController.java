/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.ALTERAR;
import static config.Config.EXCLUIR;
import static config.Config.INCLUIR;
import static config.DAO.pessoaRepository;
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
public class CRUDPessoaController implements Initializable {

    public PessoaController controllerPai;
    @FXML
    private TextField txtFldURL;
    @FXML
    private TextField txtFldNome;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button btnConfirma;

    public void setCadastroController(PessoaController controllerPai) {
        this.controllerPai = controllerPai;
        txtFldNome.setText(controllerPai.pessoa.getNome());
        txtFldURL.setText(controllerPai.pessoa.getUrl());
        txtFldNome.setDisable(controllerPai.acao == EXCLUIR);
        txtFldURL.setDisable(controllerPai.acao == EXCLUIR);
    }

    @FXML
    private void btnConfirmaClick() {
        controllerPai.pessoa.setUrl(txtFldURL.getText());
        controllerPai.pessoa.setNome(txtFldNome.getText());
        
        try {
            switch (controllerPai.acao) {
                case INCLUIR:
                    pessoaRepository.insert(controllerPai.pessoa);
                    break;
                case ALTERAR:
                    pessoaRepository.save(controllerPai.pessoa);
                    break;
                case EXCLUIR:
                    pessoaRepository.delete(controllerPai.pessoa);
                    break;
            }
            controllerPai.tblView.setItems(
                    FXCollections.observableList(pessoaRepository.findAll(
                            new Sort(new Sort.Order("nome")))));
            controllerPai.tblView.refresh();
            controllerPai.tblView.getSelectionModel().clearSelection();
            controllerPai.tblView.getSelectionModel().select(controllerPai.pessoa);
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
        controllerPai.tblView.requestFocus();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnConfirma.disableProperty().bind(txtFldURL.textProperty().isEmpty().
                or(txtFldNome.textProperty().isEmpty()));
    }

}
