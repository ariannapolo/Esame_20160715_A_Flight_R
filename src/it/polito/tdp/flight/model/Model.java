package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.alg.*;
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	
	private Map<Integer,Airport> aeroporti;
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> graph;
	
	public Map<Integer,Airport> getAeroporti(){
		if(aeroporti==null){
			aeroporti = new HashMap<>();
			FlightDAO dao = new FlightDAO();
			for(Airport a : dao.getAllAirports()){
				aeroporti.put(a.getAirportId(), a);
			}
		}
		return aeroporti;
	}
	
	public void creaGrafo(double distanza){
		graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		FlightDAO dao = new FlightDAO();
		for(AirportsAndDistance ad : dao.getPairs(this.getAeroporti())){
			if(ad.getDistance()<distanza && !ad.getA1().equals(ad.getA2())){
				//System.out.println(ad);
				double peso = ad.getDistance()/800;
				Graphs.addEdgeWithVertices(graph, ad.getA1(), ad.getA2(), peso);
			}
		}
		//System.out.println(graph);
	}

	public boolean graphIsConnected(){
		KosarajuStrongConnectivityInspector<Airport, DefaultWeightedEdge> ksci = new KosarajuStrongConnectivityInspector<>(graph);
		return ksci.isStronglyConnected();
	}
	
	public Airport getPiuLontanoDaFiumicino(){
		Airport a=null;
		for(Airport v : graph.vertexSet()){
			if(v.getName().compareTo("Fiumicino")==0){
				a = v;
			}
		}
		double max = 0;
		Airport aMax = null;
		if(a!=null){
		for(DefaultWeightedEdge e : graph.outgoingEdgesOf(a)){
			if(graph.getEdgeWeight(e)>=max){
				max=graph.getEdgeWeight(e);
				aMax = graph.getEdgeTarget(e);
			}
		}
		
		}
		return aMax;
	}
	
	public List<AirportPeople> simulazione(int k){
		if(graph==null){
			return null;
		}
		Simulator s = new Simulator(k,graph);
		s.simula();
		return s.getAirportPeople();
	}
	public static void main(String[] args){
		Model model = new Model();
		model.creaGrafo(600);
		for(AirportPeople ap : model.simulazione(100)){
			System.out.println(ap.getAirport()+" "+ap.getPeople());
		}
	}
}
