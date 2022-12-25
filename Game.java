import javax.swing.JOptionPane;

public class Game {

	public static void main(String[] args) {
		
		int difficulty = 0;
		int nbLignes = 0;
		int nbColonnes = 0;
		int nbBombes = 0;
		int TAILLE = 30;
		
		String texte = "Choisissez le mode de difficulté:\n";
		texte += "1. Facile,\n";
		texte += "2. Intermediaire,\n";
		texte += "3. Difficile.";
		
		String choix;
		
		do {
			choix = JOptionPane.showInputDialog(texte);
			if(choix == null) 
				break;
			try {
				difficulty = Integer.parseInt(choix);
				if(difficulty <= 0 || difficulty > 3)
					JOptionPane.showConfirmDialog(null, "Choisir un choix valide", "Erreur", JOptionPane.DEFAULT_OPTION);
			} catch (Exception e) {
				JOptionPane.showConfirmDialog(null, "Choisir un choix valide", "Erreur", JOptionPane.DEFAULT_OPTION);
			}
			
		} while (difficulty != 1 && difficulty != 2 && difficulty != 3);
		
		switch (difficulty) {
		case 1: {
			nbLignes = 9;
			nbColonnes = 9;
			nbBombes = 10;
			break;
		}
		case 2:
			nbLignes = 16;
			nbColonnes = 16;
			nbBombes = 40;
			break;
		case 3:
			nbLignes = 30;
			nbColonnes = 30;
			nbBombes = 99;
			TAILLE = 20;
			break;
		default:
			System.exit(0);
		}
		
		new MinesweeperGame(nbLignes, nbColonnes, nbBombes, TAILLE);

	}

}
