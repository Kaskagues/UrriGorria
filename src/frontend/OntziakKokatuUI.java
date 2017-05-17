package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OntziakKokatuUI extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private TableroaUI jok_tableroa;
	private InbentarioaUI inb;
	private JPanel top;

	public OntziakKokatuUI(String jokalaria, Color c) {
		this.setLayout(new BorderLayout());
		jok_tableroa = new TableroaUI(jokalaria, c);
		this.add(jok_tableroa, BorderLayout.CENTER);
		top= new JPanel();
		inb = new InbentarioaUI(jokalaria,0);
		JButton nora = new JButton(new ImageIcon(TableroaUI.class.getResource(norabideaLortu())));
		nora.setName("Norabidea");
		nora.addActionListener(this);
		JButton atzera = new JButton("Atzera");
		atzera.setName("Atzera");
		atzera.addActionListener(this);
		this.add(inb, BorderLayout.WEST);
		top.add(nora);
		top.add(atzera);
		this.add(top,BorderLayout.PAGE_START);
		JButton jarraitu = new JButton("Jarraitu");
		jarraitu.setName("Jarraitu");
		jarraitu.addActionListener(this);
		this.add(jarraitu, BorderLayout.EAST);
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(((JButton)e.getSource()).getName().equals("Jarraitu")){
			UrriGorriaUI.getUrriGorriaUI().faseaAldatu();
		}
		if(((JButton)e.getSource()).getName().equals("Norabidea")){
			UrriGorriaUI.getUrriGorriaUI().norabideaAldatu();
		}
		if(((Component) e.getSource()).getName().equals("Atzera")){
			UrriGorriaUI.getUrriGorriaUI().komandoaAtzera();
		}
		UrriGorriaUI.getUrriGorriaUI().panelaAktualizatu();
	}
}
