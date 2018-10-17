/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.DAO.pessoaRepository;
import static config.DAO.premioRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.Pessoa;
import model.Premio;
import org.springframework.data.domain.Sort;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class CRUDSorteioController implements Initializable {

    public SorteioController controllerPai;
    private Timeline timeline;
    List<Pessoa> lstPessoa = new ArrayList<>();
    List<Premio> lstPremio = new ArrayList<>();
    private Random rand = new Random();
    private Premio prVez;
    @FXML
    private Button btnFecharSorteio;
    @FXML
    private Button btnIniciarSorteio;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label lblPremio;
    @FXML
    private TableView tblView;

    public void setCadastroController(SorteioController controllerPai) {
        this.controllerPai = controllerPai;
    }

    private void tempo() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(1000), new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        Integer posPessoa = rand.nextInt(lstPessoa.size());
                        Pessoa pessoaTmp = lstPessoa.get(posPessoa);
                        lstPessoa.remove(pessoaTmp);
                        tblView.refresh();
                        tblView.setItems(FXCollections.observableList(lstPessoa));
                        tblView.getSelectionModel().select(rand.nextInt(lstPessoa.size()));
                        if (lstPessoa.size() == 1) {
                            btnFecharSorteio.setDisable(false);
                            btnIniciarSorteio.setDisable(true);
                            (lstPessoa.get(0)).setIdPremio(prVez);
                            premioRepository.save(prVez);
                            pessoaRepository.save(lstPessoa.get(0));
                            timeline.stop();
                        }
                    }
                }));
        timeline.playFromStart();
    }

    @FXML
    public void btnSorteioClick(Event event) {
        if (lstPremio.size() > 0) {
            btnFecharSorteio.setDisable(true);
            Integer posPremio = rand.nextInt(lstPremio.size());
            prVez = lstPremio.get(posPremio);
            prVez.setDisponivel(false);
            lblPremio.setText(prVez.getNome());
            tempo();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Criação de sorteio");
            alert.setContentText("Não foi possível iniciar o sorteio.");
            alert.showAndWait();
        }

    }

    @FXML
    public void btnFecharSorteioClick(Event event) {
        controllerPai.pessoa.clear();
        controllerPai.premio.clear();
        controllerPai.iniciaListas();
        anchorPane.getScene().getWindow().hide();
    }

    public void iniciaListas() {

        for (Pessoa p : pessoaRepository.findAll(new Sort(new Sort.Order("nome")))) {
            if (!p.isSorteado()) {
                lstPessoa.add(p);
            }
        }
        for (Premio p : premioRepository.findAll(new Sort(new Sort.Order("nome")))) {
            if (p.isDisponivel()) {
                lstPremio.add(p);
            }
        }
        tblView.setItems(FXCollections.observableList(lstPessoa));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciaListas();
        if (lstPessoa.size() < 2) {
            btnFecharSorteio.setDisable(false);
            btnIniciarSorteio.setDisable(true);
        }
    }

}
