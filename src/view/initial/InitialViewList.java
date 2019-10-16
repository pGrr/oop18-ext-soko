package view.initial;

import static view.Components.*;
import static view.initial.InitialViewConstants.*;
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

public class InitialViewList {
	
	private final InitialViewContainer owner;
	private final SokobanController controller;
	private final InitialViewSaveOrLoad saveOrLoad;
	private final JPanel panel;
	private final JList<String> levelList;
	private final DefaultListModel<String> listModel;
	private Optional<LevelSequence> levelSequence;
	
	public InitialViewList(InitialViewContainer owner, SokobanController controller, Optional<LevelSequence> levelSequence) {
		this.owner = owner;
		this.controller = controller;
		this.saveOrLoad = new InitialViewSaveOrLoad(this.controller, this.owner, this);
		this.listModel = new DefaultListModel<>();
		this.levelList = new JList<>(this.listModel);
		this.levelSequence = levelSequence;
		this.panel = createPanel(levelSequence.isPresent() ? levelSequence.get().getNames() : new ArrayList<>());
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
	
	public DefaultListModel<String> getListModel() {
		return this.listModel;
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
		JButton addLevelButton = createButton("", ICON_PLUS, addLevelAction());
		editListPanel.add(addLevelButton);
		JButton removeLevelButton = createButton("", ICON_MINUS, removeSelectedAction());
		editListPanel.add(removeLevelButton);
		editListPanel.setBorder(createTitledPaddingBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton upButton = createButton("", ICON_UP, moveUpAction());
		editListPanel.add(upButton);
		JButton downButton = createButton("", ICON_DOWN, moveDownAction());
		editListPanel.add(downButton);
		JButton cancelButton = createButton("", ICON_RESET, removeAllAction());
		editListPanel.add(cancelButton);
		p2.add(editListPanel);
		// save or load panel	
		JPanel saveOrLoadListPanel = this.saveOrLoad.createPanel();
		p2.add(saveOrLoadListPanel);
		p.add(p2, BorderLayout.PAGE_END);
		return p;	
	}

	private ActionListener addLevelAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = createFileChooser(controller.getLevelFileDescription(), controller.getLevelFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			String path = fc.getSelectedFile().getAbsolutePath();
			this.listModel.addElement(path);
		});
	}
	
	private ActionListener moveUpAction() {
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
	
	private ActionListener moveDownAction() {
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
	
	private ActionListener removeSelectedAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.listModel.remove(this.levelList.getSelectedIndex());
		});
	}
	
	private ActionListener removeAllAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.listModel.removeAllElements();
		});
	}

}
