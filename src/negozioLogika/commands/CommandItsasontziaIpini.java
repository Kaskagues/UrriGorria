package negozioLogika.commands;

import java.util.ArrayList;

import negozioLogika.Itsasontzia;
import negozioLogika.Objektuak;
import negozioLogika.Partida;

public class CommandItsasontziaIpini extends Commands {
	private ArrayList<Objektuak> ontzia= new ArrayList<Objektuak>();
	private int koordX, koordY;
	private char norabidea;
	
	public CommandItsasontziaIpini(String pJ, Itsasontzia pItsasontzia, int pX, int pY, char pNorabidea){
		//Datuak gorde----------
		super.exekutatu();
		ontzia.add(pItsasontzia);
		koordX=pX;
		koordY=pY;
		norabidea=pNorabidea;
		//----------------------
	}
	
	public void exekutatu(){
		if(konprobatu()){
			Partida.jokalariariObjektuakEman(jokalaria, ontzia, false);
			ontzia.add(0, Partida.itsasontziaJarri(jokalaria, (Itsasontzia) ontzia.get(0), koordX, koordY, norabidea, true));
			super.komandoaGorde(true);
		}
	}
	public void deuseztatu(){
		Partida.itsasontziaJarri(jokalaria, (Itsasontzia) ontzia.get(0), koordX, koordY, norabidea, false);
		Partida.jokalariariObjektuakEman(jokalaria, ontzia, true);
		super.komandoaGorde(false);
	}
	private boolean konprobatu(){
		return (Partida.kokatuDaiteke(jokalaria, koordX, koordY, norabidea, ((Itsasontzia) ontzia.get(0)).luzeera())
				&& Partida.jokalariakObjektuakDitu(jokalaria, ontzia));
	}
	public String info(){
		String info=super.info();
		return info.concat("CommandItsasontziaIpini("+ontzia.get(0).getIzena()+")");
	}
}
