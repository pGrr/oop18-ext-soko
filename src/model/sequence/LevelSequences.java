package model.sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import controller.Controller;
import model.grid.GridImpl;
import model.level.Level;
import model.level.LevelImpl;

import static controller.ControllerConstants.DEFAULT_LEVEL_SEQUENCE;

/**
 * An utility static class for level sequences.
 */
public final class LevelSequences {

    /**
     * Static class, cannot be instantiated.
     */
    private LevelSequences() {
    }

    /**
     * Tries to load from the file-system the default level sequence. .
     *
     * @return the optional of the default level sequence if the loading is
     *         successful, else returns an empty optional
     */
    public static Optional<LevelSequence> createDefault() {
        try {
            return Optional.of(Controller.getInstance().getLevelSequenceController()
                    .loadLevelSequence(ClassLoader.getSystemResource(DEFAULT_LEVEL_SEQUENCE).getPath()));
        } catch (Exception e) {
            // if the default sequence can't be loaded, no problem will occur. There will
            // just be an initial empty level sequence
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Creates the copy of the given level sequence.
     *
     * @param levelSequence the level sequence to be copied
     * @return the level sequence copy
     */
    public static LevelSequence createCopyOf(final LevelSequence levelSequence) {
        List<Level> levelsCopy = new ArrayList<>();
        levelSequence.getAllLevels()
                .forEach(l -> levelsCopy.add(new LevelImpl(new String(l.getName()), new GridImpl(l.getGrid()))));
        return new LevelSequenceImpl(new String(levelSequence.getName()), levelsCopy);
    }
}
