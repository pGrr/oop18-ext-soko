package controller.sequence;

import java.io.IOException;
import java.util.List;
import model.level.LevelSchemaImpl.LevelNotValidException;
import model.sequence.LevelSequence;

public interface SequenceController {
	
	String getLevelSequenceFileDescription();
	
	String getLevelSequenceFileExtension();
	
	LevelSequence createLevelSequence(String name, List<String> paths) 
			throws LevelNotValidException, IOException, ClassNotFoundException;
	
	void saveLevelSequence(String path, String name, List<String> levels) 
			throws LevelNotValidException, ClassNotFoundException, IOException;
	
	void playLevelSequence(LevelSequence levelSequence);
	
	LevelSequence loadLevelSequence(String path) 
			throws IOException, ClassNotFoundException;
	
	static SequenceController getDefaultInstance() {
		return SequenceControllerImpl.getInstance();
	}
	
}
