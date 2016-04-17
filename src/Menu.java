import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

	private JPanel texteJouer1;
	private JPanel texteJouer2;
	private JPanel descJoueur1;
	private JPanel nbPersos1;
	private JPanel descJoueur2;
	private JPanel nbPersos2;
	
	private JPanel[] inputPersos;
	private JButton[] boutonMoins;
	private JButton[] boutonPlus;
	private JTextField[] input;	
	
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
		c.insets = new Insets(0, 0, 5, 0);
		frame.add(typeJeuPanel,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(5, 0,-5, 0);
		frame.add(choix,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(5, 0, 5, 0);
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
		
		texteJouer1 = new JPanel();
		texteJouer1.add(new JLabel("Joueur 1"));
		texteJouer2 = new JPanel();
		texteJouer2.add(new JLabel("Joueur 2"));
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		c.insets = new Insets(5, 10, 0, 0);
		frame.add(texteJouer1,c);
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 2;
		c.insets = new Insets(5, 0, 0, 5);
		frame.add(texteJouer2,c);
		
		// JButton et JTextField des types de persos

		nbPersos1 = new JPanel();
		nbPersos1.setLayout(new BoxLayout(nbPersos1, BoxLayout.Y_AXIS));
		nbPersos2 = new JPanel();
		nbPersos2.setLayout(new BoxLayout(nbPersos2, BoxLayout.Y_AXIS));
		// Identification du type de personnage
		descJoueur1 = new JPanel();
		descJoueur1.setLayout(new BoxLayout(descJoueur1, BoxLayout.Y_AXIS));
		descJoueur2 = new JPanel();
		descJoueur2.setLayout(new BoxLayout(descJoueur2, BoxLayout.Y_AXIS));
		
		// Il y a 8 panes associés chacun à 16 boutons et 8 fields
		// les 4 premiers sont pour le joueur 1 et les 4 derniers pour le joueur 2
		inputPersos = new JPanel[8];
		boutonMoins = new JButton[8];
		boutonPlus = new JButton[8];
		input = new JTextField[8];
		for (int i=0;i<boutonMoins.length;i++) {
			inputPersos[i] = new JPanel();
			boutonMoins[i] = new JButton(" - ");
			boutonMoins[i].setActionCommand("moins_" +i);
			boutonMoins[i].addActionListener(new buttonAction());
			boutonPlus[i] = new JButton(" + ");
			boutonPlus[i].setActionCommand("plus_" + i);
			boutonPlus[i].addActionListener(new buttonAction());
			input[i] = new JTextField();
			input[i].setHorizontalAlignment(JTextField.CENTER);
			input[i].setText("" + 0);
			input[i].setPreferredSize(new Dimension(30, 25));
		}
		for (int i=0; i<boutonMoins.length/2;i++) {
			inputPersos[i].add(boutonMoins[i]);
			inputPersos[i].add(input[i]);
			inputPersos[i].add(boutonPlus[i]);
		}
		for (int i=boutonMoins.length/2; i<boutonMoins.length;i++) {
			inputPersos[i].add(boutonMoins[i]);
			inputPersos[i].add(input[i]);
			inputPersos[i].add(boutonPlus[i]);
		}
		for (int i=0;i<inputPersos.length/2;i++) {
			nbPersos1.add(inputPersos[i]);
		}
		for (int i=inputPersos.length/2;i<inputPersos.length;i++) {
			System.out.println(i);
			nbPersos2.add(inputPersos[i]);
		}
		descJoueur1.add(new JLabel("Nombre d'Explorateurs : "));
		descJoueur1.add(Box.createRigidArea(new Dimension(descJoueur1.getWidth(), 20)));
		descJoueur1.add(new JLabel("Nombre de Voleurs : "));
		descJoueur1.add(Box.createRigidArea(new Dimension(descJoueur1.getWidth(), 20)));
		descJoueur1.add(new JLabel("Nombre de Guerriers : "));
		descJoueur1.add(Box.createRigidArea(new Dimension(descJoueur1.getWidth(), 20)));
		descJoueur1.add(new JLabel("Nombre de Piégueurs : "));
		descJoueur2.add(new JLabel("Nombre d'Explorateurs : "));
		descJoueur2.add(Box.createRigidArea(new Dimension(descJoueur2.getWidth(), 20)));
		descJoueur2.add(new JLabel("Nombre de Voleurs : "));
		descJoueur2.add(Box.createRigidArea(new Dimension(descJoueur2.getWidth(), 20)));
		descJoueur2.add(new JLabel("Nombre de Guerriers : "));
		descJoueur2.add(Box.createRigidArea(new Dimension(descJoueur2.getWidth(), 20)));
		descJoueur2.add(new JLabel("Nombre de Piégueurs : "));
		
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.insets = new Insets(5, 10, 0, 0);
		frame.add(descJoueur1,c);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 0, 5);
		frame.add(nbPersos1,c);
		c.gridx = 2;
		c.gridy = 4;
		c.gridwidth = 1;
		c.insets = new Insets(5, 5, 0, 0);
		frame.add(descJoueur2,c);
		c.gridx = 3;
		c.gridy = 4;
		c.gridwidth = 1;
		c.insets = new Insets(5, 0, 5, 10);
		frame.add(nbPersos2,c);
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 4;
		c.insets = new Insets(5, 0, 0, 0);
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
			} else if (e.getActionCommand().startsWith("moins_")) {
				for (int i=0;i<boutonMoins.length;i++) {
					if (e.getActionCommand().endsWith("_"+i) && Integer.valueOf(input[i].getText()) > 0) {
						input[i].setText(""+ (Integer.valueOf(input[i].getText()) -1) );
					}
				}
			} else if (e.getActionCommand().startsWith("plus_")) {
				for (int i=0;i<boutonPlus.length;i++) {
					if (e.getActionCommand().endsWith("_"+i)) {
						input[i].setText(""+ (Integer.valueOf(input[i].getText()) +1) );
					}
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
	/**
	 * Les numéros des personnages vont de 0 à 7 pour les deux joueurs
	 * ex pour joueur 1 : 0 = explorateur, 1 = voleur, 2 = guerrier, 3 = piégeur
	 * @param i le numéro du personnage 
	 * @return le nombre de personnages souhaités pour ce type
	 */
	public int getNbPersos(int i) {
		return Integer.valueOf(input[i].getText());
	}
}
