import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MinesweeperGame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MinesweeperPanel panel;

	int[][] grBombes, grCompte, grCouv;

	int NBLIG;
	int NBCOL;
	int TAILLE;
	int NBBOMB;
	int difficulty;
	
	boolean win = false;
	boolean gameOver = false;

	JButton rejouerBtn;
	JButton exitBtn;
	JLabel labelImage;
	JLabel chronoLabel;
	JLabel nbBombesLabel;
	int sec, min, heure;
	Timer timer;
	public MinesweeperGame(int ligne, int col, int nbBomb, int TAILLE, int difficulty) {
		this.NBLIG = ligne;
		this.NBCOL = col;
		this.NBBOMB = nbBomb;
		this.TAILLE = TAILLE;
		this.difficulty = difficulty;
		this.setSize(new Dimension((col + 5) * TAILLE + 100, (ligne + 5) * TAILLE));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(Color.decode("#F1E3DA"));
		this.setTitle("Démineur");
		this.setLayout(null);

		init();
		generateBombes();
		calculBombesAutour();
		// panel du jeu
		this.panel = new MinesweeperPanel(this);
		panel.setBounds(TAILLE, TAILLE, (col) * TAILLE, (ligne) * TAILLE + 1);
		panel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {

//				System.out.println("col="+(e.getX()/TAILLE));	
//				System.out.println("ligne="+(e.getY()/TAILLE));
//				System.out.println(e.getButton());
				if (!gameOver) {
					int l = e.getX() / TAILLE;
					int c = e.getY() / TAILLE;
					if (l >= 0 && l < NBLIG && c >= 0 && c < NBCOL) {
						if (e.getButton() == 1) {
							if (grCouv[l][c] != 2) {
								if (grBombes[l][c] == 1) {
									gameOver = true;
								}
								floodFill(l, c);
								checkWin();
							}
						} else {
							if (grCouv[l][c] == 2) {
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
		// bouton pou relancer le jeu à zéro
		rejouerBtn = new JButton("Rejouer");
		rejouerBtn.setEnabled(true);
		rejouerBtn.setFocusable(false);
		rejouerBtn.setBounds((col + 2) * TAILLE, (ligne) * TAILLE, 100, 30);
		rejouerBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				Game.main(new String[] { "myparam" });
			}
		});
		rejouerBtn.setBackground(Color.decode("#F1E3DA"));
		rejouerBtn.setFont(new Font("Consolas", Font.PLAIN, 16));
		// bouton pour quitter la partie
		exitBtn = new JButton("Quitter");
		exitBtn.setEnabled(true);
		exitBtn.setFocusable(false);
		exitBtn.setBounds((col + 2) * TAILLE, (ligne - 2) * TAILLE, 100, 30);
		exitBtn.setBackground(Color.decode("#F1E3DA"));
		exitBtn.setFont(new Font("Consolas", Font.PLAIN, 16));
		exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		// démineur image
		labelImage = new JLabel();
		labelImage.setBounds((col + 2) * TAILLE, TAILLE / 2, 150, 100);
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(Game.class.getResource("img.png")).getImage().getScaledInstance(labelImage.getWidth(),
				labelImage.getHeight(), Image.SCALE_SMOOTH));
		labelImage.setIcon(imageIcon);
		// panel indiquant le nombre des bombes
		JPanel panelBombe = new JPanel();
		panelBombe.setLayout(null);
		panelBombe.setBounds((col + 2) * TAILLE, 110, 150, 80);

		JLabel bombeImg = new JLabel();
		bombeImg.setBounds(30, 0, 120, 80);
		ImageIcon imageBombe = new ImageIcon(new ImageIcon(Game.class.getResource("bombe.png")).getImage()
				.getScaledInstance(bombeImg.getWidth(), bombeImg.getHeight(), Image.SCALE_SMOOTH));
		bombeImg.setIcon(imageBombe);

		nbBombesLabel = new JLabel();
		nbBombesLabel.setText(String.valueOf(NBBOMB));
		nbBombesLabel.setFont(new Font("Serif", Font.BOLD, 22));
		nbBombesLabel.setBounds(30, 0, 30, 80);

		panelBombe.setBackground(Color.decode("#F1E3DA"));
		panelBombe.add(bombeImg);
		panelBombe.add(nbBombesLabel);
		// Chrono
		chronoLabel = new JLabel("00:00:00");
		chronoLabel.setForeground(Color.decode("#216262"));
		chronoLabel.setFont(new Font("Serif", Font.BOLD, 26));
		// affichage du chrono en fonction de la difficulté:
		if(difficulty == 2)
			chronoLabel.setBounds((col + 1) * TAILLE + 30, (col - 4) * TAILLE, 100, 50);
		else if(difficulty == 1)
			chronoLabel.setBounds((col + 1) * TAILLE + 30, (col +1) * TAILLE, 100, 50);
		else
			chronoLabel.setBounds((col + 1) * TAILLE + 30, (col - 6) * TAILLE, 100, 50);
		// action comment incrémenter le temps:
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameOver) {
					String strSec, strMin, strH;
					sec++;
					if(sec == 60) {
						sec = 0;
						min++;
					}
					if(min == 60) {
						min = 0;
						heure++;
					}
					strSec = sec < 10 ? "0"+sec : String.valueOf(sec);
					strMin = min < 10 ? "0"+min : String.valueOf(min);
					strH = heure < 10 ? "0"+heure : String.valueOf(heure);
					chronoLabel.setText(strH+":"+strMin+":"+strSec);
					repaint();
				}
			}
		});
		timer.start();
		// add elements
		this.add(chronoLabel);
		this.add(panelBombe);
		this.add(labelImage);
		this.add(exitBtn);
		this.add(rejouerBtn);
		this.add(panel);
		this.setVisible(true);
	}

	private void init() {
		grBombes = new int[NBLIG][NBCOL];
		grCompte = new int[NBLIG][NBCOL];
		grCouv = new int[NBLIG][NBCOL];
		for (int l = 0; l < NBLIG; l++)
			for (int c = 0; c < NBCOL; c++)
				grCouv[l][c] = 1;
	}

	private void generateBombes() {
		int nb = 0;
		do {
			int c = (int) Math.floor(Math.random() * NBCOL);
			int l = (int) Math.floor(Math.random() * NBLIG);
			if (grBombes[l][c] == 0) {
				grBombes[l][c] = 1;
				nb++;
			}
		} while (nb != NBBOMB);
	}

	private int getBombes(int l, int c) {
		if (l < 0 || l >= NBLIG)
			return 0;
		if (c < 0 || c >= NBCOL)
			return 0;
		return grBombes[l][c];
	}

	private void calculBombesAutour() {
		for (int l = 0; l < NBLIG; l++) {
			for (int c = 0; c < NBCOL; c++) {
				if (grBombes[l][c] == 0) {
					int nb = 0;
					nb += getBombes(l - 1, c - 1);
					nb += getBombes(l - 1, c);
					nb += getBombes(l - 1, c + 1);
					nb += getBombes(l, c - 1);
					nb += getBombes(l, c + 1);
					nb += getBombes(l + 1, c - 1);
					nb += getBombes(l + 1, c);
					nb += getBombes(l + 1, c + 1);
					grCompte[l][c] = nb;
				}
			}
		}
	}

	private void floodFill(int l, int c) {
		if (l < 0 || l >= NBLIG)
			return;
		if (c < 0 || c >= NBCOL)
			return;
		if (grCompte[l][c] != 0) {
			grCouv[l][c] = 0;
			return;
		}
		if (grCouv[l][c] == 0)
			return;
		grCouv[l][c] = 0;
		floodFill(l - 1, c);
		floodFill(l, c + 1);
		floodFill(l + 1, c);
		floodFill(l, c - 1);
	}

	private void checkWin() {
		int nbCouv = 0;
		for (int l = 0; l < NBLIG; l++)
			for (int c = 0; c < NBCOL; c++)
				if (grCouv[l][c] != 0)
					nbCouv++;

		if (nbCouv == NBBOMB) {
			win = true;
			timer.stop();
		}
	}
	
	public int calculateFlags() {
		int nbFlags = 0;
		for (int l = 0; l < NBLIG; l++)
			for (int c = 0; c < NBCOL; c++)
				if (grCouv[l][c] == 2)
					nbFlags++;
		return nbFlags;
	}

}
