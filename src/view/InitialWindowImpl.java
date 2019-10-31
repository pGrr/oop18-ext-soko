package view;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Model;
import model.level.Level;
import model.sequence.LevelSequence;

import static view.GuiComponentFactoryImpl.*;
import static view.InitialConstants.*;

// TODO: Auto-generated Javadoc
/**
 * The Class InitialWindowImpl.
 */
public class InitialWindowImpl extends WindowAbstract implements InitialWindow {

    /** The level list. */
    private final InitialLevelList levelList;

    /** The choices. */
    private final InitialOptions choices;

    /** The save load sequence. */
    private final InitialSaveLoad saveLoadSequence;

    /**
     * Instantiates a new initial window impl.
     */
    public InitialWindowImpl() {
        super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
        this.choices = new InitialOptions(this);
        this.saveLoadSequence = new InitialSaveLoad(this);
        this.levelList = new InitialLevelList(this);
        this.getFrame().add(createMainPanel());
    }

    /**
     * Show.
     */
    @Override
    public void show() {
        super.show();
        this.levelList.updateListModel();
    }
    
    /**
     * Creates the main panel.
     *
     * @return the j panel
     */
    @Override
    protected JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
        mainPanel.add(welcomePanel(), BorderLayout.PAGE_START);
        mainPanel.add(levelSequencePanel(), BorderLayout.CENTER);
        mainPanel.add(this.choices.createPanel(), BorderLayout.PAGE_END);
        return mainPanel;
    }

    /**
     * Gets the level list.
     *
     * @return the level list
     */
    protected InitialLevelList getLevelList() {
        return this.levelList;
    }

    /**
     * Update list model.
     */
    @Override
    public void updateListModel() {
        this.levelList.updateListModel();
    }
    
    /**
     * Gets the choices.
     *
     * @return the choices
     */
    protected InitialOptions getChoices() {
        return this.choices;
    }

    /**
     * Gets the save load sequence.
     *
     * @return the save load sequence
     */
    protected InitialSaveLoad getSaveLoadSequence() {
        return this.saveLoadSequence;
    }

    /**
     * Welcome panel.
     *
     * @return the j panel
     */
    private JPanel welcomePanel() {
        JPanel p = new JPanel();
        p.add(getComponentFactory().createLabel(LABEL_WELCOME_TEXT));
        return p;
    }

    /**
     * Level sequence panel.
     *
     * @return the j panel
     */
    private JPanel levelSequencePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
        p.add(this.levelList.getPanel());
        return p;
    }

    /**
     * Show IO error dialog.
     */
    @Override
    public void showIOErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT).setVisible(true);
    }

    /**
     * Show class not found error dialog.
     */
    @Override
    public void showClassNotFoundErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT).setVisible(true);
    }

    /**
     * Show level invalid dialog.
     *
     * @param cause the cause
     */
    @Override
    public void showLevelInvalidDialog(String cause) {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + cause)
                .setVisible(true);
    }
}