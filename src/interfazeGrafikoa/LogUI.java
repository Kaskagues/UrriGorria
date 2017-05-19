package interfazeGrafikoa;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.html.parser.Parser; //<br> a jartzeko, hau da bi lerroko JButtonak

import interfazeGrafikoa.properties.Hizkuntza;

@SuppressWarnings("unused")
public class LogUI extends JPanel{

	private static final long serialVersionUID = 1L;
	private String jokalaria=null;
	private JButton[] BattleLoga;
	private ArrayList<String> loga = new ArrayList<>();
	private Hizkuntza h;
	private int komandoKop;


	public LogUI(String pIzena, String hizkuntza) {
		h = new Hizkuntza(hizkuntza);
		komandoKop=0;
		jokalaria=pIzena;
		loga = UrriGorriaUI.getUrriGorriaUI().logaEman(jokalaria);
		this.setLayout(new GridLayout(16, 1));
		this.setBorder(BorderFactory.createTitledBorder("Log:"));
		this.setPreferredSize(new Dimension(250, UrriGorriaUI.getLeihoaH()-150));
		this.logaAktualizatu();
	}
	public void logaAktualizatu(){
		BattleLoga = new JButton[loga.size()];
		for(int i=(loga.size()-16 <0 ? 0 : loga.size()-16); i<loga.size(); i++){
			BattleLoga[i] = new JButton();
			BattleLoga[i].setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
			String info=loga.get(i);
			BattleLoga[i].setName(i+"");
			
			info=info.split("#")[1];
			String jok=h.getProperty(info.split("'")[1]);
			String kom=" "+h.getProperty(info.split("'")[2]);
			String obj=(!info.split("'")[3].equals("Ezer")?
					" ("+h.getProperty(info.split("'")[3])+")" :" ");
			BattleLoga[i].setText("<html>" +info.split("'")[0]+jok+"<br>"+kom+obj +"</html>");
			BattleLoga[i].addActionListener(e->
				UrriGorriaUI.getUrriGorriaUI().komandoaAtzera(komandoKop-Integer.parseInt((
							(Component) e.getSource()).getName())));
			komandoKop++;
			this.add(BattleLoga[i]);
		}
	}
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		int zenbatgarrena=);
//		
//	}
}