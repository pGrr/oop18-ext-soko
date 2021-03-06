package view.craft;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.Controller;
import controller.craft.CraftWindowController;
import model.levelsequence.level.LevelNotValidException;
import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the options of the {@link CraftWindowImpl} window,
 * i.e. save, load, reset and go back to initial view.
 */
public final class CraftOptions {

    private static final String PANEL_OPTIONS_TITLE = "Edit level options";
    private static final String BUTTON_SAVE_TEXT = "SAVE";
    private static final String BUTTON_LOAD_TEXT = "LOAD";
    private static final String BUTTON_RESET_TEXT = "RESET";
    private static final String BUTTON_BACK_TEXT = "BACK";
    private static final String ICON_SAVE = "icons/download.png";
    private static final String ICON_LOAD = "icons/upload.png";
    private static final String ICON_CANCEL = "icons/cross.png";
    private static final String ICON_BACK = "icons/back.png";

    private final GuiComponentFactory guiComponentFactory;
    private final CraftWindowImpl owner;
    private CraftWindowController controller;

    /**
     * Creates a new instance with the given {@link CraftWindowImpl} owner.
     *
     * @param owner the {@link CraftWindowImpl} object which creates and contains
     *              this object
     */
    public CraftOptions(final CraftWindowImpl owner) {
        this.guiComponentFactory = new GuiComponentFactoryImpl();
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
        choicesPanel
                .setBorder(this.guiComponentFactory.createTitledPaddingBorder(PANEL_OPTIONS_TITLE, DEFAULT_PADDING));
        choicesPanel
                .add(this.guiComponentFactory.createButton(BUTTON_SAVE_TEXT, ICON_SAVE, saveButtonActionListener()));
        choicesPanel
                .add(this.guiComponentFactory.createButton(BUTTON_LOAD_TEXT, ICON_LOAD, loadButtonActionListener()));
        choicesPanel.add(this.guiComponentFactory.createButton(BUTTON_RESET_TEXT, ICON_CANCEL,
                this.owner.getGrid().resetButtonActionListener()));
        choicesPanel
                .add(this.guiComponentFactory.createButton(BUTTON_BACK_TEXT, ICON_BACK, backButtonActionListener()));
        return choicesPanel;
    }

    /**
     * This is the action listener of the save button. It shows a file chooser and
     * tells the controller to save the selected file.
     * 
     * @return the save button action listener
     */
    private ActionListener saveButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = this.guiComponentFactory.createFileChooser(Controller.LEVEL_FILE_DESCRIPTION,
                    Controller.LEVEL_FILE_EXTENSION);
            fc.showSaveDialog(this.owner.getFrame());
            String path = fc.getSelectedFile().getAbsolutePath() + Controller.LEVEL_FILE_EXTENSION;
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
     * This is the action listener of the load button. It shows a file-chooser and
     * tells the controller to load the selected file.
     *
     * @return the load button action listener
     */
    private ActionListener loadButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = this.guiComponentFactory.createFileChooser(Controller.LEVEL_FILE_DESCRIPTION,
                    Controller.LEVEL_FILE_EXTENSION);
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
     * This is the action listener of the back button. It tells the controller to go
     * back to the initial view.
     *
     * @return the back button action listener
     */
    private ActionListener backButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.owner.toInitialView();
        });
    }
}
