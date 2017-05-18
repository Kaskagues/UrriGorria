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
				pMap[Y][X] = TileFactory.getTileFactory().createUraTile(jabea, X, Y);
			}
		}
	}

	public boolean kokatuDaiteke(int pX, int pY) {
		return jokalariMapa[pX][pY].kokatuDaiteke();
	}

	public boolean kokatuDaiteke(int pX, int pY, char pNorabidea, int pLuzeera) {// throws
																					// IndepXOutOfBoundsEpXception{
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
				System.out.println(pOntzi.getIzena() + " itsasontzia kendu da " + pJabea + "-tik");
			} else
				System.out.println("Ezin izan da " + pOntzi.getIzena() + " itsasontzia  " + pJabea + "-tik kendu");
		}
		return pOntzi;
	}

	private Itsasontzia tilakJarri(String pJabea, Itsasontzia pItsasontzia, int pX, int pY, char pNorabidea,
			boolean pZer) {

		ItsasontziTile tile;
		int koordX = pX, koordY = pY;
		if (pZer)
			pItsasontzia = (Itsasontzia) ObjektuakFactory.getObjektuakFactory().createObjektua(pItsasontzia.getIzena());
		pItsasontzia.jabeaJarri(pJabea);

		for (int i = 0; i < pItsasontzia.luzeera; i++) {
			if (pZer) {
				tile = new ItsasontziTile(pJabea, koordX, koordY, pItsasontzia);
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
		while(it.hasNext()&&bilatzen){
			its = it.next();
			if(its.posizioanDago(pX, pY)){
				bilatzen=false;
			}else i++;
		}
		if(!bilatzen){
			for(Tile tile:its.jo(pNork, pIndarra, pX, pY,pZer)){
				jokalariMapa[tile.getX()][tile.getY()]=tile;
			}
			nireItsasontziak.set(i,its);
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

	public Mapa ezkutuaJarri(String pNork, int pX, int pY, boolean pZer) {
		boolean bilatzen =true;
		Itsasontzia its=null;
		Iterator<Itsasontzia> it= nireItsasontziak.iterator();
		while(it.hasNext()&&bilatzen){
			its = it.next();
			if(its.posizioanDago(pX, pY)){	
				bilatzen =false;
				for(Tile tile:its.ezkutuaJarri(pZer)){
					jokalariMapa[tile.getX()][tile.getY()]=tile;
				}
			}
		}
		return this;
	}

	public int[] radarraErabili(String pNork, int pX, int pY, int pRadio, boolean pZer) {
		int[] koord = new int[2];
		koord[0] = -1;
		koord[1] = -1;
		double hurbilena = pRadio + 1;
		if (jokalariMapa[pX][pY].itsasontziaDa() && jokalariMapa[pX][pY].bizirikDago()) {
			hurbilena = 0;
			koord[0] = pX;
			koord[1] = pY;
		}else{
			for (int x = pX - pRadio; x <= pX + pRadio; x++) {
				for (int y = pY - pRadio; y <= pY + pRadio; y++) {

					if (x >= 0 && x < zut && y >= 0 && y < erren) {

						if (Math.pow(x - pX, 2) + Math.pow(y - pY, 2) <= Math.pow(pRadio, 2)) {

							if (jokalariMapa[x][y].itsasontziaDa() && jokalariMapa[x][y].bizirikDago()
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
		for (int x = pX - pRadio; x <= pX + pRadio; x++) {
			for (int y = pY - pRadio; y <= pY + pRadio; y++) {
				if(y==koord[1]&&x==koord[0]) System.out.print("X");
				else System.out.print("#");
			}
			System.out.println("");
		}
			
		return koord;
	}

	public int getErrenkada() {
		return erren;
	}

	public int getZutabe() {
		return zut;
	}

	public boolean itsasontziaDa(int pX, int pY) {
		return jokalariMapa[pX][pY].itsasontziaDa();
	}

}