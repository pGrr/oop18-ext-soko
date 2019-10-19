package view.craft;

import static view.GuiComponentFactoryImpl.*;
import static view.craft.CraftViewConstants.*;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.ControllerFacade;
import model.element.Element.Type;
import model.level.LevelSchemaImpl;
import model.level.LevelSchemaImpl.LevelNotValidException;

public class CraftViewOptions {
	
	private final CraftViewWindowImpl owner;
	private final CraftViewGrid grid;
	
	public CraftViewOptions(CraftViewWindowImpl owner, CraftViewGrid grid) {
		this.owner = owner;
		this.grid = grid;
	}
	
	public JPanel createPanel() {
		JPanel choicesPanel = new JPanel(new GridLayout(1,4, DEFAULT_PADDING, DEFAULT_PADDING));
		choicesPanel.setBorder(owner.getComponentFactory().createTitledPaddingBorder(PANEL_OPTIONS_TITLE, DEFAULT_PADDING));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_SAVE_TEXT, ICON_SAVE, saveButtonActionListener()));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_LOAD_TEXT, ICON_LOAD, loadButtonActionListener()));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_RESET_TEXT, ICON_CANCEL, this.grid.resetButtonActionListener()));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_BACK_TEXT, ICON_BACK, backButtonActionListener()));
		return choicesPanel;
	}

	private ActionListener saveButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = owner.getComponentFactory().createFileChooser(ControllerFacade.getInstance().getLevelFileDescription(), ControllerFacade.getInstance().getLevelFileExtension());
			fc.showSaveDialog(this.owner.getFrame());
			try {
				String path = fc.getSelectedFile().getAbsolutePath() + ControllerFacade.getInstance().getLevelFileExtension();
				String fileName = fc.getSelectedFile().getName();
				ControllerFacade.getInstance().saveLevel(path, new LevelSchemaImpl(fileName, this.grid.getCurrentTypeGrid()));
			} catch (IOException ioException) {
				this.owner.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				ioException.printStackTrace();
			} catch (LevelNotValidException levelNotValidException) {
				this.owner.showErrorDialog(DIALOG_LEVEL_NOT_CORRECT_TITLE, levelNotValidException.toString());
			}
		});
	}
	
	private ActionListener loadButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = owner.getComponentFactory().createFileChooser(ControllerFacade.getInstance().getLevelFileDescription(), ControllerFacade.getInstance().getLevelFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			List<List<Type>> typeGrid;
			try {
				typeGrid = ControllerFacade.getInstance().loadLevel(fc.getSelectedFile().getAbsolutePath()).getSchema();
				this.grid.acceptTypeGrid(typeGrid);
			} catch (ClassNotFoundException | IOException  inputError) {
				this.owner.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(inputError.toString());
			} catch (LevelNotValidException levelNotValidException) {
				this.owner.showErrorDialog(DIALOG_LEVEL_NOT_CORRECT_TITLE, levelNotValidException.toString());
			}			
		});
	}
	
	private ActionListener backButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			ControllerFacade.getInstance().backToInitialView();
		});
	}

}
