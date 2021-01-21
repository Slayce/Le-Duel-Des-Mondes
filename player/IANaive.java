package player;
import java.util.ArrayList;
import univers.*;

/** 
 * @author Nathan
 */
public class IANaive extends IA {

	/** Constructeur de la classe IANaive */
	public IANaive(Heros herosc) {
		super(herosc);
	}

	/**
	 * Donne la carte � jouer en la retirant de la main et en enlevant le mana
	 */
	@Override
	public Carte jouerCarte() {
		
		int mana = this.getMana();
		int NbCarteEnMain = this.getMain().size();
		Carte CarteAJouer = null;
		int i = 0;
		
		while (CarteAJouer == null) {
			if (this.getMain().get(i).getCout() <= mana && i < NbCarteEnMain) { // Joue la premi�re carte jouable � gauche
				CarteAJouer = this.getMain().get(i);
				this.setMana(mana - this.getMain().get(i).getCout());
				retirer(this.getMain().get(i));
			}
			i++;
		}
		return CarteAJouer;
	}
	
	/**
	 *
	 */
	@Override
	public ArrayList<Attaque> choixAttaques(Heros herosAdverse, ArrayList<Carte> plateau, ArrayList<Carte> plateauAdversaire) {
		int NbCartePlateau = plateau.size();
		int NbCartePlateauAdverse = plateauAdversaire.size();
		ArrayList<Attaque> resultat = new ArrayList<Attaque>();
		Attaque attaque;
		int j = 0; // Indice � comparer au nombre de carte sur le plateau adverse
		
		for (int i = 0 ; i < NbCartePlateau ; i++) { // On parcourt toutes les cartes de notre plateau			
			int degats = plateau.get(i).getDegats();
			
			// Strat�gie d'attaque (Chaque carte attaque la carte en face d'elle, sinon attaque le h�ros adverse)
			if (j < NbCartePlateauAdverse) {  // Si il reste des cartes � attaquer sur le plateau adverse, on les attaque
				attaque = new Attaque(degats,plateau.get(i), (Personnage) plateauAdversaire.get(i));
			} else {  // Sinon, on attaque le h�ros adverse
				attaque = new Attaque(degats,plateau.get(i), (Personnage) herosAdverse);
			}
			
			j++;
			resultat.add(attaque);  // Ajout de l'attaque au r�sultat
		}
		
		return resultat;
	}

}
