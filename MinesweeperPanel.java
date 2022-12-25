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
					g.setColor(Color.decode("#EA44C3"));
				}
				else {
					g.setColor(Color.decode("#E3C8B5"));
				}
				g.fillRect(l*game.TAILLE, c*game.TAILLE, game.TAILLE, game.TAILLE);
				g.setColor(Color.black);
				if(game.grCompte[l][c] != 0)
					g.drawString(String.valueOf(game.grCompte[l][c]), l*game.TAILLE, (c+1)*game.TAILLE-game.TAILLE/2);
				if(game.grCouv[l][c] != 0 && game.gameOver == false && game.win == false) {
					g.setColor(Color.decode("#C09C83"));
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
			g.setColor(Color.decode("#698DEE"));
			g.setFont(new Font("TimesRoman", Font.BOLD, 28));
			g.drawString("Game Over", (this.getWidth() / 2) - ("Game Over".length()/2) -50, ((this.getHeight()+game.TAILLE) / 2));
		} else if (game.win) {
			g.setColor(Color.decode("#216262"));
			g.setFont(new Font("TimesRoman", Font.BOLD, 32));
			g.drawString("Victory", (this.getWidth() / 2) - ("Victory".length()/2) -50, (this.getHeight() / 2));
		}
	}
	
	public MinesweeperGame getGame() {
		return game;
	}
}
