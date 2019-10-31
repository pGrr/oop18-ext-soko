package model;

/**
 * The exception meaning a level is not valid. It's thrown when a level is
 * validated or created if the level is not correct. A level is correct if and
 * only if contains exactly one user, at least one target and an equal number of
 * targets and boxes.
 */
public class LevelNotValidException extends Exception {

    private static final long serialVersionUID = 7024248848064914746L;
    private static final String BASE_MESSAGE = "Level not valid. ";
    private static final String UNCORRECT_POSITION_MESSAGE = "Uncorrect position found.";
    private static final String NO_INITIAL_POINT_MESSAGE = "No initial point was found.";
    private static final String MULTIPLE_INITIAL_POINT_MESSAGE = "Initial point is not singular.";
    private static final String NO_TARGET_MESSAGE = "No target was found.";
    private static final String UNEQUAL_BOX_AND_TARGET_MESSAGE = "Boxes and targets quantity is not equal.";

    /**
     * Returns the base error message saying the level is not valid. Sub-classes
     * will include this message at the start of their own and more specific
     * message.
     */
    @Override
    public String toString() {
        return BASE_MESSAGE;
    }

    /**
     * The exception meaning that one or more positions are not in the correct range
     * allowed by the grid.
     */
    public static class UncorrectPositionException extends LevelNotValidException {

        private static final long serialVersionUID = 2049339700366934644L;

        @Override
        public final String toString() {
            return super.toString() + UNCORRECT_POSITION_MESSAGE;
        }
    }

    /**
     * The exception meaning no initial position of the user was found.
     */
    public static class NoInitialPointException extends LevelNotValidException {

        private static final long serialVersionUID = -1894510007823585149L;

        @Override
        public final String toString() {
            return super.toString() + NO_INITIAL_POINT_MESSAGE;
        }
    }

    /**
     * The exception meaning that more than one initial position of the user were
     * found.
     */
    public static class MultipleInitialPointException extends LevelNotValidException {

        private static final long serialVersionUID = -3154665343553697380L;

        @Override
        public final String toString() {
            return super.toString() + MULTIPLE_INITIAL_POINT_MESSAGE;
        }
    }

    /**
     * The Exception meaning no target was found.
     */
    public static class NoTargetException extends LevelNotValidException {

        private static final long serialVersionUID = 3081257022270340114L;

        @Override
        public final String toString() {
            return super.toString() + NO_TARGET_MESSAGE;
        }
    }

    /**
     * The Exception meaning boxes and targets are not in equal number.
     */
    public static class UnequalBoxAndTargetNumberException extends LevelNotValidException {

        private static final long serialVersionUID = -594628595864277803L;

        @Override
        public final String toString() {
            return super.toString() + UNEQUAL_BOX_AND_TARGET_MESSAGE;
        }
    }
}
