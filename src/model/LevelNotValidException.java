package model;

// TODO: Auto-generated Javadoc
/**
 * The Class LevelNotValidException.
 */
public class LevelNotValidException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 7024248848064914746L;

    /** The Constant BASE_MESSAGE. */
    private final static String BASE_MESSAGE = "Level not valid. ";

    /** The Constant UNCORRECT_POSITION_MESSAGE. */
    private final static String UNCORRECT_POSITION_MESSAGE = "Uncorrect position found.";

    /** The Constant NO_INITIAL_POINT_MESSAGE. */
    private final static String NO_INITIAL_POINT_MESSAGE = "No initial point was found.";

    /** The Constant MULTIPLE_INITIAL_POINT_MESSAGE. */
    private final static String MULTIPLE_INITIAL_POINT_MESSAGE = "Initial point is not singular.";

    /** The Constant NO_TARGET_MESSAGE. */
    private final static String NO_TARGET_MESSAGE = "No target was found.";

    /** The Constant UNEQUAL_BOX_AND_TARGET_MESSAGE. */
    private final static String UNEQUAL_BOX_AND_TARGET_MESSAGE = "Boxes and targets quantity is not equal.";

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return BASE_MESSAGE;
    }

    /**
     * The Class UncorrectPositionException.
     */
    public static class UncorrectPositionException extends LevelNotValidException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 2049339700366934644L;

        /**
         * To string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return super.toString() + UNCORRECT_POSITION_MESSAGE;
        }
    }

    /**
     * The Class NoInitialPointException.
     */
    public static class NoInitialPointException extends LevelNotValidException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -1894510007823585149L;

        /**
         * To string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return super.toString() + NO_INITIAL_POINT_MESSAGE;
        }
    }

    /**
     * The Class MultipleInitialPointException.
     */
    public static class MultipleInitialPointException extends LevelNotValidException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -3154665343553697380L;

        /**
         * To string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return super.toString() + MULTIPLE_INITIAL_POINT_MESSAGE;
        }
    }

    /**
     * The Class NoTargetException.
     */
    public static class NoTargetException extends LevelNotValidException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 3081257022270340114L;

        /**
         * To string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return super.toString() + NO_TARGET_MESSAGE;
        }
    }

    /**
     * The Class UnequalBoxAndTargetNumberException.
     */
    public static class UnequalBoxAndTargetNumberException extends LevelNotValidException {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -594628595864277803L;

        /**
         * To string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return super.toString() + UNEQUAL_BOX_AND_TARGET_MESSAGE;
        }
    }
}
