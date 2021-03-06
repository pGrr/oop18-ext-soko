package view.craft;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import controller.craft.CraftWindowController;
import model.levelsequence.level.grid.element.Element;
import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;
import view.View;
import view.WindowAbstract;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * An implementation of {@link CraftWindow}. It is composed by a
 * {@link CraftGrid}, a {@link CraftSelection} and a {@link CraftOptions} object
 * each with specific responsibilities.
 */
public final class CraftWindowImpl extends WindowAbstract implements CraftWindow {

    private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
    private static final double WIDTH_TO_HEIGHT_RATIO = 1;
    private static final String TITLE = "SOKOBAN - Craft your level";
    private static final String DIALOG_ERROR_TITLE = "ERROR";
    private static final String DIALOG_IOERROR_TEXT = "An error occurred during input / output operation";
    private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "An error occurred while trying to save the level.";
    private static final String DIALOG_FILE_CORRUPTED_TEXT = "Loaded file is corrupted";

    private final GuiComponentFactory guiComponentFactory;
    private final View owner;
    private final CraftGrid grid;
    private final CraftSelection selection;
    private final CraftOptions options;

    /**
     * Creates a new instance with an empty grid.
     *
     * @param owner the {@link View} which created this object
     */
    public CraftWindowImpl(final View owner) {
        super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
        this.guiComponentFactory = new GuiComponentFactoryImpl();
        this.owner = owner;
        this.grid = new CraftGrid(this);
        this.selection = new CraftSelection();
        this.options = new CraftOptions(this);
        this.getFrame().add(createMainPanel());
    }

    @Override
    public void setController(final CraftWindowController controller) {
        this.grid.setController(controller);
        this.options.setController(controller);
    }

    @Override
    public void clearGrid() {
        this.grid.clear();
    }

    @Override
    public void addElement(final Element element) {
        this.grid.add(element);
    }

    @Override
    public void removeElement(final Element element) {
        this.grid.remove(element);
    }

    @Override
    public void toInitialView() {
        this.owner.toInitialView();
    }

    @Override
    public void show() {
        this.getFrame().setVisible(true);
        this.grid.createResizedIcons();
    }

    @Override
    public void showIOErrorDialog() {
        this.guiComponentFactory.createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT)
                .setVisible(true);
    }

    @Override
    public void showClassNotFoundErrorDialog() {
        this.guiComponentFactory.createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_FILE_CORRUPTED_TEXT)
                .setVisible(true);
    }

    @Override
    public void showLevelInvalidDialog(final String cause) {
        this.guiComponentFactory
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + " " + cause)
                .setVisible(true);
    }

    @Override
    protected JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(this.guiComponentFactory.createEmptyPaddingBorder(DEFAULT_PADDING));
        mainPanel.add(this.selection.createPanel(), BorderLayout.PAGE_START);
        mainPanel.add(this.grid.createPanel(), BorderLayout.CENTER);
        mainPanel.add(this.options.createPanel(), BorderLayout.PAGE_END);
        return mainPanel;
    }

    /**
     * Gets the reference to the {@link CraftGrid} object. It has package-private
     * visibility as it is used by the other objects of the {@link view.craft}
     * package.
     *
     * @return the {@link CraftGrid} object
     */
    CraftGrid getGrid() {
        return this.grid;
    }

    /**
     * Gets the reference to the {@link CraftSelection} object. It has
     * package-private visibility as it is used by the other objects of the
     * {@link view.craft} package.
     * 
     * @return the {@link CraftSelection} object
     */
    CraftSelection getSelection() {
        return this.selection;
    }
}
