package negozioLogika;

import java.util.ArrayList;
import java.util.Iterator;

public class Mapa {
	// Atributuak
	public static int zut = 10;
	public static int erren = 10;
	private Tile[][] jokalariMapa;
	private ArrayList<Itsasontzia> nireItsasontziak;
	String jabea;

	// Eraikitzailea
	public Mapa(String pIzena) {
		jabea = pIzena;
		jokalariMapa = new Tile[zut][erren];
		nireItsasontziak = new ArrayList<Itsasontzia>();
		this.urezBete(jokalariMapa);
	}

	// Metodoak
	private void urezBete(Tile[][] pMap) {
		for (int Y = 0; Y < zut; Y++) {
			for (int X = 0; X < erren; X++) {
				pMap[Y][X] = new UraTile(jabea, X, Y);
			}
		}
	}

	public boolean kokatuDaiteke(int pX, int pY) {
		return jokalariMapa[pX][pY].kokatuDaiteke();
	}

	public boolean kokatuDaiteke(int pX, int pY, char pNorabidea, int pLuzeera) {
		boolean libre = true;
		int koordX = pX, koordY = pY, i = 0;
		Tile t;
		while (i < pLuzeera && libre) {
			if (koordX < erren - 1 && koordX > 0 && koordY < zut - 1 && koordY > 0) {
				t = jokalariMapa[koordX][koordY];

				libre = t.kokatuDaiteke();
				if (pNorabidea == 'W') {
					koordY--;
				} else if (pNorabidea == 'E') {
					koordY++;
				} else if (pNorabidea == 'N') {
					koordX--;
				} else if (pNorabidea == 'S') {
					koordX++;
				}

				i++;
			} else
				libre = false;
		}
		return libre;
	}

	public Itsasontzia itsasontziaJarri(String pJabea, Itsasontzia pOntzi, int pX, int pY, char pNorabidea,
			boolean pZer) {
		if (pZer) {
			pOntzi = tilakJarri(pJabea, pOntzi, pX, pY, pNorabidea, pZer);
			nireItsasontziak.add(0, pOntzi);
			return pOntzi;
		} else {
			if (nireItsasontziak.remove(pOntzi)) {
				pOntzi = tilakJarri(pJabea, pOntzi, pX, pY, pNorabidea, pZer);
			}
		}
		return pOntzi;
	}

	private Itsasontzia tilakJarri(String pJabea, Itsasontzia pItsasontzia, int pX, int pY, char pNorabidea,
			boolean pZer) {

		ItsasontziTile tile;
		int koordX = pX, koordY = pY;
		if (pZer)
			pItsasontzia = (Itsasontzia) ObjektuakFactory.getObjektuakFactory().createObjektua(pItsasontzia.getIzena());
		pItsasontzia.setJabea(pJabea);

		for (int i = 0; i < pItsasontzia.luzeera; i++) {
			if (pZer) {
				tile = new ItsasontziTile(pJabea, koordX, koordY);
				jokalariMapa[koordX][koordY] = tile;
			} else {
				tile = ((ItsasontziTile) jokalariMapa[koordX][koordY]);
				jokalariMapa[koordX][koordY] = new UraTile(pJabea, koordX, koordY);
			}

			if (koordX - 1 >= 0)
				jokalariMapa[koordX - 1][koordY].kokatzekoGaitasunaEman(!pZer);
			if (koordX + 1 < zut)
				jokalariMapa[koordX + 1][koordY].kokatzekoGaitasunaEman(!pZer);
			if (koordY - 1 >= 0)
				jokalariMapa[koordX][koordY - 1].kokatzekoGaitasunaEman(!pZer);
			if (koordY + 1 < erren)
				jokalariMapa[koordX][koordY + 1].kokatzekoGaitasunaEman(!pZer);

			if (pNorabidea == 'W') {
				koordY--;
			} else if (pNorabidea == 'E') {
				koordY++;
			} else if (pNorabidea == 'N') {
				koordX--;
			} else if (pNorabidea == 'S') {
				koordX++;
			}

			pItsasontzia.tileGehitu(tile, pZer);

		}
		return pItsasontzia;
	}

	public boolean erasoSinpleaKonprobatu(int pX, int pY) {
		return(pX<zut && pY<erren && pX>= 0 && pY>= 0);
	}

