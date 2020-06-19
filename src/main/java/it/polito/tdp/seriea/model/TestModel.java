package it.polito.tdp.seriea.model;

import java.util.List;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		
		Team juve = new Team("Juventus");
		
		List<Annata> listaPunteggi = m.getPunteggiAnnate(juve);
		
		for (Annata a : listaPunteggi) {
			System.out.println(a.toString()+"\n");
		}

		int annoBest = m.getAnnataVincente();
		System.out.println("La miglior annata per la squadra "+juve.toString()+" e' stato il "+annoBest+"\n");
		System.out.println("Quell'anno il punteggio complessivo e' stato: "+m.getPunteggioAnno(annoBest)+"\n");
		
	//	List<Integer> percorsoRicorsione = m.camminoVirtuoso();
	//	System.out.println(percorsoRicorsione);
		
	}

}
