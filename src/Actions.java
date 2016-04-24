/**
 * Class Actions
 * Classe de gestion des Actions
 * @author Team J3
 */
public class Actions {
	
	/**
	 * Constructeur de la Classe
	 */
	public Actions() {
		
	}
	
	/**
	 * On vérifie si un échange de clef ou tresor est possible a partir du personnage ou un autre membre de son equipe
	 * @param perso l'instance du personnage qui pourrait eventuellement transferer son objet
	 * @param chercheClef si on veut savoir s'il peut echanger sa clef ou echanger son tresor
	 * @return si un echange est possible
	 */
	public boolean peutEchanger(Personnage perso, boolean chercheClef, Equipe tempEquipe, Ile ile) {
		for (int i=perso.getPos().x-1;i<=perso.getPos().x+1;i++) {
			for (int j=perso.getPos().y-1;j<=perso.getPos().y+1;j++) {
				if ( (ile.getPlateau()[i][j].getEquipe1() && perso.getEquipe1()) || (ile.getPlateau()[i][j].getEquipe2() && perso.getEquipe2()) ) {
					// si on cherche la clef et que le perso detient lui meme la clef OU l'autre perso de son equipe detient la clef
					if (chercheClef && !perso.getPos().equals(new Position(i, j)) && (perso.getDetientClef() || tempEquipe.getPersoSurPosition(i, j).getDetientClef() ) ) {
						return true;
					} else if (!chercheClef && !perso.getPos().equals(new Position(i, j)) && (perso.getDetientTresor() || tempEquipe.getPersoSurPosition(i, j).getDetientTresor() ) ) {
						System.out.println("l'autre perso est a x=" +i + " y=" +j);
						System.out.println("l'un des deux possde le tresor et peut l'echanger");
						return true;
					}
				}
			}
		}
	return false;
	}
	
	public void echangerClef(Personnage perso, Equipe tempEquipe, Plateau plateaGraph) {
		Position choixTransfert =new Position(-1, -1);
		boolean bonChoix = false;
		boolean choixClavier = false;
		while (!bonChoix && !choixClavier) {
			plateaGraph.println("Veuillez cliquer sur celui avec lequel vous voulez faire l'échange");
			plateaGraph.waitDeplacementOuAction(5000);
			choixTransfert.setLocation(plateaGraph.getX(), plateaGraph.getY());
			if (perso.getDetientClef()) {
				bonChoix = perso.getPos().estCentreDe(choixTransfert);
			} else {
				bonChoix = choixTransfert.estCentreDe(perso.getPos());
			}
		}
		if (perso.getDetientClef()) {
			tempEquipe.getPersoSurPosition(choixTransfert).setDetientClef(true);
			perso.setDetientClef(false);
		} else {
			tempEquipe.getPersoSurPosition(choixTransfert).setDetientClef(false);
			perso.setDetientClef(true);
		}
		System.out.println("perso a cle = " + perso.getDetientClef());
		System.out.println("destination a cle = " + tempEquipe.getPersoSurPosition(choixTransfert).getDetientClef());
	}
	
	public void echangerTresor(Personnage perso, Equipe tempEquipe, Plateau plateaGraph) {
		Position choixTransfert =new Position(-1, -1);
		boolean bonChoix = false;
		boolean choixClavier = false;
		while (!bonChoix && !choixClavier) {
			plateaGraph.println("Veuillez cliquer sur celui avec lequel vous voulez faire l'échange");
			plateaGraph.waitDeplacementOuAction(5000);
			choixTransfert.setLocation(plateaGraph.getX(), plateaGraph.getY());
			if (perso.getDetientClef()) {
				bonChoix = perso.getPos().estCentreDe(choixTransfert);
			} else {
				bonChoix = choixTransfert.estCentreDe(perso.getPos());
			}
		}
		if (perso.getDetientClef()) {
			tempEquipe.getPersoSurPosition(choixTransfert).setDetientTresor(true);
			perso.setDetientTresor(false);
		} else {
			tempEquipe.getPersoSurPosition(choixTransfert).setDetientTresor(false);
			perso.setDetientTresor(true);
		}
		System.out.println("perso a tresor = " + perso.getDetientTresor());
		System.out.println("destination a tresor = " + tempEquipe.getPersoSurPosition(choixTransfert).getDetientTresor());		
	}
	
	
	

	/**
	 * On balaie l'entourage du voleur. Si il y a une personnage de l'éequipe adverse
	 * on peut faire une tentative de vol.
	 * @param perso pour connaitre sa position
	 * @return si tentative possible
	 */
	public boolean peutVoler(Personnage perso, Ile ile) {
		if (perso.getType() == 1 || perso.getType() == 4) {
			for (int i=perso.getPos().x-1;i<=perso.getPos().x+1;i++) {
				for (int j=perso.getPos().y-1;j<=perso.getPos().y+1;j++) {
					if ( (ile.getPlateau()[i][j].getEquipe1() && perso.getEquipe2()) || (ile.getPlateau()[i][j].getEquipe2() && perso.getEquipe1()) ) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * @param perso le personnage qui serait possible de pieger
	 * @param ile le plateau de jeu
	 * @return si un piege est possible
	 */
	public boolean peutTenterPiege(Personnage perso, Ile ile){
		if(perso.getType() == 11 || perso.getType() == 13){
			if (ile.getPlateau()[perso.getPos().x][perso.getPos().y].getType() == perso.getType() ) { // Si la case n'est pas du tout occupé
				return true;
			}
		}
		return false;
	}

	/**
	 * Fonction qui permet de pièger un personnage
	 * @param personnageselectionne qui va etre pieger
	 * @param ile le plateau de jeu
	 * @param tourE1 booleen
	 */
	public void pieger(Personnage personnnageSelectionne, Ile plateau, boolean tourE1) {
		if (tourE1) {
			plateau.getPlateau()[personnnageSelectionne.getPos().x][personnnageSelectionne.getPos().y].setEstpiegeE1(true);
		} else {
			plateau.getPlateau()[personnnageSelectionne.getPos().x][personnnageSelectionne.getPos().y].setEstpiegeE2(true);
		}
		
	}

	public boolean peutAttaquer(Personnage perso, Ile plateau) {
		// TODO Auto-generated method stub
		return false;
	}

	public void attaquer(Personnage personnnageSelectionne, Ile plateau, boolean tourEquipe1) {
		// TODO Auto-generated method stub
		
	}

	public void voler(Personnage personnnageSelectionne, Ile plateau, boolean tourEquipe1) {
		// TODO Auto-generated method stub
		
	}
}
