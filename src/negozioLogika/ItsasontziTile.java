package negozioLogika;

public class ItsasontziTile extends Tile {
	
	private Itsasontzia itsasontzi;
	private int bizitza=100;
	
	public ItsasontziTile(String pJabea, int pX, int pY, Itsasontzia pItsasontzia) {
		// TODO Auto-generated constructor stub
		super(pJabea,pX, pY);
		itsasontzi = pItsasontzia;
		super.kokatuAhalDa=false;
		super.identifikadorea="Itsasontzi";
	}
	
	public void bizitzaAldatu(int pIndarra, boolean pZer){
		if(this.suntsitutaDago()) bizitza=0;
		else if(pZer){
			if(itsasontzi.ezkutuaDauka()||this.suntsitutaDago()){
				itsasontzi.ezkutuariJo(pIndarra, pZer);
			}
			else bizitza-=pIndarra;
		}
		else{
			if (bizitza<100) bizitza=100;
			else itsasontzi.ezkutuariJo(pIndarra, pZer);
		}
	}
	public boolean suntsitutaDago(){
		return bizitza<=0;
	}
	public void ezkutuaJarri(boolean pZer){
		itsasontzi.ezkutuaJarri(pZer);
	}
	
}