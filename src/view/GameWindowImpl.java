package view;

import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import controller.*;
import model.*;

import static view.GameConstants.*;
import static view.InitialConstants.DIALOG_ERROR_TITLE;
import static view.InitialConstants.DIALOG_IOERROR_TEXT;
import static view.InitialConstants.DIALOG_LEVEL_NOT_CORRECT_TEXT;

// TODO: Auto-generated Javadoc
/**
 * The Class GameWindowImpl.
 */
public class GameWindowImpl extends WindowAbstract implements GameWindow {

    /** The canvas. */
    private final GameCanvas canvas;

    /** The images. */
    private Map<Type, Image> images;

    /**
     * Instantiates a new game window impl.
     */
    public GameWindowImpl() {
        super(Model.getInstance().getCurrentLevelSequence().getClass().getName(), HEIGHT_TO_SCREENSIZE_RATIO,
                WIDTH_TO_HEIGHT_RATIO);
        this.getFrame().setJMenuBar(createMenuBar());
        this.canvas = new GameCanvas(this);
        this.images = createResizedImages();
        this.getFrame().add(createMainPanel());
        this.getFrame().pack();
        this.getFrame().setResizable(false);
    }

    public Map<Type, Image> getImages() {
        return this.images;
    }

    /**
     * Creates the main panel.
     *
     * @return the j panel
     */
    @Override
    protected JPanel createMainPanel() {
        return (JPanel) this.canvas;
    }

    /**
     * Draw element.
     *
     * @param element the element
     */
    public void draw(final Element element) {
        Image img = this.getImages().get(element.getType());
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        Position absolutePosition = this.convertRelativeToAbsolute(element.getPosition());
        int x = absolutePosition.getColumnIndex();
        int y = absolutePosition.getRowIndex();
        this.canvas.repaint(TIMER_DELAY_MS, x - w, y - h, w * 3, h * 3);
    }

    /**
     * Show level finished dialog.
     */
    @Override
    public void showLevelFinishedDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), LEVEL_FINISHED_TITLE, LEVEL_FINISHED_MESSAGE, e -> {
                    Controller.getInstance().getGameController().levelFinishedAccepted();
                }).setVisible(true);
    }

    /**
     * Show game finished dialog.
     */
    @Override
    public void showGameFinishedDialog() {
        GuiComponentFactory.getDefaultInstance()
                .createNotifyDialog(this.getFrame(), LEVEL_FINISHED_TITLE, GAME_FINISHED_MESSAGE, e -> {
                    Controller.getInstance().getGameController().gameFinishedAccepted();
                }).setVisible(true);
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
        saveLevelItem.addActionListener(saveLevel());
        menu.add(saveLevelItem);
        menuBar.add(menu);
        return menuBar;
    }
    
    private ActionListener backToInitialView() {
        return e -> {
            Model.getInstance().setCurrentLevelSequence(new LevelSequenceImpl(Model.getInstance().getCurrentLevelSequence()));
            Controller.getInstance().getNavigationController().toInitialView();
        };
    }
    
    private ActionListener restartCurrentLevel() {
        return e -> Controller.getInstance().getGameController().restartCurrentLevel();
    }
    
    private ActionListener saveLevel() {
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
     * Show save game file chooser.
     *
     * @return the file
     */
    @Override
    public File showSaveGameFileChooser() {
        JFileChooser fc = GuiComponentFactory.getDefaultInstance().createFileChooser(
                Controller.getInstance().getSequenceController().getLevelSequenceFileDescription(),
                Controller.getInstance().getSequenceController().getLevelSequenceFileExtension());
        fc.showOpenDialog(this.getFrame());
        return fc.getSelectedFile();
    }

    /**
     * Creates the resized images.
     *
     * @return the map
     */
    public Map<Type, Image> createResizedImages() {
        Map<Type, Image> imageMap = new HashMap<>();
        for (TypeImage t : TypeImage.values()) {
            imageMap.put(t.getType(), t.getImage().getScaledInstance(
                    (int) Math.round(this.canvas.getPreferredSize().getWidth() / Grid.N_ROWS),
                    (int) Math.round(this.canvas.getPreferredSize().getHeight() / Grid.N_ROWS), Image.SCALE_DEFAULT));
        }
        return imageMap;
    }

    /**
     * Convert relative to absolute.
     *
     * @param position the position
     * @return the position
     */
    public Position convertRelativeToAbsolute(final Position position) {
        return new PositionImpl(position.getRowIndex() * Integer.divideUnsigned(this.canvas.getHeight(), Grid.N_ROWS),
                position.getColumnIndex() * Integer.divideUnsigned(this.canvas.getWidth(), Grid.N_ROWS));
    }
}