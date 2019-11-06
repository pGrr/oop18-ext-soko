package view.game;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.Controller;
import controller.game.GameWindowController;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.PositionImpl;
import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;
import view.View;
import view.WindowAbstract;

/**
 * An implementation for the {@link GameWindow} interface. It creates uses a
 * {@link GameCanvas} object to manage the canvas drawings.
 */
public final class GameWindowImpl extends WindowAbstract implements GameWindow {

    private static final double HEIGHT_TO_SCREENSIZE_RATIO = 1;
    private static final double WIDTH_TO_HEIGHT_RATIO = 1;
    private static final String DIALOG_ERROR_TITLE = "ERROR";
    private static final String DIALOG_IOERROR_TEXT = "An error occurred during an input/output operation";
    private static final String DIALOG_CLASS_NOT_FOUND_TEXT = "Loaded file is corrupted.";
    private static final String DIALOG_LEVEL_NOT_CORRECT_TEXT = "Oops! One or more levels in the sequence seems to be incorrect!";
    private static final String MENU_TITLE = "Menu";
    private static final String LEVEL_FINISHED_MESSAGE = "You made it!! Congratulations!";
    private static final String GAME_FINISHED_MESSAGE = "...and that was the last one!! You won!! Congratulations!";
    private static final String MENU_BACK_ITEM_TEXT = "Go back to initial view";
    private static final String MENU_RESTART_LEVEL_TEXT = "Restart current level";
    private static final String LEVEL_FINISHED_TITLE = "LEVEL COMPLETE";
    private static final String MENU_SAVE_LEVEL_TEXT = "Save game";

    private final GuiComponentFactory guiComponentFactory;
    private final View owner;
    private final GameCanvas canvas;
    private GameWindowController controller;

    /**
     * Instantiates a new game window.
     * 
     * @param owner the {@link View} object which created this object
     */
    public GameWindowImpl(final View owner) {
        super("", HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
        this.owner = owner;
        this.guiComponentFactory = new GuiComponentFactoryImpl();
        this.getFrame().setJMenuBar(createMenuBar());
        this.canvas = new GameCanvas(this);
        this.getFrame().add(createMainPanel());
        this.getFrame().pack();
        this.getFrame().setResizable(false);
    }

    @Override
    protected JPanel createMainPanel() {
        return (JPanel) this.canvas;
    }

    @Override
    public void setController(final GameWindowController controller) {
        this.controller = controller;
        this.getFrame().setTitle(this.controller.getCurrentLevel().getName());
    }

    @Override
    public void draw(final Element element) {
        int w = this.canvas.getElementWidth();
        int h = this.canvas.getElementHeight();
        Position absolutePosition = this.convertRelativeToAbsolute(element.getPosition());
        int x = absolutePosition.getColumnIndex();
        int y = absolutePosition.getRowIndex();
        this.canvas.repaint(x - w, y - h, w * 3, h * 3);
    }

    @Override
    public void toInitialView() {
        this.owner.toInitialView();
    }

    @Override
    public void showLevelInvalidDialog(final String cause) {
        this.guiComponentFactory
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + cause)
                .setVisible(true);
    }

    @Override
    public void showLevelFinishedDialog() {
        this.guiComponentFactory
                .createActionDialog(this.getFrame(), LEVEL_FINISHED_TITLE, LEVEL_FINISHED_MESSAGE, e -> {
                    controller.levelFinishedAccepted();
                }).setVisible(true);
    }

    @Override
    public void showGameFinishedDialog() {
        this.guiComponentFactory.createActionDialog(this.getFrame(), LEVEL_FINISHED_TITLE, GAME_FINISHED_MESSAGE, e -> {
            controller.gameFinishedAccepted();
        }).setVisible(true);
    }

    @Override
    public void showClassNotFoundErrorDialog() {
        this.guiComponentFactory.createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT)
                .setVisible(true);
    }

    @Override
    public void showIOErrorDialog() {
        this.guiComponentFactory.createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT)
                .setVisible(true);
    }

    /**
     * Creates the menu bar.
     *
     * @return the j menu bar
     */
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu(MENU_TITLE);
        JMenuItem menuBackItem = new JMenuItem(MENU_BACK_ITEM_TEXT);
        menuBackItem.addActionListener(backToInitialView());
        menu.add(menuBackItem);
        JMenuItem restartLevelItem = new JMenuItem(MENU_RESTART_LEVEL_TEXT);
        restartLevelItem.addActionListener(restartCurrentLevel());
        menu.add(restartLevelItem);
        JMenuItem saveLevelItem = new JMenuItem(MENU_SAVE_LEVEL_TEXT);
        saveLevelItem.addActionListener(saveGame());
        menu.add(saveLevelItem);
        menuBar.add(menu);
        return menuBar;
    }

    /**
     * This is the action listener for the "go back to initial view" menu item. It
     * Goes back to initial view calling the appropriate
     * {@link GameWindowController} function.
     *
     * @return the action listener for the back button
     */
    private ActionListener backToInitialView() {
        return e -> SwingUtilities.invokeLater(() -> this.controller.backToInitialView());
    }

    /**
     * This is the action listener for "restart current level" menu item. It
     * restarts the current level calling the appropriate
     * {@link GameWindowController} function.
     *
     * @return the action listener
     */
    private ActionListener restartCurrentLevel() {
        return e -> SwingUtilities.invokeLater(() -> this.controller.restartCurrentLevel());
    }

    /**
     * This is the action listener for the "save game" menu item. It saves the
     * current game calling the appropriate {@link GameWindowController} function.
     *
     * @return the action listener
     */
    private ActionListener saveGame() {
        return e -> {
            File file = showSaveGameFileChooser();
            if (file != null) {
                try {
                    this.controller.saveGame(file.getAbsolutePath());
                } catch (IOException e1) {
                    this.showIOErrorDialog();
                    e1.printStackTrace();
                }
            }
        };
    }

    /**
     * Shows the save game file chooser and returns the selected file.
     *
     * @return the selected file
     */
    private File showSaveGameFileChooser() {
        JFileChooser fc = this.guiComponentFactory.createFileChooser(Controller.LEVEL_SEQUENCE_FILE_DESCRIPTION,
                Controller.LEVEL_SEQUENCE_FILE_EXTENSION);
        fc.showOpenDialog(this.getFrame());
        return fc.getSelectedFile();
    }

    /**
     * Converts a relative position expressed in indexes to an absolute position
     * expressed in pixels.
     *
     * @param position the relative position expressed in indexes
     * @return the absolute position expressed in pixels
     */
    Position convertRelativeToAbsolute(final Position position) {
        return new PositionImpl(position.getRowIndex() * Integer.divideUnsigned(this.canvas.getHeight(), Grid.N_ROWS),
                position.getColumnIndex() * Integer.divideUnsigned(this.canvas.getWidth(), Grid.N_ROWS));
    }
}
