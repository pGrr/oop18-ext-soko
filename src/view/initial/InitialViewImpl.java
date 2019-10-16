package view.initial;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.SokobanController;
import model.LevelSchemaImpl.LevelNotValidException;
import view.AbstractView;

import static view.Views.*;

public class InitialViewImpl extends AbstractView implements InitialView {

	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 0.9;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final int DEFAULT_PADDING = 20;
	private static final String ICON_CRAFT = "icons/craft.png";
	private static final String TITLE = "SOKOBAN - InitialView";
	private static final String LABEL_WELCOME_TEXT = "Welcome to Sokoban! What would you like to do?";
	private static final String BUTTON_CRAFT_TEXT = "CRAFT A LEVEL";
	private static final String BUTTON_PLAY_TEXT = "PLAY";
	private static final String DIALOG_ERROR_TITLE = "ERROR";
	private static final String DIALOG_IOERROR_TEXT = "An error occurred during an input/output operation";
	private static final String DIALOG_LEVEL_NOT_CORRECT_TITLE = "LEVEL NOT CORRECT";
	private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";
	private static final String DIALOG_CLASS_NOT_FOUND_TITLE = "CLASS NOT FOUND";
	private static final String DIALOG_CLASS_NOT_FOUND_TEXT = "Loaded file is corrupted.";
	private static final ImageIcon ICON_PLAY = createImageIcon("icons/ok.png");

	private final SokobanController controller;
	private final LevelListEditor levelListEditor;
	
	public InitialViewImpl(SokobanController controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.levelListEditor = new LevelListEditor(this, this.controller, new ArrayList<>());
		this.getFrame().add(createMainPanel());
	}
	
	public InitialViewImpl(SokobanController controller, List<String> levels) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.levelListEditor = new LevelListEditor(this, this.controller, levels);
		this.getFrame().add(createMainPanel());
	}
	
	@Override
	public void showLevelNotValidDialog() {
		showErrorDialog(DIALOG_LEVEL_NOT_CORRECT_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT);
	}

	@Override
	protected JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(welcomePanel(), BorderLayout.PAGE_START);
		mainPanel.add(levelSequencePanel(), BorderLayout.CENTER);
		mainPanel.add(choicesPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}

	private JPanel welcomePanel() {
		JPanel p = new JPanel();
		p.add(createLabel(LABEL_WELCOME_TEXT));
		return p;
	}

	private JPanel levelSequencePanel() {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		p.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		p.add(this.levelListEditor.getPanel());
		return p;
	}
	
	private JPanel choicesPanel() {
		int littlePadding = Math.round(DEFAULT_PADDING / 5);
		JPanel p = new JPanel(new BorderLayout(littlePadding, littlePadding));
		p.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		JButton craftButton = createButton(BUTTON_CRAFT_TEXT, ICON_CRAFT, craftButtonActionListener());
		p.add(craftButton, BorderLayout.PAGE_START);
		JButton playButton = createButton(BUTTON_PLAY_TEXT, ICON_PLAY, playButtonActionListener());
		p.add(playButton, BorderLayout.PAGE_END);
		return p;
	}
	
	private ActionListener craftButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.controller.craftLevel();
		});
	}
	
	private ActionListener playButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			try {
				this.controller.playLevelSequence(this.levelListEditor.getLevelSequence());
			} catch (LevelNotValidException levelNotValidException) {
				showLevelNotValidDialog();
			} catch (IOException ioException) {
				showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(ioException);
			} catch (ClassNotFoundException classNotFoundException) {
				showErrorDialog(DIALOG_CLASS_NOT_FOUND_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT);
				System.err.println(classNotFoundException);
			}
		});
	}

	@Override
	protected ActionListener errorButtonActionListener(JDialog dialog) {
		return e -> {
			dialog.dispose();
			this.controller.backToInitialView();
		};	}
}