package view;

import static view.CraftConstants.*;
import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Type;
import model.element.Element;
import model.element.ElementImpl;
import model.grid.Grid;
import model.level.Level;
import model.position.Position;
import model.position.PositionImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class CraftGrid.
 */
public class CraftGrid {

    /** The level grid. */
    private Grid levelGrid;

    /** The owner. */
    private final CraftWindowImpl owner;

    /** The button grid. */
    private final List<List<JButton>> buttonGrid;

    /** The resized icons. */
    private final Map<Type, Icon> resizedIcons;

    /**
     * Instantiates a new craft grid.
     *
     * @param owner the owner
     */
    public CraftGrid(CraftWindowImpl owner) {
        this.owner = owner;
        this.levelGrid = Grid.createEmpty();
        this.buttonGrid = createButtonGrid();
        this.resizedIcons = new HashMap<>();
    }

    /**
     * Sets the level.
     *
     * @param level the new level
     */
    public void setLevel(Level level) {
        this.levelGrid = level.getGrid();
        updateButtonGrid();
    }

    /**
     * Creates the panel.
     *
     * @return the j panel
     */
    public final JPanel createPanel() {
        JPanel panel = new JPanel(new GridLayout(Grid.N_ROWS, Grid.N_ROWS));
        panel.setBorder(owner.getComponentFactory().createTitledPaddingBorder(PANEL_GRID_TITLE, DEFAULT_PADDING));
        this.buttonGrid.stream().flatMap(List::stream).forEach(button -> {
            button.addActionListener(gridButtonActionListener());
            panel.add(button);
        });
        return panel;
    }

    /**
     * Gets the grid.
     *
     * @return the grid
     */
    public Grid getGrid() {
        return this.levelGrid;
    }

    /**
     * Creates the button grid.
     *
     * @return the list
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
     * Creates the resized icons.
     *
     * @return the map
     */
    public Map<Type, Icon> createResizedIcons() {
        int w = (int) Math.round(this.buttonGrid.get(0).get(0).getWidth() * GRIDBUTTON_RELATIVE_ICON_WIDTH);
        int h = (int) Math.round(this.buttonGrid.get(0).get(0).getHeight() * GRIDBUTTON_RELATIVE_ICON_HEIGHT);
        for (TypeImage typeImage : TypeImage.values()) {
            this.resizedIcons.put(typeImage.getType(),
                    new ImageIcon(typeImage.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT)));
        }
        return this.resizedIcons;
    }

    /**
     * Reset button grid.
     */
    private void resetButtonGrid() {
        this.buttonGrid.stream().flatMap(List::stream).forEach(b -> b.setIcon(new ImageIcon()));
    }

    /**
     * Update button grid.
     */
    private void updateButtonGrid() {
        resetButtonGrid();
        this.levelGrid.getAllElements().forEach(element -> {
            this.buttonGrid.get(element.getPosition().getRowIndex()).get(element.getPosition().getColumnIndex())
                    .setIcon(resizedIcons.get(element.getType()));
        });
    }

    /**
     * Grid button action listener.
     *
     * @return the action listener
     */
    private ActionListener gridButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            JButton clickedButton = (JButton) e.getSource();
            Position position = findButtonPosition(clickedButton);
            Collection<Element> elementsInPosition = this.levelGrid.getElementsAt(position);
            Element newElement = new ElementImpl(this.owner.getSelection().getSelectedType(), position, this.levelGrid);
            if (elementsInPosition.isEmpty()) {
                this.levelGrid.add(newElement);
            }
            elementsInPosition.forEach(oldElement -> {
                this.levelGrid.remove(oldElement);
                if (!oldElement.getType().equals(owner.getSelection().getSelectedType())) {
                    this.levelGrid.add(newElement);
                }
            });
            updateButtonGrid();
        });
    }

    /**
     * Reset button action listener.
     *
     * @return the action listener
     */
    public ActionListener resetButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.levelGrid.clear();
            updateButtonGrid();
        });
    }

    /**
     * Find button position.
     *
     * @param button the button
     * @return the position
     */
    private Position findButtonPosition(JButton button) {
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
