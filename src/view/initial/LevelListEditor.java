package view.initial;

import static view.Views.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import controller.SokobanController;
import model.LevelSchemaImpl.LevelNotValidException;
import model.LevelSequence;

public class LevelListEditor {
	
	private static final String PANEL_LEVEL_SEQUENCE_TITLE = "Level sequence";
	private static final String PANEL_EDIT_LEVEL_SEQUENCE_TITLE = "Load levels, remove them or change their orders into the sequence";
	private static final String PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE = "Save current sequence or load one";
	private static final String ICON_UP = "icons/arrow-up.png";
	private static final String ICON_DOWN = "icons/arrow-down.png";
	private static final String ICON_PLUS = "icons/plus-30px.png";
	private static final String ICON_MINUS = "icons/minus-30px.png";
	private static final String ICON_RESET = "icons/cross.png";
	private static final String ICON_DOWNLOAD = "icons/download.png";
	private static final String ICON_UPLOAD = "icons/upload.png";
	private static final String DIALOG_CLASS_NOT_FOUND_TITLE = "CLASS NOT FOUND";
	private static final String DIALOG_CLASS_NOT_FOUND_TEXT = "Loaded file is corrupted.";
	private static final String DIALOG_ERROR_TITLE = "ERROR";
	private static final String DIALOG_IOERROR_TEXT = "An error occurred during an input/output operation";

	private final InitialViewImpl owner;
	private final SokobanController controller;
	private final JPanel panel;
	private final JList<String> levelList;
	private final DefaultListModel<String> listModel;
	private Optional<LevelSequence> levelSequence;
	
	public LevelListEditor(InitialViewImpl owner, SokobanController controller, List<String> levels) {
		this.owner = owner;
		this.controller = controller;
		this.listModel = new DefaultListModel<>();
		this.levelList = new JList<>(this.listModel);
		this.panel = createPanel(levels);
	}

	public JPanel getPanel() {
		return this.panel;
	}
	
	public final LevelSequence getLevelSequence() throws ClassNotFoundException, LevelNotValidException, IOException {
		if (this.levelSequence.isPresent()) {
			return this.levelSequence.get();
		} else {
			return this.controller.createLevelSequence("", this.getLevelsAsStringList());
		}
	}
	
	public final List<String> getLevelsAsStringList() {
		List<String> levels = new ArrayList<>();
		IntStream.range(0, this.listModel.getSize())
		.forEach(i -> levels.add(this.listModel.getElementAt(i)));				
		return levels;
	}
	
	protected JPanel createPanel(List<String> levels) {
		JPanel p = new JPanel(new BorderLayout());
		// level list panel
		JPanel levelListPanel = new JPanel(new BorderLayout());
		levelListPanel.setBorder(createTitledPaddingBorder(PANEL_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		levels.forEach(this.listModel::addElement);
		JScrollPane scrollPane = new JScrollPane(this.levelList); 
		levelListPanel.add(scrollPane);
		p.add(levelListPanel, BorderLayout.CENTER);
		// edit list panel
		JPanel p2 = new JPanel(new GridLayout(2,1));
		JPanel editListPanel = new JPanel();
		JButton addLevelButton = createButton("", ICON_PLUS, addLevelButtonActionListener());
		editListPanel.add(addLevelButton);
		JButton removeLevelButton = createButton("", ICON_MINUS, removeLevelButtonActionListener());
		editListPanel.add(removeLevelButton);
		editListPanel.setBorder(createTitledPaddingBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton upButton = createButton("", ICON_UP, upButtonActionListener());
		editListPanel.add(upButton);
		JButton downButton = createButton("", ICON_DOWN, downButtonActionListener());
		editListPanel.add(downButton);
		JButton cancelButton = createButton("", ICON_RESET, resetButtonActionListener());
		editListPanel.add(cancelButton);
		p2.add(editListPanel);
		// save or load panel	
		JPanel saveOrLoadListPanel = new JPanel();
		saveOrLoadListPanel.setBorder(createTitledPaddingBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton saveButton = createButton("", ICON_DOWNLOAD, saveSequenceActionListener());
		saveOrLoadListPanel.add(saveButton);
		JButton loadButton = createButton("", ICON_UPLOAD, loadSequenceActionListener());
		saveOrLoadListPanel.add(loadButton);
		p2.add(saveOrLoadListPanel);
		p.add(p2, BorderLayout.PAGE_END);
		return p;	
	}

	private ActionListener addLevelButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelFileDescription(), controller.getLevelFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			String path = fc.getSelectedFile().getAbsolutePath();
			this.listModel.addElement(path);
		});
	}
	
	private ActionListener removeLevelButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.listModel.remove(this.levelList.getSelectedIndex());
		});
	}
	
	private ActionListener upButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.levelList.getSelectedIndex() > 0) {
				int selectedIndex = this.levelList.getSelectedIndex();
				String tmp = this.listModel.get(selectedIndex);
				this.listModel.set(selectedIndex, this.listModel.get(selectedIndex - 1));
				this.listModel.set(selectedIndex - 1, tmp);				 
				this.levelList.setSelectedIndex(selectedIndex - 1);
			}
		});
	}
	
	private ActionListener downButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.levelList.getSelectedIndex();
			if (this.levelList.getSelectedIndex() + 1 < this.listModel.size()) {
				int selectedIndex = this.levelList.getSelectedIndex();
				String tmp = this.listModel.get(selectedIndex);
				this.listModel.set(selectedIndex, this.listModel.get(selectedIndex + 1));
				this.listModel.set(selectedIndex + 1, tmp);
				this.levelList.setSelectedIndex(selectedIndex + 1);
			}
		});
	}
	
	private ActionListener resetButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.listModel.removeAllElements();
		});
	}
	
	private ActionListener saveSequenceActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelSequenceFileDescription(), controller.getLevelSequenceFileExtension());
			fc.showSaveDialog(this.owner.getFrame());
			try {
				String fileName = fc.getSelectedFile().getAbsolutePath() + this.controller.getLevelSequenceFileExtension();
				this.controller.saveLevelSequence(fileName, getLevelsAsStringList());
			} catch (LevelNotValidException levelNotValidException) {
				this.owner.showLevelNotValidDialog();
			} catch (IOException ioException) {
				this.owner.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(e);
			} catch (ClassNotFoundException classNotFoundException) {
				this.owner.showErrorDialog(DIALOG_CLASS_NOT_FOUND_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT);
				System.err.println(classNotFoundException);
			}
		});
	}
	
	private ActionListener loadSequenceActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelSequenceFileDescription(), controller.getLevelSequenceFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			try {
				this.levelSequence = Optional.of(this.controller.loadLevelSequence(fc.getSelectedFile().getAbsolutePath()));
				List<String> paths = this.levelSequence.get().getNames();
				paths.stream().forEach(this.listModel::addElement);
			} catch (IOException ioException) {
				this.owner.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(ioException);
			} catch (ClassNotFoundException classNotFoundException) {
				this.owner.showErrorDialog(DIALOG_CLASS_NOT_FOUND_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT);
				System.err.println(classNotFoundException);
			}
		});
	}
}