	public void erasoSinpleaJaso(String pNork, int pX, int pY, int pIndarra, boolean pZer) {
		int i=0;
		boolean bilatzen=true;
		Itsasontzia its=null;
		Iterator<Itsasontzia> it= nireItsasontziak.iterator();
		if(pX>=0&&pX<getErrenkada()&&pY>=0&&pY<getZutabe()){
			while(it.hasNext()&&bilatzen){	//Itsasontzia bilatu
				its = it.next();
				if(its.posizioanDago(pX, pY)){
					bilatzen=false;
					//Aurkitu bada, erasotu
					ArrayList<Tile> tileList=its.jo(pNork, pIndarra, pX, pY,pZer);
					for(Tile tile:tileList){
						jokalariMapa[tile.getX()][tile.getY()]=tile;//mapa aktualizatu
					}
					//itsasontzia berriro gorde
					nireItsasontziak.set(i,its);
				}else i++;
			}
			if(bilatzen)	jokalariMapa[pX][pY].jo(pNork, pIndarra, pZer);
		}
	}

	public String[][] mapaInterpretatu(String pNork) {
		String[][] mapa = new String[zut][erren];

		for (int indX = 0; indX < zut; indX++) {
			for (int indY = 0; indY < erren; indY++) {
				mapa[indX][indY] = jokalariMapa[indX][indY].erakutsi(pNork);
			}
		}
		return mapa;
	}
	
//=======================================================================================================================
//Erabili ekipoa=========================================================================================================
	public Mapa erabiliEkipo(String pMota,int pX, int pY, boolean pZer) {
		boolean bilatzen =true;
		Itsasontzia its=null;
		Iterator<Itsasontzia> it= nireItsasontziak.iterator();
		while(it.hasNext()&&bilatzen){
			its = it.next();
			if(its.posizioanDago(pX, pY)){	
				bilatzen =false;
				if(pMota.equals("Ezkutua")){
					for(ItsasontziTile tile:its.erabiliEzkutua(pZer)){
						jokalariMapa[tile.getX()][tile.getY()]=tile;}
				}else if(pMota.equals("Konponketa")){
					for(ItsasontziTile tile:its.erabiliKonponketa(pZer)){
						jokalariMapa[tile.getX()][tile.getY()]=tile;}
				}
			}
		}
		return this;
	}
		
	public int[] erabiliRadarra(String pNork, int pX, int pY, int pRadio, boolean pZer) {
		int[] koord = new int[2];
		koord[0] = -1;
		koord[1] = -1;
		double hurbilena = pRadio + 1;
		if (jokalariMapa[pX][pY].itsasontziaDa() && jokalariMapa[pX][pY].getBizirik()) {
			hurbilena = 0;
			koord[0] = pX;
			koord[1] = pY;
		}else{
			for (int x = pX - pRadio; x <= pX + pRadio; x++) {
				for (int y = pY - pRadio; y <= pY + pRadio; y++) {

					if (x >= 0 && x < zut && y >= 0 && y < erren) {

						if (Math.pow(x - pX, 2) + Math.pow(y - pY, 2) <= Math.pow(pRadio, 2)) {

							if (jokalariMapa[x][y].itsasontziaDa() && jokalariMapa[x][y].getBizirik()
									&& Math.pow(x - pX, 2) + Math.pow(y - pY, 2) < Math.pow(hurbilena, 2)) {
								hurbilena = Math.pow(x - pX, 2) + Math.pow(y - pY, 2);
								koord[0] = x;
								koord[1] = y;
							}
						}
					}
				}
			}
		}			
		return koord;
	}
//=======================================================================================================================
	public int getErrenkada() {
		return erren;
	}

	public int getZutabe() {
		return zut;
	}

	public boolean itsasontziaDa(int pX, int pY) {
		return jokalariMapa[pX][pY].itsasontziaDa();
	}

	public boolean itsasontziBizirik() {
		int bizirik=0;
		for(Itsasontzia ontzi : nireItsasontziak)
			if(!ontzi.getSuntzituta())bizirik++;
		return (bizirik>0);
	}

	public boolean itsasontziGuztiakBizirik() {
		int hilda=0;
		for(Itsasontzia ontzi : nireItsasontziak)
			if(ontzi.getBizitzaOsoarekin())hilda++;
		return (hilda==0);
	}

	public ArrayList<Itsasontzia> getItsasontziak() {
		return nireItsasontziak;
	}


}