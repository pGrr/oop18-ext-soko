package view;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import controller.SokobanController;
import static view.Views.*;

public class InitialViewImpl extends AbstractView implements InitialView {

	private static final double HEIGHT_TO_SCREENSIZE_RATIO = 0.8;
	private static final double WIDTH_TO_HEIGHT_RATIO = 1;
	private static final int DEFAULT_PADDING = 20;
	private static final String ICON_CRAFT = "craft.png";
	private static final String ICON_OK = "ok.png";
	private static final String ICON_UP = "arrow-up.png";
	private static final String ICON_DOWN = "arrow-down.png";
	private static final String ICON_PLUS = "plus-30px.png";
	private static final String ICON_MINUS = "minus-30px.png";
	private static final String ICON_RESET = "cross.png";
	private static final String ICON_DOWNLOAD = "download.png";
	private static final String ICON_UPLOAD = "upload.png";
	private static final String TITLE = "SOKOBAN - InitialView";
	private static final String PANEL_LEVEL_SEQUENCE_TITLE = "Level sequence";
	private static final String PANEL_EDIT_LEVEL_SEQUENCE_TITLE = "Load levels, remove them or change their orders into the sequence";
	private static final String PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE = "Save current sequence or load one";
	private static final String LABEL_WELCOME_TEXT = "Welcome to Sokoban! What would you like to do?";
	private static final String BUTTON_CRAFT_TEXT = "CRAFT A LEVEL";
	private static final String BUTTON_PLAY_TEXT = "PLAY";
	private static final String DIALOG_LEVEL_NOT_CORRECT_TITLE = "LEVEL NOT CORRECT";
	private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";

	private final SokobanController controller;
	private final List<String> levels;
	
	public InitialViewImpl(SokobanController controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		// --------- fakeData for testing purposes ---------
		String[] fakeData = {"Prova1", "Prova2", "Prova3", "Prova1", "Prova2", "Prova3", "Prova1", "Prova2", "Prova3", "Prova1", "Prova2", "Prova3"};
		// -------------------------------------------------
		this.levels = Arrays.asList(fakeData);
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
		p.add(levelListPanel());
		p.add(saveOrLoadListPanel());
		return p;
	}
	
	private JPanel choicesPanel() {
		int littlePadding = Math.round(DEFAULT_PADDING / 5);
		JPanel p = new JPanel(new BorderLayout(littlePadding, littlePadding));
		p.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		JButton craftButton = createButton(BUTTON_CRAFT_TEXT, ICON_CRAFT, craftButtonActionListener());
		p.add(craftButton, BorderLayout.PAGE_START);
		JButton playButton = createButton(BUTTON_PLAY_TEXT, ICON_OK, playButtonActionListener());
		p.add(playButton, BorderLayout.PAGE_END);
		return p;
	}
	
	private JPanel levelListPanel() {
		JPanel p = new JPanel(new BorderLayout());
		JPanel levelListPanel = new JPanel(new BorderLayout());
		levelListPanel.setBorder(createTitledPaddingBorder(PANEL_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		DefaultListModel<String> listModel = new DefaultListModel<>();
		listModel.addAll(this.levels);
		JList<String> levelList = new JList<String>(listModel);
		JScrollPane scrollPane = new JScrollPane(levelList); 
		levelListPanel.add(scrollPane);
		JPanel editListPanel = new JPanel();
		JButton addLevelButton = createButton("", ICON_PLUS, addLevelButtonActionListener());
		editListPanel.add(addLevelButton);
		JButton removeLevelButton = createButton("", ICON_MINUS, removeLevelButtonActionListener(levelList, listModel));
		editListPanel.add(removeLevelButton);
		editListPanel.setBorder(createTitledPaddingBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton upButton = createButton("", ICON_UP, upButtonActionListener(levelList, listModel));
		editListPanel.add(upButton);
		JButton downButton = createButton("", ICON_DOWN, downButtonActionListener(levelList, listModel));
		editListPanel.add(downButton);
		JButton cancelButton = createButton("", ICON_RESET, resetButtonActionListener(listModel));
		editListPanel.add(cancelButton);
		p.add(levelListPanel, BorderLayout.CENTER);
		p.add(editListPanel, BorderLayout.PAGE_END);
		return p;
	}

	private JPanel saveOrLoadListPanel() {
		JPanel p = new JPanel();
		p.setBorder(createTitledPaddingBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton saveButton = createButton("", ICON_DOWNLOAD, saveButtonActionListener());
		p.add(saveButton);
		JButton loadButton = createButton("", ICON_UPLOAD, loadButtonActionListener());
		p.add(loadButton);
		return p;
	}
	
	private ActionListener craftButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.controller.craftLevelButtonPressed();
		});
	}
	
	private ActionListener playButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			//TODO
		});
	}

	private ActionListener addLevelButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelFileDescription(), controller.getLevelFileExtension());
			fc.showSaveDialog(getFrame());
		});
	}
	
	private ActionListener removeLevelButtonActionListener(JList<String> levelList, DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			listModel.remove(levelList.getSelectedIndex());
		});
	}
	
	private ActionListener upButtonActionListener(JList<String> levelList, DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			if (levelList.getSelectedIndex() > 0) {
				Collections.swap(this.levels, levelList.getSelectedIndex(), levelList.getSelectedIndex() - 1);
				listModel.removeAllElements();
				listModel.addAll(InitialViewImpl.this.levels);
			}
		});
	}
	
	private ActionListener downButtonActionListener(JList<String> levelList, DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			levelList.getSelectedIndex();
			if (levelList.getSelectedIndex() + 1 < listModel.size()) {
				Collections.swap(this.levels, levelList.getSelectedIndex(), levelList.getSelectedIndex() + 1);
				listModel.removeAllElements();
				listModel.addAll(InitialViewImpl.this.levels);
			}
		});
	}
	
	private ActionListener resetButtonActionListener(DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			listModel.removeAllElements();
		});
	}
	
	private ActionListener saveButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelSequenceFileDescription(), controller.getLevelSequenceFileExtension());
			fc.showSaveDialog(getFrame());
			// TODO
			// controller.saveLevelSequence(this.levels, fc.getSelectedFile().getAbsolutePath());
		});
	}
	
	private ActionListener loadButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelSequenceFileDescription(), controller.getLevelSequenceFileExtension());
			fc.showOpenDialog(getFrame());
			// TODO
			// controller.load(fc.getSelectedFile().getAbsolutePath());
		});
	}

}