package SnakeGame;

import Geometry.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicScrollBarUI;

import GameEngine.*;

/**
 * The overlay to display the highscores
 * 
 * @author Diogo Fonseca a79858
 * @version 08/05/2024
 *
 * @see score
 * @see IHighscoresReader
 * @see IOverlay
 */
public class HighscoresOverlay extends GameObject implements IOverlay
{
	public static final String HIGHSCORE_TITLE_STR = "Highscores";
	public static final String ERR_NO_SCORES_STR = " No scores found...";
	public static final String ERR_READING_STR = " An error ocurred while reading scores...";

	private IHighscoresReader highscoresReader;
	private TextOverlay overlay;
	private int maxHighscoreEntries;

	private GraphicOverlay panel;

	/**
	 * Instantiates a HighscoresOverlay
	 * @param highscoresReader where to get the highscores from
	 * @param camera the camera to draw the overlay on
	 * @param maxHighscoreEntries the max entries to display
	 * @param outline the overlay's outline
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, int maxHighscoreEntries, TextOverlayOutline outline)
	{
		this.highscoresReader = highscoresReader;
		this.overlay = new TextOverlay(camera);
		this.overlay.setOutline(outline);
		this.maxHighscoreEntries = maxHighscoreEntries;

		this.panel = new GraphicOverlay(camera);
	}

	/**
	 * Instantiates a HighscoresOverlay without limiting the number of entries
	 * @param highscoresReader where to get the highscores from
	 * @param camera the camera to draw the overlay on
	 * @param outline the overlay's outline
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, TextOverlayOutline outline)
	{
		this(highscoresReader, camera, -1, outline);
	}

	/**
	 * Initializes a HighscoresOverlay. 
	 * @param camera the camera to draw the overlay in
	 * @param maxHighscoreEntries the max entries to display
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, int maxHighscoreEntries, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(highscoresReader, camera, maxHighscoreEntries, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	/**
	 * Initializes a HighscoresOverlay without limiting the number of entries. 
	 * @param camera the camera to draw the overlay in
	 * @param cornerTL the top left corner character
	 * @param cornerTR the top right corner character
	 * @param cornerDL the bottom left corner character
	 * @param cornerDR the bottom right corner character
	 * @param sideLR the left and right sides character
	 * @param sideTD the top and bottom character
	 */
	public HighscoresOverlay(IHighscoresReader highscoresReader, Rectangle camera, char cornerTL, char cornerTR, char cornerDL, char cornerDR, char sideLR, char sideTD)
	{
		this(highscoresReader, camera, new TextOverlayOutline(cornerTL, cornerTR, cornerDL, cornerDR, sideLR, sideTD));
	}

	/**
	 * Draws the highscores overlay
	 * @param maxScores the maximum number of scores to display
	 */
	private void drawOverlay(int maxScores)
	{
		overlay.fillEmpty();
		if (this.maxHighscoreEntries < 0)
			overlay.writeCentered(HIGHSCORE_TITLE_STR, 2);
		else
			overlay.writeCentered(HIGHSCORE_TITLE_STR + " (Top " + this.maxHighscoreEntries + ")", 2);
		writeScores();
	}

	/**
	 * Writes the scores from the highscores reader to the screen
	 */
	private void writeScores()
	{
		Score[] scores = null;
		boolean errorOccurred = false;
		int writeIndex = 4;
		try
		{
			scores = highscoresReader.getScores();
		}
		catch (SnakeGameException e)
		{
			errorOccurred = true;
		}

		if (errorOccurred)
		{
			overlay.writeParagraph(ERR_READING_STR, writeIndex);
			return;
		}

		if (scores == null)
		{
			overlay.writeParagraph(ERR_NO_SCORES_STR, writeIndex);
			return;
		}

		writeScores(scores, writeIndex);
	}

