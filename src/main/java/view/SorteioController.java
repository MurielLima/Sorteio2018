package view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static config.DAO.pessoaRepository;
import static config.DAO.premioRepository;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import model.Pessoa;
import model.Premio;
import org.springframework.data.domain.Sort;
import utility.XPopOver;

/**
 * FXML Controller class
 *
 * @author Muriel
 */
public class SorteioController implements Initializable {

    @FXML
    public TableView<Pessoa> tblViewPessoa;
    @FXML
    public TableView<Premio> tblViewPremio;
    public List<Pessoa> pessoa = new ArrayList<>();
    public List<Premio> premio = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    @FXML
    public void btnSorteioClick() {
        String cena = "/fxml/CRUDSorteio.fxml";
        XPopOver popOver = null;
        popOver = new XPopOver(cena, "Sorteio", null);

        CRUDSorteioController controllerFilho = popOver.getLoader().getController();
        controllerFilho.setCadastroController(this);
    }
public void iniciaListas(){
       
        for (Pessoa p : pessoaRepository.findAll(new Sort(new Sort.Order("nome")))) {
            if (p.isSorteado()) {
                pessoa.add(p);
            }
        }
        for (Premio p : premioRepository.findAll(new Sort(new Sort.Order("nome")))) {
            if (!p.isDisponivel()) {
                premio.add(p);
            }
        }
}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    iniciaListas();
        tblViewPessoa.setItems(FXCollections.observableList(pessoa));
        tblViewPremio.setItems(
                FXCollections.observableList(premio));
    }

}
