package view;

import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToggleButton;

import model.ElementModel.Type;

public class ElementViewImpl implements ElementView {
	
	private final Type type;
	private final Optional<String> iconPath;
	
	public ElementViewImpl(Type type) {
		this.type = type;
		this.iconPath = Optional.empty();
	}
	
	public ElementViewImpl(Type type, String iconPath) {
		this.type = type;
		this.iconPath = Optional.of(iconPath);
	}

	@Override
	public Type getType() {
		return this.type;
	}

	@Override
	public Optional<String> getIconPath() {
		return this.iconPath;
	}
	
	public final ImageIcon getImageIcon() {
		return this.iconPath.isPresent() ? 
				new ImageIcon(ClassLoader.getSystemResource(iconPath.get())) :
					new ImageIcon();
	}

	public final JButton getJButton() {
		return new JButton(getImageIcon());
	}
	
	public final JToggleButton getJToggleButton() {
		return new JToggleButton(getImageIcon());
	}
}