	/**
	 * Writes scores to the screen
	 * @param scores the scores to write to display
	 * @param writeIndex the index where the scores should start being displayed in
	 */
	private void writeScores(Score[] scores, int writeIndex)
	{
		int maxNameLen = scores[0].name().length();
		int maxScoreLen = Integer.toString(scores[0].score()).length();

		int maxScoresCanBeDisplayed = overlay.innerHeight() - writeIndex;
		int numScoresDisplayed = this.maxHighscoreEntries < 0 ? maxScoresCanBeDisplayed : Math.min(maxScoresCanBeDisplayed, this.maxHighscoreEntries);
		numScoresDisplayed = Math.min(numScoresDisplayed, scores.length);

		for (int i = 0; i < numScoresDisplayed; i++)
			maxNameLen = Math.max(maxNameLen, scores[i].name().length());

		for (int i = 0; i < numScoresDisplayed; i++)
			overlay.writeLeft(formatScore(scores[i], i + 1, numScoresDisplayed, maxNameLen, maxScoreLen), writeIndex + i);
	}

	/**
	 * Formats a score line
	 * @param s the score to format
	 * @param place the place in the leaderboard (top n)
	 * @param maxPlace the maximum place to be displayed
	 * @param maxNameLen the maximum name length of the scores
	 * @param maxScoreLen the maximum score digit length of the scores
	 * @return A string with the score formatted
	 */
	private String formatScore(Score s, int place, int maxPlace, int maxNameLen, int maxScoreLen)
	{
		int maxNumLen = Integer.toString(maxPlace).length();

		StringBuilder builder = new StringBuilder();
		builder.append(" ");
		builder.append(place);
		builder.append(". ");
		for (int i = 0; i < maxNumLen - Integer.toString(place).length(); i++)
			builder.append(" ");
		builder.append(s.name());
		for (int i = 0; i < maxNameLen - s.name().length() + 2; i++)
			builder.append(" ");
		builder.append(s.score());
		for (int i = 0; i < maxScoreLen - Integer.toString(s.score()).length() + 2; i++)
			builder.append(" ");
		builder.append(String.format("%02d", s.date().getDayOfMonth()));
		builder.append("/");
		builder.append(String.format("%02d", s.date().getMonthValue()));
		builder.append("/");
		builder.append(s.date().getYear());
		return builder.toString();
	}

	@Override
	public void start()
	{
		drawOverlay(this.maxHighscoreEntries);
		initPanel();
	}

	/**
	 * Initializes the graphic panel
	 */
	private void initPanel()
	{
		panel.setOpaque(true);
		panel.setBackground(Color.black);

		Dimension titleSize = generateTitleLabel();
		generateHighscoresList(titleSize);
	}

	/**
	 * Generates the title label
	 * @return the dimension of the label
	 */
	private Dimension generateTitleLabel()
	{
		JLabel title = new JLabel(HIGHSCORE_TITLE_STR);
		panel.add(title);

		int titleTextSize = (int)((panel.width() / HIGHSCORE_TITLE_STR.length()) / 2);
		title.setFont(new Font("SansSerif", Font.BOLD, titleTextSize));
		title.setForeground(Color.white);

		Dimension titleSize = title.getPreferredSize();
		int x = (panel.width() / 2) - (titleSize.width / 2);
		int y = (titleSize.height / 2);
		title.setBounds(x, y, titleSize.width, titleSize.height);

		return titleSize;
	}

	/**
	 * Generates the highscores list
	 * @param titleSize the dimension of the title
	 */
	private void generateHighscoresList(Dimension titleSize)
	{
		Score[] scores = null;

		try
		{
			scores = highscoresReader.getScores();
		}
		catch (SnakeGameException e)
		{
			generateErrorReadingLabel();
			return;
		}

		if (scores == null)
		{
			generateNoScoresLabel();
			return;
		}

		generateList(scores, titleSize);
	}

