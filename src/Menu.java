import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
	
	/**
	 * Constructeur du menu. Crée tout le JFrame et tous les JPanels, JButtons...
	 */
	public Menu() {
		// frame et panels
		frame = new JFrame("Treasure Hunt");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		typeJeuPanel = new JPanel();
		typeJeuPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		choix = new JPanel();
		choix.setLayout(new FlowLayout(FlowLayout.CENTER));
		confirmation = new JPanel();
		messagePane = new JPanel();
		messagePane.setPreferredSize(new Dimension(frame.getWidth(), 20));
		frame.add(typeJeuPanel);
		frame.add(Box.createRigidArea(new Dimension(frame.getWidth(),20)));
		frame.add(choix);
		frame.add(messagePane);
		frame.add(confirmation);
		
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
		
		// messages
		messages = new JLabel();
	//	messages.setPreferredSize(new Dimension(messagePane.getWidth(), messagePane.getHeight()));
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

}
