/**
 * Importation
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * La classe Plateau permet d'afficher un plateau de Jeu carré
 * sur lequel sont disposés des images représentant les éléments du jeu
 * Les images sont toutes de même taille et carrées. Optionellement, on peut y associer 
 * une zone d'affichage de texte et caturer les entrées (souris / clavier) de l'utilisateur.
 * @author Team J3
 */
public class Plateau {
	/**
	 * Attribut
	 */
	private static boolean defaultVisibility = true ;
	private static final long serialVersionUID = 1L;
	private JFrame window ;
	private GraphicPane graphic ;
	private ConsolePane console ;
	private JPanel PersoPane ;
	private int persoPrecis;
	private Color sable;
	
	private boolean peutVoler;
	private boolean dejaAjoutVol;
	private boolean peutEchangerClef;
	private boolean peutEchangerTresor;
	private boolean peutSeDeplacer;
	private boolean veutDeplacer;
	private boolean veutEchangerClef;
	private boolean veutEchangerTresor;
	
	private boolean dejaAjoutClef;
	private boolean dejaAjoutTresor;
	private boolean peutPieger;
	private boolean dejaAjoutPieger;

	private JButton pieger;
	private JButton voler;
	private JButton coffre;
	private JButton clef;
	private ImageIcon volerIcone;
	private ImageIcon clefIcone;
	private ImageIcon coffreIcone;
	private boolean dejaAjoutClefIcon;
	private boolean dejaAjoutCoffre;
	private JButton echangerClef;
	private JButton echangerTresor;
	private JButton annuler;
	private boolean annulerChoix;
	private boolean veutPieger;
	private boolean faitAction;
	private boolean clicAction;

	private int selectionListe;
	private int nbPersos;
	
	private boolean dejaAjoutAnnuler;
	private int savePerso;
	private JButton attaquer;
	private ImageIcon attaquerIcone;
	private boolean dejaAjoutAttaquer;
	private boolean veutAttaquer;
	private boolean veutVoler;
	private boolean peutAttaquer;
	private boolean detientClef;
	private boolean detientCoffre;
	
	private boolean aSelectionnePerso;
	private boolean confirmeSelection;
	private boolean confirmeSelectionPane;
	private boolean confirmeFinTour;
	private boolean attendFinTour;
	
	private List<JButton> listeBoutons;
	private List<Personnage> listePersos;
	private Personnage tempPersoSelectionne;
	private Position oldHighlight;
	private Position directionDeplacement;
	private int waitTime;
	
