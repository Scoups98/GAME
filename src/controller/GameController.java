package controller;
import listener.GameListener;
import model.*;
import view.*;
import javax.swing.*;
import java.awt.*;
import static view.ChessGameFrame.getCount;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {
    private static Chessboard model;
    private static ChessboardComponent view;
    private ChessGameFrame frame;
    public static PlayerColor currentPlayer = PlayerColor.BLUE;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public static String color(PlayerColor currentPlayer) {
        if (currentPlayer.getColor() == Color.BLUE) {
            return "BLUE";
        } else {
            return "RED";
        }
    }
    public static void restart(){
        model.initGrid();
        model.initPieces();
        view.removeChessComponent();
        view.initiateChessComponent(model);
        currentPlayer = PlayerColor.BLUE;
        ChessGameFrame.count = 2;
        ChessGameFrame.statusLabel.setText("round :" + getCount()/2);
        ChessGameFrame.statusLabelOne.setText("current player: " + GameController.currentPlayer);
        view.repaint();
        view.revalidate();
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        ChessGameFrame.count++;
        ChessGameFrame.statusLabel.setText("round :" + getCount()/2);
        ChessGameFrame.statusLabelOne.setText("current player: " + GameController.currentPlayer);
    }
    public static boolean number(){
        int blue = 0,red = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if(Chessboard.grid[i][j].getPiece() == null){
                    continue;
                }else if(Chessboard.grid[i][j].getPiece().owner == PlayerColor.RED){
                    red++;
                }
                if(Chessboard.grid[i][j].getPiece().owner == PlayerColor.BLUE){
                    blue++;
                }
            }
        }
        if(blue == 0 || red == 0){
            return true;
        }else{
            return false;
        }
    }
    public static boolean win(ChessboardPoint qi) {
        if (occupycamp(qi) || number()) {
            return true;
        } else {
            return false;
        }
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        System.out.println("onPlayer");
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            Chessboard.rankChange();
            if(win(point)){
                int show = JOptionPane.showConfirmDialog(frame,currentPlayer,"The following one is Lose.",JOptionPane.YES_NO_OPTION);
                restart();
            }
            // TODO: if the chess enter Dens or Traps and so on
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, allcomponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
        } else {
            if (model.isValidCapture(selectedPoint, point)) {
                model.captureChessPiece(selectedPoint, point);
//                model.getChessPieceOwner(point);
                view.removeChessComponentAtGrid(point);
                view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
                swapColor();
                selectedPoint = null;
//                component.setSelected(false);
                view.repaint();
            }
            Chessboard.rankChange();
            if(win(point)){
                int show = JOptionPane.showConfirmDialog(frame,currentPlayer,"The following one is Lose.",JOptionPane.YES_NO_OPTION);
                restart();
            }
        }
        // TODO: Implement capture function
    }

    private static boolean occupycamp(ChessboardPoint qi) {
        if (currentPlayer == PlayerColor.BLUE && qi.getRow() == 0 && qi.getCol() == 3) {
            return true;
        }
        if (currentPlayer == PlayerColor.RED && qi.getRow() == 8 && qi.getCol() == 3) {
            return true;
        }
        return false;
    }

/*    private static boolean occupycamp(ChessPiece src,ChessboardPoint dest){
        if(dest.getRow() == 3 && dest.getCol() ==0){//蓝方兽穴所在位置，只有红方动物能进入
            if(src.owner.getColor() == Color.RED){
                //判断是不是红方进入
                return  true;
            }
            else return false;
        }else if(dest.getRow() == 3 && dest.getCol() ==8){//红方兽穴所在位置
            if(src.owner.getColor() == Color.BLUE){
                //判断是否蓝方棋子
                return true;
            }
            else return false;
        }
        return true; //如果b不在兽穴则返回真值
    }

 */
}
