package negozioLogika;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Jokalariak {
	
	private String izena;
	boolean bizirik;
	private Mapa mapa;
	private Inbentarioa inb;
	private Denda denda;
	private int dirua = 0;
	private ArrayList<Itsasontzia> nireItsasontziak;

	
	
	public Jokalariak(String pIzena){
		izena=pIzena;
		bizirik=true;
	}
	
	
	public boolean kokatuDaiteke( int pX, int pY,  char pNorabidea, int pLuzeera){
		return mapa.kokatuDaiteke(pX, pY,  pNorabidea, pLuzeera);
	}
	public Itsasontzia itsasontziaJarri(Itsasontzia pOntzi, int pX, int pY, char pNorabidea, boolean pZer) {
		// TODO Auto-generated method stub
		if(pZer==true){
			pOntzi = mapa.itsasontziaJarri(izena, pOntzi, pX, pY, pNorabidea,pZer);
			nireItsasontziak.add(pOntzi);
		}
		else{
			boolean aurkituta=false;
			Itsasontzia itsasontzia;
			Iterator<Itsasontzia> it=nireItsasontziak.iterator();
			while(!aurkituta && it.hasNext()){
				itsasontzia= it.next();
				if(itsasontzia.hauDa(pOntzi)){
					pOntzi = mapa.itsasontziaJarri(izena, itsasontzia, pX, pY, pNorabidea, pZer);
				}
				else{
					pOntzi = null;
					//Hemen ez dago itsasontzirik
				}
			}
		}
		return pOntzi;
	}
	public boolean izenHauDu(String pJ) {
		return pJ==izena;
	}
	public ArrayList<Objektuak> dendakIzakinakDitu(Erosketa pErosketa) {
		return denda.dendakIzakinakDitu(pErosketa);		//Izakinik ez baldin badu, null bat bueltatuko du
	}
	public void jokalariariDiruaEman(int pPrezioa, boolean pZer) { 
		//pZer=true denean jokalariari dirua emango zaio, bestela kendu
		if(pZer==true)	dirua+=pPrezioa;
		else			dirua-=pPrezioa;
	}
	public void dendariObjektuakEman(ArrayList<Objektuak> pObjektuak, boolean pZer) {
		// TODO Auto-generated method stub
		denda.objektuakEman(pObjektuak,pZer);
	}
	public void jokalariariObjektuakEman(ArrayList<Objektuak> pObjektuak, boolean pZer) {
		inb.objektuakEman(pObjektuak,pZer);
	}
	public String izenaLortu() {
		return izena;
	}
	public boolean objektuakDitu(ArrayList<Objektuak> pObjektuak) {
		return inb.objektuakDitu(pObjektuak);
	}
	public void jokalariariErasotu(String pNork, Objektuak pObjektua, int pX, int pY,char pNorabide, boolean pZer) {
		mapa=pObjektua.aktibatu(pNork,mapa, pX, pY, pNorabide, pZer);
	}
	public boolean jokalariaBizirikDago() {
		return bizirik;
	}
	public String[][] mapaInterpretatu(String pNork){
		return mapa.mapaInterpretatu(pNork);
	}
	public boolean jokalariakDiruaDu(int pPrezioa) {
		return (dirua>=pPrezioa);
	}

}
