package view.craft;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import model.levelsequence.level.grid.element.Type;
import view.GuiComponentFactory;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the {@link Type} selection in the
 * {@link CraftWindowImpl} window, i.e. the toggle buttons.
 */
public class CraftSelection {

    private static final String LABEL_WELCOME_TEXT = "Welcome! Click on a element to select it and then the cell's grid to mark them.";
    private static final String ICON_WALL = "icons/wall.png";
    private static final String ICON_BOX = "icons/box.png";
    private static final String ICON_TARGET = "icons/target.png";
    private static final String ICON_USER = "icons/user.png";
    private static final int TOGGLE_BUTTON_ICON_EDGE_SIZE_PX = 30;

    private final Map<Type, JToggleButton> toggleButtons;

    /**
     * Instantiates a new craft selection object.
     *
     */
    public CraftSelection() {
        this.toggleButtons = createToggleButtonSelectionList();
    }

    /**
     * Creates the panel containing the toggle buttons for the {@link Type}
     * selection.
     *
     * @return the created JPanel
     */
    public JPanel createPanel() {
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel(LABEL_WELCOME_TEXT);
        welcomeLabel.setBorder(GuiComponentFactory.getInstance().createEmptyPaddingBorder(DEFAULT_PADDING));
        upperPanel.add(welcomeLabel);
        this.toggleButtons.values().stream().forEach(upperPanel::add);
        return upperPanel;
    }

    /**
     * Gets the selected toggle button.
     *
     * @return the selected toggle button
     */
    public JToggleButton getSelectedToggleButton() {
        return this.toggleButtons.values().stream().filter(e -> e.isSelected()).findFirst().orElse(new JToggleButton());
    }

    /**
     * Gets the selected type.
     *
     * @return the selected type
     */
    public Type getSelectedType() {
        return this.toggleButtons.entrySet().stream().filter(e -> e.getValue().isSelected()).map(e -> e.getKey())
                .findFirst().orElse(Type.EMPTY);
    }

    /**
     * This is the action listener of toggle buttons. It set the clicked button as
     * selected and all the others as non-selected.
     *
     * @return the toggle buttons action listener
     */
    private ActionListener toggleButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.toggleButtons.values().forEach(b -> b.setSelected(false));
            ((JToggleButton) e.getSource()).setSelected(true);
        });
    }

    /**
     * Creates the toggle buttons {@link Map} which links every {@link Type} with a
     * {@link JToggleButton}.
     *
     * @return the {@link Map} which links every {@link Type} with a
     *         {@link JToggleButton}
     */
    private Map<Type, JToggleButton> createToggleButtonSelectionList() {
        Map<Type, JToggleButton> toggleButtons = new HashMap<>();
        toggleButtons
                .put(Type.USER,
                        GuiComponentFactory.getInstance().createToggleButton("",
                                GuiComponentFactory.getInstance().createResizedIcon(ICON_USER,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        toggleButtons
                .put(Type.TARGET,
                        GuiComponentFactory.getInstance().createToggleButton("",
                                GuiComponentFactory.getInstance().createResizedIcon(ICON_TARGET,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        toggleButtons
                .put(Type.BOX,
                        GuiComponentFactory.getInstance().createToggleButton(
                                "", GuiComponentFactory.getInstance().createResizedIcon(ICON_BOX,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        toggleButtons
                .put(Type.WALL,
                        GuiComponentFactory.getInstance().createToggleButton("",
                                GuiComponentFactory.getInstance().createResizedIcon(ICON_WALL,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        return toggleButtons;
    }
}
