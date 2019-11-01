package view.initial;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.Controller;
import model.Model;
import view.GuiComponentFactory;

import static view.GuiComponentFactoryImpl.DEFAULT_PADDING;

/**
 * The class responsible for the navigation options in the
 * {@link InitialWindowImpl} window, e.g. "save", "craft a level", "play"
 * buttons.
 */
public class InitialOptions {

    private static final String BUTTON_CRAFT_TEXT = "CRAFT A LEVEL";
    private static final String BUTTON_PLAY_TEXT = "PLAY";
    private static final String ICON_CRAFT = "icons/craft.png";
    private static final String ICON_PLAY = "icons/ok.png";

    /**
     * Creates the panel.
     *
     * @return the j panel
     */
    public JPanel createPanel() {
        int littlePadding = Math.round(DEFAULT_PADDING / 2);
        JPanel p = new JPanel(new BorderLayout(littlePadding, littlePadding));
        p.setBorder(GuiComponentFactory.getInstance().createEmptyPaddingBorder(DEFAULT_PADDING));
        JButton craftButton = GuiComponentFactory.getInstance().createButton(BUTTON_CRAFT_TEXT, ICON_CRAFT, craft());
        p.add(craftButton, BorderLayout.PAGE_START);
        JButton playButton = GuiComponentFactory.getInstance().createButton(BUTTON_PLAY_TEXT, ICON_PLAY, play());
        p.add(playButton, BorderLayout.PAGE_END);
        return p;
    }

    /**
     * This is the action listener for the "craft a level" button. It shows the
     * "craft a level" view.
     *
     * @return the action listener for the "craft a level" button
     */
    public ActionListener craft() {
        return e -> SwingUtilities.invokeLater(() -> {
            Controller.getInstance().getNavigationController().toCraftLevelView();
        });
    }

    /**
     * This is the action listener for the "play level sequence" button. It starts
     * the level sequence and shows the game view of the first level.
     *
     * @return the action listener for the "play level sequence" button
     */
    public ActionListener play() {
        return e -> SwingUtilities.invokeLater(() -> {
            Controller.getInstance().getLevelSequenceController()
                    .startLevelSequence(Model.getInstance().getCurrentState());
        });
    }
}
