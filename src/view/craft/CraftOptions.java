package view.craft;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.Controllers;
import controller.craft.CraftWindowController;
import model.level.LevelNotValidException;
import view.GuiComponentFactory;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the options of the {@link CraftWindowImpl} window,
 * i.e. save, load, reset and go back to initial view.
 */
public class CraftOptions {

    private static final String PANEL_OPTIONS_TITLE = "Edit level options";
    private static final String BUTTON_SAVE_TEXT = "SAVE";
    private static final String BUTTON_LOAD_TEXT = "LOAD";
    private static final String BUTTON_RESET_TEXT = "RESET";
    private static final String BUTTON_BACK_TEXT = "BACK";
    private static final String ICON_SAVE = "icons/download.png";
    private static final String ICON_LOAD = "icons/upload.png";
    private static final String ICON_CANCEL = "icons/cross.png";
    private static final String ICON_BACK = "icons/back.png";

    private final CraftWindowImpl owner;
    private CraftWindowController controller;

    /**
     * Instantiates a new craft options object.
     *
     * @param owner the {@link CraftWindowImpl} object which creates and contains
     *              this object
     */
    public CraftOptions(final CraftWindowImpl owner) {
        this.owner = owner;
    }

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    public void setController(final CraftWindowController controller) {
        this.controller = controller;
    }

    /**
     * Creates the panel containing the options buttons.
     *
     * @return the created JPanel
     */
    public JPanel createPanel() {
        JPanel choicesPanel = new JPanel(new GridLayout(1, 4, DEFAULT_PADDING, DEFAULT_PADDING));
        choicesPanel.setBorder(
                GuiComponentFactory.getInstance().createTitledPaddingBorder(PANEL_OPTIONS_TITLE, DEFAULT_PADDING));
        choicesPanel.add(GuiComponentFactory.getInstance().createButton(BUTTON_SAVE_TEXT, ICON_SAVE,
                saveButtonActionListener()));
        choicesPanel.add(GuiComponentFactory.getInstance().createButton(BUTTON_LOAD_TEXT, ICON_LOAD,
                loadButtonActionListener()));
        choicesPanel.add(GuiComponentFactory.getInstance().createButton(BUTTON_RESET_TEXT, ICON_CANCEL,
                this.owner.getGrid().resetButtonActionListener()));
        choicesPanel.add(GuiComponentFactory.getInstance().createButton(BUTTON_BACK_TEXT, ICON_BACK,
                backButtonActionListener()));
        return choicesPanel;
    }

    /**
     * This is the action listener of the save button. It does the following: 1) It
     * gets the {@link Grid} object representing the current state of the edited
     * grid from the {@link CraftGrid} object 2) it shows a save file-chooser dialog
     * asking the user to input a target file 3) it creates a new Level with the
     * file name as level name and the created grid object as its grid.
     */
    private ActionListener saveButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = GuiComponentFactory.getInstance().createFileChooser(
                    Controllers.getLevelFileDescription(),
                    Controllers.getLevelFileExtension());
            fc.showSaveDialog(this.owner.getFrame());
            String path = fc.getSelectedFile().getAbsolutePath() + Controllers.getLevelFileExtension();
            String name = fc.getSelectedFile().getName();
            try {
                this.controller.saveLevel(name, this.owner.getGrid().getGrid(), path);
            } catch (LevelNotValidException e1) {
                this.owner.showLevelInvalidDialog(e1.toString());
                e1.printStackTrace();
            } catch (IOException e1) {
                this.owner.showIOErrorDialog();
                e1.printStackTrace();
            }
        });
    }

    /**
     * This is the action listener of the load button. It does the following: 1) it
     * shows an open file-chooser to ask the user to select a target file 2) it
     * opens the target file using the controller 3) if the loading succeeded, it
     * sets the loaded leve's grid as the current grid in the {@link CraftGrid}
     * object.
     *
     * @return the load button action listener
     */
    private ActionListener loadButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = GuiComponentFactory.getInstance().createFileChooser(
                    Controllers.getLevelFileDescription(),
                    Controllers.getLevelFileExtension());
            fc.showOpenDialog(this.owner.getFrame());
            try {
                this.controller.loadLevel(fc.getSelectedFile().getAbsolutePath());
            } catch (ClassNotFoundException e1) {
                this.owner.showClassNotFoundErrorDialog();
                e1.printStackTrace();
            } catch (LevelNotValidException e1) {
                this.owner.showLevelInvalidDialog(e1.toString());
                e1.printStackTrace();
            } catch (IOException e1) {
                this.owner.showClassNotFoundErrorDialog();
                e1.printStackTrace();
            }
        });
    }

    /**
     * This is the action listener of the back button. It asks the controller to go
     * back to the initial view.
     *
     * @return the back button action listener
     */
    private ActionListener backButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.controller.backToInitialView();
        });
    }
}