	/**
	 * Generates the list of scores
	 * @param scores the scores to populate the list
	 * @param titleSize the size of the title
	 */
	private void generateList(Score[] scores, Dimension titleSize)
	{
		String[] scoreStrs = getScoreStrings(scores);
		JList<String> highscoresList = new JList<String>(scoreStrs);
		JScrollPane scrollPane = new JScrollPane(highscoresList);
		panel.add(scrollPane);

		int fontSize = (int)((panel.width() / HIGHSCORE_TITLE_STR.length()) / 3);
		highscoresList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
		highscoresList.setBackground(Color.black);
		highscoresList.setForeground(Color.white);

		Dimension listDim = highscoresList.getPreferredSize();
		int x = (panel.width() / 2) - (listDim.width / 2);
		int y = (int)(titleSize.height * 2);
		highscoresList.setBounds(x, y, listDim.width, listDim.height);

		highscoresList.setSelectionModel(new DefaultListSelectionModel()
		{
			@Override
			public void setSelectionInterval(int index0, int index1) { super.setSelectionInterval(-1, -1); }
		});
		highscoresList.setFocusable(false);

		int heightBound = panel.height() - (2 * y);
		int widthBound = Math.min((int)(listDim.width * 1.2), (int)(panel.width() * 0.9));
		scrollPane.setBounds((panel.width() / 2) - (widthBound / 2), y, widthBound, heightBound);
		scrollPane.setBorder(null);
		configureScrollbar(scrollPane.getVerticalScrollBar());
		configureScrollbar(scrollPane.getHorizontalScrollBar());
	}

	/**
	 * Configures the scrollbar for the list of scores
	 * @param scrollBar the scrollbar to configure
	 */
	private void configureScrollbar(JScrollBar scrollBar)
	{
		scrollBar.setBackground(Color.darkGray);
		scrollBar.setUI(new BasicScrollBarUI()
		{
			@Override
			protected void configureScrollBarColors() { this.thumbColor = Color.white; }

			@Override
			protected JButton createDecreaseButton(int orientation) { return createZeroButton(); }

			@Override
			protected JButton createIncreaseButton(int orientation) { return createZeroButton(); }

			/**
			 * Creates a zero-size button
			 * @return the zero-size button
			 */
			private JButton createZeroButton()
			{
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(0, 0));
				button.setMinimumSize(new Dimension(0, 0));
				button.setMaximumSize(new Dimension(0, 0));
				return button;
			}
		});
	}

	/**
	 * Generates the strings of scores
	 * @param scores the scores to generate the strings with
	 * @return the string representations of the scores
	 */
	private String[] getScoreStrings(Score[] scores)
	{
		int maxNameLen = scores[0].name().length();
		int maxScoreLen = Integer.toString(scores[0].score()).length();

		int numScoresDisplayed = this.maxHighscoreEntries < 0 ? scores.length : Math.min(this.maxHighscoreEntries, scores.length);

		for (int i = 0; i < numScoresDisplayed; i++)
			maxNameLen = Math.max(maxNameLen, scores[i].name().length());

		ArrayList<String> scoreStrs = new ArrayList<String>();
		for (int i = 0; i < numScoresDisplayed; i++)
			scoreStrs.add(formatScore(scores[i], i + 1, numScoresDisplayed, maxNameLen, maxScoreLen));
		return scoreStrs.toArray(new String[0]);
	}

	/**
	 * Generates the error message when there was an error reading from file
	 */
	private void generateErrorReadingLabel()
	{
		JLabel label = new JLabel(ERR_READING_STR);
		panel.add(label);

		int labelTextSize = (int)((panel.width() / ERR_READING_STR.length()) * 2);
		label.setFont(new Font("SansSerif", Font.PLAIN, labelTextSize));
		label.setForeground(Color.red);

		Dimension labelSize = label.getPreferredSize();
		int x = (panel.width() / 2) - (labelSize.width / 2);
		int y = (panel.height() / 2) - (labelSize.height / 2);
		label.setBounds(x, y, labelSize.width, labelSize.height);
	}

	/**
	 * Generates the error message when there are no scores
	 */
	private void generateNoScoresLabel()
	{
		JLabel label = new JLabel(ERR_NO_SCORES_STR + " Go set some!");
		panel.add(label);

		int labelTextSize = (int)((panel.width() / ERR_READING_STR.length()) * 2);
		label.setFont(new Font("SansSerif", Font.PLAIN, labelTextSize));
		label.setForeground(Color.lightGray);

		Dimension labelSize = label.getPreferredSize();
		int x = (panel.width() / 2) - (labelSize.width / 2);
		int y = (panel.height() / 2) - (labelSize.height / 2);
		label.setBounds(x, y, labelSize.width, labelSize.height);
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
