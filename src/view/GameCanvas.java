package view;

import static view.GameConstants.TIMER_DELAY_MS;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import controller.Controller;
import model.Direction;
import model.Element;
import model.Grid;
import model.Model;
import model.Position;
import model.PositionImpl;
import model.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class GameCanvas.
 */
public class GameCanvas extends JPanel {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 8149893762262015513L;

    /** The owner. */
    private final GameWindowImpl owner;

    /** The timer. */
    private final Timer timer;

    /** The key pressed code. */
    private Integer keyPressedCode;

    /** The graphics. */
    private Graphics graphics;

    /** The images. */
    private Map<Type, Image> images;

    /**
     * Instantiates a new game canvas.
     *
     * @param owner the owner
     */
    public GameCanvas(GameWindowImpl owner) {
        this.owner = owner;
        this.images = createResizedImages();
        this.timer = new Timer(TIMER_DELAY_MS, this.timerAction());
        this.keyPressedCode = null;
        this.graphics = null;
        this.setSize(this.getPreferredSize());
        this.setFocusable(true);
        this.addKeyListener(buttonPressed());
        this.timer.start();
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.graphics = g;
        Model.getInstance().getCurrentLevelSequence().getCurrentLevel().getGrid().getAllElements()
                .forEach(this::drawElement);
    }

    /**
     * Gets the preferred size.
     *
     * @return the preferred size
     */
    @Override
    public Dimension getPreferredSize() {
        int frameHeight = (int) Math.round(owner.getFrame().getSize().getHeight()) - owner.getFrame().getInsets().top;
        int frameWidth = (int) Math.round(owner.getFrame().getSize().getWidth());
        return new Dimension(frameWidth, frameHeight);
    }

    /**
     * Draw element.
     *
     * @param element the element
     */
    public void drawElement(Element element) {
        if (graphics != null && images != null) {
            Position absolutePosition = convertRelativeToAbsolute(element.getPosition());
            graphics.drawImage(images.get(element.getType()), absolutePosition.getRowIndex(),
                    absolutePosition.getColumnIndex(), this);
        }
        this.repaint(TIMER_DELAY_MS);
    }

    /**
     * Creates the resized images.
     *
     * @return the map
     */
    public Map<Type, Image> createResizedImages() {
        Map<Type, Image> imageMap = new HashMap<>();
        for (TypeImage t : TypeImage.values()) {
            imageMap.put(t.getType(),
                    t.getImage().getScaledInstance((int) Math.round(this.getPreferredSize().getWidth() / Grid.N_ROWS),
                            (int) Math.round(this.getPreferredSize().getHeight() / Grid.N_ROWS), Image.SCALE_DEFAULT));
        }
        return imageMap;
    }

    /**
     * Convert relative to absolute.
     *
     * @param position the position
     * @return the position
     */
    private Position convertRelativeToAbsolute(Position position) {
        return new PositionImpl(position.getColumnIndex() * Integer.divideUnsigned(this.getWidth(), Grid.N_ROWS),
                position.getRowIndex() * Integer.divideUnsigned(this.getHeight(), Grid.N_ROWS));
    }

    /**
     * Button pressed.
     *
     * @return the key listener
     */
    private KeyListener buttonPressed() {
        return new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                SwingUtilities.invokeLater(() -> GameCanvas.this.keyPressedCode = e.getKeyCode());
            }
        };
    }

    /**
     * Timer action.
     *
     * @return the action listener
     */
    private ActionListener timerAction() {
        return e -> SwingUtilities.invokeLater(() -> {
            if (this.keyPressedCode != null) {
                Integer key = keyPressedCode;
                if (key.equals(KeyEvent.VK_DOWN) || key.equals(KeyEvent.VK_KP_DOWN)) {
                    Controller.getInstance().getGameController().move(Direction.DOWN);
                } else if (key.equals(KeyEvent.VK_UP) || key.equals(KeyEvent.VK_KP_UP)) {
                    Controller.getInstance().getGameController().move(Direction.UP);
                } else if (key.equals(KeyEvent.VK_LEFT) || key.equals(KeyEvent.VK_KP_LEFT)) {
                    Controller.getInstance().getGameController().move(Direction.LEFT);
                } else if (key.equals(KeyEvent.VK_RIGHT) || key.equals(KeyEvent.VK_KP_RIGHT)) {
                    Controller.getInstance().getGameController().move(Direction.RIGHT);
                }
                this.keyPressedCode = null;
            }
        });
    }
}
