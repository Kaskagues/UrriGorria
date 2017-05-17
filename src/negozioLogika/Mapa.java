package negozioLogika;

public class Mapa {
	//Atributuak
	public static int zut=10;
	public static int erren=10;
	private Tile[][] jokalariMapa;
	String jabea;
	
	//Eraikitzailea
	public Mapa(String pIzena){
		jabea= pIzena;
		jokalariMapa= new Tile[zut][erren];
		this.urezBete(jokalariMapa);
	}
		
	//Metodoak
	private void urezBete(Tile[][] pMap){
		for(int Y=0;Y<zut; Y++){
			for(int X=0;X<erren; X++){
				pMap[Y][X]=TileFactory.getTileFactory().createUraTile(jabea, X, Y);
			}
		}
	}
	public boolean kokatuDaiteke(int pX, int pY) {
		return jokalariMapa[pX][pY].kokatuDaiteke();
	}

	public boolean kokatuDaiteke(int pX, int pY, char pNorabidea, int pLuzeera){// throws IndepXOutOfBoundsEpXception{
		boolean libre = true;
		int koordX=pX, koordY=pY,i=0;
		Tile t;
		while (i<pLuzeera&&libre){
			if(koordX<erren-1&&koordX>0&&koordY<zut-1&&koordY>0){
				t=jokalariMapa[koordX][koordY];

				libre=t.kokatuDaiteke();
				if(pNorabidea=='W'){koordY--;}
				else if(pNorabidea=='E'){koordY++;}
				else if(pNorabidea=='N'){koordX--;}
				else if(pNorabidea=='S'){koordX++;}
				
				System.out.println(i+" X:"+koordX+" Y:"+koordY+" Librea:"+libre+" Norabidea:" +pNorabidea);
				i++;
			}
			else libre=false;
		}
		return libre;
	}

	public Itsasontzia itsasontziaJarri(String pJabea, Itsasontzia pItsasontzia, int pX, int pY, char pNorabidea, boolean pZer) {
		ItsasontziTile tile;
		int koordX=pX, koordY=pY;
		if(pZer)pItsasontzia=(Itsasontzia)ObjektuakFactory.getObjektuakFactory().createObjektua(pItsasontzia.getIzena());
		
		for (int i=0;i<pItsasontzia.luzeera;i++){
			if(pZer){
				tile= new ItsasontziTile(pJabea, koordX, koordY, pItsasontzia);
				jokalariMapa[koordX][koordY]= tile;
			}
			else{
				tile=((ItsasontziTile)jokalariMapa[koordX][koordY]);				
				jokalariMapa[koordX][koordY]=new UraTile(pJabea, koordX, koordY);
			}

			if(koordX-1>0)jokalariMapa[koordX-1][koordY].kokatzekoGaitasunaEman(!pZer);
			if(koordX+1<zut)jokalariMapa[koordX+1][koordY].kokatzekoGaitasunaEman(!pZer);
			if(koordY-1>0)jokalariMapa[koordX][koordY-1].kokatzekoGaitasunaEman(!pZer);
			if(koordY+1<erren)jokalariMapa[koordX][koordY+1].kokatzekoGaitasunaEman(!pZer);
			
			if(pNorabidea=='W'){koordY--;}
			else if(pNorabidea=='E'){koordY++;}
			else if(pNorabidea=='N'){koordX--;}
			else if(pNorabidea=='S'){koordX++;}
			
			pItsasontzia.tileGehitu(tile,pZer);	
			
		}
		return pItsasontzia;
	}

	public boolean erasoSinpleaKonprobatu(int pX, int pY) {
		if(pX<zut && pY<erren&&pX>=0 && pY>=0){
			return true;
		}else return false;
	}
	public void erasoSinpleaJaso(String pNork,int pX, int pY, int pIndarra, boolean pZer) {
		if(erasoSinpleaKonprobatu(pX,pY))jokalariMapa[pX][pY].jo(pNork, pIndarra, pZer);
	}

	public String[][] mapaInterpretatu(String pNork){
		String[][] mapa= new String[zut][erren];
		
		for(int indX=0;indX<zut;indX++){
			for(int indY=0;indY<erren;indY++){
				mapa[indX][indY]=jokalariMapa[indX][indY].erakutsi(pNork);
			}
		}
		return mapa;
	}
	public void ezkutuaJarri(String pNork, int pX, int pY, boolean pZer){
		if (jokalariMapa[pX][pY].jabeaDa(pNork)&&jokalariMapa[pX][pY].itsasontziaDa()) ((ItsasontziTile)jokalariMapa[pX][pY]).ezkutuaJarri(pZer);
	}
	
	public int[] radarraErabili(String pNork,int pX, int pY, int pRadio, boolean pZer){//al encontrar hazle un jokalariMapa[x][y].jo(pNork,0,True)
		int[] koord=new int[2];
		koord[0]=-1;
		koord[1]=-1;
		double hurbilena=pRadio+1;
		if(jokalariMapa[pX][pY].itsasontziaDa()&&jokalariMapa[pX][pY].bizirikDago()){
			hurbilena=0;
			koord[0]=pX;
			koord[1]=pY;
		}else
		for(int x = pX-pRadio;x<=pX+pRadio;x++){
			for(int y = pY-pRadio;y<=pY+pRadio;y++){
				
				if(x>=0&&x<zut&&y>=0&&y<erren){
					
					if(Math.pow(x-pX, 2)+Math.pow(y-pY, 2)<=Math.pow(pRadio, 2)){
						
						if(jokalariMapa[x][y].itsasontziaDa()&&jokalariMapa[x][y].bizirikDago()
								&&Math.pow(x-pX, 2)+Math.pow(y-pY, 2)<Math.pow(hurbilena,2)){
							hurbilena=Math.pow(x-pX, 2)+Math.pow(y-pY, 2);
							koord[0]=x;
							koord[1]=y;
						}
					}
				}
			}
		}
		return koord;
	}

	public int getErrenkada() {
		return erren;
	}
	public int getZutabe() {
		return zut;
	}

	public boolean itsasontziaDa(int x, int y) {
		return jokalariMapa[x][y].itsasontziaDa();
	}

}