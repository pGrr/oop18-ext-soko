package view.initial;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialConstants.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import controller.ControllerFacade;
import model.level.LevelSchemaImpl.LevelNotValidException;
import model.sequence.LevelSequence;

public class SaveLoadSequence {
	
	private final InitialWindowImpl owner;

	public SaveLoadSequence(InitialWindowImpl owner) {
		this.owner = owner;
	}
	
	public JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setBorder(owner.getComponentFactory().createTitledPaddingBorder(PANEL_SAVE_OR_LOAD_SEQUENCE_TITLE, DEFAULT_PADDING));
		JButton saveButton = owner.getComponentFactory().createButton("", ICON_DOWNLOAD, saveSequenceAction());
		panel.add(saveButton);
		JButton loadButton = owner.getComponentFactory().createButton("", ICON_UPLOAD, loadSequenceAction());
		panel.add(loadButton);
		return panel;
	}
	
	private ActionListener saveSequenceAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = owner.getComponentFactory().createFileChooser(
					ControllerFacade.getInstance().getSequenceController().getLevelSequenceFileDescription(), 
					ControllerFacade.getInstance().getSequenceController().getLevelSequenceFileExtension());
			fc.showSaveDialog(this.owner.getFrame());
			try {
				String path = fc.getSelectedFile().getAbsolutePath() + ControllerFacade.getInstance().getSequenceController().getLevelSequenceFileExtension();
				String name = fc.getSelectedFile().getName();
				ControllerFacade.getInstance().getSequenceController().saveLevelSequence(path, name, this.owner.getLevelList().getLevelNames());
			} catch (LevelNotValidException e1) {
				this.owner.showLevelInvalidDialog(e1.toString());
			} catch (ClassNotFoundException e1) {
				this.owner.showClassNotFoundErrorDialog();
			} catch (IOException e2) {
				this.owner.showIOErrorDialog();
			}
		});
	}
	
	private ActionListener loadSequenceAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = owner.getComponentFactory().createFileChooser(
					ControllerFacade.getInstance().getSequenceController().getLevelSequenceFileDescription(), 
					ControllerFacade.getInstance().getSequenceController().getLevelSequenceFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			try {
				File file = fc.getSelectedFile();
				String path = new String();
				if (file != null) {
					path = file.getPath();
				}
				LevelSequence levelSequence = ControllerFacade.getInstance().getSequenceController().loadLevelSequence(path);
				List<String> names = levelSequence.getLevelNames();
				names.stream().forEach(this.owner.getLevelList().getListModel()::addElement);
			}  catch (ClassNotFoundException e1) {
				this.owner.showClassNotFoundErrorDialog();
			} catch (IOException e2) {
				this.owner.showIOErrorDialog();
			}
		});
	}
}