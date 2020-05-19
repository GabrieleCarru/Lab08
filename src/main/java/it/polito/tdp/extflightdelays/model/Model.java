package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	// Verificare uso variabili ed eventualemente eliminare quelle inutilizzate
	private ExtFlightDelaysDAO dao; 
	
	private List<Airline> airlines;
	private List<Airport> airports;
	private List<Flight> flights;
	
	private Map<Integer, Airport> idMap;
	
	private SimpleWeightedGraph<Airport, DefaultWeightedEdge> graph;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		
		this.idMap = new HashMap<>();
		
		this.airlines = dao.loadAllAirlines();
		this.airports = dao.loadAllAirports();
		this.flights = dao.loadAllFlights();
		this.dao.loadAllAirports(idMap);
		
	}
	
	public void creaGrafo(int minDistance) {
		graph = new SimpleWeightedGraph<Airport, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		// Recupero tramite DAO tutti gli aeroporti che hanno almeno  
		// una tratta che rispetti la distanza minima
		List<Airport> airportsMinDistance = dao.getAirportMinDistance(minDistance, idMap);
		
		// Creo i vertici del grafo
		for(Airport a : airportsMinDistance) {
			this.graph.addVertex(a);
		}
		
		for(Rotta r : dao.getRotteDistanceMin(minDistance, idMap)) {
			if(this.graph.containsVertex(r.getAp()) && this.graph.containsVertex(r.getAd())) {
				DefaultWeightedEdge e = this.graph.getEdge(r.getAp(), r.getAd());
				
				Graphs.addEdgeWithVertices(graph, r.getAp(), r.getAd(), r.getDistance());
				
			} 
		}
	}
	
	public List<Rotta> getRotte(int minDistance) {
		return dao.getRotteDistanceMin(minDistance, idMap);
	}
	
	public int vertexNumber() {
		return this.graph.vertexSet().size();
	}
	
	public int edgeNumber() {
		return this.graph.edgeSet().size();
	}
	
	public List<Airline> getAirlines() { 
		return dao.loadAllAirlines();
	}
	
	public List<Airport> getAirports() {
		return dao.loadAllAirports();
	}
	
	public List<Flight> getFlights() {
		return dao.loadAllFlights();
	}
	
	public Map<Integer, Airport> getIdMap() {
		return this.idMap;
	}
	
	
}
