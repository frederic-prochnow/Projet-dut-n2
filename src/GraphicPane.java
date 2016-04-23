/**
 * Importation
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/**
 * Classe GraphicPane
 * La classe GraphicPane permet d'afficher un plateau de Jeu carré
 * sur lequel sont disposés des images représentant les éléments du jeu
 * Les images sont toutes de même taille et carrées.
 * @author Team J3
 */
class GraphicPane extends JPanel {
	/**
	 * Attribut
	 */
	private static final String ERR_NOT_INIT_MSG = "jeu non initialisé" ;
	private static final long serialVersionUID = 2L;
	private int nbImages;
	private int nbLig ; // taille (en nombre de lignes). par défaut: paramètre taille du constructeur.
	private int nbCol ; // taille (en nombre de colonnes). par défaut: paramètre taille du constructeur.
	private ImageIcon[] images;
	private int dimImage;
	private int[][] jeu;
	private String[][] text ;
	private boolean[][] highlight = null ;
	private Color[][] highlightColor = null ;
	private int yText = 0;
	/**
	 * Constructeur de la classe
	 * Construit un plateau de jeu vide de dimension taille x taille.
	 * Initialement, les cellules sont vides. Le constructeur demande la fourniture
	 * d'un catalogue d'images gif ou png.
	 * @param gif tableau 1D des chemins des fichiers des différentes images affichées.
	 * @param taille dimension (en nombre de cellules) d'un côté du plateau.
	 */
	public GraphicPane(ImageIcon[] images, int taille){
		jeu = null ;
		// TODO Rendre la spécification de taille optionnelle (la calculer à partir du tableau d'entier)
		// TODO Affichage d'un message d'erreur si fichier non trouvé.
		// TODO Vérifier l'uniformité de taille des images	
		nbLig = taille ;
		nbCol = taille ;
		this.images = images ;
		nbImages=images.length;
		dimImage = 15 ; // Taille par défaut d'une case.
		for (int i = 0 ; i < nbImages ; i++) {
			if (images[i] != null) {
				int height = images[i].getIconHeight() ;
				int width = images[i].getIconWidth() ;
				if (height >  dimImage) { dimImage = height ; }
				if (width > dimImage) { dimImage = width ; }
			}
		}
		text = null ;
	}
	/**
	 * Retourne la dimension
	 * @return dimension
	 */
	public Dimension getGraphicSize() {
		return new Dimension(nbCol * dimImage, nbLig * dimImage) ;
	}
	/**
	 * Configure la demension
	 */
	private void setGraphicSize() {
		this.setPreferredSize(getGraphicSize());
	}
	/**
	 * Gestion de l'affichage du jeu avec 3 parametres
	 * @param Graphics g
	 * @param String message
	 * @param Rectangle
	 */
	private void showText(Graphics g, String msg, Rectangle r) {	
		g.setColor(Color.LIGHT_GRAY) ;
		g.setFont(new Font("Arial", Font.BOLD, 14)) ;
		yText = 0;
		for (String line : msg.split("\n")) {
			g.drawString(line, r.x+ 25, r.y +12 + yText) ;
			yText += g.getFontMetrics().getStringBounds(msg, g).getHeight()-5;
		}
	}
	/**
	 * Gestion de l'affichage du jeu avec 2 parametres
	 * @param Graphics g
	 * @param String message
	 */
	private void showText(Graphics g, String msg) {
		Dimension dimPage = this.getPreferredSize() ;
		showText(g, msg, new Rectangle(dimPage)) ;
	}
	/**
	 * Dessine la surbrillance autour de la cellule placée en w/l (coord. graphiques).
	 * La cellule fait dimImage x dimImage.
	 * @param w la position en largeur.
	 * @param h la position en hauteur.
	 */
	private void highlight(Graphics g, int w,int h, Color color) {
		Color oldColor = g.getColor() ;
		Color transparent = new Color(color.getRed(), color.getGreen(), color.getBlue(), 96) ;
		g.setColor(transparent) ;
		g.fillRect(w+1, h+1, dimImage-2, dimImage-2);
		g.setColor(oldColor) ;
	}
	/**
	 * Méthode d'affichage du composant (utilisée par Swing. Ne pas appeler directement).
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (jeu != null) {
			{
				Dimension size=this.getSize();
				int w=2,h=1,lig=0,col=0;
				g.setColor( Color.white );
				while ((h<size.height) && (lig < nbLig)) {
					while ((w<size.width) && (col < nbCol)) {
						if (jeu[col][lig]!=0) {
							g.drawImage(images[jeu[col][lig]-1].getImage(),w,h,null);
						} else {
							g.drawRect(w-1, h-1, dimImage-2, dimImage-2);
						}
						if (highlight[lig][col]) {
							highlight(g, w, h, highlightColor[lig][col]) ;
						}
						w+=dimImage;
						col++;
					}
					lig++;
					col=0;
					w=2;
					h+=dimImage;
				}
			}
			{
				Dimension size=this.getSize();
				int w=2,h=1,lig=0,col=0;
				while ((h<size.height) && (lig < nbLig)) {
					while ((w<size.width) && (col < nbCol)) {
						String msg = getText(col, lig) ;
						if ((msg != null) && (msg.length() > 0)) {
							showText(g, msg, new Rectangle(w, h, dimImage, dimImage)) ;
						}
						w+=dimImage;
						col++;
					}
					lig++;
					col=0;
					w=2;
					h+=dimImage;
				}
			}
		} else {
			this.showText(g, ERR_NOT_INIT_MSG) ;
		}
	}
	/**
	 * Rafraichissement de la case concernée
	 * @param Position de la case
	 * @param type de case
	 */
	public void refreshCase(Position cell, int type) {
		jeu[cell.x][cell.y] = type;
		repaint();
	}
	/**
	 * Rafraichissement de la case concernée en surbrillance
	 * @param position de la case
	 * @param couleur de la case
	 */
	public void refreshCaseHighlight(Position dest, Color color) {
		highlight[dest.y][dest.x] = true;
		highlightColor[dest.y][dest.x] = color;
		repaint();
		
	}
	/**
	 * Configuration de la case
	 */
	public void setSize() {
		highlight = new boolean[nbLig][nbCol] ;
		highlightColor = new Color[nbLig][nbCol] ;
		text = new String[nbLig][nbCol] ;
	}
	/**
	 * Nettoyage de la surbrillance
	 */
	public void clearHighlight() {
		for (int c = 0 ; c < nbCol ; c++) {
			for (int l = 0 ; l < nbLig ; l++) {
				highlight[l][c] = false ;
				highlightColor[l][c] = null ;
			}
		}
	}
	/**
	 * Configuration de la surbrillance
	 * @param coordonnee x
	 * @param coordonnee y
	 * @param couleur
	 */
	public void setHighlight(int x, int y, Color color) {
		if (highlight != null) {
			highlight[y][x] = true ;
			highlightColor[y][x] = color ;
		}
	}
	/**
	 * Reconfiguration a default de la surbrillance
	 * @param coordonnee x
	 * @param coordonnee y
	 */
	public void resetHighlight(int x, int y) {
		if (highlight != null) {
			highlight[y][x] = false ;
		}
	}
	/**
	 * Reconfiguration a default de la surbrillance
	 * @param position de la case
	 */
	public void resetHighlight(Position dest) {
		if (highlight != null) {
			highlight[dest.y][dest.x] = false ;
		}
	}
	/**
	 * Retorune le booleen qui indique la surbrillance effectif
	 * @param coordonnee x
	 * @param coordonnee y
	 * @return booleen indicatif
	 */
	public boolean isHighlight(int x, int y) {
		return highlight[y][x] ;
	}
	/**
	 * Méthode permettant de placer les éléments sur le plateau. 
	 * Le tableau doit être de même taille que la dimension déclarée via le constructeur.
	 * @param jeu tableau 2D représentant le plateau  
	 * la valeur numérique d'une cellule désigne l'image correspondante
	 * dans le tableau des chemins (décalé de un, 0 désigne une case vide)
	 */
	public void setJeu(int[][] jeu, boolean debut){
		// Calcule nbLig et nbCol en fonction de la taille réelle du tableau d'entier
		if (jeu != null) {
			this.jeu=jeu;	
			nbCol = jeu.length ;
			nbLig = jeu[0].length ;
			setGraphicSize() ;
			if (debut) {
				setSize() ;
				clearHighlight() ;
				clearText() ;
				text = new String[nbCol][nbLig] ;
			}
		} else {
			text = null ;
		}
	}
	/**
	 * Nettoyage du texte
	 */
	public void clearText() {
		for (int c = 0 ; c < nbCol ; c++) {
			for (int l = 0 ; l < nbLig ; l++) {
				text[l][c] = "" ;
			}
		}
	}
	/**
	 * Nettoyage de texte via des coordonnees
	 * @param coordonnee x
	 * @param coordonnee y
	 */
	public void clearText(int x, int y) {
		text[x][y] = null;
	}
	/**
	 * Configuration de texte via des coordonnees
	 * @param coordonnee x
	 * @param coordonnee y
	 * @param messsage
	 */
	public void setText(int x, int y, String msg) {
		if (text != null) {
			text[x][y] += msg + "\n";
		}
	}
	/**
	 * Retourne le texte au coordonnees concernée
	 * @param coordonnee x
	 * @param coordonnee y
	 * @return texte
	 */
	private String getText(int x, int y) {
		if (text != null) {
			return text[x][y] ;
		} else {
			return null ;
		}
	}
	/**
	 * Retourne la coordonnee X de la position pointeur
	 * @return coordonnee X
	 */
	public int getX(MouseEvent event) {
		return ((event.getX() - 2) / dimImage) ;
	}
	/**
	 * Retourne la coordonnee Y de la position pointeur
	 * @return coordonnee Y
	 */
	public int getY(MouseEvent event) {
		return ((event.getY() - 1) / dimImage) ;
	}
	/**
	 * Retourne le tableau d'entiers représentant le plateau
	 * @return le tableau d'entiers
	 */
	public int[][] getJeu(){return jeu;}
	

}
