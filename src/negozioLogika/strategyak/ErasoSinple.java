package negozioLogika.strategyak;

import negozioLogika.Mapa;

public class ErasoSinple implements StrategyArmak{
	public ErasoSinple(){
	}
	public void eraso(String pNork,Mapa pMapa, int pX, int pY, char pNorabide, int pIndarra, boolean pZer){
		pMapa.erasoSinpleaJaso(pNork, pX, pY, pIndarra, pZer);
	}
	public boolean konprobatu(Mapa pMapa,int pX, int pY){
		return pMapa.erasoSinpleaKonprobatu(pX, pY);
	}
}
