import com.sun.media.jfxmedia.events.PlayerStateEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class GameBoard extends JPanel {

    public static final int BOARD_LENGTH = 8;

    private ArrayList<ArrayList<Square>> boardData;
    public static final int PLAYER_NONE = 0;
    public static final int PLAYER_BLUE = 1;
    public static final int PLAYER_RED = 2;
    public static final int PLAYER_DEAD = 3;

    int redPiecesLeft = 12;
    int bluePiecesLeft = 12;
    int firstClickRow;
    int firstClickColumn;
    int secondClickRow;
    int secondClickColumn;
    int thirdClickRow;
    int thirdClickColumn;

    protected void highLightPiece(Graphics g) {
        g.setColor(Color.GREEN);


    }


    Square firstClick = null;
    Square secondClick = null;
    // Square thirdClick = null;
    Square redDeadPiece = null;
    Square blueDeadPiece = null;
    boolean isRedTurn = false;

    public GameBoard(int x, int y, int width, int height) {
        this.setBackground(Color.BLUE);
        GridLayout gridLayout = new GridLayout(BOARD_LENGTH, BOARD_LENGTH);
        this.setLayout(gridLayout);
        this.boardData = new ArrayList<>();

        boolean black = false;
        boolean setPlayer = false;

        for (int row = 0; row < BOARD_LENGTH; row++) {
            ArrayList<Square> currentRow = new ArrayList<>();
            for (int column = 0; column < BOARD_LENGTH; column++) {
                int player = PLAYER_NONE;
                if (setPlayer) {
                    if (row < 3) {
                        player = PLAYER_RED;
                    } else if (row >= 5) {
                        player = PLAYER_BLUE;
                    }
                }
                Square square = null;
                if (black) {
                    square = new Square(Color.BLACK, player, false);
                } else {
                    square = new Square(Color.WHITE, player, false);
                }
                int finalRow = row;
                int finalColumn = column;

//                Square finalFirstClick = firstClick;
//                Square finalSecondClick;
                Square finalSquare = square;
                square.addActionListener((event) -> {
                    if (firstClick == null) {
                        firstClick = finalSquare;
                        firstClickRow = finalRow;
                        firstClickColumn = finalColumn;
                    } else if(firstClick != null) {
                        secondClick = finalSquare;
                        secondClickRow = finalRow;
                        secondClickColumn = finalColumn;
                    }
                    while (firstClick != null && secondClick != null && bluePiecesLeft > 0 && redPiecesLeft > 0) {
                        boolean isRedFirstClickValid = isRedTurn && firstClick.isRedPlayer() && secondClick.isEmptySquare();
                        boolean isBlueFirstClickValid = !isRedTurn && firstClick.isBluePlayer() && secondClick.isEmptySquare();
                        if ((isRedFirstClickValid || isBlueFirstClickValid) && secondClick.isValidSquare() && secondClick.isEmptySquare() ) {
                            if (Math.abs(secondClickColumn - firstClickColumn) == 1 && Math.abs(secondClickRow - firstClickRow) == 1) {
                                if (isRedTurn) {
                                    firstClick.setPlayer(PLAYER_NONE,firstClickRow,firstClickColumn);
                                    secondClick.setPlayer(PLAYER_RED,secondClickRow,secondClickColumn);
                                } else {
                                    firstClick.setPlayer(PLAYER_NONE,firstClickRow,firstClickColumn);
                                    secondClick.setPlayer(PLAYER_BLUE,secondClickRow,secondClickColumn);
                                }
                                isRedTurn = !isRedTurn;
                            }
//                            isRedTurn = !isRedTurn;
                            else if (Math.abs(secondClickColumn - firstClickColumn) == 2 && Math.abs(secondClickRow - firstClickRow) == 2) {
                                if (isRedTurn) {
                                    firstClick.setPlayer(PLAYER_NONE,firstClickRow,firstClickColumn);
                                    secondClick.setPlayer(PLAYER_RED,secondClickRow,secondClickColumn);
                                    if(secondClickRow-firstClickRow > 0){             //eating right piece
                                        redDeadPiece.setPlayer(PLAYER_DEAD,(firstClickRow+1),(firstClickColumn+1));
                                        bluePiecesLeft--;
                                    }
                                    else {                                           //eating left piece
                                        redDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow + 1), (firstClickColumn - 1));
                                        bluePiecesLeft--;
                                    }
                                } else {
                                    firstClick.setPlayer(PLAYER_NONE,firstClickRow,firstClickColumn);
                                    secondClick.setPlayer(PLAYER_BLUE,secondClickRow,secondClickColumn);
                                    if(secondClickRow-firstClickRow > 0){            //eating right
                                        blueDeadPiece.setPlayer(PLAYER_DEAD,(firstClickRow-1),firstClickColumn+1);
                                        redPiecesLeft--;
                                    }else {                                          //eating left
                                        blueDeadPiece.setPlayer(PLAYER_DEAD, (firstClickRow - 1), firstClickColumn - 1);
                                        redPiecesLeft--;
                                    }
                                }
                                isRedTurn = !isRedTurn;
                            }
                        }
                        firstClick = null;
                        secondClick = null;
                        blueDeadPiece = null;
                        redDeadPiece = null;

                        break;
                    }
                    if(redPiecesLeft < 1) System.out.println("BLUE TEAM WINNNSSS");
                    else if (bluePiecesLeft < 1) System.out.println("RED TEAM WINNNSSS");

                });
                this.add(square);
                currentRow.add(square);
                black = !black;
                setPlayer = !setPlayer;
            }
            this.boardData.add(currentRow);
            black = !black;
            setPlayer = !setPlayer;
        }

    }
}