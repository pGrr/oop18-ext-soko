package view.craft;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import controller.craft.CraftWindowController;
import model.level.grid.Grid;
import view.GuiComponentFactory;
import view.WindowAbstract;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * An implementation for the {@link CraftWindow} interface. It is composed by a
 * {@link CraftGrid}, a {@link CraftSelection} and a {@link CraftOptions} object
 * which are responsible for specific responsibilities.
 */
public final class CraftWindowImpl extends WindowAbstract implements CraftWindow {

    private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
    private static final double WIDTH_TO_HEIGHT_RATIO = 1;
    private static final String TITLE = "SOKOBAN - Craft your level";
    private static final String DIALOG_ERROR_TITLE = "ERROR";
    private static final String DIALOG_IOERROR_TEXT = "An error occurred during input / output operation";
    private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "An error occurred while trying to save the level.";
    private static final String DIALOG_FILE_CORRUPTED_TEXT = "Loaded file is corrupted";

    private final CraftGrid grid;
    private final CraftSelection selection;
    private final CraftOptions options;

    /**
     * Instantiates a new CraftWindowImpl object with an initially empty grid.
     */
    public CraftWindowImpl() {
        super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
        this.grid = new CraftGrid(this);
        this.selection = new CraftSelection();
        this.options = new CraftOptions(this);
        this.getFrame().add(createMainPanel());
    }

    @Override
    public void setController(final CraftWindowController controller) {
        this.grid.setController(controller);
    }

    @Override
    public void updateGrid(final Grid grid) {
        this.grid.setGrid(grid);
    }

    @Override
    protected JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(GuiComponentFactory.getInstance().createEmptyPaddingBorder(DEFAULT_PADDING));
        mainPanel.add(this.selection.createPanel(), BorderLayout.PAGE_START);
        mainPanel.add(grid.createPanel(), BorderLayout.CENTER);
        mainPanel.add(this.options.createPanel(), BorderLayout.PAGE_END);
        return mainPanel;
    }

    @Override
    public void show() {
        this.getFrame().setVisible(true);
        this.grid.createResizedIcons();
    }

    @Override
    public void showIOErrorDialog() {
        GuiComponentFactory.getInstance().createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT)
                .setVisible(true);
    }

    @Override
    public void showClassNotFoundErrorDialog() {
        GuiComponentFactory.getInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_FILE_CORRUPTED_TEXT).setVisible(true);
    }

    @Override
    public void showLevelInvalidDialog(final String cause) {
        GuiComponentFactory.getInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + " " + cause)
                .setVisible(true);
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
     * {@link view.craft} package. *
     * 
     * @return the {@link CraftSelection} object
     */
    CraftSelection getSelection() {
        return this.selection;
    }
}
