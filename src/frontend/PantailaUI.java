package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import properties.Hizkuntza;

public class PantailaUI extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private TableroaUI aurk_tableroa,jok_tableroa;
	private DendaUI denda;
	private LogUI log;
	private InbentarioaUI inbentarioa;
	private int[] egoera;
	private String jokalaria,aurkaria;
	private JPanel tableroak, dendaP, inbentarioaP,dendaInbP,logP,top;
	private String hizk;
	private Hizkuntza h;
	
	public PantailaUI(String pJokalaria,String pAurkaria,  int[] pEgoera, String hikuntza) {
		hizk =hikuntza;
		h = new Hizkuntza(hizk);
		jokalaria=pJokalaria;
		egoera=pEgoera;
		aurkaria=pAurkaria;
		
		pantailaAktualizatu();
	}
	
	private void pantailaAktualizatu() {
		this.setLayout(new BorderLayout());
		this.setTableroak();
		this.setLoga();
		this.setDendaInbP();
		this.setTop();

		this.add(dendaInbP,BorderLayout.WEST);
		this.add(tableroak, BorderLayout.CENTER);
		this.add(logP, BorderLayout.EAST);
		this.add(top,BorderLayout.PAGE_START);
	}
	private void setTop() {
		top= new JPanel();
		String titulua=""+egoera[0];
		switch (egoera[0]){
		case 0: titulua="ErosiFasea";
			break;
		case 1: titulua="EkipoFasea";
			break;
		case 2: titulua="ErasoFasea";
			break;
		}
		JButton tituluaJ = new JButton(h.getProperty(titulua));
		top.setBorder(new TitledBorder(new LineBorder(Color.CYAN),h.getProperty("iraupena") + ": ("+egoera[2]+") " + h.getProperty(jokalaria)));
		JButton nora2 = new JButton(new ImageIcon(TableroaUI.class.getResource(norabideaLortu())));
		JButton atzera = new JButton(h.getProperty("atzera"));

		tituluaJ.setToolTipText("Txanda pasatu");
		tituluaJ.setName("Txanda pasatu");
		nora2.setName("Norabidea");
		atzera.setName("Atzera");
		tituluaJ.addActionListener(this);
		nora2.addActionListener(this);
		atzera.addActionListener(this);
		top.setPreferredSize(new Dimension(UrriGorriaUI.getLeihoaW(),UrriGorriaUI.getLeihoaH()*12/100));
		top.add(tituluaJ, BorderLayout.WEST);
		top.add(nora2);
		top.add(atzera);
	}

	private String norabideaLortu() {
		String irudi;
		switch (UrriGorriaUI.norabideaLortu()){
			case 0: irudi="/externals/eki.png";
				break;
			case 1: irudi="/externals/hego.png";
				break;
			case 2: irudi="/externals/mend.png";
				break;
			case 3: irudi="/externals/ipar.png";
				break;
			default: irudi="/externals/eki.png";
				break;
		}
		return irudi;
	}

	private void setDendaInbP(){
		dendaInbP=new JPanel();
		dendaInbP.setLayout(new GridLayout(2, 1));
		
        
		dendaP = new JPanel();
		denda = new DendaUI(jokalaria,(egoera[0]==0? true:false), hizk);
		dendaP.add(denda);
		
		inbentarioaP = new JPanel();
		inbentarioa = new InbentarioaUI(jokalaria,egoera[0], hizk);
		inbentarioaP.add(inbentarioa);
		
		if(egoera[0]!=0){
			inbentarioaP.setEnabled(false);
		}

		dendaInbP.add(dendaP);
		dendaInbP.add(inbentarioaP);
	}
	private void setTableroak() {
		tableroak = new JPanel();
		tableroak.setLayout(new GridLayout(2, 1));
		jok_tableroa = new TableroaUI(jokalaria, Color.RED, hizk);
		aurk_tableroa = new TableroaUI(aurkaria, Color.RED, hizk);
		tableroak.add(aurk_tableroa);
		tableroak.add(jok_tableroa);
	}
	

	private void setLoga() {
		logP = new JPanel();
		log = new LogUI(jokalaria, hizk);
		logP.add(log);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(((Component) e.getSource()).getName().equals("Atzera")){
			UrriGorriaUI.getUrriGorriaUI().komandoaAtzera();
		}
		if(((Component) e.getSource()).getName().equals("Norabidea")){
			UrriGorriaUI.getUrriGorriaUI().norabideaAldatu();
		}
		if(((Component) e.getSource()).getName().equals("Txanda pasatu")){
			UrriGorriaUI.getUrriGorriaUI().komandoaEgikaritu(jokalaria, "CommandTxandaPasa", new String[3]);
		}
		UrriGorriaUI.getUrriGorriaUI().panelaAktualizatu();
	}

}