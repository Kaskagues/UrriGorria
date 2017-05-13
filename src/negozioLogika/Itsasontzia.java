package negozioLogika;

import java.util.ArrayList;
import java.util.Iterator;

import negozioLogika.ItsasontziTile;
import negozioLogika.commands.CommandItsasontziaIpini;

public class Itsasontzia extends Objektuak {
	protected final int luzeera,prezioa;
	private int ezkutua;
	private final String jabea=null;
	private boolean suntsituta;
	private ArrayList<ItsasontziTile> tileLista;
	public Itsasontzia(String pMota, int pLuzeera, int pPrezioa){
		super(pMota);
		ezkutua=0;
		luzeera = pLuzeera;
		prezioa = pPrezioa;
		tileLista= new ArrayList<ItsasontziTile>();
	}
	public void informazioaInprimatu() {
		// TODO Auto-generated method stub
		System.out.println("       Mota: "		+izena);
		System.out.println("       Jabea: "		+jabea);
		System.out.println("       Luzera: "	+luzeera);
		System.out.println("       Prezioa: "	+prezioa);
		System.out.println("       Suntsituta: "+suntsituta);

	}
	public void tileGehitu(ItsasontziTile pTile, boolean pZer) {
		// TODO Auto-generated method stub
		if(pZer){
			tileLista.add(pTile);
		}
		else{
			tileLista.remove(pTile);
		}
	}
	public void erabili(String pNori, int pX, int pY,char pNorabide) {
		new CommandItsasontziaIpini(pNori, this, pX, pY, pNorabide).exekutatu();
	}
	public void suntsitutaDago() {
		// TODO Auto-generated method stub
		boolean suntsitutaDago=true;
		Iterator<ItsasontziTile> it=tileLista.iterator();
		while(suntsitutaDago && it.hasNext()){
			suntsitutaDago= it.next().suntsitutaDago();
		}
		suntsituta=suntsitutaDago;
	}
	public boolean hauDa(Itsasontzia pOntzi) {
		// TODO Auto-generated method stub
		return (pOntzi.izenBerdina(super.izena) && pOntzi.tileBerdinakDira(tileLista));
	}
//	private boolean hemenDago(int pX, int pY) {
//		// TODO Auto-generated method stub
//		boolean aurkituta=false;
//		Iterator<ItsasontziTile> it=tileLista.iterator();
//		while(!aurkituta && it.hasNext()){
//			aurkituta= it.next().posizioanDago(pX, pY);
//		}
//		return aurkituta;
//	}
	private boolean tileBerdinakDira(ArrayList<ItsasontziTile> pTileLista){
		return pTileLista==this.tileLista;
	}
	public int luzeera() {
		// TODO Auto-generated method stub
		return luzeera;
	}
	public void ezkutuaJarri(boolean pZer){
		if(pZer) ezkutua=200;
		else ezkutua-=200;
	}
	public boolean ezkutuaDauka(){
		return ezkutua!=0;
	}
	public void ezkutuariJo(int pIndarra, boolean pZer){
		if (pZer) this.ezkutua-=pIndarra;
		else this.ezkutua+=pIndarra;
	}
}