	/**
	 *  Attribut ou est enregistré un événement observé. Cet attribut est
	 * initialisé à null au début de la scrutation et rempli par l'événement observé 
	 * par les deux listeners (mouseListener et keyListener). 
	 * cf {@link java.awt.event.InputEvent}.
	 */
	private InputEvent currentEvent = null ;
	private MouseEvent mouse = null;
	/**
	 * Classe interne MouseListener. Quand instanciée et associée à un JPanel, elle
	 * répond à un événement souris en stockant cet événement dans l'attribut 
	 * {@link #currentEvent}.
	 * @author place
	 *
	 */
	private class Mouse implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			currentEvent = event ;
			mouse = event;
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {	}
		@Override
		public void mouseExited(MouseEvent arg0) { }
		@Override
		public void mousePressed(MouseEvent arg0) { }
		@Override
		public void mouseReleased(MouseEvent arg0) { }
	}
	/**
	 * Classe interne keyListener. Quand instanciée et associée à une JFrame, elle
	 * répond à un événement clavier en le stockant dans la variable {@link #currentEvent}.
	 * @author place
	 *
	 */
	private class Key implements KeyListener {
		@Override
		public void keyPressed(KeyEvent event) {
			currentEvent = event ;
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	/**
	 * Détermine la visibilité par défaut des plateaux construits. La valeur initiale est true : 
	 * tout plateau construit est immédiatement affiché.
	 * @param defaultValue vrai si tout plateau est affiché immédiatement 
	 */
	public static void setDefaultVisibility(boolean defaultValue) {
		defaultVisibility = defaultValue ;
	}
	
	/**
	 * Construit un plateau de jeu vide de dimension taille x taille.
	 * Initialement, les cellules sont vides. Le constructeur demande la fourniture
	 * d'un catalogue d'images gif ou png. La fermeture de la fenêtre provoque uniquement le
	 * masquage de celle-ci. La destruction complète doit être faite explicitement par programme via 
	 * la méthode {@link #close()}.
	 * @param gif tableau 1D des chemins des fichiers des différentes images affichées.
	 * @param taille dimension (en nombre de cellules) d'un côté du plateau.
	 */
	public Plateau(String[] gif,int taille){
		this(gif, taille, false) ;
	}
	/**
	 * Construit un plateau de jeu vide de dimension taille x taille aec une éventuelle zone de texte associée.
	 * Initialement, les cellules sont vides. Le constructeur demande la fourniture
	 * d'un catalogue d'images gif ou png.
	 * @param class1
	 * @param gif tableau 1D des chemins des fichiers des différentes images affichées.
	 * @param taille Dimension (en nombre de cellules) d'un côté du plateau.
	 *        <b>La taille fixée ici est la taille par défaut (plateau carré)</b> 
	 *        (gardé pour raison de compatibilité.
	 *        Le plateau s'ajustera en fonction des dimensions du tableau jeu.
	 * @param withTextArea Indique si une zone de texte doit être affichée.
	 */
	public Plateau(String[] gif,int taille, boolean withTextArea){
		// Instancie la fenetre principale et et les deux composants.
		window = new JFrame() ;
		ImageIcon[] images = loadImages(gif) ;
		graphic = new GraphicPane(images, taille) ;
		console = null ;
		PersoPane = new JPanel();
		PersoPane.setLayout(new GridLayout(2,3));
		PersoPane.setPreferredSize(new Dimension(200, 100));
		PersoPane.addKeyListener(new Keys());
		window.addKeyListener(new Keys());
		peutVoler = false;
		sable = new Color(239, 228, 176);

		// Instancie les composants de PersoPane
		volerIcone = new ImageIcon(Plateau.class.getResource("images/voler.png"));
		voler = new JButton(volerIcone);
		voler.setPreferredSize(new Dimension(volerIcone.getIconWidth(), volerIcone.getIconHeight()));
		voler.setActionCommand("voler");
		voler.addActionListener(new Action());
		
		ImageIcon piegerIcone = new ImageIcon(Plateau.class.getResource("images/pieger.png"));
		pieger = new JButton(piegerIcone);
		pieger.setPreferredSize(new Dimension(piegerIcone.getIconWidth(), piegerIcone.getIconHeight()));
		pieger.setActionCommand("pieger");
		pieger.addActionListener(new Action());
		
		ImageIcon clefIcone = new ImageIcon(Plateau.class.getResource("images/cle.jpg"));
		clef = new JButton(clefIcone);
		clef.setPreferredSize(new Dimension(clefIcone.getIconWidth(), clefIcone.getIconHeight()));
		
		ImageIcon coffreIcone = new ImageIcon(Plateau.class.getResource("images/tresor.png"));
		coffre = new JButton(coffreIcone);
		coffre.setPreferredSize(new Dimension(coffreIcone.getIconWidth(), coffreIcone.getIconHeight()));
		
		ImageIcon echangerClefIcone = new ImageIcon(Plateau.class.getResource("images/echanger_clef.png"));
		echangerClef = new JButton(echangerClefIcone);
		echangerClef.setPreferredSize(new Dimension(echangerClefIcone.getIconWidth(), echangerClefIcone.getIconHeight()));
		echangerClef.setActionCommand("echangerClef");
		echangerClef.addActionListener(new Action());

		ImageIcon echangerTresorIcone = new ImageIcon(Plateau.class.getResource("images/echanger_tresor2.png"));
		echangerTresor = new JButton(echangerTresorIcone);
		echangerTresor.setPreferredSize(new Dimension(echangerTresorIcone.getIconWidth(), echangerTresorIcone.getIconHeight()));
		echangerTresor.setActionCommand("echangerTresor");
		echangerTresor.addActionListener(new Action());
		
		ImageIcon attaquerIcone = new ImageIcon(Plateau.class.getResource("images/attaquer.png"));
		attaquer = new JButton(attaquerIcone);
		attaquer.setPreferredSize(new Dimension(attaquerIcone.getIconWidth(), attaquerIcone.getIconHeight()));
		attaquer.setActionCommand("attaquer");
		attaquer.addActionListener(new Action());
		
		listeBoutons = new ArrayList<>();
		
		// Caractéristiques initiales pour la fenetre.
		window.setTitle("Plateau de jeu ("+taille+"X"+taille+")");
		window.setLocationRelativeTo(null);
		window.setLayout(new BorderLayout());
		window.setResizable(false);
		window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Ajout des deux composants à la fenetre
		window.getContentPane().add(graphic, BorderLayout.NORTH);
		if (withTextArea) {
			console = new ConsolePane() ;
			window.getContentPane().add(console) ;
		}
				
		window.getContentPane().add(PersoPane,BorderLayout.EAST);
		resizeFromGraphic() ; // ajoute la console		
		window.setLocationRelativeTo(null); // a la fin sinon pas appliquée

		// Affichage effectif 
		window.setVisible(defaultVisibility);
		// Ajout des listeners.
		graphic.addMouseListener(new Mouse());
		window.addKeyListener(new Key()) ;
		PersoPane.addMouseListener(new Mouse());
		currentEvent = null ;
	}
	/**
	 * Méthode permettant de placer les éléments sur le plateau. Le tableau doit être  
	 * de même taille que la dimension déclarée via le constructeur.
	 * @param jeu tableau 2D représentant le plateau  
	 * la valeur numérique d'une cellule désigne l'image correspondante
	 * dans le tableau des chemins (décalé de un, 0 désigne une case vide)
	 */
	public void setJeu(int[][] jeu, boolean debut){
		graphic.setJeu(jeu, debut) ;	// Délégué au composant graphic.
		resizeFromGraphic() ;
	}
	/**
	 * Retourne le tableau d'entiers représentant le plateau
	 * @return le tableau d'entiers
	 */
	public int[][] getJeu(){
		return graphic.getJeu() ;	// Délégué au composant graphic.
	}
	/**
	 * Demande l'affichage de la fenetre entiere. 
	 * Si la fenêtre était cachée, elle redevient visible.
	 */
	public void affichage(){ 
		graphic.repaint();
		window.setVisible(true);	// Révèle la fenêtre.
		window.repaint(); 			// Délégué à Swing (appelle indirectement graphic.paintComponent via Swing)
	}
	/**
	 * Détermine le titre de la fenetre associée.
	 * @param title Le titre à afficher.
	 */
	public void setTitle(String title) {
		window.setTitle(title) ;
	}
	/**
	 * Provoque le masquage du plateau.
	 * Le plateau est conservé en mémoire et peut être réaffiché par {@link #affichage()}.
	 */
	public void masquer() {
		window.setVisible(false);
	}
	/**
	 * Provoque la destruction du plateau. 
	 * L'arrêt du programme est conditionné par la fermeture de tous les plateaux ouverts.
	 */
	public void close() {
		window.dispose();
	}
	/**
	 * prépare l'attente d'événements Swing (clavier ou souris).
	 * Appelé par waitEvent() et waitEvent(int). 
	 */
	private void prepareWaitEvent(boolean paneSelectionPrecis) {
		currentEvent = null ;	// Annule tous les événements antérieurs
		mouse = null;
		clicAction = false;
		affichage() ;	// Remet à jour l'affichage (peut être optimisé)
		if (paneSelectionPrecis) {
			PersoPane.requestFocusInWindow();
		} else {
			window.requestFocusInWindow();
		}
	}
	/**
	 * Attends (au maximum timeout ms) l'apparition d'une entrée selon selectionPrecis.
	 * 
	 * @param timeout La durée maximale d'attente.
	 * @param paneSelectionPrecis si on attend un clic dans PersoPane ou dans window
	 * @return L'événement observé si il y en a eu un, <i>null</i> sinon.
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/InputEvent.html">java.awt.event.InputEvent</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseEvent.html">java.awt.event.MouseEvent</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html">java.awt.event.KeyEvent</a>
	 */
	public InputEvent waitEvent(int timeout, boolean selectionPrecis) {
		int time = 0 ;
		prepareWaitEvent(selectionPrecis) ;
		mouse = null;
		confirmeFinTour = false;
		annulerChoix = false;
		if (selectionPrecis) {
			while ((persoPrecis == -1) && !annulerChoix && (time < timeout)) {
				try {
					Thread.sleep(100) ;	// Cette instruction - en plus du délai induit - permet à Swing de traiter les événements GUI 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				time += 100 ;
			}
		} else {
			while (mouse == null && !confirmeFinTour && !annulerChoix && (time < timeout)) {
				try {
					Thread.sleep(100) ;	// Cette instruction - en plus du délai induit - permet à Swing de traiter les événements GUI 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				time += 100 ;
			}
		}
		return mouse ;
	}
	/**
	 * Attente de deplacement ou de clic sur un bouton d'action
	 * @param timeout
	 */
	public void waitDeplacementOuAction(int timeout) {
		int time = 0;
		prepareWaitEvent(true);
		while ( !annulerChoix && !clicAction && !veutDeplacer  && mouse == null && (time < timeout)) {
			try {
				Thread.sleep(100) ;	// Cette instruction - en plus du délai induit - permet à Swing de traiter les événements GUI 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			time += 100 ;
		}
	}
	/**
	 * Wait tant qu'on a pas selectionne de personnage par le biais d'un clic  n'importe où OU qu'on a pas confirmé
	 * sa séléction en appuyant sur entree une fois qu'on a selectionne un perso en appuyant sur 'A'
	 * @param timeout
	 */
	public void waitSelectionPerso(int timeout) {
		waitTime = 0;
		prepareWaitEvent(true);
		while ( !annulerChoix && !confirmeSelection && !confirmeSelectionPane && mouse == null && (waitTime < timeout)) {
			try {
				Thread.sleep(100) ;	// Cette instruction - en plus du délai induit - permet à Swing de traiter les événements GUI 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitTime += 100 ;
		}
	}
	/**
	 * Attends (indéfiniment) un événement clavier ou souris. 
	 * Pour limiter le temps d'attente (timeout) voir {@link #waitEvent(int)}.
	 * 
	 * @return L'événement observé.
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/InputEvent.html">java.awt.event.InputEvent</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/MouseEvent.html">java.awt.event.MouseEvent</a>
	 * @see <a href="https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html">java.awt.event.KeyEvent</a>
	 */
	public InputEvent waitEvent() {
		prepareWaitEvent(false) ;
		while (currentEvent == null) {
			Thread.yield() ;	// Redonne la main à Swing pour gérer les événements
		}
		return currentEvent ;
	}
	/**
	 * Calcule la ligne de la case ciblée par un mouseEvent.
	 * Attention: il est possible si l'utilsateur agrandi la fenêtre que le clic
	 * se produise à l'extérieur du plateau. Cette configuration n'est pas détectée par cette méthode
	 * qui retourne alors une valeur hors des limites. 
	 *
	 * @param event L'évenement souris capturé.
	 * @return le numéro de la colonne ciblée (0 à taille-1)
	 */
	public int getX() {
		if (mouse != null) {
			return graphic.getX(mouse) ;
		}
		return -1;
	}
	/**
	 * Calcule la colonne de la case ciblée par un mouseEvent.
	 * Attention: il est possible si l'utilsateur agrandi la fenêtre que le clic
	 * se produise à l'extérieur du plateau. Cette configuration n'est pas détectée par cette méthode
	 * qui retourne alors une valeur hors des limites. 
	 *
	 * @param event L'évenement souris capturé.
	 * @return le numéro de la colonne ciblée (0 à taille-1)
	 */
	public int getY() { 	
		if (mouse != null) {
			return graphic.getY(mouse) ;
		}
		return -1;
	}
	// Note la taille initiale est calculée d'après la taille du graphique.
	private void resizeFromGraphic() {
		Dimension dim = graphic.getGraphicSize() ;
		if (console == null) {
			dim.height += 10 ;
		} else {
			dim.height += 100 ;
		}
		window.getContentPane().setPreferredSize(dim) ;
		window.pack() ;
	}
	
	public MouseEvent getCurrentEvent() {
		return mouse; // mouse car currentEvent mauvais type et bricolage sans trop savoir comment ca marche
	}
	
	/**
	 * CAS : plusieurs persos sur une case
	 * 
	 * @param event L'évenement souris capturé.
	 * @return int, le y du perso selectionne dans PersoPane
	 */
	public int getPersoPrecis() {
		return persoPrecis;
	}
		
	/**
	 * Chaque perso sur la position est un JButton qui sera ajouté dans persoPane
	 * @param selection
	 */
	public void ajouterSelectionPersos(List<Personnage> selection) {
		listePersos = selection;
		listeBoutons.clear();
		
		veutPieger = false;
		faitAction = false;
		annulerChoix = false;
		dejaAjoutAnnuler = false;
		dejaAjoutAttaquer = false;
		dejaAjoutClef = false;
		dejaAjoutTresor = false;
		
		persoPrecis = -1;
		savePerso = -1;
		selectionListe = -1;
		nbPersos = selection.size();
		
		oldHighlight = new Position(-1,-1);
		PersoPane.removeAll();
		for (int i=0;i<selection.size();i++) {
			ImageIcon image = new ImageIcon(Plateau.class.getResource(selection.get(i).getCheminImage()));
			
			listeBoutons.add(new JButton(image));
			listeBoutons.get(i).setOpaque(true);
			listeBoutons.get(i).setBackground(Color.GREEN);
			listeBoutons.get(i).setActionCommand("perso_" + i);
			listeBoutons.get(i).setName(""+ selection.get(i).getType());
			listeBoutons.get(i).addActionListener(new Action());
			listeBoutons.get(i).setPreferredSize(new Dimension(image.getIconWidth(),image.getIconHeight()));
			PersoPane.add(listeBoutons.get(i));

			if (selection.get(i).getEstPiege()) {
				listeBoutons.get(i).setEnabled(false);
				listeBoutons.get(i).setBackground(Color.LIGHT_GRAY);
			}
		}
		if (listeBoutons.size() == 1) {
			listeBoutons.get(0).setBackground(sable);
			tempPersoSelectionne = selection.get(0);
		}
	}
	
	public void setDejaFaits(boolean b) {
		dejaAjoutAttaquer = b;
		dejaAjoutVol = b;
		dejaAjoutClef = b;
		dejaAjoutTresor = b;
		dejaAjoutPieger = b;
	}

	private void ajouterAnnuler() {
		ImageIcon annulerIcone = new ImageIcon(Plateau.class.getResource("images/annuler.png"));
		annuler = new JButton(annulerIcone);
		annuler.setBackground(sable);
		annuler.setActionCommand("annuler");
		annuler.addActionListener(new Action());
		if (!dejaAjoutAnnuler) {
			PersoPane.add(annuler);
			listeBoutons.add(annuler);
			dejaAjoutAnnuler = true;
		}
	}
	
	public void actionsSiListeUnique() {
		if (tempPersoSelectionne.getType() == 1 || tempPersoSelectionne.getType() == 4) {
			ajouterActionVoler();
			dejaAjoutVol = true;
		}
		if (tempPersoSelectionne.getType() == 11 || tempPersoSelectionne.getType() == 13) {
			ajouterActionPieger();
			dejaAjoutPieger = true;
		}
		if (tempPersoSelectionne.getType() == 1 || tempPersoSelectionne.getType() == 4) {
			ajouterActionVoler();
			dejaAjoutVol = true;
		}
		if (tempPersoSelectionne.getType() == 10 || tempPersoSelectionne.getType() == 12) {
			ajouterActionAttaquer();
			dejaAjoutAttaquer = true;
		}
		if (peutEchangerClef) {
			ajouterActionEchangerClef();
		}
		if (peutEchangerTresor) {
			ajouterActionEchangerTresor();
		}
		
		if(detientClef){
			ajouterClef();
		}
		
		if(detientCoffre){
			ajouterCoffre();
		}
		
		ajouterAnnuler();
		PersoPane.repaint();
		window.repaint();
	}
	
	private void ajouterClef(){
		if (!dejaAjoutClef) {
			PersoPane.add(clef);
			dejaAjoutClef = true;
		}
		if (!detientClef) {
			clef.setEnabled(false);
			clef.setBackground(Color.LIGHT_GRAY);
		} else {
			clef.setEnabled(true);
			clef.setBackground(sable);
		}
	}
	
	private void ajouterCoffre(){
		if (!dejaAjoutCoffre) {
			PersoPane.add(coffre);
			dejaAjoutCoffre = true;
		}
		if (!detientCoffre) {
			coffre.setEnabled(false);
			coffre.setBackground(Color.LIGHT_GRAY);
		} else {
			coffre.setEnabled(true);
			coffre.setBackground(sable);
		}
	}
		
	private void ajouterActionAttaquer() {
		if (!dejaAjoutAttaquer) {
			PersoPane.add(attaquer);
			listeBoutons.add(attaquer);
			dejaAjoutAttaquer = true;
		}
		if (!peutAttaquer) {
			attaquer.setEnabled(false);
			attaquer.setBackground(Color.LIGHT_GRAY);
		} else {
			attaquer.setEnabled(true);
			attaquer.setBackground(sable);
		}
	}
	
	/**
	 * On ajoute le bouton de l'action voler. Ensuite, selon si un vol est possible, le bouton est vert ou gris
	 */
	private void ajouterActionVoler() {
		if (!dejaAjoutVol) {
			PersoPane.add(voler);
			listeBoutons.add(voler);
			dejaAjoutVol = true;
		}
		if (!peutVoler) {
			voler.setEnabled(false);
			voler.setBackground(Color.LIGHT_GRAY);
		} else {
			voler.setEnabled(true);
			voler.setBackground(sable);
		}
	}
	/**
	 * On ajoute le bouton de l'action pieger dans PersoPane
	 */
	
	private void ajouterActionPieger(){
		if (!dejaAjoutPieger) {
			PersoPane.add(pieger);
			listeBoutons.add(pieger);
			dejaAjoutPieger = true;
		}
		if (!peutPieger) {
			pieger.setEnabled(false);
			pieger.setBackground(Color.LIGHT_GRAY);
		} else {
			pieger.setEnabled(true);
			pieger.setBackground(sable);
		}
	}
	
	/**
	 * On ajoute le bouton de l'action d'échanger une clef dans PersoPane
	 */
	private void ajouterActionEchangerClef() {
		if (!dejaAjoutClef) {
			echangerClef.setEnabled(true);
			echangerClef.setBackground(sable);
			PersoPane.add(echangerClef);
			listeBoutons.add(echangerClef);
			dejaAjoutClef = true;
		}
	}
	
	/**
	 * On ajoute le bouton de l'action d'échanger le trésor dans PersoPane
	 */
	private void ajouterActionEchangerTresor() {
		echangerTresor.setEnabled(true);
		echangerTresor.setBackground(sable);
		if (!dejaAjoutTresor) {
			PersoPane.add(echangerTresor);
			listeBoutons.add(echangerTresor);
			dejaAjoutTresor = true;
		}
	}
	
	/**
	 * Permet de rappeler graphic.Component apres avoir modifié temporairement le type d'une cellule
	 * @param destination la case qu'on veut temporairement changer
	 * @param type le type qu'on veut afficher
	 */
	public void refreshCase(Position destination, int type) {
		graphic.refreshCase(destination, type+2);
	}
	
	public void refreshCaseHighlight(Position dest, Color color) {
		if (!oldHighlight.equals(new Position(-1,-1))) {
			graphic.resetHighlight(oldHighlight);
		}
		oldHighlight = dest;
		graphic.refreshCaseHighlight(dest, color);
	}
	
	/**
	 * On desactive les boutons si on choisit deja une
	 *
	 */
	private class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("annuler")) {
				System.out.println("annule sa selection");
				annulerChoix = true;
			} else if (e.getActionCommand().equals("pieger")) {
				veutPieger = true;
				clicAction = true;
			} else if (e.getActionCommand().equals("voler")) {
				veutVoler = true;
				clicAction = true;
			} else if (e.getActionCommand().equals("attaquer")) {
				veutAttaquer = true;
				clicAction = true;
			}else if (e.getActionCommand().equals("echangerClef")) {
				veutEchangerClef = true;
				clicAction = true;
			} else if (e.getActionCommand().equals("echangerTresor")) {
				veutEchangerTresor = true;
				clicAction = true;
			} else {
				for (int i=0;i<nbPersos;i++) {
					// si ce n'est psa le bouton appuyé, on le desactive
					if (!("perso_"+i).equals(e.getActionCommand())) {
						listeBoutons.get(i).setEnabled(false);
						listeBoutons.get(i).setBackground(Color.LIGHT_GRAY);
					} else {
						confirmeSelectionPane = true;
						tempPersoSelectionne = listePersos.get(i);
						listeBoutons.get(i).setBackground(sable);
						persoPrecis = i;
					}
				}
			}
		}		
	}
	
	public void setAttendFinTour(boolean set) {
		attendFinTour = set;
	}
	
	public boolean getAttendFinTour() {
		return attendFinTour;
	}
	
	public boolean getClicAction () {
		return clicAction;
	}
	
	public boolean getVeutEchangerClef() {
		return veutEchangerClef;
	}
	
	public boolean getVeutEchangerTresor() {
		return veutEchangerTresor;
	}
	
	/**
	 * Si le joueur a confirmé sa selection en cliquant dans PersoPane
	 * @return
	 */
	public boolean getConfirmeSelectionPane() {
		return confirmeSelectionPane;
	}
	
	public void setConfirmeSelectionPane(boolean b) {
		confirmeSelectionPane = b;
	}
	public boolean getVeutDeplacer() {
		return veutDeplacer;
	}
	
	public void setVeutDeplacer(boolean b) {
		veutDeplacer = b;
	}
	
	public Position getDirectionDeplacement() {
		return directionDeplacement;
	}
	
	public boolean getDirectionDeplacementNulle() {
		return directionDeplacement.equals(new Position(-1, -1));
	}
	
	public void setDirectionDeplacement(Position pos) {
		directionDeplacement.setLocation(pos);
	}
	/**
	 * Verification de la confirmation de selection avec le clavier
	 * @return booleen
	 */
	public boolean getConfirmeSelection() {
		return confirmeSelection;
	}
	/**
	 * Configuration de la confirmation de selection
	 */
	public void setConfirmeSelection(boolean b) {
		confirmeSelection = b;
		peutSeDeplacer = true;
		directionDeplacement = new Position(-1,-1);
		if (b) {
			for (int i=0;i<listeBoutons.size();i++) {
				if (i != persoPrecis) {
					listeBoutons.get(i).setEnabled(false);
					listeBoutons.get(i).setBackground(Color.LIGHT_GRAY);
				}
			}
		}
	}
	/**
	 * Retourne la selection de personnage
	 * @return selection
	 */
	public boolean getASelectionnePerso() {
		return aSelectionnePerso;
	}
	
	public Personnage getTempPersoSelec() {
		if (tempPersoSelectionne != null) {
			return tempPersoSelectionne;
		}
		return null;
	}
	
	public void setTempPersoSelec(Personnage perso) {
		tempPersoSelectionne = perso;
	}
	
	public boolean getConfirmeFinTour() {
		return confirmeFinTour;
	}
	
	public void setConfirmeFinTour(boolean b) {
		confirmeFinTour = b;
	}	
	
	/**
	 * Desactiver annulation
	 */
	public void disableAnnulerEtActions() {
		for (int i=0; i<listeBoutons.size();i++) {
			listeBoutons.get(i).setEnabled(false);
			listeBoutons.get(i).setBackground(Color.LIGHT_GRAY);
		}
	}
	
	/**
	 * Changement de la selection du personnage
	 */
	private void changerSelectionPerso() {
		for (int i=0; i<listeBoutons.size();i++) {
			if (!listeBoutons.get(i).isEnabled()) {
				listeBoutons.get(i).setBackground(Color.LIGHT_GRAY);
			} else {
				listeBoutons.get(i).setBackground(sable);
			}
		}
		if (savePerso != -1) {
			listeBoutons.get(savePerso).setBackground(sable);
		}
		// set dernier = annuler a sable
		listeBoutons.get(listeBoutons.size()-1).setBackground(sable);
		
		// si on peut encore selectionner des persos
		if (selectionListe < nbPersos-1) {
			persoPrecis++;
			selectionListe++;
			listeBoutons.get(selectionListe).setBackground(Color.GREEN);

		// si on est au dernier bouton de persos ou plus que le dernier bouton de persos et qu'il reste encore des boutos
		} else if (selectionListe >= nbPersos-1 && selectionListe < listeBoutons.size()-1) {
			selectionListe++;
			listeBoutons.get(selectionListe).setBackground(Color.GREEN);
		// si on est au dernier = annuler
		} else if (selectionListe == listeBoutons.size()-1){
			selectionListe = 0;
			persoPrecis = 0;
			listeBoutons.get(selectionListe).setBackground(Color.GREEN);
		// cas de secours : on remet a zéro
		} else {
			persoPrecis = 0;
			selectionListe = 0;
			listeBoutons.get(selectionListe).setBackground(Color.GREEN);
		}
		if (!confirmeSelection && !confirmeSelectionPane) {
			tempPersoSelectionne = listePersos.get(persoPrecis);
			refreshCaseHighlight(tempPersoSelectionne.getPos(), Color.CYAN);
		}
	}
	
	
	private void executeEnter() {
		// les actions		
		if (listeBoutons.get(selectionListe).getActionCommand().equals("pieger")) {
			veutPieger = true;
			clicAction = true;
		} else if (listeBoutons.get(selectionListe).getActionCommand().equals("voler")) {
			veutVoler = true;
			clicAction = true;
		} else if (listeBoutons.get(selectionListe).getActionCommand().equals("attaquer")) {
			veutAttaquer = true;
			clicAction = true;
		} else if (listeBoutons.get(selectionListe).getActionCommand().equals("echangerClef")) {
			veutEchangerClef = true;
			clicAction = true;
		} else if (listeBoutons.get(selectionListe).getActionCommand().equals("echangerTresor")) {
			veutEchangerTresor = true;
			clicAction = true;
		} else if (listeBoutons.get(selectionListe).getActionCommand().equals("annuler")) {
			System.out.println("annule sa selection");
			annulerChoix = true;
		} else if (persoPrecis != -1 && !confirmeSelection && !confirmeSelectionPane && !annulerChoix && !listePersos.get(persoPrecis).getEstPiege()) {
			setConfirmeSelection(true);
			savePerso = persoPrecis;
		}
		// fin tour
		if (confirmeSelection || confirmeSelectionPane) {
			confirmeFinTour = true;
		}		
	}
	
	/**
	 * Class Keys
	 * @author Team J3
	 */
	private class Keys implements KeyListener {

		/**
		 * Gestion des clee utilise
		 * @Override
		 */
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				if (!attendFinTour) {
					waitTime = 0;
					changerSelectionPerso();
				}
				break;
			case KeyEvent.VK_ENTER:
				executeEnter();
				break;
			case KeyEvent.VK_SPACE:
				executeEnter();
				break;
			case KeyEvent.VK_X:
				annulerChoix = true;
				break;
			case KeyEvent.VK_ESCAPE:
				annulerChoix = true;
				break;
				
			default:
				break;
			}
			if (peutSeDeplacer) {
				switch ( e.getKeyCode()) {
				case KeyEvent.VK_Z:
					directionDeplacement.setLocation(0, -1);
					veutDeplacer = true;
					break;
				case KeyEvent.VK_S:
					directionDeplacement.setLocation(0, +1);
					veutDeplacer = true;
					break;
				case KeyEvent.VK_D:
					directionDeplacement.setLocation(+1, 0);
					veutDeplacer = true;
					break;
				case KeyEvent.VK_Q:
					directionDeplacement.setLocation(-1, 0);
					veutDeplacer = true;
					break;
	
				default:
					break;
				}
			}
			
		}

		/**
		 * Gestion des clee relachee
		 * @param evenement
		 * @Override
		 */
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		/**
		 * Gestion des clee tapee
		 * @param evenement
		 * @Override
		 */
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	/**
	 * Vouloir de pieger
	 * @return possibilite
	 */
	public boolean veutPieger() {
		return veutPieger;
	}
	
	public boolean veutVoler() {
		return veutVoler;
	}
	
	public boolean veutAttaquer() {
		return veutAttaquer;
	}
	
	/**
	 * Retourne si action en cours realise
	 * @return booleen
	 */
	public boolean getFaitAction() {
		return faitAction;
	}
	/**
	 * Configuration du booleen en gestion de l action realise
	 * @param booleen
	 */
	public void setFaitAction(boolean b) {
		faitAction = b;
	}
	
	/**
	 * Annuler son choix
	 * @return annulation
	 */
	public boolean getAnnulerChoix() {
		return annulerChoix;
	}

	/**
	 * Peut voler ?
	 * @return booleen
	 */
	public boolean getPeutVoler() {
		return this.peutVoler;
	}
	/**
	 * Configuration du pouvoir de voler
	 * @param booleen
	 */
	public void setPeutVoler(boolean set) {
		this.peutVoler = set;
	}
	
	public void setPeutAttaquer(boolean set) {
		this.peutAttaquer = set;
	}
	/**
	 * Configuration du pouvoir de pieger
	 * @param booleen
	 */
	public void setPeutPieger(boolean set){
		this.peutPieger = set;
	}
	/**
	 * Configuration du pouvoir d echanger la clee
	 * @param booleen
	 */
	public void setPeutEchangerClef(boolean set) {
		this.peutEchangerClef = set;
	}
	/**
	 * Configuration du pouvoir d echanger tresor
	 * @param booleen
	 */
	public void setPeutEchangerTresor(boolean set) {
		this.peutEchangerTresor = set;
	}
	
	
	/**
	 * Affiche un message dans la partie texte du plateau.
	 * Si le plateau a été construit sans zone texte, cette méthode est sans effet.
	 * Cela provoque aussi le déplacement du scroll vers l'extrémité basse de façon 
	 * à rendre le texte ajouté visible. On ajoute automatiquement une fin de ligne 
	 * de telle sorte que le message est seul sur sa ligne.
	 * @param message Le message à afficher.
	 */
	public void println(String message) {
		if (console != null) {
			console.println(message) ;
		}
	}
	/**
	 * Affiche un message dans la partie texte du plateau.
	 * Si le plateau a été construit sans zone texte, cette méthode est sans effet.
	 * Cela provoque aussi le déplacement du scroll vers l'extrémité basse de façon 
	 * à rendre le texte ajouté visible. On ajoute automatiquement une fin de ligne 
	 * de telle sorte que le message est seul sur sa ligne.
	 * @param message Le message à afficher.
	 */
	public void print(String message) {
		if (console != null) {
			console.print(message) ;
		}
	}
	/**
	 * Afficage de message
	 * @param message
	 * @param equipe
	 * @param message
	 */
	public void print(String message, boolean equipe, String message2) {
		print(message, equipe);
		console.print(" " + message2);
	}
	/**
	 * Afficage de message
	 * @param message
	 * @param equipe
	 * @param message
	 */
	public void println(String message, boolean equipe, String message2) {
		println(message, equipe);
		console.print(" " + message2);
	}
	/**
	 * Afficage de message
	 * @param message
	 * @param equipe
	 */	
	public void print(String message, boolean equipe) {
		console.print(message);
		console.printEquipe(equipe);
	}
	/**
	 * Afficage de message
	 * @param message
	 * @param equipe
	 */
	public void println(String message, boolean equipe) {
		console.println(message);
		console.printEquipe(equipe);
	}
	/**
	 * Nettoyage de la console
	 */
	public void clearConsole() {
		console.clear();
	}
	
	/**
	 * Efface la surbrillance pour toutes les cellules du plateau. 
	 */
	public void clearHighlight() {
		if (graphic != null) {
			graphic.clearHighlight();
		}
	}
	/**
	 * Place une cellule en surbrillance. La surbrillance provoque la superposition d'un carré translucide 
	 * de la couleur précisée. 
	 * Les cellules peuvent revenir à leur état normal:
	 * <ul>
	 * <li>globalement par un appel à {@link #clearHighlight()}</li>
	 * <li>individuellement par un appel à {@link #resetHighlight(int, int)}</li>
	 * </ul>
	 * @param x La ligne de la cellule.
	 * @param y La colonne de la cellule.
	 * @param color La couleur du carré affiché.
	 */
	public void setHighlight(int x, int y, Color color) {
		if (graphic != null) {
			graphic.setHighlight(x, y, color);
		}
	}
	/**
	 * Efface la surbrillance pour une cellule du plateau. La cellule est déterminée par
	 * son numéro de ligne et de colonne. Si la cellule n'était pas en surbrillance, 
	 * cette méthode est sans effet.
	 * @param x Numéro de ligne de la cellule à affecter.
	 * @param y Numéro de colonne de la cellule à affecter.
	 */
	public void resetHighlight(int x, int y) {
		if (graphic != null) {
			graphic.resetHighlight(x, y);
		}
	}
	
	public void resetHighlight(Position dest) {
		if (graphic != null && !dest.getNulle()) {
			graphic.resetHighlight(dest.x, dest.y);
		}
	}
	/**
	 * Permet de savoir si une cellule est actuellement en surbrillance.
	 * @param x Le numéro de ligne de la cellule.
	 * @param y Le numéro de colonne de la cellule.
	 * @return true si la cellule est actuellement en surbrillance.
	 */
	public boolean isHighlight(int x, int y) {
		return graphic.isHighlight(x, y) ;
	}
	/**
	 * Efface l'affichage de tout texte dans la partie graphique.
	 */
	public void clearText() {
		graphic.clearText() ;
	}
	/**
	 * Efface l'affichage de texte dans la case [x][y].
	 * @param x
	 * @param y
	 */
	public void clearText(int x, int y) {
		graphic.clearText(x, y);
	}
	/**
	 * Demande l'affichage d'un texte dans une case. Le texte est centré horizontalement et verticalement. Ecrit en Color.BLACK.
	 * @param x Le numéro de ligne de la cellule où apparaît le texte.
	 * @param y Le numéro de colonne de la cellule où apparaît le texte.
	 * @param msg les texte à afficher.
	 */
	public void setText(int x, int y, String msg) {
		graphic.setText(x, y, msg) ;		
	}
	private ImageIcon[] loadImages(String[] imagesPath) {
		int nbImages = imagesPath.length ;
		ImageIcon notFound = null ;
		java.net.URL notFoundURL = Plateau.class.getResource("images/not_found.gif") ;
		if (notFoundURL != null) {
			notFound = new ImageIcon(notFoundURL) ;
		} else {
			System.err.println("Image par défaut non trouvée") ;
		}
		ImageIcon[] images = new ImageIcon[nbImages] ;
		for (int i=0; i<nbImages;i++) {
			java.net.URL imageURL = Plateau.class.getResource(imagesPath[i]);
			if (imageURL != null) {
			    images[i] = new ImageIcon(imageURL);
			} else {
				System.err.println("Image : '" + imagesPath[i]+ "' non trouvée") ;
				images[i] = notFound ;
			}
		}
		return images ;
	}
	
	/**
	 * Sauvegarde
	 */
	public void save() {
		console.save();
	}
	/**
	 * Restauration
	 */
	public void recover() {
		console.recover();
	}
	/**
	 * Nettoyage de sauvegarde
	 */
	public void clearSave() {
		console.clearSave();
	}

	public void setDetientClef(boolean detientClef2) {
		// TODO Auto-generated method stub
		detientClef = detientClef2;
	}

	public void setDetientCoffre(boolean detientTresor) {
		// TODO Auto-generated method stub
		detientCoffre = detientTresor;
	}


}
