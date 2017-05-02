package strategyak;

import negozioLogika.Mapa;

public class ErasoLineal implements StrategyArmak{
	public ErasoLineal(){
	}
	public Mapa eraso(String pNork,Mapa pMapa, int pX, int pY, char pNorabide, int pIndarra, boolean pZer){
		int ind=0;
		ErasoSinple er=new ErasoSinple();
		if(pNorabide=='E'||pNorabide=='W'){
			while(pMapa.erasoSinpleaJaso(pNork, ind, pY, pIndarra, pZer)){
				ind++;
			}
		}
		else{
			while(pMapa.erasoSinpleaJaso(pNork, pX, ind, pIndarra, pZer)){
				ind++;
			}
		}
		return pMapa;
	}

}