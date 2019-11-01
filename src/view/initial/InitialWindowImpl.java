package view.initial;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import view.GuiComponentFactory;
import view.WindowAbstract;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * An implementation for the {@link InitialWindow} interface. It is composed by
 * a {@link InitialLevelList}, a {@link InitialOptions} and a
 * {@link InitialSaveLoad} object which are responsible for specific
 * responsibilities.
 */
public class InitialWindowImpl extends WindowAbstract implements InitialWindow {

    private static final double HEIGHT_TO_SCREENSIZE_RATIO = 0.9;
    private static final double WIDTH_TO_HEIGHT_RATIO = 1;
    private static final String TITLE = "SOKOBAN - InitialView";
    private static final String LABEL_WELCOME_TEXT = "Welcome to Sokoban! What would you like to do?";
    private static final String DIALOG_ERROR_TITLE = "ERROR";
    private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";
    private static final String DIALOG_ERROR_LEVEL_SEQUENCE_EMPTY_TEXT = "Level sequence is empty";
    private static final String DIALOG_IOERROR_TEXT = "An error occurred during an input/output operation";
    private static final String DIALOG_CLASS_NOT_FOUND_TEXT = "Loaded file is corrupted.";

    private final InitialLevelList levelList;
    private final InitialOptions choices;
    private final InitialSaveLoad saveLoadSequence;

    /**
     * Instantiates a new initial window object.
     */
    public InitialWindowImpl() {
        super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
        this.choices = new InitialOptions(this);
        this.saveLoadSequence = new InitialSaveLoad(this);
        this.levelList = new InitialLevelList(this);
        this.getFrame().add(createMainPanel());
    }

    /**
     * {@inheritDoc} Then, it syncs the level list with the model.
     */
    @Override
    public final void show() {
        super.show();
        this.levelList.syncListModel();
    }

    /**
     * Syncs the level list content with the model data.
     */
    @Override
    public final void syncWithModel() {
        this.levelList.syncListModel();
    }

    @Override
    public final void showLevelInvalidErrorDialog(final String cause) {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + cause)
                .setVisible(true);
    }

    @Override
    public final void showLevelSequenceEmptyErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_ERROR_LEVEL_SEQUENCE_EMPTY_TEXT)
                .setVisible(true);
    }

    @Override
    public final void showIOErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT).setVisible(true);
    }

    @Override
    public final void showClassNotFoundErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT).setVisible(true);
    }

    /**
     * Gets the {@link InitialLevelList} object.
     *
     * @return the {@link InitialLevelList} object
     */
    protected final InitialLevelList getLevelList() {
        return this.levelList;
    }

    /**
     * Gets the {@link InitialSaveLoad} object.
     *
     * @return the save load sequence
     */
    protected final InitialSaveLoad getSaveLoadSequence() {
        return this.saveLoadSequence;
    }

    /**
     * Gets the {@link InitialOptions} object.
     *
     * @return the choices
     */
    protected final InitialOptions getChoices() {
        return this.choices;
    }

    @Override
    protected final JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
        mainPanel.add(welcomePanel(), BorderLayout.PAGE_START);
        mainPanel.add(levelSequencePanel(), BorderLayout.CENTER);
        mainPanel.add(this.choices.createPanel(), BorderLayout.PAGE_END);
        return mainPanel;
    }

    /**
     * Creates the welcome panel.
     *
     * @return the welcome panel
     */
    private JPanel welcomePanel() {
        JPanel p = new JPanel();
        p.add(getComponentFactory().createLabel(LABEL_WELCOME_TEXT));
        return p;
    }

    /**
     * Creates the level sequence panel.
     *
     * @return the level sequence panel
     */
    private JPanel levelSequencePanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
        p.add(this.levelList.getPanel());
        return p;
    }
}
