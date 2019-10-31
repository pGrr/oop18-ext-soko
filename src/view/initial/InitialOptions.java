package view.initial;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialConstants.*;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.Controller;
import model.Model;

// TODO: Auto-generated Javadoc
/**
 * The Class InitialOptions.
 */
public class InitialOptions {

    /** The owner. */
    private final InitialWindowImpl owner;

    /**
     * Instantiates a new initial options.
     *
     * @param owner the owner
     */
    public InitialOptions(InitialWindowImpl owner) {
        this.owner = owner;
    }

    /**
     * Creates the panel.
     *
     * @return the j panel
     */
    public JPanel createPanel() {
        int littlePadding = Math.round(DEFAULT_PADDING / 5);
        JPanel p = new JPanel(new BorderLayout(littlePadding, littlePadding));
        p.setBorder(owner.getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
        JButton craftButton = owner.getComponentFactory().createButton(BUTTON_CRAFT_TEXT, ICON_CRAFT, crafAction());
        p.add(craftButton, BorderLayout.PAGE_START);
        JButton playButton = owner.getComponentFactory().createButton(BUTTON_PLAY_TEXT, ICON_PLAY, playAction());
        p.add(playButton, BorderLayout.PAGE_END);
        return p;
    }

    /**
     * Craf action.
     *
     * @return the action listener
     */
    public ActionListener crafAction() {
        return e -> SwingUtilities.invokeLater(() -> {
            Controller.getInstance().getNavigationController().toCraftLevelView();
        });
    }

    /**
     * Play action.
     *
     * @return the action listener
     */
    public ActionListener playAction() {
        return e -> SwingUtilities.invokeLater(() -> {
            Controller.getInstance().getLevelSequenceController()
                    .startLevelSequence(Model.getInstance().getCurrentState());
        });
    }
}