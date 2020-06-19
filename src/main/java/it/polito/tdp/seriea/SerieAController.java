/**
 * Sample Skeleton for 'SerieA.fxml' Controller Class
 */

package it.polito.tdp.seriea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Annata;
import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

public class SerieAController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxSquadra"
    private ChoiceBox<Team> boxSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnSelezionaSquadra"
    private Button btnSelezionaSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaAnnataOro"
    private Button btnTrovaAnnataOro; // Value injected by FXMLLoader

    @FXML // fx:id="btnTrovaCamminoVirtuoso"
    private Button btnTrovaCamminoVirtuoso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doSelezionaSquadra(ActionEvent event) {
    	this.txtResult.clear();
    	if (this.boxSquadra.getValue() == null) {
    		this.txtResult.setText("Devi prima scegliere una squadra!\n");
    		return;
    	}
    	List<Annata> listaAnnate = this.model.getPunteggiAnnate(this.boxSquadra.getValue());
    	for (Annata a : listaAnnate) {
    		this.txtResult.appendText(a.toString()+"\n");
    	}
    	this.btnTrovaAnnataOro.setDisable(false);
    }

    @FXML
    void doTrovaAnnataOro(ActionEvent event) {
    	this.txtResult.clear();
    	if (this.boxSquadra.getValue() == null) {
    		this.txtResult.setText("Devi prima scegliere una squadra!\n");
    		return;
    	}
    	Integer annoBest = this.model.getAnnataVincente();
    	Integer punteggioAnnoBest = this.model.getPunteggioAnno(annoBest);
    	this.txtResult.appendText("La miglior annata per la squadra "+this.boxSquadra.getValue().toString()+" e' stato il "+annoBest+"\n");
    	this.txtResult.appendText("Per quell'anno la differenza dei pesi nel grafo e' pari a: "+punteggioAnnoBest+"\n");
    }

    @FXML
    void doTrovaCamminoVirtuoso(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSelezionaSquadra != null : "fx:id=\"btnSelezionaSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaAnnataOro != null : "fx:id=\"btnTrovaAnnataOro\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnTrovaCamminoVirtuoso != null : "fx:id=\"btnTrovaCamminoVirtuoso\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }
    
    public void setModel(Model model) {
		this.model = model;	
		this.boxSquadra.getItems().addAll(this.model.getAllTeams());
		this.btnTrovaAnnataOro.setDisable(true);
		this.btnTrovaCamminoVirtuoso.setDisable(true);
	}
    
}
