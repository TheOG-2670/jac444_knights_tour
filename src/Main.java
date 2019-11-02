import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Main extends Application {
    //grid layout for chessboard
    private static GridPane gridPane = new GridPane();

    public static StackPane getBoardSquare(GridPane gridPane, int row, int col)
    {
        //accesses each child node of the gridpane chessboard
        for (Node n: gridPane.getChildren())
        {
            //if the node at the row and column is the same as the incoming row and column numbers,
            //return that node casted as a stackpane node
            if (GridPane.getColumnIndex(n) == col
                    && GridPane.getRowIndex(n) == row)
            {
                return (StackPane) n;
            }
        }
        //return nothing otherwise
        return null;
    }

    public static GridPane getBoard()
    {
        return gridPane;
    }

    public static void createChessBoard()
    {
        //rows
        for (int row=0; row < 8; row++)
        {
            //columns
            for (int col=0; col < 8; col++)
            {
                //chessboard square
                StackPane boardSquares = new StackPane();

                //both row and column sum is even
                if ((row+col) % 2 == 0)
                {
                    //set square color as grey
                    boardSquares.setStyle("-fx-background-color: grey");
                }
                else
                {
                    //row and column sum is odd, set square color to white
                    boardSquares.setStyle("-fx-background-color: white");
                }

                //add square to specific row and column in gridpane
                gridPane.add(boardSquares, col, row);

            }

            //set row height (minHeight)
            gridPane.getRowConstraints().add(new RowConstraints(63));
            //set column width (minWidth)
            gridPane.getColumnConstraints().add(new ColumnConstraints(63));

        }
    }

    @Override
    public void start(Stage primaryStage)
    {
        createChessBoard();
        Controller controller = new Controller();
        controller.initializeBoard();
        controller.initialMove();

        primaryStage.setTitle("workshop 6 - bonus");
        primaryStage.setScene(new Scene(gridPane, 500, 500));
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
