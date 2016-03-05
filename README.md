## Treasure Hunt - Partie 1 - Jalon 1

##TREASURE HUNT : VERSION N°1

Vous allez développer plusieurs versions du jeu, de plus en plus riches.
Dans la version initiale, il n'y a que deux types de personnages : les explorateurs et les voleurs.

#RÈGLES DU JEU

Les équipes s'affrontent en contrôlant des personnages sur une île.
L'objectif est de ramener le trésor à bord du navire de son équipe.
Le jeu se déroule tour par tour, une équipe est choisie aléatoirement pour commencer la partie.
Au départ les personnages se trouvent dans leur navire respectif.
A chaque tour de jeu, tous les personnages de l'équipe dont c'est le tour réalisent une action (si aucune autre action
n'est possible, le personnage peut faire une action nulle).
Au cours de la partie chaque équipe doit conserver en permanence au moins un personnage sur l'île. La toute
première action de chaque équipe dans la partie sera donc de sortir un personnage de son navire.
La partie se termine quand le trésor est monté à bord d'un navire, ou si l'une des équipes ne possède plus de
personnage vivant (niveau d'énergie<=0).

#LES ÉQUIPES

Le jeu oppose deux équipes composées d'un même nombre de personnages (paramètre du jeu).
Chaque équipe choisit les personnages qui la constituent (dont au moins 1 explorateur), cela fait partie de sa
stratégie pour remporter la victoire.

#LES PERSONNAGES

Chaque personnage appartient à une des catégories suivantes :
 explorateur :
o personnage qui cherche le trésor, peut soulever un rocher, pour ramasser un objet caché,
 voleur :
o personnage qui est capable de voler un objet détenu par un autre Personnage
Les personnages disposent d'une énergie initiale :
 chaque action consomme un peu d'énergie,
 un retour à bord de leur navire leur permet de régénérer des points d'énergie.
Pour la première version du jeu : il n'y a que ces deux catégories de personnages.

#L'ÎLE

L'île est subdivisée en parcelles égales.
Chaque élément du jeu, personnage, rocher… occupe une parcelle entière.
Les navires sont disposés aléatoirement sur les côtés de l'île.
La taille de l'île, le nombre de parcelles, le nombre de rochers, sont des paramètres de l'application.
Les personnages n'ont qu'une vue partielle de l'île, ils ne voient que les éléments découverts par leur équipe (parcelle
vide, rocher, clef, coffre).
Pour la première version du jeu : l'île est rectangulaire et les parcelles sont carrées.

#LES ÉLÉMENTS

Sur l'île se trouvent plusieurs types d'éléments :
• la clef :
◦ qu'un explorateur peut ramasser et transporter, se trouve initialement cachée sous un rocher,
• le trésor :
◦ qu'un explorateur peut ramasser et transporter, se trouve initialement cachée sous dans le coffre,
◦ le ramassage du trésor ne supprime évidement pas le coffre.
• le coffre :
◦ où se trouve le trésor, ne peut être déplacé, s'ouvre avec la clef, est dissimulé initialement sous un rocher,
• les rochers :
◦ peuvent dissimuler des éléments et peuvent être soulevés afin de montrer un élément caché (clef ou
coffre).
▪ Un rocher ne peut pas se trouver sur une parcelle déjà occupée, personnage, navire…
• les navires :
◦ permettent de reconstituer l'énergie initiale des personnages,
Pour la première version du jeu : il n'y a pas d'autres éléments, hormis les personnages.

#LES ROCHERS

Un certain nombre de rochers (paramètre du jeu) sont initialement disposés aléatoirement sur l'île.
L'un d'entre eux couvre la clef, un autre couvre le coffre contenant le trésor (il y a au moins deux rochers).
On vérifiera que tous les rochers sont accessibles aux membres des deux équipes, i.e. qu'il existe un chemin
permettant de les atteindre.

#LES NAVIRES

Le navire accueille les personnages de son équipe et ne peut être attaqué, ni occupé par un adversaire.
Les personnages y sont à l'abri, et reconstituent leur énergie le temps qu'ils y restent.
Chaque catégorie de personnages dispose d'une énergie initiale (maximale), qu'il peut reconstituer par la suite lors
d'un séjour sur son navire, chaque tour passé sur son navire augmente son énergie d'un montant paramétrable jusqu'à
atteindre, au maximum, son niveau d'énergie initial.

#LES ACTIONS

Les actions consomment presque toutes une part de l'énergie du personnage qui les réalise, les attaques diminuent en plus
l'énergie de celui qui les subit.
Un tableau synthétisant ces pertes d'énergie sera donné plus loin.
Pour la première version du jeu il n'y a que deux actions possibles :
1) le déplacement (ce qui inclus soulever un rocher, ramasser et transporter un objet),
2) le vol d'un objet.
3) le repos permettant de regagner de l'énergie (uniquement dans son navire).

