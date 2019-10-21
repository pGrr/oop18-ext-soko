package view.craft;

import static view.GuiComponentFactoryImpl.*;
import static view.craft.CraftWindowConstants.*;
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
import view.GuiComponentFactory;

public class Options {
	
	private final CraftWindowImpl owner;
	
	public Options(CraftWindowImpl owner) {
		this.owner = owner;
	}
	
	public JPanel createPanel() {
		JPanel choicesPanel = new JPanel(new GridLayout(1,4, DEFAULT_PADDING, DEFAULT_PADDING));
		choicesPanel.setBorder(owner.getComponentFactory().createTitledPaddingBorder(PANEL_OPTIONS_TITLE, DEFAULT_PADDING));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_SAVE_TEXT, ICON_SAVE, saveButtonActionListener()));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_LOAD_TEXT, ICON_LOAD, loadButtonActionListener()));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_RESET_TEXT, ICON_CANCEL, this.owner.getGrid().resetButtonActionListener()));
		choicesPanel.add(owner.getComponentFactory().createButton(BUTTON_BACK_TEXT, ICON_BACK, backButtonActionListener()));
		return choicesPanel;
	}

	private ActionListener saveButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = owner.getComponentFactory().createFileChooser(
					ControllerFacade.getInstance().getLevelController().getLevelFileDescription(), 
					ControllerFacade.getInstance().getLevelController().getLevelFileExtension());
			fc.showSaveDialog(this.owner.getFrame());
			try {
				String path = fc.getSelectedFile().getAbsolutePath() + ControllerFacade.getInstance().getLevelController().getLevelFileExtension();
				String fileName = fc.getSelectedFile().getName();
				ControllerFacade.getInstance().getLevelController().saveLevel(path, new LevelSchemaImpl(fileName, this.owner.getGrid().getCurrentTypeGrid()));
			} catch (IOException ioException) {
				GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.owner.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				ioException.printStackTrace();
			} catch (LevelNotValidException levelNotValidException) {
				GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.owner.getFrame(), DIALOG_LEVEL_NOT_CORRECT_TITLE, levelNotValidException.toString());
			}
		});
	}
	
	private ActionListener loadButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = owner.getComponentFactory().createFileChooser(
					ControllerFacade.getInstance().getLevelController().getLevelFileDescription(), 
					ControllerFacade.getInstance().getLevelController().getLevelFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			List<List<Type>> typeGrid;
			try {
				typeGrid = ControllerFacade.getInstance().getLevelController().loadLevel(fc.getSelectedFile().getAbsolutePath()).getSchema();
				this.owner.getGrid().acceptTypeGrid(typeGrid);
			} catch (ClassNotFoundException | IOException  inputError) {
				GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.owner.getFrame(), DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(inputError.toString());
			} catch (LevelNotValidException levelNotValidException) {
				GuiComponentFactory.getDefaultInstance().createNotifyDialog(this.owner.getFrame(), DIALOG_LEVEL_NOT_CORRECT_TITLE, levelNotValidException.toString());
			}			
		});
	}
	
	private ActionListener backButtonActionListener() {
		return e -> SwingUtilities.invokeLater(() -> {
			ControllerFacade.getInstance().backToInitialView();
		});
	}
}