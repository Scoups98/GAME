package view;
import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;
/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    public final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> blueTrap = new HashSet<>();
    private final Set<ChessboardPoint> redTrap = new HashSet<>();
    private final Set<ChessboardPoint> dens = new HashSet<>();
    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }

    public void removeChessComponent(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessboardPoint point = new ChessboardPoint(i,j);
                getGridComponentAt(point).removeAll();
                getGridComponentAt(point).revalidate();
            }
        }
    }

    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    if (chessPiece.rank == 8) {
                        gridComponents[i][j].add(
                                new ElephantChessComponent(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (chessPiece.rank == 7) {
                        gridComponents[i][j].add(
                                new ShiZi(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (chessPiece.rank == 6) {
                        gridComponents[i][j].add(
                                new Tigher(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (chessPiece.rank == 5) {
                        gridComponents[i][j].add(
                                new Bao(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (chessPiece.rank == 4) {
                        gridComponents[i][j].add(
                                new lang(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (chessPiece.rank == 3) {
                        gridComponents[i][j].add(
                                new Dog(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (chessPiece.rank == 2) {
                        gridComponents[i][j].add(
                                new Cat(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                    if (chessPiece.rank == 1) {
                        gridComponents[i][j].add(
                                new Mice(
                                        chessPiece.getOwner(),
                                        CHESS_SIZE));
                    }
                }
            }
        }
    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));
        blueTrap.add(new ChessboardPoint(0,2));
        blueTrap.add(new ChessboardPoint(0,4));
        blueTrap.add(new ChessboardPoint(1,3));
        dens.add(new ChessboardPoint(0,3));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));
        redTrap.add(new ChessboardPoint(8,2));
        redTrap.add(new ChessboardPoint(8,4));
        redTrap.add(new ChessboardPoint(7,3));
        dens.add(new ChessboardPoint(8,3));

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (blueTrap.contains(temp)) {
                    cell = new CellComponent(Color.PINK, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (redTrap.contains(temp)) {
                    cell = new CellComponent(Color.PINK, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else if (dens.contains(temp)) {
                    cell = new CellComponent(Color.YELLOW, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, allcomponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public allcomponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        allcomponent chess = (allcomponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    public ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    public Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (allcomponent) clickedComponent.getComponents()[0]);
            }
        }
    }
}
