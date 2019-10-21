package controller.sequence;

import static controller.ControllerConstants.LEVEL_SEQUENCE_FILE_DESCRIPTION;
import static controller.ControllerConstants.LEVEL_SEQUENCE_FILE_EXTENSION;
import static view.initial.InitialConstants.DIALOG_ERROR_LEVEL_SEQUENCE_EMPTY_TEXT;
import static view.initial.InitialConstants.DIALOG_ERROR_TITLE;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import controller.ControllerFacade;
import model.ModelFacade;
import model.level.LevelSchema;
import model.level.LevelSchemaImpl.LevelNotValidException;
import model.sequence.LevelSequence;
import model.sequence.LevelSequenceImpl;
import view.GuiComponentFactory;

public class SequenceControllerImpl implements SequenceController {
	
	private static SequenceController SINGLETON;

	private SequenceControllerImpl() {}
	
	public static final SequenceController getInstance() {
		if (SINGLETON == null) {
			SINGLETON = new SequenceControllerImpl();
		}
		return SINGLETON;
	}

	@Override
	public String getLevelSequenceFileExtension() {
		return LEVEL_SEQUENCE_FILE_EXTENSION;
	}

	@Override
	public String getLevelSequenceFileDescription() {
		return LEVEL_SEQUENCE_FILE_DESCRIPTION;
	}
	

	@Override
	public LevelSequence createLevelSequence(String name, List<String> paths) throws LevelNotValidException, IOException, ClassNotFoundException {		
		List<LevelSchema> levelSchemaList = new ArrayList<>();
		for (String path : paths) {
			levelSchemaList.add(ControllerFacade.getInstance().getLevelController().loadLevel(path));
		}
		return new LevelSequenceImpl(name, levelSchemaList);	
	}

	@Override
	public void saveLevelSequence(String path, String name, List<String> levels) throws LevelNotValidException, ClassNotFoundException, IOException {
		try (ObjectOutputStream o = new ObjectOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(
								new File(path))))) {
			o.writeObject(createLevelSequence(name, levels));
		}	
	}

	@Override
	public LevelSequence loadLevelSequence(String path) throws IOException, ClassNotFoundException {
		try (ObjectInputStream o = new ObjectInputStream(
				new BufferedInputStream(
						new FileInputStream(
								new File(path))))) {
			return (LevelSequence) o.readObject();
		}	
	}

	@Override
	public void playLevelSequence(LevelSequence levelSequence) {
		ModelFacade.getInstance().initializeLevelSequence(levelSequence);
		if (ModelFacade.getInstance().hasNextSchema()) {
			LevelSchema levelSchema = ModelFacade.getInstance().getNextSchema();
			ControllerFacade.getInstance().playLevel(levelSchema);
		} else {
			GuiComponentFactory.getDefaultInstance().createNotifyDialog(null, DIALOG_ERROR_TITLE, DIALOG_ERROR_LEVEL_SEQUENCE_EMPTY_TEXT).setVisible(true);;
		}
	}

}
