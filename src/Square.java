import javax.swing.*;
import java.awt.*;

public class Square extends JButton {
    private Color color;
    private int player;
    private boolean isKing;

    public Square (Color color, int player, boolean isKing) {
        this.color = color;
        this.player = player;
        this.isKing = isKing;
    }

    public Square setPlayer(int player,int x,int y) {
        this.player = player;
        return this;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.color);
        g.fillRect(0, 0,
                this.getWidth(), this.getHeight());
        switch (this.player) {
            case GameBoard.PLAYER_NONE:
            case GameBoard.PLAYER_DEAD:
                //do nothing
                break;
            case GameBoard.PLAYER_RED:
                g.setColor(Color.RED);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
            case GameBoard.PLAYER_BLUE:
                g.setColor(Color.BLUE);
                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
                break;
//            case GameBoard.PLAYER_SELECTED:
//                g.setColor(Color.green);
//                g.drawOval(4,4, this.getWidth() - 8, this.getHeight() -8);
//                g.setColor(Color.BLUE);
//                g.fillOval(5, 5, this.getWidth() - 10, this.getHeight() - 10);
//                break;
        }
        if (this.isKing) {
            g.setColor(Color.GREEN);
            g.fillOval(20, 20, this.getWidth() - 40, this.getHeight() - 40);
        }
    }


    public boolean isBluePlayer () {
        return this.player == GameBoard.PLAYER_BLUE;
    }

    public boolean isRedPlayer () {
        return this.player == GameBoard.PLAYER_RED;
    }
    public boolean isEmptySquare(){
        return this.player == GameBoard.PLAYER_NONE;
    }


    public boolean isValidSquare () {

        return this.color == Color.BLACK;
    }
    //&& this.color != null
    public boolean isKing() {
        return isKing;
    }
}
