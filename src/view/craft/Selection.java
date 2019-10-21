package view.craft;

import static view.GuiComponentFactoryImpl.*;
import static view.craft.CraftWindowConstants.*;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import model.Pair;
import model.PairImpl;
import model.element.Element.Type;

public class Selection {
	
	private final CraftWindowImpl owner;		
	private final List<Pair<Type,JToggleButton>> toggleButtons;

	public Selection(CraftWindowImpl owner) {
		this.owner = owner;
		this.toggleButtons = createToggleButtonSelectionList();
	}
	
	public JPanel createPanel() {
		JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel welcomeLabel = new JLabel(LABEL_WELCOME_TEXT);
		welcomeLabel.setBorder(owner.getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
		upperPanel.add(welcomeLabel);
		this.toggleButtons.stream()
						  .map(Pair::getY)
						  .forEach(upperPanel::add);
		return upperPanel;
	}
	
	public JToggleButton getSelectedToggleButton() {
		return  this.toggleButtons.stream()
				  				  .map(Pair::getY)
								  .filter(e -> e.isSelected())
								  .findFirst()
								  .orElse(new JToggleButton());
	}
	
	public Type getSelectedType() {
		return  this.toggleButtons.stream()
				  .filter(e -> e.getY().isSelected())
				  .map(Pair::getX)
				  .findFirst()
				  .orElse(Type.EMPTY);
	}

	private ActionListener toggleButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			this.toggleButtons.forEach(b -> b.getY().setSelected(false));
			((JToggleButton) e.getSource()).setSelected(true);
		});
	}
	
	private List<Pair<Type,JToggleButton>> createToggleButtonSelectionList() {
		List<Pair<Type,JToggleButton>> l = new ArrayList<>();
		l.add(new PairImpl<>(Type.BOX, owner.getComponentFactory().createToggleButton("", 
			owner.getComponentFactory().createResizedIcon(ICON_BOX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX), 
			toggleButtonActionListener())));
		l.add(new PairImpl<>(Type.WALL, owner.getComponentFactory().createToggleButton("", 
			owner.getComponentFactory().createResizedIcon(ICON_WALL, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
			toggleButtonActionListener())));
		l.add(new PairImpl<>(Type.TARGET, owner.getComponentFactory().createToggleButton("", 
			owner.getComponentFactory().createResizedIcon(ICON_TARGET, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX), 
			toggleButtonActionListener())));
		l.add(new PairImpl<>(Type.USER, owner.getComponentFactory().createToggleButton("", 
			owner.getComponentFactory().createResizedIcon(ICON_USER, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX, TOGGLE_BUTTON_ICON_EDGE_SIZE_PX),
			toggleButtonActionListener())));
		return l; 
	}

}