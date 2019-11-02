import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/*
* numerical positions of a knight on the chessboard:
* horizontal positions 1,6 -> right 1; positions: 0,7 -> right 2
* horizontal positions 2,5 -> left -1; positions 3,4 -> left -2
* vertical positions 4,7 -> down 1; positions 5,6 -> down 2
* vertical positions 0,3 -> up -1;  positions 1,2 -> up -2
* */
public class Controller
{
    //'board' will store all moves made
    private int[][] board = new int[8][8];
    //keeps track of moves
    private int currentMoveNumber = 0;

    //sets/resets board squares to 0
    public void initializeBoard()
    {
        //rows
        for (int i=0; i < 8; i++)
        {
            //columns
            for (int j=0; j < 8; j++)
            {
                //initialize each square to 0
                board[i][j] = 0;
            }
        }
    }

    public void embedMoveNumber(int currentRow, int currentColumn)
    {
        //text node for move counter
        Text positionNumber = new Text();
        positionNumber.setFont(new Font("Calibri", 20));
        positionNumber.setFill(Color.DARKRED);

        //add text node into board square
        positionNumber.setText(board[currentRow][currentColumn] + "");
        Main.getBoardSquare(Main.getBoard(), currentRow, currentColumn).getChildren().add(positionNumber);
    }


    public void nextValidMoves(int currentRow, int currentColumn)
    {
        int badMoveCount=0;

        embedMoveNumber(currentRow, currentColumn);

        //cycle through all positions on horizontal and vertical axes for available moves
        for (int i = 0; i < 8; i++)
        {
            //for calculating available positions
            int[] horizontalMove = {2,1,-1,-2,-2,-1,1,2};
            int[] verticalMove = {-1,-2,-2,-1,1,2,2,1};

            //copy of current row and column to find all available positions
            int currentRowCopy = currentRow,
                    currentColumnCopy = currentColumn;

            //calculate next available positions to move based on current position
            currentRowCopy += verticalMove[i];
            currentColumnCopy += horizontalMove[i];

            //for 'setOnMouseClick' lambda expression
            int finalCurrentRowCopy = currentRowCopy;
            int finalCurrentColumnCopy = currentColumnCopy;
            //

            //check bounds of available positions and check that board square values are 0
            if (currentRowCopy >=0 && currentRowCopy <=7 && currentColumnCopy >=0 && currentColumnCopy <= 7
            && board[finalCurrentRowCopy][finalCurrentColumnCopy] == 0)
            {
                //highlight all available move positions
                Main.getBoardSquare(Main.getBoard(), currentRowCopy, currentColumnCopy).setStyle("-fx-background-color:green");

                //set click event for available positions
                Main.getBoardSquare(Main.getBoard(), currentRowCopy, currentColumnCopy).setOnMouseClicked(
                        mouseEvent -> {
                        //move to new positions only if it's green
                        if (Main.getBoardSquare(Main.getBoard(), finalCurrentRowCopy, finalCurrentColumnCopy).getStyle().contains("green"))
                        {
                            //clear all highlighted squares
                            clearHighlightedSquares();
                            //increment move counter
                            currentMoveNumber++;
                            //assign counter to square
                            board[finalCurrentRowCopy][finalCurrentColumnCopy] = currentMoveNumber;

                            //recursively call for next available positions
                            nextValidMoves(finalCurrentRowCopy, finalCurrentColumnCopy);
                        }

                });
            }
            else
            {
                badMoveCount++;
            }
        }

        if (badMoveCount == 8)
        {
            new Alert(Alert.AlertType.ERROR, "No more valid moves left!", ButtonType.CLOSE).show();
        }
    }

    public void initialMove()
    {
        /*positions on chessboard:
         * loop through all rows and columns until one coordinate is pressed*/
        for (int row=0; row < 8; row++)
        {
            for (int col=0; col < 8; col++)
            {

                //pass row and column positions to 'setOnMouseClicked' lambda expression
                int currentRow = row,
                        currentColumn = col;
                //

                //mouse click event on board square
                Main.getBoardSquare(Main.getBoard(), currentRow,currentColumn).setOnMouseClicked(
                        mouseEvent -> {
                            //initial move number
                            if (currentMoveNumber == 0)
                            {
                                //increment square's value and update move counter
                                board[currentRow][currentColumn]++;
                                currentMoveNumber = board[currentRow][currentColumn];

                                //find next available moves from current position
                                nextValidMoves(currentRow, currentColumn);
                            }

                        });
            }
        }

    }

    public void clearHighlightedSquares()
    {
        for (int i=0; i < 8; i++)
        {
            for (int j=0; j < 8; j++)
            {
                if ((i+j) % 2 == 0)
                {
                    Main.getBoardSquare(Main.getBoard(), i,j).setStyle("-fx-background-color: grey");
                }
                else
                {
                    Main.getBoardSquare(Main.getBoard(), i,j).setStyle("-fx-background-color: white");
                }
            }
        }
    }

}