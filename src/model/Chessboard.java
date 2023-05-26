package model;
import controller.GameController;

import java.sql.SQLOutput;

import static java.lang.Math.min;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    public static Cell[][] grid;
    public static void rankChange(){
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if(grid[i][j].getPiece() == null){
                }else if ((i == 0 &&(j == 2 || j == 4) || (i == 1 && j == 3)
                        && grid[i][j].getPiece().getOwner() == PlayerColor.RED) ||
                        (i == 8 && (j == 2 || j == 4) || (i == 7 && j == 3)
                                && grid[i][j].getPiece().getOwner() == PlayerColor.BLUE)) {
                    grid[i][j].getPiece().setRank(0);
              //  } else if ((i == 3 || i == 4 || i == 5) && (j == 1 || j == 2 || j == 4 || j == 5) &&
                  //      grid[i][j].getPiece().rank == 1) {
                  //  grid[i][j].getPiece().setRank(10);
                } else if (grid[i][j].getPiece().getName().equals("shu")) {
                    grid[i][j].getPiece().setRank(1);
                } else if (grid[i][j].getPiece().getName().equals("mao")) {
                    grid[i][j].getPiece().setRank(2);
                }else if(grid[i][j].getPiece().getName().equals("gou")){
                    grid[i][j].getPiece().setRank(3);
                } else if (grid[i][j].getPiece().getName().equals("lang")) {
                    grid[i][j].getPiece().setRank(4);
                } else if (grid[i][j].getPiece().getName().equals("bao")) {
                    grid[i][j].getPiece().setRank(5);
                } else if (grid[i][j].getPiece().getName().equals("hu")) {
                    grid[i][j].getPiece().setRank(6);
                } else if (grid[i][j].getPiece().getName().equals("lion")) {
                    grid[i][j].getPiece().setRank(7);
                } else if (grid[i][j].getPiece().getName().equals("xiang")) {
                    grid[i][j].getPiece().setRank(8);
                }
            }
        }
    }
    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        initGrid();
        initPieces();
    }
    public void initGrid() {
        grid = new Cell[9][7];
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();   //把棋盘格子分出来
            }
        }
    }
    public void initPieces() {
        grid[0][0].setPiece(new ChessPiece(PlayerColor.BLUE, "lion",7));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.RED, "lion",7));
        grid[2][6].setPiece(new ChessPiece(PlayerColor.BLUE, "xiang",8));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "xiang",8));
        grid[2][0].setPiece(new ChessPiece(PlayerColor.BLUE, "shu",1));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.RED, "shu",1));
        grid[2][2].setPiece(new ChessPiece(PlayerColor.BLUE, "bao",5));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.RED, "bao",5));
        grid[1][5].setPiece(new ChessPiece(PlayerColor.BLUE, "mao",2));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.RED, "mao",2));
        grid[1][1].setPiece(new ChessPiece(PlayerColor.BLUE, "gou",3));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.RED, "gou",3));
        grid[2][4].setPiece(new ChessPiece(PlayerColor.BLUE, "lang",4));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.RED, "lang",4));
        grid[0][6].setPiece(new ChessPiece(PlayerColor.BLUE, "hu",6));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.RED, "hu",6));
    }
    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }
    
    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    public int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
        //返回值为一说明在同一列或同一行
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        System.out.println("moveChess");
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
//        if (isValidCapture(src, dest)) {
//
//            throw new IllegalArgumentException("Illegal chess capture!");
//        }
        removeChessPiece(dest);
        setChessPiece(dest, getChessPieceAt(src));
        removeChessPiece(src);
    }

    public Cell[][] getGrid() {
        return grid;
    }
    
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        System.out.println("in");
        boolean sc = src.getCol() == 1 || src.getCol() == 2 || src.getCol() == 4 || src.getCol() == 5;
        boolean sr = src.getRow() == 3 || src.getRow() == 4 || src.getRow() == 5;
        boolean riverOne = sc && (src.getRow() == 2 || src.getRow() == 6); //src在河边（上下）
        boolean riverTwo = sr && (src.getCol() == 0 ||src.getCol() == 3 || src.getCol() == 6); //src在河边（左右和中间）
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {
            return false;
        }
        System.out.println(getChessPieceAt(src).rank);
        System.out.println(dest.getRow());
        System.out.println(dest.getCol());
        if(getChessPieceAt(src).rank != 1 && (dest.getRow() == 3 || dest.getRow() == 4 || dest.getRow() == 5)
        && (dest.getCol() == 1 || dest.getCol() == 2 || dest.getCol() == 4 || dest.getCol() == 5)){
            return false; // 非鼠的棋子不能下河 ？？为什么还是可以被吃掉！
        }
        if(dest.getCol() == 3 && dest.getRow() == 0 && getChessPieceAt(src).getOwner() == PlayerColor.BLUE ||
                (dest.getCol() == 3 && dest.getRow() == 8 && getChessPieceAt(src).getOwner() == PlayerColor.RED)){
            return false; //不能进自己的兽穴
        }
        if((getChessPieceAt(src).rank == 7 || getChessPieceAt(src).rank == 6) &&
                (riverOne || riverTwo) && isValidCap(src,dest) && river(src,dest)){
            return true;
        }
        return calculateDistance(src, dest) == 1 && (getChessPieceAt(src).rank == 1 || !(sc && sr)); //！sc&&sr表示不在河里
    }

    public boolean river(ChessboardPoint src, ChessboardPoint dest){
        if(src.getRow() == dest.getRow()) {
            int h = src.getRow();
            int x = src.getCol(), y = dest.getCol();
            if(x > y) {
                int t = x; x = y; y = t;
            }
            for(int k = x+1; k <= y-1; k ++)
                if(grid[h][k].getPiece() != null && grid[h][k].getPiece().getName().equals("shu"))
                    return false;
            return true;
        } else {
            int h = src.getCol();
            int x = src.getRow(), y = dest.getRow();
            if(x > y) {
                int t = x; x = y; y = t;
            }
            for(int k = x+1; k <= y-1; k ++)
                if(grid[k][h].getPiece() != null && grid[k][h].getPiece().getName().equals("shu"))
                    return false;
            return true;
        }
        /*
        return true; //no use
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                for (int k = 3; k <= 5; k++) {
                    if (grid[i][j].getPiece() == null) {
                        continue;
                    } else if (grid[i][j].getPiece().getName().equals("shu") && i == k && (j == 1 || j == 2) &&
                            src.getRow() == k && src.getCol() == 0 && dest.getRow() == k && dest.getCol() == 3 ||
                            (src.getRow() == k && src.getCol() == 3 && dest.getRow() == k && dest.getCol() == 0)) {
                        return false;
                    }//河左边有老鼠
                    if (grid[i][j].getPiece().getName().equals("shu") && i == k && (j == 4 || j == 5) &&
                            src.getRow() == k && src.getCol() == 3 && dest.getRow() == k && dest.getCol() == 6 ||
                            src.getRow() == k && src.getCol() == 6 && dest.getRow() == k && dest.getCol() == 3) {
                        return false;
                    }//河右边有老鼠
                }

                for (int k = 1; k < 3; k++) {
                    if (grid[i][j].getPiece() == null) {
                        continue;
                    } else if (grid[i][j].getPiece().getName().equals("shu") && (i == 3 || i == 4 || i == 5) && j == k &&
                            src.getRow() == 2 && src.getCol() == k && dest.getRow() == 6 && dest.getCol() == k ||
                            src.getRow() == 6 && src.getCol() == k && dest.getRow() == 2 && dest.getCol() == k) {
                        return false;
                    }
                }

                for (int k = 4; k <= 5 ; k++) {
                    if (grid[i][j].getPiece() == null) {
                        continue;
                    } else if (grid[i][j].getPiece().getName().equals("shu") && (i == 3 || i == 4 || i == 5) && j == k &&
                            src.getRow() == 2 && src.getCol() == k && dest.getRow() == 6 && dest.getCol() == k ||
                            src.getRow() == 6 && src.getCol() == k && dest.getRow() == 2 && dest.getCol() == k) {
                        return false;
                    }
                }
            }
        }
        return true;
         */
    }
    public boolean isInRiver(ChessboardPoint dest) {
        boolean sc = dest.getCol() == 1 || dest.getCol() == 2 || dest.getCol() == 4 || dest.getCol() == 5;
        boolean sr = dest.getRow() == 3 || dest.getRow() == 4 || dest.getRow() == 5;
        return sc && sr;
    }
    public  boolean isValidCap(ChessboardPoint src, ChessboardPoint dest) {//判断可以过河的棋子是否在河两边， 避免走对角线，允许过河时跨越多行/列的特殊走法
        boolean sc = dest.getCol() == 1 || dest.getCol() == 2 || dest.getCol() == 4 || dest.getCol() == 5;
        boolean sr = dest.getRow() == 3 || dest.getRow() == 4 || dest.getRow() == 5;
        boolean riverOne = sc && (src.getRow() == 2 || src.getRow() == 6); //src在河边（上下）
        boolean riverTwo = sr && (src.getCol() == 0 ||src.getCol() == 3 || src.getCol() == 6); //src在河边（左右和中间）
        int difr = src.getRow() - dest.getRow();
        int difc = src.getCol() - dest.getCol();
        if((difr == 1 || difr == -1) && difc == 0){
            return true;
        }
        if((difc == 1 || difc == -1) && (difr == 0)){
            return true;
        }
        if ((((difc == 3 || difc == -3) && difr == 0 && riverTwo) || (riverOne && (difr == 4 || difr == -4) && difc == 0)
        && (getChessPieceAt(src).rank == 7 || getChessPieceAt(src).rank == 6))) {
            return true;
        }
        return false;
    }

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) { //SRC: source; DEST: destination
        if(getChessPieceOwner(src) != getChessPieceOwner(dest)) {
            int srcRank = getChessPieceAt(src).rank;
            int destRank = getChessPieceAt(dest).rank;
            if(destRank == 1 && isInRiver(dest))
                return false;
            // TODO: 坐标合法？ 判断大小
            System.out.println(srcRank + ", " + destRank);
            if ((srcRank >= destRank && isValidCap(src, dest) &&
                    (destRank != 1 || srcRank != 8))){
                return true;
            }
            if((srcRank < destRank && isValidCap(src, dest) &&
                    (srcRank == 1 && destRank == 8))){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
}
