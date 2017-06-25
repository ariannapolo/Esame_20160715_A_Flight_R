package it.polito.tdp.flight;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.AirportPeople;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlightController {

	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtDistanzaInput;

	@FXML
	private TextField txtPasseggeriInput;

	@FXML
	private TextArea txtResult;

	@FXML
	void doCreaGrafo(ActionEvent event) {
		txtResult.clear();
		String d = txtDistanzaInput.getText();
    	//controlli
    	if(d.length()==0){
    		txtResult.appendText("ERRORE: devi inserire un anno\n");
    		return;
    	}
    	double dist;
    	try{
    		dist = Double.parseDouble(d);
    	}catch(NumberFormatException nfe){
    		txtResult.appendText("ERRORE: l'anno deve essere in formato numerico\n");
    		return;
    	}
    	model.creaGrafo(dist);
    	if(model.graphIsConnected())
    	    txtResult.appendText("Grafo è connesso\n");
    	else
    		txtResult.appendText("Grafo non è connesso\n");
    	
    	txtResult.appendText("L'aeroporto piu' lontano da Fiumicino e' : "+model.getPiuLontanoDaFiumicino());;

		
	}

	@FXML
	void doSimula(ActionEvent event) {
		txtResult.clear();
		String passeggeri = this.txtPasseggeriInput.getText();
    	//controlli
    	if(passeggeri.length()==0){
    		txtResult.appendText("ERRORE: devi inserire un numero di passeggeri\n");
    		return;
    	}
    	int num;
    	try{
    		num = Integer.parseInt(passeggeri);
    	}catch(NumberFormatException nfe){
    		txtResult.appendText("ERRORE: il numero di passeggeri deve essere in formato numerico\n");
    		return;
    	}

    	if(model.simulazione(num)==null){
    		txtResult.appendText("ERRORE: Trovare prima le rotte con la distanza\n");
    		return;
    	}
    	for(AirportPeople ap : model.simulazione(num)){
    		txtResult.appendText(ap.getAirport()+" "+ap.getPeople()+"\n");
    	};

		
	}

	@FXML
	void initialize() {
		assert txtDistanzaInput != null : "fx:id=\"txtDistanzaInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtPasseggeriInput != null : "fx:id=\"txtPasseggeriInput\" was not injected: check your FXML file 'Untitled'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Untitled'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
