package view.initial;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import controller.Controller;
import controller.initial.InitialWindowController;
import model.levelsequence.level.LevelNotValidException;
import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the level list in the {@link InitialWindowImpl}
 * window.
 */
public final class InitialLevelList {

    private static final String PANEL_LEVEL_SEQUENCE_TITLE = "Level sequence";
    private static final String PANEL_EDIT_LEVEL_SEQUENCE_TITLE = "Load levels, remove them or change their orders into the sequence";
    private static final String ICON_UP = "icons/arrow-up.png";
    private static final String ICON_DOWN = "icons/arrow-down.png";
    private static final String ICON_PLUS = "icons/plus.png";
    private static final String ICON_MINUS = "icons/minus.png";
    private static final String ICON_RESET = "icons/cross.png";

    private final GuiComponentFactory guiComponentFactory;
    private final InitialWindowImpl owner;
    private final JPanel panel;
    private final JList<String> levelList;
    private final DefaultListModel<String> listModel;
    private InitialWindowController controller;

    /**
     * Instantiates a new initial level list object.
     *
     * @param owner the {@link InitialWindowImpl} object which creates and contains
     *              this object
     */
    public InitialLevelList(final InitialWindowImpl owner) {
        this.guiComponentFactory = new GuiComponentFactoryImpl();
        this.owner = owner;
        this.listModel = new DefaultListModel<>();
        this.levelList = new JList<>(this.listModel);
        this.panel = createPanel();
    }

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    public void setController(final InitialWindowController controller) {
        this.controller = controller;
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
     * Updates the list to reflect the given level names.
     * 
     * @param levelNames the level names to be displayed
     */
    public void updateList(final List<String> levelNames) {
        this.listModel.removeAllElements();
        levelNames.forEach(this.listModel::addElement);
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
        levelListPanel.setBorder(
                this.guiComponentFactory.createTitledPaddingBorder(PANEL_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
        JScrollPane scrollPane = new JScrollPane(this.levelList);
        levelListPanel.add(scrollPane);
        p.add(levelListPanel, BorderLayout.CENTER);
        // edit list panel
        JPanel p2 = new JPanel(new GridLayout(2, 1));
        JPanel editListPanel = new JPanel();
        JButton addLevelButton = this.guiComponentFactory.createButton("", ICON_PLUS, addLevel());
        editListPanel.add(addLevelButton);
        JButton removeLevelButton = this.guiComponentFactory.createButton("", ICON_MINUS, removeSelected());
        editListPanel.add(removeLevelButton);
        editListPanel.setBorder(
                this.guiComponentFactory.createTitledPaddingBorder(PANEL_EDIT_LEVEL_SEQUENCE_TITLE, DEFAULT_PADDING));
        JButton upButton = this.guiComponentFactory.createButton("", ICON_UP, move(i -> i - 1));
        editListPanel.add(upButton);
        JButton downButton = this.guiComponentFactory.createButton("", ICON_DOWN, move(i -> i + 1));
        editListPanel.add(downButton);
        JButton cancelButton = this.guiComponentFactory.createButton("", ICON_RESET, removeAll());
        editListPanel.add(cancelButton);
        p2.add(editListPanel);
        // save or load panel
        JPanel saveOrLoadListPanel = this.owner.getSaveLoadSequence().createPanel();
        p2.add(saveOrLoadListPanel);
        p.add(p2, BorderLayout.PAGE_END);
        return p;
    }

    /**
     * This is the action listener for the "add a level" button. It shows the file
     * chooser and then tells the controller to load the chosen file. If the
     * controller throws an exception it shows the corresponding error dialog.
     *
     * @return the action listener for the "add a level" button
     */
    private ActionListener addLevel() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = this.guiComponentFactory.createFileChooser(Controller.LEVEL_FILE_DESCRIPTION,
                    Controller.LEVEL_FILE_EXTENSION);
            fc.showOpenDialog(this.owner.getFrame());
            String path = fc.getSelectedFile().getAbsolutePath();
            try {
                this.controller.addLevel(path);
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
        });
    }

    /**
     * This is the action listener for the "move level up" and "move level down"
     * buttons. It tells the controller to move the element up or down (the
     * controller will take care of updating the model and the view subsequently).
     *
     * @return the action listener for the "move level up" and "move level down"
     *         buttons
     */
    private ActionListener move(final Function<Integer, Integer> computeNewIndex) {
        return e -> SwingUtilities.invokeLater(() -> {
            int selectedIndex = this.levelList.getSelectedIndex();
            int newIndex = computeNewIndex.apply(selectedIndex);
            if (newIndex >= 0 && newIndex < this.listModel.getSize()) {
                this.controller.swap(selectedIndex, newIndex);
                this.levelList.setSelectedIndex(newIndex);
            }
        });
    }

    /**
     * This is the action listener for the "remove level" button. It tells the
     * controller to remove the level. (the controller will take care of updating
     * the model and the view subsequently).
     * 
     * @return the action listener for the "remove level" button
     */
    private ActionListener removeSelected() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.controller.remove(this.levelList.getSelectedIndex());
        });
    }

    /**
     * This is the action listener for the "reset list" button. It tells the
     * controller to clear the list. (the controller will take care of updating the
     * model and the view subsequently).
     *
     * @return the action listener for the "reset list" button
     */
    private ActionListener removeAll() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.controller.removeAll();
        });
    }
}
