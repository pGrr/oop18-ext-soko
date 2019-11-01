/*
 * 
 */
package view.initial;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import controller.Controller;
import model.Model;
import model.level.Level;
import model.level.LevelNotValidException;
import view.GuiComponentFactory;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the level list in the {@link InitialWindowImpl}
 * window.
 */
public class InitialLevelList {

    private static final String PANEL_LEVEL_SEQUENCE_TITLE = "Level sequence";
    private static final String PANEL_EDIT_LEVEL_SEQUENCE_TITLE = "Load levels, remove them or change their orders into the sequence";
    private static final String ICON_UP = "icons/arrow-up.png";
    private static final String ICON_DOWN = "icons/arrow-down.png";
    private static final String ICON_PLUS = "icons/plus-30px.png";
    private static final String ICON_MINUS = "icons/minus-30px.png";
    private static final String ICON_RESET = "icons/cross.png";

    private final InitialWindowImpl owner;
    private final JPanel panel;
    private final JList<String> levelList;
    private final DefaultListModel<String> listModel;

    /**
     * Instantiates a new initial level list object.
     *
     * @param owner the {@link InitialWindowImpl} object which creates and contains
     *              this object
     */
    public InitialLevelList(final InitialWindowImpl owner) {
        this.owner = owner;
        this.listModel = new DefaultListModel<>();
        this.levelList = new JList<>(this.listModel);
        this.panel = createPanel();
    }

    /**
     * Gets the panel.
     *
     * @return the panel
     */
    public JPanel getPanel() {
        return this.panel;
    }

    /**
     * Gets the list model.
     *
     * @return the list model
     */
    public DefaultListModel<String> getListModel() {
        return this.listModel;
    }

    /**
     * Gets the level names.
     *
     * @return the level names
     */
    public final List<String> getLevelNames() {
        return Model.getInstance().getCurrentState().getAllLevels().stream().map(Level::getName)
                .collect(Collectors.toList());
    }

    /**
     * Syncs the list-model with the model data.
     */
    public void syncListModel() {
        this.listModel.removeAllElements();
        Model.getInstance().getCurrentState().getAllLevels().stream().map(Level::getName)
                .forEach(listModel::addElement);
    }

    /**
     * Creates the panel.
     *
     * @return the j panel
     */
    private JPanel createPanel() {
        JPanel p = new JPanel(new BorderLayout());
        // level list panel
        JPanel levelListPanel = new JPanel(new BorderLayout());
        levelListPanel.setBorder(GuiComponentFactory.getDefaultInstance()
                .createTitledPaddingBorder(PANEL_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
        JScrollPane scrollPane = new JScrollPane(this.levelList);
        levelListPanel.add(scrollPane);
        p.add(levelListPanel, BorderLayout.CENTER);
        // edit list panel
        JPanel p2 = new JPanel(new GridLayout(2, 1));
        JPanel editListPanel = new JPanel();
        JButton addLevelButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_PLUS, addLevel());
        editListPanel.add(addLevelButton);
        JButton removeLevelButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_MINUS,
                removeSelected());
        editListPanel.add(removeLevelButton);
        editListPanel.setBorder(GuiComponentFactory.getDefaultInstance()
                .createTitledPaddingBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
        JButton upButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_UP, move(i -> i - 1));
        editListPanel.add(upButton);
        JButton downButton = GuiComponentFactory.getDefaultInstance().createButton("", ICON_DOWN, move(i -> i + 1));
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

    /**
     * This is the action listener for the "add a level" button. It adds a level to
     * the model and then syncs the list.
     *
     * @return the action listener for the "add a level" button
     */
    private ActionListener addLevel() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = GuiComponentFactory.getDefaultInstance().createFileChooser(
                    Controller.getInstance().getLevelController().getLevelFileDescription(),
                    Controller.getInstance().getLevelController().getLevelFileExtension());
            fc.showOpenDialog(this.owner.getFrame());
            String path = fc.getSelectedFile().getAbsolutePath();
            try {
                Model.getInstance().getCurrentState()
                        .add(Controller.getInstance().getLevelController().loadLevel(path));
            } catch (ClassNotFoundException e1) {
                this.owner.showClassNotFoundErrorDialog();
                e1.printStackTrace();
            } catch (LevelNotValidException e1) {
                this.owner.showLevelInvalidErrorDialog(e1.toString());
                e1.printStackTrace();
            } catch (IOException e1) {
                this.owner.showIOErrorDialog();
                e1.printStackTrace();
            }
            syncListModel();
        });
    }

    /**
     * This is the action listener for the "move level up" and "move level down"
     * buttons. It changes the order of the elements accordingly in the model and
     * then it syncs the list.
     *
     * @return the action listener for the "move level up" and "move level down"
     *         buttons
     */
    private ActionListener move(final Function<Integer, Integer> computeNewIndex) {
        return e -> SwingUtilities.invokeLater(() -> {
            int selectedIndex = this.levelList.getSelectedIndex();
            int newIndex = computeNewIndex.apply(selectedIndex);
            if (newIndex >= 0 && newIndex < this.listModel.getSize()) {
                Model.getInstance().getCurrentState().swap(selectedIndex, newIndex);
                syncListModel();
                levelList.setSelectedIndex(newIndex);
            }
        });
    }

    /**
     * This is the action listener for the "remove level" button. It removes the level from the
     * sequence in the model and then syncs the list.
     *
     * @return the action listener for the "remove level" button
     */
    private ActionListener removeSelected() {
        return e -> SwingUtilities.invokeLater(() -> {
            Model.getInstance().getCurrentState().remove(this.levelList.getSelectedIndex());
            syncListModel();
        });
    }

    /**
     * This is the action listener for the "reset list" button. It clears the
     * sequence in the model and then syncs the list.
     *
     * @return the action listener for the "reset list" button
     */
    private ActionListener removeAll() {
        return e -> SwingUtilities.invokeLater(() -> {
            Model.getInstance().getCurrentState().clear();
            syncListModel();
        });
    }
}
