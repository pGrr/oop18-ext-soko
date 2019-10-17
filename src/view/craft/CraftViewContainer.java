package view.craft;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JPanel;
import controller.ControllerFacade;
import view.AbstractGenericView;

import static view.Components.*;
import static view.craft.CraftViewConstants.*;

public final class CraftViewContainer extends AbstractGenericView implements CraftView {
	
	private final ControllerFacade controller;
	private final CraftViewSelection selection;
	private final CraftViewGrid grid;
	private final CraftViewOptions options;
	
	public CraftViewContainer(ControllerFacade controller) {
		super(TITLE, HEIGHT_TO_SCREENSIZE_RATIO, WIDTH_TO_HEIGHT_RATIO);
		this.controller = controller;
		this.selection = new CraftViewSelection();
		this.grid = new CraftViewGrid(this.selection);
		this.options = new CraftViewOptions(controller, this, this.grid);
		this.getFrame().add(createMainPanel());
	}
	
	@Override
	public void showLevelNotValidDialog() {
		showErrorDialog(DIALOG_LEVEL_NOT_CORRECT_TITLE, DIALOG_LEVEL_NOT_CORRECT_TEXT);
	}
	
	@Override
	protected JPanel createMainPanel() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(createEmptyPaddingBorder(DEFAULT_PADDING));
		mainPanel.add(this.selection.createPanel(), BorderLayout.PAGE_START);
		mainPanel.add(grid.createPanel(), BorderLayout.CENTER);
		mainPanel.add(this.options.createPanel(), BorderLayout.PAGE_END);
		return mainPanel;
	}	

	@Override
	public void show() {
		this.getFrame().setVisible(true);
	}
	
	@Override
	protected ActionListener errorAction(JDialog dialog) {
		return e -> {
			dialog.dispose();
			this.controller.backToInitialView();
		};
	}

}