package interfazeGrafikoa;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import interfazeGrafikoa.properties.Hizkuntza;
import negozioLogika.Partida;

public class MenuBarra extends JMenuBar {

	private static final long serialVersionUID = 1L;
	private JMenu jokoa,hizkuntza,jokalariMenu;
	private JMenuItem[] hizkBat;
	private String[] hizkuntzak;
	private Hizkuntza h;
	
	public MenuBarra() {
		hizkuntzak= new String[3];
		hizkuntzak[0]="Euskera";hizkuntzak[1]="Castellano";hizkuntzak[2]="English";
		hizkBat= new JMenuItem[hizkuntzak.length];
		erreseteatu("Euskera",false);
	}
	public void erreseteatu(String pHizkunta,boolean pJokalariakJarri){
		h = new Hizkuntza(pHizkunta);
		this.removeAll();
		this.jokoa();
		this.hizkuntzaEraiki();
		if(pJokalariakJarri)this.jokalariaJarri();
		this.revalidate();
	}
	private void jokoa() {
		jokoa = new JMenu(h.getProperty("jokoa"));
		JMenuItem erreseteatu = new JMenuItem(h.getProperty("Berriro"));
		erreseteatu.addActionListener(gureAE -> UrriGorriaUI.getUrriGorriaUI().berriroHasi());
		jokoa.add(erreseteatu);
		this.add(jokoa);
	}
	private void hizkuntzaEraiki() {
		hizkuntza = new JMenu(h.getProperty("hizkuntza"));
		int i=0;
		for(String hiz:hizkuntzak){
			hizkBat[i] = new JMenuItem(hiz);
			hizkBat[i].addActionListener(gureAE -> this.hizkuntzaAldatu(hiz));
			if(h.equals(hiz.toLowerCase()))
				hizkBat[i].setBackground(new Color(175, 255, 175));
			hizkuntza.add(hizkBat[i]);
			i++;
		}
		this.add(hizkuntza);
	}
	
	private void jokalariaJarri() {		
		jokalariMenu= new JMenu(h.getProperty("jokalariak"));
		
        String aurkaria=UrriGorriaUI.getUrriGorriaUI().getAurkaria();
        String[] jokalariak=Partida.getPartida().jokalarienIzenakEman();
        JMenuItem[] eMenuItem = new JMenuItem[jokalariak.length];
        String pNorenTxanda=UrriGorriaUI.getUrriGorriaUI().norenTxandaDaIzena();
        
        int ind=0;        
		for(String izena:jokalariak)
			if(izena!=null)
				if(!izena.equals(pNorenTxanda)){
					java.awt.Color c=UrriGorriaUI.getUrriGorriaUI().getKolorea(izena);

		     		eMenuItem[ind] = new JMenuItem(h.getIzena(izena));
		            eMenuItem[ind].setName(izena);
		            eMenuItem[ind].addActionListener(e->this.aurkariaAldatu(((JMenuItem)e.getSource()).getName()));
//		            if(aurkaria!=null)
//		            	if(eMenuItem[ind].getName().equals(aurkaria))
//		            		eMenuItem[ind].setEnabled(false);

		            eMenuItem[ind].setBackground(c);
		            eMenuItem[ind].setForeground(UrriGorriaUI.getUrriGorriaUI().getKolorKontraste(c));
		            jokalariMenu.add(eMenuItem[ind]);
		     	}
       

        if(aurkaria==null||aurkaria.equals(pNorenTxanda))	
        	if(eMenuItem[ind]!=null)this.aurkariaAldatu(eMenuItem[ind].getName());
        
        this.add(jokalariMenu);
		
	}

	private void aurkariaAldatu(String pIzen) {
		UrriGorriaUI.getUrriGorriaUI().setAurkaria(pIzen);
	}
	private void hizkuntzaAldatu(String pHiz) {
		UrriGorriaUI.getUrriGorriaUI().setHizkuntza(pHiz.toLowerCase());
		this.hizkuntza.setText(h.getProperty("hizkuntza"));
	}

}
