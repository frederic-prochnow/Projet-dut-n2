
public class Actions {
	
	public Actions() {
		
	}
	
	/**
	 * On vérifie si un échange de clef ou tresor est possible a partir du personnage ou un autre membre de son equipe
	 * @param perso l'instance du personnage qui pourrait eventuellement transferer son objet
	 * @param chercheClef si on veut savoir s'il peut echanger sa clef ou echanger son tresor
	 * @return si un echange est possible
	 */
	public boolean peutEchanger(Personnage perso, boolean chercheClef, Equipe equipe1, Equipe equipe2, Ile ile) {
		Equipe tempEquipe;
		if (perso.getEquipe1()) {
			tempEquipe = equipe1;
		} else {
			tempEquipe = equipe2;
		}
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

	/**
	 * On balaie l'entourage du voleur. Si il y a une personnage de l'éequipe adverse
	 * on peut faire une tentative de vol.
	 * @param perso pour connaitre sa position
	 * @return si tentative possible
	 */
	public boolean tenterVol(Personnage perso, Ile ile) {
		if (perso.getType() == 1 || perso.getType() == 4) {
			for (int i=perso.getPos().x-1;i<=perso.getPos().x+1;i++) {
				for (int j=perso.getPos().y-1;j<=perso.getPos().y+1;j++) {
					if ( (ile.getPlateau()[i][j].getEquipe1() && perso.getEquipe2()) || (ile.getPlateau()[i][j].getEquipe2() && perso.getEquipe1()) ) {
						System.out.println("alentour equipe 1 " + ile.getPlateau()[i][j].getEquipe1());
						System.out.println("voleur equipe 1 " + perso.getEquipe1());
						return true;
					}
				}
			}
		}
		System.out.println("il peut PAS voler");
		return false;
	}
	
	public boolean tenterPiege(Personnage perso, Ile ile){
		if(perso.getType() == 11 || perso.getType() == 13){
			if(!ile.getPlateau()[perso.getPos().x][perso.getPos().y].getEstPiege() && !ile.getPlateau()[perso.getPos().x][perso.getPos().y].estNavire() ){ // Si la case n'est pas déja piégé et est le sol
				System.out.println("peut pieger");
				return true;
			}
		}
		
		return false;
	}

	public void pieger(Personnage personnnageSelectionne, Ile plateau) {
		plateau.getPlateau()[personnnageSelectionne.getPos().x][personnnageSelectionne.getPos().y].setEstPiege(true);
		
	}
}
