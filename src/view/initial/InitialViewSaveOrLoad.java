package view.initial;

import static view.GuiComponentFactoryImpl.*;
import static view.initial.InitialViewConstants.*;
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

public class InitialViewSaveOrLoad {
	
	private final InitialViewWindowImpl owner;
	private final InitialViewList list;

	public InitialViewSaveOrLoad(InitialViewWindowImpl owner, InitialViewList levelList) {
		this.owner = owner;
		this.list = levelList;
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
			JFileChooser fc = owner.getComponentFactory().createFileChooser(ControllerFacade.getInstance().getLevelSequenceFileDescription(), ControllerFacade.getInstance().getLevelSequenceFileExtension());
			fc.showSaveDialog(this.owner.getFrame());
			try {
				String path = fc.getSelectedFile().getAbsolutePath() + ControllerFacade.getInstance().getLevelSequenceFileExtension();
				String name = fc.getSelectedFile().getName();
				ControllerFacade.getInstance().saveLevelSequence(path, name, list.getLevelNames());
			} catch (LevelNotValidException levelNotValidException) {
				this.owner.showLevelNotValidDialog();
			} catch (IOException ioException) {
				this.owner.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(e);
			} catch (ClassNotFoundException classNotFoundException) {
				this.owner.showErrorDialog(DIALOG_CLASS_NOT_FOUND_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT);
				System.err.println(classNotFoundException);
			}
		});
	}
	
	private ActionListener loadSequenceAction() {
		return e -> SwingUtilities.invokeLater(() -> {
			JFileChooser fc = owner.getComponentFactory().createFileChooser(ControllerFacade.getInstance().getLevelSequenceFileDescription(), ControllerFacade.getInstance().getLevelSequenceFileExtension());
			fc.showOpenDialog(this.owner.getFrame());
			try {
				File file = fc.getSelectedFile();
				String path = new String();
				if (file != null) {
					path = file.getPath();
				}
				LevelSequence levelSequence = ControllerFacade.getInstance().loadLevelSequence(path);
				List<String> names = levelSequence.getLevelNames();
				names.stream().forEach(this.list.getListModel()::addElement);
			} catch (IOException ioException) {
				this.owner.showErrorDialog(DIALOG_ERROR_TITLE, DIALOG_IOERROR_TEXT);
				System.err.println(ioException);
			} catch (ClassNotFoundException classNotFoundException) {
				this.owner.showErrorDialog(DIALOG_CLASS_NOT_FOUND_TITLE, DIALOG_CLASS_NOT_FOUND_TEXT);
				System.err.println(classNotFoundException);
			}
		});
	}

}
