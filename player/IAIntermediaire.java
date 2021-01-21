package player;

import java.util.ArrayList;

import univers.Attaque;
import univers.Carte;
import univers.Heros;
import univers.Personnage;

/** 
 * @author Nathan
 */
public class IAIntermediaire extends IA {

	/** Constructeur de la classe IAIntermediaire */
	public IAIntermediaire(Heros herosc) {
		super(herosc);
	}

	@Override
	public Carte jouerCarte() {

		int mana = this.getMana();
		int NbCarteEnMain = this.getMain().size();
		Carte CarteMin = this.getMain().get(0);
		int indice = 0;

		for (int i = 0 ; i < NbCarteEnMain ; i++) { // Recherche de la carte � plus bas co�t
			if (this.getMain().get(i).getCout() <= CarteMin.getCout()) {
				CarteMin = this.getMain().get(i);
				indice = i;
			}
		}

		this.setMana(mana - this.getMain().get(indice).getCout());
		retirer(this.getMain().get(indice));

		return CarteMin;
	}

	@Override
	public ArrayList<Attaque> choixAttaques(Heros herosAdverse, ArrayList<Carte> plateau, ArrayList<Carte> plateauAdversaire) {

		int NbCartePlateau = plateau.size();
		int NbCartePlateauAdverse = plateauAdversaire.size();
		ArrayList<Attaque> resultat = new ArrayList<Attaque>();
		Attaque attaque;
		int j = 0; //Indice � comparer au nombre de carte sur le plateau adverse

		for (int i = 0 ; i < NbCartePlateau ; i++) { // On parcourt toutes les cartes de notre plateau

			int degats = plateau.get(i).getDegats();

			// Strat�gie d'attaque (Attaque les cartes adverses jusqu'� leur mort avec nos cartes de gauche � droite)
			if (j < NbCartePlateauAdverse) {  // Si il reste des cartes � attaquer sur le plateau adverse, on les attaque
				attaque = new Attaque(degats,plateau.get(i), (Personnage) plateauAdversaire.get(j));
				
				Personnage CarteAdverseCourante = (Personnage) plateauAdversaire.get(j);
				int PvAdverse = CarteAdverseCourante.getPV();
				
				PvAdverse = PvAdverse - degats;  // Mise � jour des Pv adverse
				
				// Mise � jour des variables repr�sentant l'�tat de notre phase d'attaque (j,i,tourFini)
				if (PvAdverse <= 0) {  // Si la carte adverse est morte
					j++;   // Puis on passe � la carte suivante dans le plateau de l'adversaire
				}
			} else {  // Sinon, on attaque le h�ros adverse
				attaque = new Attaque(degats,plateau.get(i), (Personnage) herosAdverse);
			}
			
			resultat.add(attaque);  // Ajout de l'attaque au r�sultat
		}

		return resultat;
	}
}
