# Treasure Hunt

### Version avec la souris

 * #### (2/3) Jeu avec le clavier :
   ###### Déja fait :
 * * On peut selectionner le personnage voulue avec la touche 'A'
 * * Ce personnage est highlight dynamiquement dans le Plateau et PersoPane
 * * Tous les personnages de l'équipe sont affichés dès le tour de l'équipe pour visualiser quels personnages
 il peut directement selectionner avec la touche 'A'. D'ailleurs on peut selectionner ceux-cis avec la souris
 * * Le joueur doit appuyer sur 'Entree' ou 'Espace' pour confirmer le choix de la touche 'A'
 * * On peut appuyer sur 'Entree' ou 'Espace' pour confirmer la fin de son tour
   ###### A faire :
 * * Les actions avec le clavier
 * * Des touches séparées pour le joueur 2 (clavier numérique?)
 

 * #### (1/4) IA basique, 1 chance sur 2 de se deplacer gauche/droite OU haut/bas/  
   ###### Déja fait :
* * Continuer dans son direction de deplacement

  ###### A rajouter :
 * * Constitution de son propre equipe
 * * Retour vers navire quand possede tresor
 * * Retour vers navire quand energie est bas
 * * Possiblité de voler/pieger/combattre
 * * Stratégie de jeu ???


 * #### Choix du nombre de personnages dans le menu.
     Chaque personnage est associé à un élément dans
 un tableau (ex: JButton[])
 

 * #### Contrôle des niveaux d'énergie.
 * * Visualisation de l'énergie sur le plateau
 * * Mise à jour de l'énergie lorsqu'un personnage est resté sur son navire
 * * Séléction du personnage impossible lorsque son énergie == 0


 * #### (3/3) Brouillard de guerre :
 * * sauvegarde les positions deja vus et visualisation des positions pas encore decouverts.


 * #### (2/2) mise à jour de la case du plateau où on a dernierement vu un personnage si on le rencontre de nouveau sur une autre case.  
   Attenion : s'il se déplace pendant son tour, alors quand cela revient
 a notre tour, on n'aura pas un tag car on peut actuellement voir où il était. C'est au joueur de retenir
 s'il quelqu'un a disparu entre temps.