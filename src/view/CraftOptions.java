package view;

import static view.CraftConstants.*;
import static view.GuiComponentFactoryImpl.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.Controller;
import model.level.Level;
import model.level.LevelImpl;
import model.level.LevelNotValidException;

// TODO: Auto-generated Javadoc
/**
 * The Class CraftOptions.
 */
public class CraftOptions {

    /** The owner. */
    private final CraftWindowImpl owner;

    /**
     * Instantiates a new craft options.
     *
     * @param owner the owner
     */
    public CraftOptions(CraftWindowImpl owner) {
        this.owner = owner;
    }

    /**
     * Creates the panel.
     *
     * @return the j panel
     */
    public JPanel createPanel() {
        JPanel choicesPanel = new JPanel(new GridLayout(1, 4, DEFAULT_PADDING, DEFAULT_PADDING));
        choicesPanel
                .setBorder(owner.getComponentFactory().createTitledPaddingBorder(PANEL_OPTIONS_TITLE, DEFAULT_PADDING));
        choicesPanel
                .add(owner.getComponentFactory().createButton(BUTTON_SAVE_TEXT, ICON_SAVE, saveButtonActionListener()));
        choicesPanel
                .add(owner.getComponentFactory().createButton(BUTTON_LOAD_TEXT, ICON_LOAD, loadButtonActionListener()));
        choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_RESET_TEXT, ICON_CANCEL,
                this.owner.getGrid().resetButtonActionListener()));
        choicesPanel
                .add(owner.getComponentFactory().createButton(BUTTON_BACK_TEXT, ICON_BACK, backButtonActionListener()));
        return choicesPanel;
    }

    /**
     * Save button action listener.
     *
     * @return the action listener
     */
    private ActionListener saveButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = owner.getComponentFactory().createFileChooser(
                    Controller.getInstance().getLevelController().getLevelFileDescription(),
                    Controller.getInstance().getLevelController().getLevelFileExtension());
            fc.showSaveDialog(this.owner.getFrame());
            String path = fc.getSelectedFile().getAbsolutePath()
                    + Controller.getInstance().getLevelController().getLevelFileExtension();
            String name = fc.getSelectedFile().getName();
            Level level = new LevelImpl(name, this.owner.getGrid().getGrid());
            try {
                level.validate();
            } catch (LevelNotValidException exc) {
                View.getInstance().getCraftWindow().showLevelInvalidDialog(exc.toString());
                exc.printStackTrace();
            }
            try {
                Controller.getInstance().getLevelController().saveLevel(path, level);
            } catch (IOException e1) {
                this.owner.showIOErrorDialog();
                e1.printStackTrace();
            }
        });
    }

    /**
     * Load button action listener.
     *
     * @return the action listener
     */
    private ActionListener loadButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JFileChooser fc = owner.getComponentFactory().createFileChooser(
                    Controller.getInstance().getLevelController().getLevelFileDescription(),
                    Controller.getInstance().getLevelController().getLevelFileExtension());
            fc.showOpenDialog(this.owner.getFrame());
            Level level;
            try {
                level = Controller.getInstance().getLevelController().loadLevel(fc.getSelectedFile().getAbsolutePath());
                this.owner.getGrid().setLevel(level);
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
     * Back button action listener.
     *
     * @return the action listener
     */
    private ActionListener backButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            Controller.getInstance().getNavigationController().toInitialView();
        });
    }
}