package negozioLogika;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Jokalariak {
	
	protected String izena;
	boolean bizirik;
	protected Mapa mapa;
	protected Inbentarioa inb;
	private Denda denda;
	private int dirua;
	private ArrayList<Itsasontzia> nireItsasontziak;
	
	public Jokalariak(String pIzena){
		izena=pIzena;
		jokalariaErreseteatu();
	}
	
	public abstract void itsasontziakIpini();
	
	public void jokalariaErreseteatu(){
		denda= new Denda();
		inb = new Inbentarioa();
		dirua=500;
		bizirik=true;
		mapa= new Mapa(izena);
		nireItsasontziak=new ArrayList<Itsasontzia>();
		
		ArrayList<Objektuak> objektuak=new ArrayList<Objektuak>();
		objektuak=denda.dendakIzakinakDitu(ErosketaFactory.getErosketaFactory()
				.createErosketa("DHasteko Objektu Guztiak"));
		denda.objektuakEman(objektuak, false);
		inb.objektuakEman(objektuak, true);
	}
	
	public boolean kokatuDaiteke( int pX, int pY,  char pNorabidea, int pLuzeera){
		return mapa.kokatuDaiteke(pX, pY,  pNorabidea, pLuzeera);
	}
	public Itsasontzia itsasontziaJarri(Itsasontzia pOntzi, int pX, int pY, char pNorabidea, boolean pZer) {
		if(pZer==true){
			pOntzi = mapa.itsasontziaJarri(izena, pOntzi, pX, pY, pNorabidea,pZer);
			nireItsasontziak.add(0,pOntzi);
		}
		else{
			for(Itsasontzia its:nireItsasontziak){
				its.informazioaInprimatu();
			}
			boolean aurkituta=false;
			Itsasontzia itsasontzia;
			Iterator<Itsasontzia> it=nireItsasontziak.iterator();
			while(!aurkituta && it.hasNext()){
				itsasontzia= it.next();
				if(itsasontzia.equals(pOntzi)){
					pOntzi = mapa.itsasontziaJarri(izena, itsasontzia, pX, pY, pNorabidea, pZer);
					aurkituta=true;
					nireItsasontziak.remove(itsasontzia);
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
	public boolean objektuakNahikoakDitu(ArrayList<Objektuak> pObjektuak) {
		return inb.objektuakNahikoakDitu(pObjektuak);
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
	public String getIzena() {
		return izena;
	}
	public ArrayList<String> inbentarioaEman() {
		return inb.inbentarioaEman();
	}
	public ArrayList<String> dendaEman() {
		return denda.dendaEman();
	}
	public int jokalariakZenbatDiru() {
		return dirua;
	}

	public void objektuaErabili(String pNori, String[] pInfo) {
		inb.objektuaErabili(pNori,pInfo);
	}

	public abstract void jokatuCPU(int pFasea);

	public int itsasontziaJo(ItsasontziTile pTile, int pIndarra, boolean pZer) {
		for(Itsasontzia its:nireItsasontziak){
			if(its.tileHauDauka(pTile)){
				return its.jo(pIndarra, pZer);
			}
		}
		return 0;
	}


}
