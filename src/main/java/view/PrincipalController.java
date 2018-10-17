package view;

import static config.DAO.pessoaRepository;
import static config.DAO.premioRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Pessoa;
import model.Premio;

public class PrincipalController implements Initializable {
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        for(Premio p: premioRepository.findAll()){
            p.setDisponivel(true);
            premioRepository.save(p);
        }
        for(Pessoa p: pessoaRepository.findAll()){
            p.setIdPremio(null);
            pessoaRepository.save(p);
        }
    }    
}
