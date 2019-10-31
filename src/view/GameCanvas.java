package view;

import static view.GameConstants.TIMER_DELAY_MS;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import controller.Controller;
import model.Direction;
import model.Model;
import model.Type;
import model.element.Element;
import model.grid.Grid;
import model.position.Position;

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

    Image boxOnTargetImage;

    /**
     * Instantiates a new game canvas.
     *
     * @param owner the owner
     */
    public GameCanvas(final GameWindowImpl owner) {
        this.owner = owner;
        this.timer = new Timer(TIMER_DELAY_MS, this.timerAction());
        this.keyPressedCode = null;
        this.graphics = null;
        this.setSize(this.getPreferredSize());
        this.setFocusable(true);
        this.addKeyListener(buttonPressed());
        this.images = createResizedImages();
        this.boxOnTargetImage = boxOnTargetImage();
        this.timer.start();
    }

    /**
     * Paint component.
     *
     * @param g the g
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        this.graphics = g;
        drawAllElements(Model.getInstance().getCurrentLevelSequence().getCurrentLevel().getGrid().getAllElements());
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
     * Draw all elements.
     *
     * @param elements the elements
     */
    public void drawAllElements(final Collection<Element> elements) {
        Collection<Position> boxesOnTargetPositions = Model.getInstance().getCurrentLevelSequence().getCurrentLevel()
                .getGrid().getBoxesOnTarget().stream().map(b -> b.getPosition()).collect(Collectors.toList());
        for (Element element : elements) {
            Position pos = this.owner.convertRelativeToAbsolute(element.getPosition());
            if (boxesOnTargetPositions.contains(element.getPosition())) {
                this.graphics.drawImage(boxOnTargetImage, pos.getColumnIndex(), pos.getRowIndex(),
                        this.owner.getFrame());
            } else {
                this.graphics.drawImage(this.images.get(element.getType()), pos.getColumnIndex(), pos.getRowIndex(),
                        this.owner.getFrame());
            }
        }
    }

    public int getElementWidth() {
        return (int) Math.round(this.getPreferredSize().getWidth() / Grid.N_ROWS);
    }

    public int getElementHeight() {
        return (int) Math.round(this.getPreferredSize().getHeight() / Grid.N_ROWS);
    }

    private Image boxOnTargetImage() {
        return new ImageIcon(ClassLoader.getSystemResource("icons/box-on-target.png")).getImage()
                .getScaledInstance(getElementWidth(), getElementHeight(), Image.SCALE_DEFAULT);
    }

    /**
     * Button pressed.
     *
     * @return the key listener
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

}
