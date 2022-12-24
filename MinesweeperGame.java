import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MinesweeperGame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MinesweeperPanel panel;
	int[][] grBombes, grCompte, grCouv;
	
	int NBLIG;
	int NBCOL;
	int TAILLE = 30;
	int NBBOMB;
	
	boolean win = false;
	boolean gameOver = false;
	
	JLabel label;
	
	public MinesweeperGame(int ligne, int col, int nbBomb) {
		this.NBLIG = ligne;
		this.NBCOL = col;
		this.NBBOMB = nbBomb;
		this.setSize(new Dimension((col+2) * TAILLE, (ligne+2) * TAILLE));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Démineur");
		this.setLayout(null);
		
		init();
		generateBombes();
		calculBombesAutour();
		
		label = new JLabel();
		
		this.panel = new MinesweeperPanel(this);
		panel.setBounds(0, 0, (col+1) * TAILLE, (ligne+1) * TAILLE);
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
//				System.out.println("col="+(e.getX()/TAILLE));	
//				System.out.println("ligne="+(e.getY()/TAILLE));
//				System.out.println(e.getButton());
				if(!gameOver) {
					int l = e.getX()/TAILLE;
					int c = e.getY()/TAILLE - 1;
					if(l >= 0 && l < NBLIG && c >= 0 && c < NBCOL) {
						if(e.getButton() == 1) {
							if(grCouv[l][c] != 2) {
								if(grBombes[l][c] == 1) {
								gameOver = true;
								}
								floodFill(l, c);
								checkWin();
							}
						} else {
							if(grCouv[l][c] == 2) {
								grCouv[l][c] = 1;
							} else {
								grCouv[l][c] = 2;
							}
							
						}
					}
					
				}
				panel.repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.add(panel);
		this.setVisible(true);
	}
	
	private void init() {
		grBombes = new int[NBLIG][NBCOL];
		grCompte = new int[NBLIG][NBCOL];
		grCouv = new int[NBLIG][NBCOL];	
		for(int l = 0; l < NBLIG; l++)
			for(int c = 0; c < NBCOL; c++)
				grCouv[l][c] = 1;
	}
	private void generateBombes() {
		int nb = 0;
		do {
			int c = (int) Math.floor(Math.random() * NBCOL);
			int l = (int) Math.floor(Math.random() * NBLIG);
			if(grBombes[l][c] == 0) {
				grBombes[l][c] = 1;
				nb++;
			}
		}while(nb != NBBOMB);
	}
	private int getBombes(int l, int c) {
		if(l < 0 || l >= NBLIG)
			return 0;
		if(c < 0 || c >= NBCOL)
			return 0;
		return grBombes[l][c];
	}
	private void calculBombesAutour() {
		for(int l = 0; l < NBLIG; l++) {
			for(int c = 0; c < NBCOL; c++) {
				if(grBombes[l][c] == 0) {
					int nb = 0;
					nb += getBombes(l-1, c-1);
					nb += getBombes(l-1, c);
					nb += getBombes(l-1, c+1);
					nb += getBombes(l, c-1);
					nb += getBombes(l, c+1);
					nb += getBombes(l+1, c-1);
					nb += getBombes(l+1, c);
					nb += getBombes(l+1, c+1);
					grCompte[l][c] = nb;
				}
			}
		}
	}
	
	private void floodFill(int l, int c) {
		if(l < 0 || l >= NBLIG)
			return;
		if(c < 0 || c >= NBCOL)
			return;
		if(grCompte[l][c] != 0) {
			grCouv[l][c] = 0;
			return;
		}
		if(grCouv[l][c] == 0)
			return;
		grCouv[l][c] = 0;
		floodFill(l-1, c);
		floodFill(l, c+1);
		floodFill(l+1, c);
		floodFill(l, c-1);
	}
	
	private void checkWin() {
		int nbCouv = 0;
		for(int l = 0; l < NBLIG; l++) 
			for(int c = 0; c < NBCOL; c++) 
				if(grCouv[l][c] != 0)
					nbCouv++;
			
		if(nbCouv == NBBOMB)
			win = true;
				
	}
}
