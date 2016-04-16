import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Menu {

	private JFrame frame;
	private JPanel typeJeuPanel;
	private JPanel choix;
	
	private JButton humain;
	private JButton ordi;
	private boolean versusOrdi;
	
	private JLabel tailleLabel;
	private JTextField tailleField;
	private JLabel rochersLabel;
	private JTextField rochersField;
	private String taille;
	private String rochers;
	
	private JPanel confirmation;
	private JButton validerButton;
	private boolean confirme;
	private boolean choixValides;
	
	private JPanel messagePane;
	private JLabel messages;
	
	private Dimension screenSize;
	private int maxHeight;
	
	
	private JPanel texteJoueur1;
	private JPanel nbPersos1;
	private JPanel texteJoueur2;
	private JPanel nbPersos2;
	private JPanel exp1;
	private int nbExp1;
	private JButton exp1Moins;
	private JButton exp1Plus;
	private JTextField nbExp1Field;
	private JPanel vol1;
	private int nbVol1;
	private JButton vol1Moins;
	private JButton vol1Plus;
	private JTextField nbVol1Field;
	private JPanel gue1;
	private int nbGuer1;
	private JButton guerrier1Moins;
	private JButton guerrier1Plus;
	private JTextField nbGue1Field;
	private JPanel pie1;
	private int nbPieg1;
	private JButton piegeur1Moins;
	private JButton piegeur1Plus;
	private JTextField nbPie1Field;

	private JPanel exp2;
	private int nbExp2;
	private JButton exp2Moins;
	private JButton exp2Plus;
	private JTextField nbExp2Field;
	private JPanel vol2;
	private int nbVol2;
	private JButton vol2Moins;
	private JButton vol2Plus;
	private JTextField nbVol2Field;
	private JPanel gue2;
	private int nbGuer2;
	private JButton guerrier2Moins;
	private JButton guerrier2Plus;
	private JTextField nbGue2Field;
	private JPanel pie2;
	private int nbPieg2;
	private JButton piegeur2Moins;
	private JButton piegeur2Plus;
	private JTextField nbPie2Field;
	
	
	/**
	 * Constructeur du menu. Crée tout le JFrame et tous les JPanels, JButtons...
	 */
	public Menu() {
		// frame et panels
		frame = new JFrame("Treasure Hunt");
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		typeJeuPanel = new JPanel();
		typeJeuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		choix = new JPanel();
		choix.setLayout(new FlowLayout(FlowLayout.CENTER));
		messagePane = new JPanel();
		messagePane.setPreferredSize(new Dimension(frame.getWidth(), 20));
		confirmation = new JPanel();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		frame.add(typeJeuPanel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		frame.add(choix,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		frame.add(messagePane,c);
		
		// Choix type de jeu
		humain = new JButton("1 v 1");
		humain.setActionCommand("1v1");
		humain.addActionListener(new buttonAction());
		
		ordi = new JButton("1 v Ordinateur");
		ordi.setActionCommand("ordi");
		ordi.addActionListener(new buttonAction());
		
		typeJeuPanel.add(humain);
		typeJeuPanel.add(ordi);
		versusOrdi = false;
		
		// Choix taille et rochers
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// tout le plateau doit etre visible à l'écran, on limite alors sa taille
		// 50 par case, -3 car la console prend la hauteur de 3 cases
		maxHeight = (int) (screenSize.getHeight()/50) -2;
		
		tailleLabel = new JLabel("Taile de l'île ? (max: " + maxHeight + ") ");
		tailleField = new JTextField();
		tailleField.setPreferredSize(new Dimension(50, 25));
		tailleField.setActionCommand("taille");
		tailleField.addActionListener(new fieldAction());
		tailleField.getDocument().addDocumentListener(new fieldTypeAction());
		
		rochersLabel = new JLabel("Quel Pourcentage de rochers ? (0 - 40%)");
		rochersField = new JTextField();
		rochersField.setPreferredSize(new Dimension(50, 25));
		rochersField.setActionCommand("rochers");
		rochersField.addActionListener(new fieldAction());
		rochersField.getDocument().addDocumentListener(new fieldTypeAction());
		taille = "";
		rochers = "";
		choix.add(tailleLabel);
		choix.add(tailleField);
		choix.add(rochersLabel);
		choix.add(rochersField);
		
		// nombre de persos

		nbPersos1 = new JPanel();
		nbPersos1.setLayout(new BoxLayout(nbPersos1, BoxLayout.Y_AXIS));
		nbPersos2 = new JPanel();
		nbPersos2.setLayout(new BoxLayout(nbPersos2, BoxLayout.Y_AXIS));
		texteJoueur1 = new JPanel();
		texteJoueur1.setLayout(new BoxLayout(texteJoueur1, BoxLayout.Y_AXIS));
		texteJoueur2 = new JPanel();
		texteJoueur2.setLayout(new BoxLayout(texteJoueur2, BoxLayout.Y_AXIS));
		
		nbExp1 = 0;
		exp1Moins = new JButton(" - ");
		exp1Plus = new JButton(" + ");
		exp1Moins.setActionCommand("e1_moins");
		exp1Plus.setActionCommand("e1_plus");
		nbExp1Field = new JTextField(nbExp1);
		nbExp1Field.setPreferredSize(new Dimension(30, 25));
		exp1 = new JPanel();
		exp1.add(exp1Moins);
		exp1.add(nbExp1Field);
		exp1.add(exp1Plus);
		
		nbVol1 = 0;
		vol1Moins = new JButton(" - ");
		vol1Plus = new JButton(" + ");
		vol1Moins.setActionCommand("v1_moins");
		vol1Plus.setActionCommand("v1_plus");
		nbVol1Field = new JTextField(nbVol1);
		nbVol1Field.setPreferredSize(new Dimension(30, 25));
		vol1 = new JPanel();
		vol1.add(vol1Moins);
		vol1.add(nbVol1Field);
		vol1.add(vol1Plus);
		
		nbGuer1 = 0;
		guerrier1Moins = new JButton(" - ");
		guerrier1Plus = new JButton(" + ");
		guerrier1Moins.setActionCommand("g1_moins");
		guerrier1Plus.setActionCommand("g1_plus");
		nbGue1Field = new JTextField(nbGuer1);
		nbGue1Field.setPreferredSize(new Dimension(30, 25));
		gue1 = new JPanel();
		gue1.add(guerrier1Moins);
		gue1.add(nbGue1Field);
		gue1.add(guerrier1Plus);
		
		nbPieg1 = 0;
		piegeur1Moins = new JButton(" - ");
		piegeur1Plus = new JButton(" + ");
		piegeur1Moins.setActionCommand("p1_moins");
		piegeur1Plus.setActionCommand("p1_plus");
		nbPie1Field = new JTextField(nbPieg1);
		nbPie1Field.setPreferredSize(new Dimension(30, 25));
		pie1 = new JPanel();
		pie1.add(piegeur1Moins);
		pie1.add(nbPie1Field);
		pie1.add(piegeur1Plus);
		
		nbExp2 = 0;
		exp2Moins = new JButton(" - ");
		exp2Plus = new JButton(" + ");
		exp2Moins.setActionCommand("e2_moins");
		exp2Plus.setActionCommand("e2_plus");
		nbExp2Field = new JTextField(nbExp2);
		nbExp2Field.setPreferredSize(new Dimension(30, 25));
		exp2 = new JPanel();
		exp2.add(exp2Moins);
		exp2.add(nbExp2Field);
		exp2.add(exp2Plus);
		
		nbVol2 = 0;
		vol2Moins = new JButton(" - ");
		vol2Plus = new JButton(" + ");
		vol2Moins.setActionCommand("v2_moins");
		vol2Plus.setActionCommand("v2_plus");
		nbVol2Field = new JTextField(nbVol2);
		nbVol2Field.setPreferredSize(new Dimension(30, 25));
		vol2 = new JPanel();
		vol2.add(vol2Moins);
		vol2.add(nbVol2Field);
		vol2.add(vol2Plus);
		
		nbGuer2 = 0;
		guerrier2Moins = new JButton(" - ");
		guerrier2Plus = new JButton(" + ");
		guerrier2Moins.setActionCommand("g2_moins");
		guerrier2Plus.setActionCommand("g2_plus");
		nbGue2Field = new JTextField(nbGuer2);
		nbGue2Field.setPreferredSize(new Dimension(30, 25));
		gue2 = new JPanel();
		gue2.add(guerrier2Moins);
		gue2.add(nbGue2Field);
		gue2.add(guerrier2Plus);
		
		nbPieg2 = 0;
		piegeur2Moins = new JButton(" - ");
		piegeur2Plus = new JButton(" + ");
		piegeur2Moins.setActionCommand("p2_moins");
		piegeur2Plus.setActionCommand("p2_plus");
		nbPie2Field = new JTextField(nbPieg2);
		nbPie2Field.setPreferredSize(new Dimension(30, 25));
		pie2 = new JPanel();
		pie2.add(piegeur2Moins);
		pie2.add(nbPie2Field);
		pie2.add(piegeur2Plus);

		nbPersos1.add(exp1);
		nbPersos1.add(vol1);
		nbPersos1.add(gue1);
		nbPersos1.add(pie1);
		nbPersos2.add(exp2);
		nbPersos2.add(vol2);
		nbPersos2.add(gue2);
		nbPersos2.add(pie2);
		texteJoueur1.add(new JLabel("Nombre d'Explorateurs : "));
		texteJoueur1.add(Box.createRigidArea(new Dimension(texteJoueur1.getWidth(), 20)));
		texteJoueur1.add(new JLabel("Nombre de Voleurs : "));
		texteJoueur1.add(Box.createRigidArea(new Dimension(texteJoueur1.getWidth(), 20)));
		texteJoueur1.add(new JLabel("Nombre de Guerriers : "));
		texteJoueur1.add(Box.createRigidArea(new Dimension(texteJoueur1.getWidth(), 20)));
		texteJoueur1.add(new JLabel("Nombre de Piégueurs : "));
		texteJoueur2.add(new JLabel("Nombre d'Explorateurs : "));
		texteJoueur2.add(Box.createRigidArea(new Dimension(texteJoueur2.getWidth(), 20)));
		texteJoueur2.add(new JLabel("Nombre de Voleurs : "));
		texteJoueur2.add(Box.createRigidArea(new Dimension(texteJoueur2.getWidth(), 20)));
		texteJoueur2.add(new JLabel("Nombre de Guerriers : "));
		texteJoueur2.add(Box.createRigidArea(new Dimension(texteJoueur2.getWidth(), 20)));
		texteJoueur2.add(new JLabel("Nombre de Piégueurs : "));
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		frame.add(texteJoueur1,c);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		frame.add(nbPersos1,c);
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		frame.add(texteJoueur2,c);
		c.gridx = 3;
		c.gridy = 3;
		c.gridwidth = 1;
		frame.add(nbPersos2,c);
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 4;
		frame.add(confirmation,c);
		
		// messages
		messages = new JLabel();
		messagePane.add(messages);
		
		// validation des choix
		validerButton = new JButton("Validation");
		validerButton.setActionCommand("valider");
		validerButton.addActionListener(new buttonAction());
		confirmation.add(validerButton);
		confirme = false;
		choixValides = true;
	}
	/**
	 * Remet en place le menu et le rend visible
	 */
	public void affichage() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.repaint();
		frame.requestFocusInWindow();
	}
	/**
	 * Provoque le masquage du menu.
	 * Le menu est conservé en mémoire et peut être réaffiché par {@link #affichage()}.
	 */
	public void masquer() {
		frame.setVisible(false);
	}
	/**
	 * {@link ActionListener} propre aux actions faites dans les JButton
	 * @author Christopher Caroni
	 *
	 */
	private class buttonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ("1v1".equals(e.getActionCommand()) ) {
				humain.setEnabled(false);
				ordi.setEnabled(false);
				versusOrdi = false;
			} else if ("ordi".equals(e.getActionCommand())) {
				humain.setEnabled(false);
				ordi.setEnabled(false);
				versusOrdi = true;
			} else if ("valider".equals(e.getActionCommand())) {
				choixValides = true;
				taille = tailleField.getText();
				rochers = rochersField.getText();
				if (getTaille() < 10) {
					JOptionPane.showMessageDialog(null, "L'île est trop petite");
					tailleField.setText("");
					choixValides = false;
				} else if (getTaille() > maxHeight) {
					JOptionPane.showMessageDialog(null, "L'île est trop grande pour jouer sur votre écran");
					tailleField.setText("");
					choixValides = false;
				} 
				if (getRochers() < 0) {
					JOptionPane.showMessageDialog(null, "Le pourcentage de rochers voulue est impossible");
					rochersField.setText("");
					choixValides = false;
				} else if (getRochers() > 40) {
					JOptionPane.showMessageDialog(null, "Le pourcentage de rochers est trop grand pour pouvoir jouer");
					rochersField.setText("");
					choixValides = false;
				}
				if (choixValides) {
					// on a besoin d'un boolean confirme car dans map, on boucle while tant que !confirme
					// choixValides est vrai par defaut donc elle ne fonctionnerait pas dans la boucle
					confirme = true;
					masquer();
				}
			}
		}
	}
	/**
	 * {@link ActionListener} propre aux actions faites aux JTextField
	 * @author Christopher Caroni
	 *
	 */
	private class fieldAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if ("taille".equals(e.getActionCommand())) {
				taille = tailleField.getText();
				if (getTaille() < 10) {
					messages.setText("L'île est trop petite");
				} else if (getTaille() > maxHeight) {
					messages.setText("L'île est trop grande pour jouer sur votre écran");
				} else {
					messages.setText("");
					rochersField.requestFocusInWindow();
				}
			} else if ("rochers".equals(e.getActionCommand())) {
				rochers = rochersField.getText();
				if (getRochers() < 0) {
					messages.setText("Le pourcentage de rochers voulue est impossible");
				} else if (getRochers() > 40) {
					messages.setText("Le pourcentage de rochers est trop grand pour pouvoir jouer");
				} else {
					messages.setText("");
					validerButton.requestFocusInWindow();
				}
			}
		}
	}
	/**
	 * {@link DocumentListener} propre aux text dans JTextField
	 * @author Christopher Caroni
	 *
	 */
	private class fieldTypeAction implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent e) {
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			if (tailleField.hasFocus()) {
				taille = tailleField.getText();
				if (getTaille() < 10) {
					messages.setText("L'île est trop petite");
				} else if (getTaille() > maxHeight) {
					messages.setText("L'île est trop grande pour jouer sur votre écran");
				} else {
					messages.setText("");
				}
			} else if (rochersField.hasFocus()) {
				rochers = rochersField.getText();
				if (getRochers() < 0) {
					messages.setText("Le pourcentage de rochers voulue est impossible");
				} else if (getRochers() > 40) {
					messages.setText("Le pourcentage de rochers est trop grand pour pouvoir jouer");
				} else {
					messages.setText("");
				}
			}
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
		}
	}
	/**
	 * 
	 * @return la valeur int de la taille souhaitée pour l'île
	 */
	public  int getTaille() {
		if (!taille.equals("")) {
			return Integer.valueOf(taille);
		}
		return -1;
	}
	/**
	 * 
	 * @return la valeur int du pourcentage souhaité de rochers
	 */
	public  int getRochers() {
		if (!rochers.equals("")) {
			return Integer.valueOf(rochers);
		}
		return -1;
	}
	/**
	 * 
	 * @return si on veut joueur contre l'ordinateur
	 */
	public boolean getVersusOrdi() {
		return versusOrdi;
	}
	/**
	 * 
	 * @return si le joueur a confirme ses choix de jeu
	 */
	public boolean getConfirme() {
		return confirme;
	}
	
	public void waitValidation(int timeout) {
		frame.requestFocusInWindow();
		int time = 0;
		while (!getConfirme() && (time < timeout)) {
			try {
				Thread.sleep(100) ;	// Cette instruction - en plus du délai induit - permet à Swing de traiter les événements GUI 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			time += 100 ;
		}
	}
	public int getNbExp1() {
		return nbExp1;
	}
	public int getNbVol1() {
		return nbVol1;
	}
	public int getNbGuer1() {
		return nbGuer1;
	}
	public int getNbPieg1() {
		return nbPieg1;
	}
	public int getNbExp2() {
		return nbExp2;
	}
	public int getNbVol2() {
		return nbVol2;
	}
	public int getNbGuer2() {
		return nbGuer2;
	}
	public int getNbPieg2() {
		return nbPieg2;
	}

}
