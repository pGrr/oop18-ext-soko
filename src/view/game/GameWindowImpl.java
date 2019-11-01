package view.game;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import controller.Controller;
import model.Model;
import model.element.Element;
import model.element.Position;
import model.element.PositionImpl;
import model.grid.Grid;
import view.GuiComponentFactory;
import view.WindowAbstract;

/**
 * An implementation for the {@link GameWindow} interface. It creates uses a
 * {@link GameCanvas} object to manage the canvas drawings.
 */
public class GameWindowImpl extends WindowAbstract implements GameWindow {

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

    private final GameCanvas canvas;

    /**
     * Instantiates a new game window.
     */
    public GameWindowImpl() {
        super(Model.getInstance().getCurrentState().getClass().getName(), HEIGHT_TO_SCREENSIZE_RATIO,
                WIDTH_TO_HEIGHT_RATIO);
        this.getFrame().setJMenuBar(createMenuBar());
        this.canvas = new GameCanvas(this);
        this.getFrame().add(createMainPanel());
        this.getFrame().pack();
        this.getFrame().setResizable(false);
    }

    @Override
    protected final JPanel createMainPanel() {
        return (JPanel) this.canvas;
    }

    @Override
    public final void draw(final Element element) {
        int w = this.canvas.getElementWidth();
        int h = this.canvas.getElementHeight();
        Position absolutePosition = this.convertRelativeToAbsolute(element.getPosition());
        int x = absolutePosition.getColumnIndex();
        int y = absolutePosition.getRowIndex();
        this.canvas.repaint(x - w, y - h, w * 3, h * 3);
    }

    @Override
    public final void showLevelInvalidDialog(final String cause) {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT + cause)
                .setVisible(true);
    }

    @Override
    public final void showLevelFinishedDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), LEVEL_FINISHED_TITLE, LEVEL_FINISHED_MESSAGE, e -> {
                    Controller.getInstance().getGameController().levelFinishedAccepted();
                }).setVisible(true);
    }

    @Override
    public final void showGameFinishedDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), LEVEL_FINISHED_TITLE, GAME_FINISHED_MESSAGE, e -> {
                    Controller.getInstance().getGameController().gameFinishedAccepted();
                }).setVisible(true);
    }

    @Override
    public final void showClassNotFoundErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT).setVisible(true);
    }

    @Override
    public final void showIOErrorDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT).setVisible(true);
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
     * Goes back to initial view calling the appropriate {@link GameController} function.
     *
     * @return the action listener for the back button
     */
    private ActionListener backToInitialView() {
        return e -> {
            Model.getInstance().setCurrentLevelSequence(Model.getInstance().getInitialState());
            Controller.getInstance().getNavigationController().toInitialView();
        };
    }

    /**
     * This is the action listener for "restart current level" menu item. It
     * restarts the current level calling the appropriate {@link GameController} function.
     *
     * @return the action listener
     */
    private ActionListener restartCurrentLevel() {
        return e -> Controller.getInstance().getGameController().restartCurrentLevel();
    }

    /**
     * This is the action listener for the "save game" menu item. It saves the
     * current game calling the appropriate {@link GameController} function.
     *
     * @return the action listener
     */
    private ActionListener saveGame() {
        return e -> {
            try {
                File file = showSaveGameFileChooser();
                if (file != null) {
                    Controller.getInstance().getGameController().saveGame(file.getAbsolutePath());
                }
            } catch (IOException e1) {
                this.showIOErrorDialog();
                e1.printStackTrace();
            }
        };
    }

    /**
     * Shows the save game file chooser and returns the selected file.
     *
     * @return the selected file
     */
    private File showSaveGameFileChooser() {
        JFileChooser fc = GuiComponentFactory.getDefaultInstance().createFileChooser(
                Controller.getInstance().getLevelSequenceController().getLevelSequenceFileDescription(),
                Controller.getInstance().getLevelSequenceController().getLevelSequenceFileExtension());
        fc.showOpenDialog(this.getFrame());
        return fc.getSelectedFile();
    }

    /**
     * Converts a relative position expressed in indexes to an absolute position expressed in pixels.
     *
     * @param position the relative position expressed in indexes 
     * @return the absolute position expressed in pixels
     */
    Position convertRelativeToAbsolute(final Position position) {
        return new PositionImpl(position.getRowIndex() * Integer.divideUnsigned(this.canvas.getHeight(), Grid.N_ROWS),
                position.getColumnIndex() * Integer.divideUnsigned(this.canvas.getWidth(), Grid.N_ROWS));
    }
}
