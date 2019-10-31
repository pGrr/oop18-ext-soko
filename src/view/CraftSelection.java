package view;

import static view.CraftConstants.*;
import static view.GuiComponentFactoryImpl.*;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import model.element.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class CraftSelection.
 */
public class CraftSelection {

    /** The owner. */
    private final CraftWindowImpl owner;

    /** The toggle buttons. */
    private final Map<Type, JToggleButton> toggleButtons;

    /**
     * Instantiates a new craft selection.
     *
     * @param owner the owner
     */
    public CraftSelection(CraftWindowImpl owner) {
        this.owner = owner;
        this.toggleButtons = createToggleButtonSelectionList();
    }

    /**
     * Creates the panel.
     *
     * @return the j panel
     */
    public JPanel createPanel() {
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel(LABEL_WELCOME_TEXT);
        welcomeLabel.setBorder(owner.getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
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
     * Toggle button action listener.
     *
     * @return the action listener
     */
    private ActionListener toggleButtonActionListener() {
        return e -> SwingUtilities.invokeLater(() -> {
            this.toggleButtons.values().forEach(b -> b.setSelected(false));
            ((JToggleButton) e.getSource()).setSelected(true);
        });
    }

    /**
     * Creates the toggle button selection list.
     *
     * @return the map
     */
    private Map<Type, JToggleButton> createToggleButtonSelectionList() {
        Map<Type, JToggleButton> toggleButtons = new HashMap<>();
        toggleButtons
                .put(Type.USER,
                        owner.getComponentFactory().createToggleButton("",
                                owner.getComponentFactory().createResizedIcon(ICON_USER,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        toggleButtons
                .put(Type.TARGET,
                        owner.getComponentFactory().createToggleButton("",
                                owner.getComponentFactory().createResizedIcon(ICON_TARGET,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        toggleButtons
                .put(Type.BOX,
                        owner.getComponentFactory().createToggleButton(
                                "", owner.getComponentFactory().createResizedIcon(ICON_BOX,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        toggleButtons
                .put(Type.WALL,
                        owner.getComponentFactory().createToggleButton("",
                                owner.getComponentFactory().createResizedIcon(ICON_WALL,
                                        TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
                                toggleButtonActionListener()));
        return toggleButtons;
    }

}