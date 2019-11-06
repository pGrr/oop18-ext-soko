package view.game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import controller.game.GameWindowController;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.MovementDirection;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.Type;

/**
 * The Class responsible for the canvas where the game played is drawn. It is a
 * JPanel with an override of {@link #paintComponents(Graphics)} method which
 * takes care of the drawing.
 */
public class GameCanvas extends JPanel {

    private static final long serialVersionUID = 8149893762262015513L;
    private static final int TIMER_DELAY_MS = 50;

    private final GameWindowImpl owner;
    private final Timer timer;
    private final Image boxOnTargetImage;
    private Map<Type, Image> standardImages;
    private Graphics graphics;
    private Integer keyPressedCode;
    private GameWindowController controller;

    /**
     * Instantiates a new game canvas object. Also, it creates and starts the timer.
     *
     * @param owner the {@link GameWindowImpl} object which creates and contains
     *              this object
     */
    public GameCanvas(final GameWindowImpl owner) {
        this.keyPressedCode = null;
        this.graphics = null;
        this.owner = owner;
        this.standardImages = createResizedStandardImages();
        this.boxOnTargetImage = boxOnTargetImage();
        this.setSize(this.getPreferredSize());
        this.setFocusable(true);
        this.addKeyListener(buttonPressed());
        this.timer = new Timer(TIMER_DELAY_MS, this.timerAction());
        this.timer.start();
    }

    /**
     * Takes care of the drawing of all the elements using the {@link Graphics}
     * object of this component.
     *
     * @param g the {@link Graphics} object of this component.
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.graphics = g;
        this.drawAllElements(this.controller.getCurrentLevel().getCurrentGrid().getAllElements());
    }

    /**
     * Overrides the standard preferred size of the panel. It sets the preferred
     * size to be a full-height square, considering the top insets of the frame,
     * i.e. the top bar size.
     *
     * @return the new preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        int frameHeight = (int) Math.round(owner.getFrame().getSize().getHeight()) - owner.getFrame().getInsets().top;
        int frameWidth = (int) Math.round(owner.getFrame().getSize().getWidth());
        return new Dimension(frameWidth, frameHeight);
    }

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    public void setController(final GameWindowController controller) {
        this.controller = controller;
    }

    /**
     * Computes the width of a single element in pixels, considering the width of
     * the panel and the {@link #Grid.N_ROWS} number of rows.
     * 
     * @return the single element absolute width in pixels
     */
    public int getElementWidth() {
        return (int) Math.round(this.getPreferredSize().getWidth() / Grid.N_ROWS);
    }

    /**
     * Computes the height of a single element in pixels, considering the height of
     * the panel and the {@link #Grid.N_ROWS} number of rows.
     * 
     * @return the single element absolute height in pixels
     */
    public int getElementHeight() {
        return (int) Math.round(this.getPreferredSize().getHeight() / Grid.N_ROWS);
    }

    /**
     * This is the key listener for when a user presses a button. It saves the
     * button key code in the {@link #keyPressedCode} variable. When the timer will
     * fire, it will take care of converting the code to movement, if appropriate,
     * using the {@link #timerAction()} method.
     *
     * @return the key listener for when a user presses a button
     */
    private KeyListener buttonPressed() {
        return new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                SwingUtilities.invokeLater(() -> GameCanvas.this.keyPressedCode = e.getKeyCode());
            }
        };
    }

    /**
     * Draw all the elements into the canvas. It is used by the overridden
     * {@link #paintComponent(Graphics)} method.
     *
     * @param elements the elements to be drawn
     */
    private void drawAllElements(final Collection<Element> elements) {
        Collection<Position> boxesOnTargetPositions = this.controller.getCurrentLevel().getCurrentGrid()
                .getBoxesOnTarget().stream().map(b -> b.getPosition()).collect(Collectors.toList());
        for (Element element : elements) {
            Position pos = this.owner.convertRelativeToAbsolute(element.getPosition());
            if (boxesOnTargetPositions.contains(element.getPosition())) {
                this.graphics.drawImage(boxOnTargetImage, pos.getColumnIndex(), pos.getRowIndex(),
                        this.owner.getFrame());
            } else {
                this.graphics.drawImage(this.standardImages.get(element.getType()), pos.getColumnIndex(),
                        pos.getRowIndex(), this.owner.getFrame());
            }
        }
    }

    /**
     * This is the action listener for when the timer fires. It converts, if
     * recognized, the saved key code into movement using the appropriate
     * {@link GameWindowController} function.
     *
     * @return the action listener for when the timer fires
     */
    private ActionListener timerAction() {
        return e -> SwingUtilities.invokeLater(() -> {
            if (this.keyPressedCode != null) {
                Integer key = keyPressedCode;
                if (key.equals(KeyEvent.VK_DOWN) || key.equals(KeyEvent.VK_KP_DOWN)) {
                    this.controller.move(MovementDirection.DOWN);
                } else if (key.equals(KeyEvent.VK_UP) || key.equals(KeyEvent.VK_KP_UP)) {
                    this.controller.move(MovementDirection.UP);
                } else if (key.equals(KeyEvent.VK_LEFT) || key.equals(KeyEvent.VK_KP_LEFT)) {
                    this.controller.move(MovementDirection.LEFT);
                } else if (key.equals(KeyEvent.VK_RIGHT) || key.equals(KeyEvent.VK_KP_RIGHT)) {
                    this.controller.move(MovementDirection.RIGHT);
                }
                this.keyPressedCode = null;
            }
        });
    }

    /**
     * Creates a map holding a resized image for each {@link Type}, in order to not
     * having to create them each time they are needed, thus improving performance.
     *
     * @return the map holding a resized image for each {@link Type}
     */
    private Map<Type, Image> createResizedStandardImages() {
        Map<Type, Image> imageMap = new HashMap<>();
        for (TypeImage t : TypeImage.values()) {
            imageMap.put(t.getType(),
                    t.getImage().getScaledInstance((int) Math.round(this.getPreferredSize().getWidth() / Grid.N_ROWS),
                            (int) Math.round(this.getPreferredSize().getHeight() / Grid.N_ROWS), Image.SCALE_DEFAULT));
        }
        return imageMap;
    }

    /**
     * Creates a scaled instant of the box on target image (i.e. a darker box).
     * 
     * @return a scaled instant of the box on target image
     */
    private Image boxOnTargetImage() {
        return new ImageIcon(ClassLoader.getSystemResource("icons/box-on-target.png")).getImage()
                .getScaledInstance(getElementWidth(), getElementHeight(), Image.SCALE_DEFAULT);
    }
}
