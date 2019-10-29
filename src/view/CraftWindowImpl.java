package view;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import static view.CraftConstants.*;
import static view.GuiComponentFactoryImpl.*;

// TODO: Auto-generated Javadoc
/**
 * The Class CraftWindowImpl.
 */
public final class CraftWindowImpl extends WindowAbstract implements CraftWindow {

    /** The grid. */
    private final CraftGrid grid;

    /** The selection. */
    private final CraftSelection selection;

    /** The options. */
    private final CraftOptions options;

    /**
     * Instantiates a new craft window impl.
     */
    public CraftWindowImpl() {
        super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
        this.grid = new CraftGrid(this);
        this.selection = new CraftSelection(this);
        this.options = new CraftOptions(this);
        this.getFrame().add(createMainPanel());
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
        mainPanel.add(this.selection.createPanel(), BorderLayout.PAGE_START);
        mainPanel.add(grid.createPanel(), BorderLayout.CENTER);
        mainPanel.add(this.options.createPanel(), BorderLayout.PAGE_END);
        return mainPanel;
    }

    /**
     * Show.
     */
    @Override
    public void show() {
        this.getFrame().setVisible(true);
        this.grid.createResizedIcons();
    }

    /**
     * Show IO error dialog.
     */
    @Override
    public void showIOErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT).setVisible(true);
        ;
    }

    /**
     * Show class not found error dialog.
     */
    @Override
    public void showClassNotFoundErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_FILE_CORRUPTED_TEXT).setVisible(true);
    }

    /**
     * Show level invalid dialog.
     *
     * @param cause the cause
     */
    @Override
    public void showLevelInvalidDialog(String cause) {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + " " + cause)
                .setVisible(true);
    }

    /**
     * Gets the grid.
     *
     * @return the grid
     */
    protected CraftGrid getGrid() {
        return this.grid;
    }

    /**
     * Gets the selection.
     *
     * @return the selection
     */
    protected CraftSelection getSelection() {
        return this.selection;
    }

}