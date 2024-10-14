/**
 * Implementation specific for the Level 1 Instructions.
 * Message contents, X, and Y positions are predetermined by assignment specification.
 */
public class InstructionMessageLevelOne extends GameMessage {
    private static final String INSTRUCTION = "PRESS SPACE TO START\nUSE ARROW KEYS TO MOVE\nEAT THE PELLET TO ATTACK";
    private static final int FONT_SIZE = 40;
    private static final int INSTRUCTION_X = 200;
    private static final int INSTRUCTION_Y = 350;

    public InstructionMessageLevelOne() {

        setMessage(INSTRUCTION);
        setFontSize(FONT_SIZE);

        setxPos(INSTRUCTION_X);
        setyPos(INSTRUCTION_Y);

        setMessageFont();
    }
}
