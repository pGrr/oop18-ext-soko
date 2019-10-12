package view.initial;

import static view.Views.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import controller.SokobanController;
import model.LevelSchemaImpl.LevelNotValidException;

public class LevelList {
	
	private static final String PANEL_LEVEL_SEQUENCE_TITLE = "Level sequence";
	private static final String PANEL_EDIT_LEVEL_SEQUENCE_TITLE = "Load levels, remove them or change their orders into the sequence";
	private static final String PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE = "Save current sequence or load one";
	private static final String ICON_UP = "arrow-up.png";
	private static final String ICON_DOWN = "arrow-down.png";
	private static final String ICON_PLUS = "plus-30px.png";
	private static final String ICON_MINUS = "minus-30px.png";
	private static final String ICON_RESET = "cross.png";
	private static final String ICON_DOWNLOAD = "download.png";
	private static final String ICON_UPLOAD = "upload.png";
	private static final String DIALOG_CLASS_NOT_FOUND_TITLE = "CLASS NOT FOUND";
	private static final String DIALOG_CLASS_NOT_FOUND_TEXT = "Loaded file is corrupted.";
	private static final String DIALOG_ERROR_TITLE = "ERROR";
	private static final String DIALOG_IOERROR_TEXT = "An error occurred during an input/output operation";

	private final InitialViewImpl owner;
	private final SokobanController controller;
	private final List<String> levels;
	private final JPanel panel;
	
	public LevelList(InitialViewImpl owner, SokobanController controller, List<String> levelList) {
		this.owner = owner;
		this.controller = controller;
		this.levels = levelList;
		this.panel = createPanel();
	}

	protected JPanel createPanel() {
		JPanel p = new JPanel(new BorderLayout());
		// level list panel
		JPanel levelListPanel = new JPanel(new BorderLayout());
		levelListPanel.setBorder(createTitledPaddingBorder(PANEL_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		DefaultListModel<String> listModel = new DefaultListModel<>();
		this.levels.forEach(listModel::addElement);
		JList<String> levelList = new JList<String>(listModel);
		JScrollPane scrollPane = new JScrollPane(levelList); 
		levelListPanel.add(scrollPane);
		p.add(levelListPanel, BorderLayout.CENTER);
		// edit list panel
		JPanel p2 = new JPanel(new GridLayout(2,1));
		JPanel editListPanel = new JPanel();
		JButton addLevelButton = createButton("", ICON_PLUS, addLevelButtonActionListener(listModel));
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
		p2.add(editListPanel);
		// save or load panel	
		JPanel saveOrLoadListPanel = new JPanel();
		saveOrLoadListPanel.setBorder(createTitledPaddingBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton saveButton = createButton("", ICON_DOWNLOAD, saveSequenceActionListener(this.levels));
		saveOrLoadListPanel.add(saveButton);
		JButton loadButton = createButton("", ICON_UPLOAD, loadSequenceActionListener(listModel));
		saveOrLoadListPanel.add(loadButton);
		p2.add(saveOrLoadListPanel);
		p.add(p2, BorderLayout.PAGE_END);
		return p;	
	}

	public JPanel getPanel() {
		return this.panel;
	}
	
	private ActionListener addLevelButtonActionListener(DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelFileDescription(), controller.getLevelFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			String path = fc.getSelectedFile().getAbsolutePath();
			this.levels.add(path);
			listModel.addElement(path);
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
				int selectedIndex = levelList.getSelectedIndex();
				Collections.swap(this.levels, selectedIndex, selectedIndex - 1);
				String tmp = listModel.get(selectedIndex);
				listModel.set(selectedIndex, listModel.get(selectedIndex - 1));
				listModel.set(selectedIndex - 1, tmp);
				levelList.setSelectedIndex(selectedIndex - 1);
			}
		});
	}
	
	private ActionListener downButtonActionListener(JList<String> levelList, DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			levelList.getSelectedIndex();
			if (levelList.getSelectedIndex() + 1 < listModel.size()) {
				int selectedIndex = levelList.getSelectedIndex();
				Collections.swap(this.levels, selectedIndex, selectedIndex + 1);
				String tmp = listModel.get(selectedIndex);
				listModel.set(selectedIndex, listModel.get(selectedIndex + 1));
				listModel.set(selectedIndex + 1, tmp);
				levelList.setSelectedIndex(selectedIndex + 1);
			}
		});
	}
	
	private ActionListener resetButtonActionListener(DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			listModel.removeAllElements();
		});
	}
	
	private ActionListener saveSequenceActionListener(List<String> levels) {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelSequenceFileDescription(), controller.getLevelSequenceFileExtension());
			fc.showSaveDialog(this.owner.getFrame());
			try {
				String fileName = fc.getSelectedFile().getAbsolutePath() + this.controller.getLevelSequenceFileExtension();
				this.controller.saveLevelSequence(fileName, levels);
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
	
	private ActionListener loadSequenceActionListener(DefaultListModel<String> listModel) {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelSequenceFileDescription(), controller.getLevelSequenceFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			try {
				List<String> paths = this.controller.loadLevelSequence(fc.getSelectedFile().getAbsolutePath()).getNames();
				paths.stream().forEach(this.levels::add);
				paths.stream().forEach(listModel::addElement);
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
