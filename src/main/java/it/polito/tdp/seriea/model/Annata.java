package it.polito.tdp.seriea.model;

public class Annata implements Comparable<Annata>{

	Integer anno;
	Integer punteggioTotale;
	
	public Annata(Integer anno, Integer punteggioTotale) {
		this.anno = anno;
		this.punteggioTotale = punteggioTotale;
	}

	@Override
	public int compareTo(Annata o) {
		return this.anno.compareTo(o.anno);
	}

	@Override
	public String toString() {
		return "Annata " + anno + ", punteggioTotale=" + punteggioTotale;
	}
	
}
