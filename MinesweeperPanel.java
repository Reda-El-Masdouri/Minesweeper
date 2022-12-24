import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MinesweeperPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MinesweeperGame game;
	
	public MinesweeperPanel(MinesweeperGame game) {
		this.game = game;
		this.setBackground(Color.white);
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for(int l = 0; l < game.NBLIG ; l++) {
			for(int c = 0; c < game.NBCOL; c++) {
				if(game.grBombes[l][c] == 1) {
					g.setColor(Color.red);
				}
				else {
					g.setColor(Color.white);
				}
				g.fillRect(l*game.TAILLE, c*game.TAILLE, game.TAILLE, game.TAILLE);
				g.setColor(Color.black);
				if(game.grCompte[l][c] != 0)
					g.drawString(String.valueOf(game.grCompte[l][c]), l*game.TAILLE, (c+1)*game.TAILLE-game.TAILLE/2);
				if(game.grCouv[l][c] != 0 && game.gameOver == false && game.win == false) {
					g.setColor(Color.CYAN);
					g.fillRect(l*game.TAILLE, c*game.TAILLE, game.TAILLE, game.TAILLE);
					if(game.grCouv[l][c] == 2) {
						g.setColor(Color.black);
						g.drawString("X", l*game.TAILLE, (c+1)*game.TAILLE-game.TAILLE/2);
					}
				}
				
				g.setColor(Color.black);
				g.drawRect(l*game.TAILLE, c*game.TAILLE, game.TAILLE, game.TAILLE);
			}
		}
		if(game.gameOver) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.BOLD, 26));
			g.drawString("Game Over", 3 * game.TAILLE, (game.NBLIG)*game.TAILLE +20);
		} else if (game.win) {
			g.setColor(Color.blue);
			g.setFont(new Font("TimesRoman", Font.BOLD, 26));
			g.drawString("Victory", 3 * game.TAILLE, (game.NBLIG)*game.TAILLE +20);
		}
	}
	
	public MinesweeperGame getGame() {
		return game;
	}
}
