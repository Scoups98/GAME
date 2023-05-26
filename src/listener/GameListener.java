package listener;
import  view.allcomponent;
import model.ChessboardPoint;
import view.CellComponent;
import view.ElephantChessComponent;

public interface GameListener {

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, allcomponent component);

}
