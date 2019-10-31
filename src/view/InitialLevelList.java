/*
 * 
 */
package view;

import static view.GuiComponentFactoryImpl.*;
import static view.InitialConstants.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class InitialLevelList.
 */
public class InitialLevelList {

    /** The owner. */
    private final InitialWindowImpl owner;

    /** The panel. */
    private final JPanel panel;

    /** The level list. */
    private final JList<String> levelList;

    /** The list model. */
    private final DefaultListModel<String> listModel;

    /**
     * Instantiates a new initial level list.
     *
     * @param owner the owner
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
     * Creates the panel.
     *
     * @return the j panel
     */
    protected JPanel createPanel() {
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
     * Update list model.
     */
    public void updateListModel() {
        this.listModel.removeAllElements();
        Model.getInstance().getCurrentState().getAllLevels().stream().map(Level::getName)
                .forEach(listModel::addElement);
    }

    /**
     * Adds the level.
     *
     * @return the action listener
     */
    private ActionListener addLevel() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = GuiComponentFactory.getDefaultInstance().createFileChooser(
                    Controller.getInstance().getLevelController().getLevelFileDescription(),
                    Controller.getInstance().getLevelController().getLevelFileExtension());
            fc.showOpenDialog(this.owner.getFrame());
            String path = fc.getSelectedFile().getAbsolutePath();
            try {
                Model.getInstance().getCurrentState().add(Controller.getInstance().getLevelController().loadLevel(path));
            } catch (ClassNotFoundException e1) {
                this.owner.showClassNotFoundErrorDialog();
                e1.printStackTrace();
            } catch (LevelNotValidException e1) {
                this.owner.showLevelInvalidDialog(e1.toString());
                e1.printStackTrace();
            } catch (IOException e1) {
                this.owner.showIOErrorDialog();
                e1.printStackTrace();
            }
            updateListModel();
        });
    }

    /**
     * Move down.
     *
     * @return the action listener
     */
    private ActionListener move(final Function<Integer, Integer> computeNewIndex) {
        return e -> SwingUtilities.invokeLater(() -> {
            int selectedIndex = this.levelList.getSelectedIndex();
            int newIndex = computeNewIndex.apply(selectedIndex);
            if (newIndex >= 0 && newIndex < this.listModel.getSize()) {
                Model.getInstance().getCurrentState().swap(selectedIndex, newIndex);
                updateListModel();
                levelList.setSelectedIndex(newIndex);
            }
        });
    }

    /**
     * Removes the selected.
     *
     * @return the action listener
     */
    private ActionListener removeSelected() {
        return e -> SwingUtilities.invokeLater(() -> {
            Model.getInstance().getCurrentState().remove(this.levelList.getSelectedIndex());
            updateListModel();
        });
    }

    /**
     * Removes the all.
     *
     * @return the action listener
     */
    private ActionListener removeAll() {
        return e -> SwingUtilities.invokeLater(() -> {
            Model.getInstance().getCurrentState().clear();
            updateListModel();
        });
    }

}