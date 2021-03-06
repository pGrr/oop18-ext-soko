package view.initial;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.Controller;
import controller.initial.InitialWindowController;
import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the "save or load a sequence" panel in the
 * {@link InitialWindowImpl} window.
 */
public final class InitialSaveLoad {

    private static final String PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE = "Save current sequence or load one";
    private static final String ICON_UPLOAD = "icons/upload.png";
    private static final String ICON_DOWNLOAD = "icons/download.png";

    private final GuiComponentFactory guiComponentFactory;
    private final InitialWindowImpl owner;
    private InitialWindowController controller;

    /**
     * Creates a new instance using the given {@link InitialWindowImpl} owner.
     *
     * @param owner the {@link InitialWindowImpl} object which creates and contains
     *              this object
     */
    public InitialSaveLoad(final InitialWindowImpl owner) {
        this.guiComponentFactory = new GuiComponentFactoryImpl();
        this.owner = owner;
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
     * Creates the panel.
     *
     * @return the j panel
     */
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(
                this.guiComponentFactory.createTitledPaddingBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE, DEFAULT_PADDING));
        JButton saveButton = this.guiComponentFactory.createButton("", ICON_DOWNLOAD, saveSequence());
        panel.add(saveButton);
        JButton loadButton = this.guiComponentFactory.createButton("", ICON_UPLOAD, loadSequence());
        panel.add(loadButton);
        return panel;
    }

    /**
     * This is the action listener for the "save sequence" button. 
     * It shows the save file dialog and tells the controller to save the current level sequence to the chosen path. 
     * If the controller throws an exception, this will show the corresponding error dialog.
     * 
     * @return the action listener for the "save sequence" button
     */
    private ActionListener saveSequence() {
        return e -> SwingUtilities.invokeLater(() -> {
            String fileExtension = Controller.LEVEL_SEQUENCE_FILE_EXTENSION;
            String fileDescription = Controller.LEVEL_SEQUENCE_FILE_DESCRIPTION;
            JFileChooser fc = this.guiComponentFactory.createFileChooser(fileDescription, fileExtension);
            fc.showSaveDialog(this.owner.getFrame());
            File selectedFile = fc.getSelectedFile();
            if (selectedFile != null) {
                String name = fc.getSelectedFile().getName();
                if (name != null && !name.isEmpty()) {
                    try {
                        this.controller.saveLevelSequence(name, fc.getSelectedFile().getAbsolutePath() + fileExtension);
                    } catch (IOException e1) {
                        this.owner.showIOErrorDialog();
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * This is the action listener for the "load sequence" button. It shows the load
     * file dialog and tells the controller to load the selected file from the file-system.
     * If the controller throws an exception, this will show the corresponding error dialog.
     *
     * @return the action listener for the "load sequence" button
     */
    private ActionListener loadSequence() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = this.guiComponentFactory.createFileChooser(Controller.LEVEL_SEQUENCE_FILE_DESCRIPTION,
                    Controller.LEVEL_SEQUENCE_FILE_EXTENSION);
            fc.showOpenDialog(this.owner.getFrame());
            File file = fc.getSelectedFile();
            String path = new String();
            if (file != null) {
                path = file.getPath();
                try {
                    this.controller.loadLevelSequence(path);
                } catch (ClassNotFoundException e1) {
                    this.owner.showClassNotFoundErrorDialog();
                    e1.printStackTrace();
                } catch (IOException e1) {
                    this.owner.showIOErrorDialog();
                    e1.printStackTrace();
                }
            }
        });
    }
}
