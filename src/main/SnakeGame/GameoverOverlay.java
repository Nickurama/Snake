package SnakeGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import GameEngine.*;
import Geometry.*;

/**
 * The overlay displayed when the current game terminates.
 * 
 * @author Diogo Fonseca a79858
 * @version 06/05/2024
 *
 * @see IOverlay
 * @see ISnakeStats
 */
public class GameoverOverlay extends GameObject implements IOverlay
{
	private static final String GAMEOVER_STR = "Game Over!";
	private static final String SCORE_STR = "Score: ";
	private static final String NAME_STR = "What is your name?";

	private TextOverlay overlay;
	private ISnakeStats snakeStats;
	private GraphicOverlay panel;
	private JTextField textField;

	/**
	 * Instantiates a GameoverOverlay
	 * @param snakeStats the handle to get the snake's info
	 * @param camera the camera to draw the overlay on
	 * @param outline the outline of the overlay
	 */
	public GameoverOverlay(ISnakeStats snakeStats, Rectangle camera, TextOverlayOutline outline)
	{
		this.snakeStats = snakeStats;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);

		this.panel = new GraphicOverlay(camera);
	}

	/**
	 * Initializes a GameoverOverlay 
	 * @param snakeStats the handle to get the snake's info
	 * @param camera the camera to draw the overlay in
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public GameoverOverlay(ISnakeStats snakeStats, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(snakeStats, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	/**
	 * Draws the gameover overlay
	 * @param score
	 */
	private void drawOverlay(int score)
	{
		overlay.fillEmpty();
		overlay.writeCentered(GAMEOVER_STR, overlay.innerHeight() / 3);
		overlay.writeCentered(SCORE_STR + score, (overlay.height()) / 2);
		overlay.writeCentered(NAME_STR, overlay.height() - (overlay.innerHeight() / 3) - 1);
	}

	@Override
	public void start()
	{
		drawOverlay(snakeStats.score());
		initPanel(snakeStats.score());
	}

	/**
	 * Initializes the graphical panel
	 * @param score the score to set the score to in the panel
	 */
	private void initPanel(int score)
	{
		panel.setOpaque(true);
		panel.setBackground(GameManager.getInstance().getBgColor());

		generateTitleLabel();
		generateScoreLabel(score);
		generateNameLabel();
		generateNameTextField();
	}

	/**
	 * Generates the title of the graphical panel
	 */
	private void generateTitleLabel()
	{
		JLabel titleLabel = new JLabel(GAMEOVER_STR);
		panel.add(titleLabel);

		int titleTextSize = (int)((double)(panel.width() / GAMEOVER_STR.length()) * 1.25);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, titleTextSize));
		titleLabel.setForeground(Color.red);

		Dimension titleSize = titleLabel.getPreferredSize();
		int x = (panel.width() / 2) - (titleSize.width / 2);
		int y = (panel.height() / 3) - (titleSize.height / 2);
		titleLabel.setBounds(x, y, titleSize.width, titleSize.height);
	}

	/**
	 * Generates the score on the graphical panel
	 * @param score the score to display
	 */
	private void generateScoreLabel(int score)
	{
		String scoreStr = SCORE_STR + score;
		JLabel scoreLabel = new JLabel(scoreStr);
		panel.add(scoreLabel);

		int scoreTextSize = (panel.width() / scoreStr.length()) / 3;
		scoreLabel.setFont(new Font("SansSerif", Font.BOLD, scoreTextSize));
		scoreLabel.setForeground(Color.white);

		Dimension scoreSize = scoreLabel.getPreferredSize();
		int x = (panel.width() / 2) - (scoreSize.width / 2);
		int y = (panel.height() / 2) - (scoreSize.height / 2);
		scoreLabel.setBounds(x, y, scoreSize.width, scoreSize.height);
	}

	/**
	 * Generates the string asking for the user's name on the screen.
	 */
	private void generateNameLabel()
	{
		JLabel nameLabel = new JLabel(NAME_STR);
		panel.add(nameLabel);

		int nameTextSize = (int)((panel.width() / NAME_STR.length()) / 1.8);
		nameLabel.setFont(new Font("SansSerif", Font.PLAIN, nameTextSize));
		nameLabel.setForeground(Color.white);

		Dimension nameSize = nameLabel.getPreferredSize();
		int x = (panel.width() / 2) - (nameSize.width / 2);
		int y = panel.height() - ((panel.height() / 3) + (nameSize.height / 2));
		nameLabel.setBounds(x, y, nameSize.width, nameSize.height);
	}

	/**
	 * Generates the name input text field
	 */
	private void generateNameTextField()
	{
		int spaces = 8;
		textField = new JTextField(8);
		panel.add(textField);

		textField.setActionCommand(GameManager.GAMEOVER_USERNAME_INSERTED_EVENT_STR);
		textField.addActionListener(GameManager.getInstance());

		int textSize = (panel.width() / spaces) / 2;
		textField.setFont(new Font("SansSerif", Font.ITALIC + Font.BOLD, textSize));
		textField.setBackground(GameManager.getInstance().getBgColor());
		textField.setForeground(Color.gray);
		textField.setHorizontalAlignment(JLabel.CENTER);
		textField.setBorder(new LineBorder(Color.red, 2));

		Dimension textLabelSize = textField.getPreferredSize();
		int x = (panel.width() / 2) - (textLabelSize.width / 2);
		int y = panel.height() - ((panel.height() / 5) + (textLabelSize.height / 2));
		textField.setBounds(x, y, textLabelSize.width, textLabelSize.height);
	}

	/**
	 * Gets the input text field
	 * @return the input text field
	 */
	public JTextField getTextField()
	{
		return this.textField;
	}

	@Override
	public char[][] getOverlay()
	{
		return this.overlay.getOverlay();
	}

	@Override
	public JPanel getPanel()
	{
		return this.panel;
	}
}
