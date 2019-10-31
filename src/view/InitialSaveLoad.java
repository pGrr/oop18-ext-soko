package view;

import static view.GuiComponentFactoryImpl.*;
import static view.InitialConstants.*;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.Model;
import model.level.Level;
import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class InitialSaveLoad.
 */
public class InitialSaveLoad {

    /** The owner. */
    private final InitialWindowImpl owner;

    /**
     * Instantiates a new initial save load.
     *
     * @param owner the owner
     */
    public InitialSaveLoad(InitialWindowImpl owner) {
        this.owner = owner;
    }

    /**
     * Creates the panel.
     *
     * @return the j panel
     */
    public JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(owner.getComponentFactory().createTitledPaddingBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE,
                DEFAULT_PADDING));
        JButton saveButton = owner.getComponentFactory().createButton("", ICON_DOWNLOAD, saveSequenceAction());
        panel.add(saveButton);
        JButton loadButton = owner.getComponentFactory().createButton("", ICON_UPLOAD, loadSequenceAction());
        panel.add(loadButton);
        return panel;
    }

    /**
     * Save sequence action.
     *
     * @return the action listener
     */
    private ActionListener saveSequenceAction() {
        return e -> SwingUtilities.invokeLater(() -> {
            String fileExtension = Controller.getInstance().getLevelSequenceController().getLevelSequenceFileExtension();
            String fileDescription = Controller.getInstance().getLevelSequenceController().getLevelSequenceFileDescription();
            JFileChooser fc = owner.getComponentFactory().createFileChooser(fileDescription, fileExtension);
            fc.showSaveDialog(this.owner.getFrame());
            File selectedFile = fc.getSelectedFile();
            if (selectedFile != null) {
                String name = fc.getSelectedFile().getName();
                if (name != null && !name.isEmpty()) {
                    LevelSequence levelSequence = new LevelSequenceImpl(name,
                            Model.getInstance().getCurrentState().getAllLevels());
                    try {
                        Controller.getInstance().getLevelSequenceController().saveLevelSequence(levelSequence,
                                fc.getSelectedFile().getAbsolutePath() + fileExtension);
                    } catch (IOException e1) {
                        this.owner.showIOErrorDialog();
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Load sequence action.
     *
     * @return the action listener
     */
    private ActionListener loadSequenceAction() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = owner.getComponentFactory().createFileChooser(
                    Controller.getInstance().getLevelSequenceController().getLevelSequenceFileDescription(),
                    Controller.getInstance().getLevelSequenceController().getLevelSequenceFileExtension());
            fc.showOpenDialog(this.owner.getFrame());
            File file = fc.getSelectedFile();
            String path = new String();
            if (file != null) {
                path = file.getPath();
            }
            try {
                LevelSequence levelSequence;
                levelSequence = Controller.getInstance().getLevelSequenceController().loadLevelSequence(path);
                Model.getInstance().setCurrentLevelSequence(levelSequence);
                List<String> names = levelSequence.getAllLevels().stream().map(Level::getName)
                        .collect(Collectors.toList());
                names.stream().forEach(this.owner.getLevelList().getListModel()::addElement);
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