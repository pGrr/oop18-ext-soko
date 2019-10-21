package view.initial;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialConstants.*;
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

public class LevelList {
	
	private final InitialWindowImpl owner;
	private final JPanel panel;
	private final JList<String> levelList;
	private final DefaultListModel<String> listModel;
	private LevelSequence levelSequence;

	public LevelList(InitialWindowImpl owner) {
		this.owner = owner;
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
		String path;
		try {
			path = URLDecoder.decode(ClassLoader.getSystemResource(DEFAULT_LEVEL_SEQUENCE).getPath(), "UTF-8");
			this.levelSequence = ControllerFacade.getInstance().getSequenceController().loadLevelSequence(path);
		} catch (ClassNotFoundException e1) {
			this.owner.showClassNotFoundErrorDialog();
		} catch (IOException e2) {
			this.owner.showIOErrorDialog();
		}
		this.updateListModel();
	}
	
	protected JPanel createPanel(List<String> levels) {
		JPanel p = new JPanel(new BorderLayout());
		// level list panel
		JPanel levelListPanel = new JPanel(new BorderLayout());
		levelListPanel.setBorder(GuiComponentFactory.getDefaultInstance().createTitledPaddingBorder(PANEL_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		levels.forEach(this.listModel::addElement);
		JScrollPane scrollPane = new JScrollPane(this.levelList); 
		levelListPanel.add(scrollPane);
		p.add(levelListPanel, BorderLayout.CENTER);
		// edit list panel
		JPanel p2 = new JPanel(new GridLayout(2,1));
		JPanel editListPanel = new JPanel();
		JButton addLevelButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_PLUS, addLevel());
		editListPanel.add(addLevelButton);
		JButton removeLevelButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_MINUS, removeSelected());
		editListPanel.add(removeLevelButton);
		editListPanel.setBorder(GuiComponentFactory.getDefaultInstance().createTitledPaddingBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton upButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_UP, moveUp());
		editListPanel.add(upButton);
		JButton downButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_DOWN, moveDown());
		editListPanel.add(downButton);
		JButton cancelButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_RESET, removeAll());
		editListPanel.add(cancelButton);
		p2.add(editListPanel);
		// save or load panel	
		JPanel saveOrLoadListPanel = this.owner.getSaveLoadSequence().createPanel();
		p2.add(saveOrLoadListPanel);
		p.add(p2, BorderLayout.PAGE_END);
		return p;	
	}
	
	public void updateListModel() {
		this.listModel.removeAllElements();
		this.levelSequence.getLevelNames().forEach(listModel::addElement);
	}
	
	public void setLevelSequence(LevelSequence sequence) {
		this.levelSequence = sequence;
	}

	private ActionListener addLevel() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = GuiComponentFactory.getDefaultInstance().createFileChooser(
					ControllerFacade.getInstance().getLevelController().getLevelFileDescription(), 
					ControllerFacade.getInstance().getLevelController().getLevelFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			String path = fc.getSelectedFile().getAbsolutePath();
			try {
				this.levelSequence.add(ControllerFacade.getInstance().getLevelController().loadLevel(path));
			} catch (ClassNotFoundException e1) {
				this.owner.showClassNotFoundErrorDialog();
			} catch (LevelNotValidException e1) {
				this.owner.showLevelInvalidDialog(e1.toString());
			} catch (IOException e1) {
				this.owner.showIOErrorDialog();
			}
			updateListModel();
		});
	}
	
	private ActionListener moveUp() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.levelList.getSelectedIndex() > 0) {
				int selectedIndex = this.levelList.getSelectedIndex();
				this.levelSequence.swap(selectedIndex, selectedIndex - 1);
				updateListModel();
				levelList.setSelectedIndex(selectedIndex - 1);
			}
		});
	}
	
	private ActionListener moveDown() {
		return e -> SwingUtilities.invokeLater(() -> {
			if (this.levelList.getSelectedIndex() > 0) {
				int selectedIndex = this.levelList.getSelectedIndex();
				this.levelSequence.swap(selectedIndex, selectedIndex + 1);
				updateListModel();
				levelList.setSelectedIndex(selectedIndex + 1);
			}
		});
	}
	
	private ActionListener removeSelected() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.levelSequence.remove(this.levelList.getSelectedIndex());
			updateListModel();
		});
	}
	
	private ActionListener removeAll() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.levelSequence.clear();
			updateListModel();
		});
	}

}