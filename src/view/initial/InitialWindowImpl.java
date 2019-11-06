package view.initial;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import controller.initial.InitialWindowController;
import model.levelsequence.level.Level;
import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;
import view.View;
import view.WindowAbstract;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * An implementation for the {@link InitialWindow} interface. It is composed by
 * a {@link InitialLevelList}, a {@link InitialOptions} and a
 * {@link InitialSaveLoad} object which are responsible for specific
 * responsibilities.
 */
public final class InitialWindowImpl extends WindowAbstract implements InitialWindow {

    private static final double HEIGHT_TO_SCREENSIZE_RATIO = 0.9;
    private static final double WIDTH_TO_HEIGHT_RATIO = 1;
    private static final String TITLE = "SOKOBAN - InitialView";
    private static final String LABEL_WELCOME_TEXT = "Welcome to Sokoban! What would you like to do?";
    private static final String DIALOG_ERROR_TITLE = "ERROR";
    private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";
    private static final String DIALOG_DEFAULT_LEVEL_SEQUENCE_LOAD_ERROR_TEXT = "Oops! An error occurred while loading the default level sequence... Don't worry, you can still create your own levels, organize them in a sequence and play!";
    private static final String DIALOG_ERROR_LEVEL_SEQUENCE_EMPTY_TEXT = "Level sequence is empty";
    private static final String DIALOG_IOERROR_TEXT = "An error occurred during an input/output operation";
    private static final String DIALOG_CLASS_NOT_FOUND_TEXT = "The file is corrupted.";

    private final GuiComponentFactory guiComponentFactory;
    private final InitialLevelList levelList;
    private final InitialOptions choices;
    private final InitialSaveLoad saveLoadSequence;
    private final View owner;
    private InitialWindowController controller;

    /**
     * Instantiates a new initial window object.
     * 
     * @param owner the {@link View} which created this object.
     */
    public InitialWindowImpl(final View owner) {
        super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
        this.guiComponentFactory = new GuiComponentFactoryImpl();
        this.owner = owner;
        this.choices = new InitialOptions();
        this.saveLoadSequence = new InitialSaveLoad(this);
        this.levelList = new InitialLevelList(this);
        this.getFrame().add(createMainPanel());
    }

    /**
     * {@inheritDoc} Then, it syncs the level list with the model.
     */
    @Override
    public void show() {
        super.show();
        this.controller.updateLevelList();
    }

    @Override
    public void setController(final InitialWindowController controller) {
        this.controller = controller;
        this.levelList.setController(controller);
        this.saveLoadSequence.setController(controller);
        this.choices.setController(controller);
    }

    @Override
    public void updateList(final List<String> levelNames) {
        this.levelList.updateList(levelNames);
    }

    @Override
    public void toCraftLevelView() {
        this.owner.toCraftLevelView();
    }

    @Override
    public void toGameView(final Level level) {
        this.owner.toGameLevelView(level);
    }

    @Override
    public void showLevelInvalidErrorDialog(final String cause) {
        this.guiComponentFactory
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + cause)
                .setVisible(true);
    }

    @Override
    public void showDefaultLevelSequenceLoadErrorDialog() {
        this.guiComponentFactory
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_DEFAULT_LEVEL_SEQUENCE_LOAD_ERROR_TEXT)
                .setVisible(true);
    }

    @Override
    public void showLevelSequenceEmptyErrorDialog() {
        this.guiComponentFactory
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_ERROR_LEVEL_SEQUENCE_EMPTY_TEXT)
                .setVisible(true);
    }

    @Override
    public void showIOErrorDialog() {
        this.guiComponentFactory.createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT)
                .setVisible(true);
    }

    @Override
    public void showClassNotFoundErrorDialog() {
        this.guiComponentFactory.createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT)
                .setVisible(true);
    }

    /**
     * Gets the {@link InitialLevelList} object.
     *
     * @return the {@link InitialLevelList} object
     */
    protected InitialLevelList getLevelList() {
        return this.levelList;
    }

    /**
     * Gets the {@link InitialSaveLoad} object.
     *
     * @return the save load sequence
     */
    protected InitialSaveLoad getSaveLoadSequence() {
        return this.saveLoadSequence;
    }

    /**
     * Gets the {@link InitialOptions} object.
     *
     * @return the choices
     */
    protected InitialOptions getChoices() {
        return this.choices;
    }

    @Override
    protected JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(this.guiComponentFactory.createEmptyPaddingBorder(DEFAULT_PADDING));
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
        p.add(this.guiComponentFactory.createLabel(LABEL_WELCOME_TEXT));
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
        p.setBorder(this.guiComponentFactory.createEmptyPaddingBorder(DEFAULT_PADDING));
        p.add(this.levelList.getPanel());
        return p;
    }
}
