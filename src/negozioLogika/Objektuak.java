package negozioLogika;

public abstract class Objektuak {
	protected String izena;
	
	public Objektuak(String pIzena){
		this.izena=pIzena;
	}
	public boolean izenBerdina (String pObjektua){
		return this.izena==pObjektua;
	}
}
