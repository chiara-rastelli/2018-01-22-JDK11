package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	
	private SerieADAO db;
	private Map<Integer, Integer> mappaAnnate;
	private SimpleDirectedGraph<Integer, DefaultWeightedEdge> graph;
	private List<Integer> bestPercorso;
	private Integer lunghezzaBestPercorso;
	
	public Model() {
		this.db = new SerieADAO();
	}

	public List<Team> getAllTeams(){
		return this.db.listTeams();
	}
	
	public List<Annata> getPunteggiAnnate(Team team){
		this.mappaAnnate = new HashMap<>();
		this.graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		Map<Integer, Integer> result = new HashMap<Integer, Integer>();
		List<Annata> daRitornare = new ArrayList<Annata>();
		
		Map<Integer, Integer> vittorieCasa = this.db.listVittorieInCasa(team.getTeam());
		Map<Integer, Integer> vittorieTrasferta = this.db.listVittorieInTrasferta(team.getTeam());
		Map<Integer, Integer> pareggiCasa = this.db.listPareggiInCasa(team.getTeam());
		Map<Integer, Integer> pareggiTrasferta = this.db.listPareggiInTrasferta(team.getTeam());
		
		for (int i : vittorieCasa.keySet()) {
			result.put(i, vittorieCasa.get(i));
		}
		
		for (int j : vittorieTrasferta.keySet()) {
			Integer parziale = result.get(j);
			if (parziale == null)
				parziale = 0;
			result.put(j, parziale+vittorieTrasferta.get(j));
		}
		
		for (int k : pareggiCasa.keySet()) {
			Integer parziale = result.get(k);
			if (parziale == null)
				parziale = 0;
			result.put(k, parziale+pareggiCasa.get(k));
		}
		
		for (int l : pareggiTrasferta.keySet()) {
			Integer parziale = result.get(l);
			if (parziale == null)
				parziale = 0;
			result.put(l, parziale+pareggiTrasferta.get(l));
		}

		mappaAnnate = new HashMap<>(result);
		
		for (int i : result.keySet())
			daRitornare.add(new Annata(i, result.get(i)));
		Collections.sort(daRitornare);
		return daRitornare;
	}
	
	public Integer getAnnataVincente() {
		for (Integer i : this.mappaAnnate.keySet())
			this.graph.addVertex(i);
	//	System.out.println("Grafo creato con "+this.graph.vertexSet().size()+"  vertici!\n");
		
		for (Integer i : this.mappaAnnate.keySet()) {
			int annoCorrente = i;
			int annoPrecedente = i-1;
			int annoSuccessivo = i+1;
			Integer punteggioAnnoCorrente = this.mappaAnnate.get(annoCorrente);
			Integer punteggioAnnoPrecedente = this.mappaAnnate.get(annoPrecedente);
			Integer punteggioAnnoSuccessivo = this.mappaAnnate.get(annoSuccessivo);
			
			if (punteggioAnnoPrecedente!=null) {
				if (!this.graph.containsEdge(annoCorrente, annoPrecedente) && !this.graph.containsEdge(annoPrecedente, annoCorrente)) {
					if (punteggioAnnoCorrente > punteggioAnnoPrecedente)
						Graphs.addEdgeWithVertices(graph, annoCorrente, annoPrecedente, (punteggioAnnoCorrente-punteggioAnnoPrecedente));
					if (punteggioAnnoPrecedente > punteggioAnnoCorrente)
						Graphs.addEdge(this.graph, annoPrecedente, annoCorrente, (punteggioAnnoPrecedente - punteggioAnnoCorrente));
				}
			}
			
			if (punteggioAnnoSuccessivo != null) {
				if (!this.graph.containsEdge(annoCorrente, annoSuccessivo) && !this.graph.containsEdge(annoSuccessivo, annoCorrente)) {
					if (punteggioAnnoCorrente > punteggioAnnoSuccessivo)
						Graphs.addEdge(this.graph, annoCorrente, annoSuccessivo, (punteggioAnnoCorrente - punteggioAnnoSuccessivo));
					if (punteggioAnnoSuccessivo > punteggioAnnoCorrente)
						Graphs.addEdge(this.graph, annoSuccessivo, annoCorrente, (punteggioAnnoSuccessivo - punteggioAnnoCorrente));
				}
			}
			
		}
	//	System.out.println("Al grafo sono stati aggiunti "+this.graph.edgeSet().size()+" archi!\n");
		
		Integer annoBest = this.calcolaBestAnnata();
		return annoBest;
	}
	
	public Integer getPunteggioAnno(Integer Anno) {
		int punteggio = 0;
		
		for (DefaultWeightedEdge e : this.graph.outgoingEdgesOf(Anno))
			punteggio = (int) (punteggio + this.graph.getEdgeWeight(e));
		for (DefaultWeightedEdge e : this.graph.incomingEdgesOf(Anno))
			punteggio = (int) (punteggio - this.graph.getEdgeWeight(e));
		return punteggio;
		
	}

	private Integer calcolaBestAnnata() {
		Integer migliorPunteggio = 0;
		Integer migliorAnno = 0;
		
		for (Integer i : this.graph.vertexSet()) {
			int punteggio = this.getPunteggioAnno(i);
			if (punteggio > migliorPunteggio) {
				migliorPunteggio = punteggio;
				migliorAnno = i;
			}
		}
		return migliorAnno;
	}
	
	public void cerca() {
		List<Integer> listaAnnate = new ArrayList<>();
		for(Integer i : this.graph.vertexSet())
			listaAnnate.add(i);
		Collections.sort(listaAnnate);
		
		List<Integer> parziale = new ArrayList<Integer>();
		this.bestPercorso = new ArrayList<Integer>();
		
		for (Integer i : listaAnnate) {
			parziale.add(i);
			this.ricorri(1, parziale);
			parziale.remove(0);
		}
		
	}

	private void ricorri(int livello, List<Integer> parziale) {
		
		Integer ultimoAnno = parziale.get(livello-1);
		
		//cerco i successivi
		for (Integer i : Graphs.successorListOf(this.graph, ultimoAnno)) {
			
		}
		
	}
	
}
