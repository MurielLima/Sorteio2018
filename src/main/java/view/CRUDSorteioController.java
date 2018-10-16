/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static config.Config.EXCLUIR;
import static config.DAO.pessoaRepository;
import static config.DAO.premioRepository;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
    @FXML
    private Label lblPremio;
    @FXML
    private TableView tblView;
    List<Pessoa> lstPessoa = new ArrayList<>();
    List<Premio> lstPremio = new ArrayList<>();
    private Random rand = new Random();
    private Premio prVez;

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
                        tblView.setItems(FXCollections.observableList(lstPessoa));

                        LocalDateTime hora = LocalDateTime.now();
                        lblPremio.setText(hora.toString());
                        if (lstPessoa.size() == 1) {
                            timeline.stop();
                        }
                    }

                }));
        timeline.playFromStart();
    }

    @FXML
    public void btnSorteioClick(Event event) {
        if (lstPremio.size() > 0) {
            Integer posPremio = rand.nextInt(lstPremio.size());
            prVez = lstPremio.get(posPremio);
            tempo();
            (lstPremio.get(posPremio)).setDisponivel(false);
            (lstPessoa.get(0)).setIdPremio(prVez);
            premioRepository.save(lstPremio.get(posPremio));
            pessoaRepository.save(lstPessoa.get(0));
            controllerPai.iniciaListas();
            controllerPai.tblViewPessoa.refresh();
            controllerPai.tblViewPremio.refresh();
            controllerPai.tblViewPessoa.setItems(FXCollections.observableList(controllerPai.pessoa));
            controllerPai.tblViewPremio.setItems(FXCollections.observableList(controllerPai.premio));

        }

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
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iniciaListas();
        tblView.setItems(FXCollections.observableList(lstPessoa));

    }

}
