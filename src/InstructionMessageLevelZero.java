/**
 * Implementation specific for the Level 0 Instructions.
 * Message contents, X, and Y positions are predetermined by assignment specification.
 */
public class InstructionMessageLevelZero extends GameMessage {
    private static final String INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE";
    private static final int FONT_SIZE = 24;
    private static final int MESSAGE_X_OFFSET = 60;
    private static final int MESSAGE_Y_OFFSET = 190;

    // According to assignment specification, position of Level 0 instruction should be derived from the GameTitle position.
    private static final int INSTRUCTION_X = GameTitle.getTitleX() + MESSAGE_X_OFFSET;
    private static final int INSTRUCTION_Y = GameTitle.getTitleY() + MESSAGE_Y_OFFSET;

    public InstructionMessageLevelZero() {

        setMessage(INSTRUCTION);
        setFontSize(FONT_SIZE);

        setxPos(INSTRUCTION_X);
        setyPos(INSTRUCTION_Y);

        setMessageFont();
    }
}
