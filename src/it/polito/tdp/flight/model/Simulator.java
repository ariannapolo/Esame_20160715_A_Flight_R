package it.polito.tdp.flight.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flight.model.Evento.EventType;



public class Simulator {
	//Parametri di simulazione --> impostati all'inizio da chi fa simulazione
	private int K; //numero di passeggeri	
	private LocalDateTime LTInizio; //ora di inizio
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph; //tratte
		
			
	//Stato del modello
	private Map<Airport,Integer> occupazioneAeroporti;
		
		
	//Variabili di interesse
	List<AirportPeople> airportPeople;
		
		
	//Lista degli eventi
	PriorityQueue<Evento> queue;


	public Simulator(int k, SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> graph) {
		super();
		K = k;
		this.graph = graph;
		LTInizio = LocalDateTime.of(2017, 06, 22, 6, 0);
		this.occupazioneAeroporti = new HashMap<>();
		queue = new PriorityQueue<>();
		
		for(Airport a : graph.vertexSet()){
			this.occupazioneAeroporti.put(a, 0);
		}
		
		for(int i=0; i<K; i++){
			List<Airport> l = new ArrayList<>();
			for(Airport a : graph.vertexSet()){
				l.add(a);
			}
			
			Evento e = new Evento(LTInizio,null,l.get((int) (Math.random()*l.size())),EventType.ARRIVAL);
			queue.add(e);
		}
		
	}


	/**
	 * @return the airportPeople
	 */
	public List<AirportPeople> getAirportPeople() {
		Collections.sort(airportPeople);
		return airportPeople;
	}


	/**
	 * @param k the k to set
	 */
	public void setK(int k) {
		K = k;
	}


	/**
	 * @param lTInizio the lTInizio to set
	 */
	public void setLTInizio(LocalDateTime lTInizio) {
		LTInizio = lTInizio;
	}
	
	
	public void simula(){
		airportPeople = new ArrayList<>();
		while(!queue.isEmpty()){
			Evento e = queue.poll();
			if(e.getTime().isBefore(LTInizio.plusDays(2))){
				switch(e.getType()){
				case ARRIVAL:
					//aggiorno stato --> arrivo all'aeroporto 
					int num = this.occupazioneAeroporti.get(e.getAirportArrivo());
					num++;
					occupazioneAeroporti.put(e.getAirportArrivo(), num);
					//aggiungo evento DEPARTURE--> da e.getAirport a casuale
					List<Airport> l = new ArrayList<>();
					for(DefaultWeightedEdge arco : graph.outgoingEdgesOf(e.getAirportArrivo())){
						l.add(graph.getEdgeTarget(arco));
					}
					//aeroporto di arrivo diventa quello di partenza!!
					if(l.size()!=0){
					Evento ev = new Evento(this.getOraDiPartenza(e.getTime()),e.getAirportArrivo(),l.get((int) (Math.random()*l.size())),EventType.DEPARTURE);
					//System.out.println(ev);
					queue.add(ev);	
					}
					
					break;
				case DEPARTURE:
					//aggiorno stato--> voglio partire 
					int numP = this.occupazioneAeroporti.get(e.getAirportPartenza());
					numP--;
					occupazioneAeroporti.put(e.getAirportPartenza(), numP);
					
					//programmo un arrivo in un tempo pari al peso dell'arco
					DefaultWeightedEdge edge = graph.getEdge(e.getAirportPartenza(), e.getAirportArrivo());
					//System.out.println("Durata volo = "+graph.getEdgeWeight(edge));
					int durata = (int) graph.getEdgeWeight(edge);	
					if(durata==0){
						durata =1;
					}
					Evento arrivo = new Evento(e.getTime().plusHours(durata),e.getAirportPartenza(),e.getAirportArrivo(),EventType.ARRIVAL);
					//System.out.println(arrivo);
					queue.add(arrivo);
					
					break;
				default:
					break;
				
				}
			}
		}
		
		for(Airport a : occupazioneAeroporti.keySet()){
			if(occupazioneAeroporti.get(a)!=0){
				airportPeople.add(new AirportPeople(a,this.occupazioneAeroporti.get(a)));
			}
		}
		
	}
		
	
	private LocalDateTime getOraDiPartenza(LocalDateTime lt){
		int ora = lt.getHour();
		LocalDateTime partenza;
		partenza = lt.withMinute(0);
		if(ora <7){
			partenza = lt.withHour(7);
		}else if(ora > 23){
			partenza = lt.plusDays(1).withHour(7);
		}else if(ora%2==0){
			//ora pari --> va all'ora successiva
			partenza = lt.plusHours(1);
		}else{
			partenza = lt;
		}
		return partenza;
	}

}
