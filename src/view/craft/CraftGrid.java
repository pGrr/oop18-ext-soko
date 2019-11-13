package view.craft;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.craft.CraftWindowController;
import model.levelsequence.level.grid.Grid;
import model.levelsequence.level.grid.GridImpl;
import model.levelsequence.level.grid.element.Element;
import model.levelsequence.level.grid.element.Position;
import model.levelsequence.level.grid.element.PositionImpl;
import model.levelsequence.level.grid.element.Type;
import view.GuiComponentFactory;
import view.GuiComponentFactoryImpl;
import view.ResizedTypeImage;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the button grid of the {@link CraftWindowImpl}
 * window.
 */
public class CraftGrid {

    private static final String PANEL_GRID_TITLE = "Level grid";
    private static final double GRIDBUTTON_RELATIVE_ICON_WIDTH = 0.5;
    private static final double GRIDBUTTON_RELATIVE_ICON_HEIGHT = 0.7;

    private final GuiComponentFactory guiComponentFactory;
    private final CraftWindowImpl owner;
    private final Map<Type, Icon> resizedIcons;
    private List<List<JButton>> buttonGrid;
    private CraftWindowController controller;
    private Grid levelGrid;

    /**
     * Instantiates a new craft grid object.
     *
     * @param owner the {@link CraftWindowImpl} object which creates and contains
     *              this object
     */
    public CraftGrid(final CraftWindowImpl owner) {
        this.guiComponentFactory = new GuiComponentFactoryImpl();
        this.owner = owner;
        this.levelGrid = new GridImpl();
        this.buttonGrid = createButtonGrid();
        this.resizedIcons = new HashMap<>();
    }

    /**
     * Sets the controller.
     *
     * @param controller the new controller
     */
    public void setController(final CraftWindowController controller) {
        this.controller = controller;
    }

    /**
     * Clears the grid.
     *
     */
    public void clear() {
        this.levelGrid = new GridImpl();
        this.buttonGrid.stream().flatMap(List::stream).forEach(b -> b.setIcon(null));
    }

    /**
     * Gets the grid.
     *
     * @return the {@link Grid} object
     */
    public Grid getGrid() {
        return this.levelGrid;
    }

    /**
     * Adds an element to the grid.
     * 
     * @param element the element to be added
     */
    public void add(final Element element) {
        this.levelGrid.add(element);
        this.buttonGrid.get(element.getPosition().getRowIndex()).get(element.getPosition().getColumnIndex())
                .setIcon(this.resizedIcons.get(element.getType()));
    }

    /**
     * Removes an element from the grid.
     * 
     * @param element the element to be removed
     */
    public void remove(final Element element) {
        this.levelGrid.remove(element);
        this.buttonGrid.get(element.getPosition().getRowIndex()).get(element.getPosition().getColumnIndex())
                .setIcon(new ImageIcon());
    }

    /**
     * Creates the panel containing the empty squared button-grid.
     *
     * @return the created JPanel
     */
    public final JPanel createPanel() {
        JPanel panel = new JPanel(new GridLayout(Grid.N_ROWS, Grid.N_ROWS));
        panel.setBorder(this.guiComponentFactory.createTitledPaddingBorder(PANEL_GRID_TITLE, DEFAULT_PADDING));
        this.buttonGrid.stream().flatMap(List::stream).forEach(button -> {
            button.addActionListener(gridButtonActionListener());
            panel.add(button);
        });
        return panel;
    }

    /**
     * Creates the resized {@link Icon} for each type in order to not re-create them
     * at every use. Uses a {@link ResizedTypeImage} object to achieve that. Since
     * the icon size is relative to the button size which is relative to the window
     * size, the icons dimensions are known only when the Window is set visible.
     * Thus, this must be public as it must be called from {@link CraftWindowImpl}
     * in its show method.
     * 
     * @return a {@link Map} which maps every {@link Type} with its own resized
     *         {@link Icon}
     */
    public Map<Type, Icon> createResizedIcons() {
        int w = (int) Math.round(this.buttonGrid.get(0).get(0).getWidth() * GRIDBUTTON_RELATIVE_ICON_WIDTH);
        int h = (int) Math.round(this.buttonGrid.get(0).get(0).getHeight() * GRIDBUTTON_RELATIVE_ICON_HEIGHT);
        ResizedTypeImage images = new ResizedTypeImage(w, h);
        for (Type type : Type.values()) {
            this.resizedIcons.put(type,
                    images.get(type).isPresent() ? new ImageIcon(images.get(type).get()) : new ImageIcon());
        }
        return this.resizedIcons;
    }

    /**
     * Resets the current state of this object to the initial empty-grid state. It
     * is the action listener of the "reset" button.
     *
     * @return the reset button action listener
     */
    public ActionListener resetButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.controller.clearGrid();
        });
    }

    /**
     * The action listener invoked when the user clicks on the button grid. It
     * inserts the currently selected toggle button (representing a Type) in the
     * level grid at that specific position and then updates the button grid to
     * reflect the changes. If the selected position already contains the selected
     * Type, it erases it making the position empty. If the selected position
     * already contains a Type different from the selected one, the selected one
     * substitutes the old one.
     *
     * @return the button grid action listener
     */
    private ActionListener gridButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JButton clickedButton = (JButton) e.getSource();
            Position position = findButtonPosition(clickedButton);
            this.controller.insert(this.levelGrid, this.owner.getSelection().getSelectedType(), position);
        });
    }

    /**
     * Creates the button grid.
     * 
     * @return the button grid i.e. a 2D list of JButtons
     */
    private List<List<JButton>> createButtonGrid() {
        List<List<JButton>> grid = new ArrayList<>();
        IntStream.range(0, Grid.N_ROWS).forEach(i -> {
            grid.add(new ArrayList<>());
            IntStream.range(0, Grid.N_ROWS).forEach(j -> {
                grid.get(i).add(new JButton());
            });
        });
        return grid;
    }

    /**
     * Converts the button index into a position.
     *
     * @param button the button
     * @return the position
     */
    private Position findButtonPosition(final JButton button) {
        for (List<JButton> row : this.buttonGrid) {
            for (JButton b : row) {
                if (b == button) {
                    return new PositionImpl(buttonGrid.indexOf(row), row.indexOf(b));

                }
            }
        }
        throw new IllegalArgumentException();
    }
}
