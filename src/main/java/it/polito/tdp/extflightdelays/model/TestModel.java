package it.polito.tdp.extflightdelays.model;

import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		Map<Integer, Airport> mappa = model.getIdMap();
		//model.creaGrafo(4502, mappa);
		
		// Risultato atteso = 3
		System.out.println(model.vertexNumber());

	}

}
