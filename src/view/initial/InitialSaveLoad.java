package view.initial;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

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

/**
 * The class responsible for the "save or load a sequence" panel in the
 * {@link InitialWindowImpl} window.
 */
public class InitialSaveLoad {

    private static final String PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE = "Save current sequence or load one";
    private static final String ICON_UPLOAD = "icons/upload.png";
    private static final String ICON_DOWNLOAD = "icons/download.png";

    private final InitialWindowImpl owner;
    private InitialWindowController controller;

    /**
     * Instantiates a new initial save load object.
     *
     * @param owner the {@link InitialWindowImpl} object which creates and contains
     *              this object
     */
    public InitialSaveLoad(final InitialWindowImpl owner) {
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
        panel.setBorder(GuiComponentFactory.getInstance().createTitledPaddingBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE,
                DEFAULT_PADDING));
        JButton saveButton = GuiComponentFactory.getInstance().createButton("", ICON_DOWNLOAD, saveSequence());
        panel.add(saveButton);
        JButton loadButton = GuiComponentFactory.getInstance().createButton("", ICON_UPLOAD, loadSequence());
        panel.add(loadButton);
        return panel;
    }

    /**
     * This is the action listener for the "save sequence" button. It shows the save
     * file dialog and saves the selected file to the file-system.
     *
     * @return the action listener for the "save sequence" button
     */
    private ActionListener saveSequence() {
        return e -> SwingUtilities.invokeLater(() -> {
            String fileExtension = Controller.getInstance().getLevelSequenceFileExtension();
            String fileDescription = Controller.getInstance().getLevelSequenceFileDescription();
            JFileChooser fc = GuiComponentFactory.getInstance().createFileChooser(fileDescription, fileExtension);
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
     * file dialog and loads the selected file from the file-system.
     *
     * @return the action listener for the "load sequence" button
     */
    private ActionListener loadSequence() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = GuiComponentFactory.getInstance().createFileChooser(
                    Controller.getInstance().getLevelSequenceFileDescription(),
                    Controller.getInstance().getLevelSequenceFileExtension());
            fc.showOpenDialog(this.owner.getFrame());
            File file = fc.getSelectedFile();
            String path = new String();
            if (file != null) {
                path = file.getPath();
            }
            try {
                this.controller.loadLevelSequence(path);
            } catch (ClassNotFoundException e1) {
                this.owner.showClassNotFoundErrorDialog();
                e1.printStackTrace();
            } catch (IOException e1) {
                this.owner.showIOErrorDialog();
                e1.printStackTrace();
            }
        });
    }
}