#SE DEPLACER

L'action de base, commune à tous les personnages, est le déplacement, déplacement dans une parcelle voisine.
De manière générale les personnages se déplacent horizontalement ou verticalement :
Règles :
• Les personnages ne peuvent pas se déplacer sur une case déjà occupée par une autre personnage, ou par
un rocher, ni se jeter dans la mer.
• Les personnages ne peuvent monter que dans leur propre navire, pas dans celui de l'adversaire.
• Un explorateur (et un explorateur seulement) peut soulever un rocher. Cette action est déclenchée par la
demande de déplacement sur la parcelle recouverte d'un rocher : le déplacement ne s'effectue pas et il
découvre ce qu'il y a en dessous du rocher. S'il y a quelque chose, il le récupère si possible (si c'est une clé
ou si c'est le trésor et qu'il possède la clé).
S'il découvre le trésor mais n'a pas la clé, l'emplacement du trésor est mémorisé.
• Un voleur peut voler un objet (clef ou trésor) possédé par un adversaire situé sur une parcelle adjacente à
la parcelle où il se trouve. Il a une chance sur deux de réussir un vol, mais perd les points d'énergie
associés que le vol réussisse ou pas. Si plusieurs personnage adverses sont adjacents, il effectue une
tentative (50 % de succès) sur chacun d'entre eux. Le coût en énergie est multiplié par le nombre de
victimes potentielles.
Les personnages d'une équipe partagent la même connaissance (i.e. ils savent tous quand un membre de leur équipe a trouvé la
clef ou découvert le coffre et quels rochers ont été soulevés par leurs coéquipiers).

##GUIDE JALON N°1

#Jalon 1: Semaine du 14 mars 2016.

 Réaliser une application comportant les éléments suivants:
– Création d'une île "vierge" (composée de parcelles vides, sans "élément").
→ classe élémentaire représentant une "parcelle" d'une île.
 Faire en sorte qu'une parcelle puisse afficher un unique "élément" (navire, rocher, …).
→ classe Ile encapsulant un tableau ([][]) de parcelles.
 Tableau (carré de taille 10 par défaut) ou bien de taille fournie à la construction,
 méthode toString() pour affichage dans un main().
– Affichage en mode texte de l'île vierge.
– Placement des 2 navires (vides, à des emplacements fixés a priori).
– Placement d'un rocher "recouvrant" le coffre à trésor (emplacement aléatoire ou fixé a priori).
– Placement d'un rocher "recouvrant" la clef du coffre (idem, emplacement distinct de celui du coffre).
– Affichage en mode texte de l'île avec ces éléments.
– Placement aléatoire de 10% (% du nombre de parcelles de l'île) de rochers ne recouvrant pas d' élément.
– Affichage en mode texte de l'île avec ces nouveaux éléments.
Exemple de représentation de l'île 10 x10, et sa légende, intégrant tous les éléments du jeu version N°1 :
Les personnages de l'équipe N°1 sont représentés par des minuscules suivies de numéro d'ordre du personnage (dans
l'équipe, sans tenir compte du type de personnage):
• e pour un explorateur
• v pour un voleur
• n pour le navire
Les personnages de l'équipe N°2 sont représentés par des Majuscules suivies de numéro d'ordre du personnage (dans
l'équipe, sans tenir compte du type de personnage):
• E pour un explorateur
• V pour un voleur
• N pour le navire
Les autres éléments sont en majuscules
• R pour un rocher
• C pour le coffre (visible uniquement par la ou les équipes ayant découvert le coffre)

+---+---+---+---+---+---+---+---+---+---+
| N |   |   |   | R |   |   |   |   | R |
+---+---+---+---+---+---+---+---+---+---+
|   |   |   |   |   | V1|   |   |   |   |
+---+---+---+---+---+---+---+---+---+---+
| R |   | E2|   |   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+---+---+
|   |   |   |   |   | v2|   |   |   |   |
+---+---+---+---+---+---+---+---+---+---+
| n |   |   |   |   |   |   | R |   |   |
+---+---+---+---+---+---+---+---+---+---+
|   |   |   | e1|   |   |   |   |   |   |
+---+---+---+---+---+---+---+---+---+---+
|   |   |   |   | R |   |   |   |   |   |
+---+---+---+---+---+---+---+---+---+---+
| C | R |   |   | R |   | e3|   |   |   |
+---+---+---+---+---+---+---+---+---+---+
|   |   | V3|   |   |   |   |   |   | R |
+---+---+---+---+---+---+---+---+---+---+
|   |   | R |   |   |   | R |   |   |   |
+---+---+---+---+---+---+---+---+---+---+
