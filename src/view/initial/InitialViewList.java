package view.initial;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialViewConstants.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import controller.ControllerFacade;
import model.level.LevelSchemaImpl.LevelNotValidException;
import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;
import view.GuiComponentFactory;

public class InitialViewList {
	
	private final InitialViewWindowImpl owner;
	private final InitialViewSaveOrLoad saveOrLoad;
	private final JPanel panel;
	private final GuiComponentFactory componentFactory;
	private final JList<String> levelList;
	private final DefaultListModel<String> listModel;
	private LevelSequence levelSequence;

	public InitialViewList(InitialViewWindowImpl owner) {
		this.owner = owner;
		this.componentFactory = GuiComponentFactory.getDefaultInstance();
		this.saveOrLoad = new InitialViewSaveOrLoad(this.owner, this);
		this.listModel = new DefaultListModel<>();
		this.levelList = new JList<>(this.listModel);
		this.levelSequence = LevelSequenceImpl.createEmpty();
		this.panel = createPanel(levelSequence.getLevelNames());	
	}

	public JPanel getPanel() {
		return this.panel;
	}
	
	public final LevelSequence getLevelSequence() {
		return this.levelSequence;
	}
	
	public DefaultListModel<String> getListModel() {
		return this.listModel;
	}
	
	public final List<String> getLevelNames() {
		return levelSequence.getLevelNames();
	}
	
	public void loadDefaultLevelSequence() {
		try {
			String path = URLDecoder.decode(ClassLoader.getSystemResource(DEFAULT_LEVEL_SEQUENCE).getPath(), "UTF-8");
			this.levelSequence = ControllerFacade.getInstance().loadLevelSequence(path);
			this.updateListModel();
		} catch (Exception e) {
			GuiComponentFactory.getDefaultInstance().createErrorDialog(DIALOG_ERROR_TITLE, DIALOG_DEFAULT_LEVEL_SEQUENCE_ERROR_TEXT).setVisible(true);;
			e.printStackTrace();
		}
	}
	
	protected JPanel createPanel(List<String> levels) {
		JPanel p = new JPanel(new BorderLayout());
		// level list panel
		JPanel levelListPanel = new JPanel(new BorderLayout());
		levelListPanel.setBorder(componentFactory.createTitledPaddingBorder(PANEL_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		levels.forEach(this.listModel::addElement);
		JScrollPane scrollPane = new JScrollPane(this.levelList); 
		levelListPanel.add(scrollPane);
		p.add(levelListPanel, BorderLayout.CENTER);
		// edit list panel
		JPanel p2 = new JPanel(new GridLayout(2,1));
		JPanel editListPanel = new JPanel();
		JButton addLevelButton = componentFactory.createButton("", ICON_PLUS, addLevelAction());
		editListPanel.add(addLevelButton);
		JButton removeLevelButton = componentFactory.createButton("", ICON_MINUS, removeSelectedAction());
		editListPanel.add(removeLevelButton);
		editListPanel.setBorder(componentFactory.createTitledPaddingBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton upButton = componentFactory.createButton("", ICON_UP, moveUpAction());
		editListPanel.add(upButton);
		JButton downButton = componentFactory.createButton("", ICON_DOWN, moveDownAction());
		editListPanel.add(downButton);
		JButton cancelButton = componentFactory.createButton("", ICON_RESET, removeAllAction());
		editListPanel.add(cancelButton);
		p2.add(editListPanel);
		// save or load panel	
		JPanel saveOrLoadListPanel = this.saveOrLoad.createPanel();
		p2.add(saveOrLoadListPanel);
		p.add(p2, BorderLayout.PAGE_END);
		return p;	
	}
	
	private void updateListModel() {
		this.listModel.removeAllElements();
		this.levelSequence.getLevelNames().forEach(listModel::addElement);
	}

	private ActionListener addLevelAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = componentFactory.createFileChooser(ControllerFacade.getInstance().getLevelFileDescription(), ControllerFacade.getInstance().getLevelFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			String path = fc.getSelectedFile().getAbsolutePath();
				try {
					this.levelSequence.add(ControllerFacade.getInstance().loadLevel(path));
				} catch (ClassNotFoundException | LevelNotValidException | IOException exc) {
					GuiComponentFactory.getDefaultInstance().createErrorDialog(DIALOG_ERROR_TITLE, DIALOG_ERROR_LOAD_LEVEL_TEXT).setVisible(true);
					exc.printStackTrace();
				}
				updateListModel();
		});
	}
	
	private ActionListener moveUpAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.levelList.getSelectedIndex() > 0) {
				int selectedIndex = this.levelList.getSelectedIndex();
				this.levelSequence.swap(selectedIndex, selectedIndex - 1);
				updateListModel();
				levelList.setSelectedIndex(selectedIndex - 1);
			}
		});
	}
	
	private ActionListener moveDownAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.levelList.getSelectedIndex() > 0) {
				int selectedIndex = this.levelList.getSelectedIndex();
				this.levelSequence.swap(selectedIndex, selectedIndex + 1);
				updateListModel();
				levelList.setSelectedIndex(selectedIndex + 1);
			}
		});
	}
	
	private ActionListener removeSelectedAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.levelSequence.remove(this.levelList.getSelectedIndex());
			updateListModel();
		});
	}
	
	private ActionListener removeAllAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.levelSequence.clear();
			updateListModel();
		});
	}

}
