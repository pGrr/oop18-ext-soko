package view.craft;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import view.WindowAbstract;

import static view.GuiComponentFactoryImpl.*;
import static view.craft.CraftWindowConstants.*;

public final class CraftWindowImpl extends WindowAbstract implements CraftWindow {
	
	private final Selection selection;
	private final Grid grid;
	private final Options options;
	
	public CraftWindowImpl() {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.selection = new Selection(this);
		this.grid = new Grid(this);
		this.options = new Options(this);
		this.getFrame().add(createMainPanel());
	}

	@Override
	protected JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(getComponentFactory().createEmptyPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(this.selection.createPanel(), BorderLayout.PAGE_START);
		mainPanel.add(grid.createPanel(), BorderLayout.CENTER);
		mainPanel.add(this.options.createPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}	

	@Override
	public void show() {
		this.getFrame().setVisible(true);
	}
	
	protected Grid getGrid() {
		return this.grid;
	}
	
	protected Selection getSelection() {
		return this.selection;
	}

}