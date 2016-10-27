import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import models.MineSweeperBoard;

/**
 * This is the GUI of the Minesweeper game.
 * <p>This file contains the view and controller classes in the MVC architecture.</p>
 *
 * @author Jeroen Keppens, King's College London
 */
public class MineSweeper extends Application {
    private MineSweeperBoard board = new MineSweeperBoard();
    private ToggleButton buttons[][];
    private Label flagsLabel;
    private Label completionLabel;
    private Label statusLabel;

    /**
     * Updates the view by retrieving potentially updated contents from the model.
     */
    private void update() {

        updateFlagsLabel();

        updateCompletionLabel();

        updateStatusLabel();

        processUserAction();

    }

    private void updateCompletionLabel() {
        completionLabel.setText(board.getCompletion() + "/" + board.getMaxCompletion() + " cells");
    }

    private void updateFlagsLabel() {
        flagsLabel.setText(board.getFlags() + "/" + board.getMines() + " flags");
    }

    private void updateStatusLabel() {
        if (board.hasWon()) {

            statusLabel.setText("You have won!");
            statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

        } else if (board.hasLost()) {

            statusLabel.setText("You have lost!");
            statusLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        } else {

            statusLabel.setText("");

        }
    }

    private void processUserAction() {

        for (int row = 0; row < board.getRows(); row++) {

            for (int col = 0; col < board.getCols(); col++) {

                ToggleButton button = buttons[row][col];

                if (board.hasFlag(row, col)) {

                    button.setSelected(false);
                    button.setText("F");
                    button.setStyle("-fx-base: green;");

                } else if (board.revealed(row, col)) {

                    button.setSelected(true);

                    if (board.hasMine(row, col)) {
                        button.setText("M");
                        button.setStyle("-fx-base: red;");

                    } else {

                        button.setText(Integer.toString(board.adjacent(row, col)));
                        button.setStyle("-fx-base: lightgrey;");

                    }

                } else {

                    button.setSelected(false);
                    button.setText("");
                    button.setStyle("-fx-base: whitesmoke;");

                }
            }
        }
    }

    /**
     * Display the game board and create event handlers for each to the widgets.
     *
     * @param primaryStage the top-level container for the UI.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minesweeper");

        GridPane grid = initGridPane();

        initGridRows(grid);
        initGridCols(grid);

        populateGridWithButtons(grid);

        HBox toolbar = initToolbar(primaryStage);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(toolbar);
        borderPane.setCenter(grid);

        Scene scene = new Scene(borderPane, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox initToolbar(final Stage primaryStage) {
        HBox toolbar = new HBox(5);

        styleToolbar(toolbar);

        Button newGame = new Button("New game");

        configureListenerForNewGameButton(newGame);

        flagsLabel = new Label(board.getFlags() + "/" + board.getMines() + " flags");
        completionLabel = new Label(board.getCompletion() + "/" + board.getMaxCompletion() + " cells");
        statusLabel = new Label();
        Button exitButton = new Button("Exit");

        exitButton.setOnAction(event -> primaryStage.close());

        toolbar.getChildren().addAll(newGame, flagsLabel, completionLabel, statusLabel, exitButton);
        return toolbar;
    }

    private void configureListenerForNewGameButton(Button newGame) {
        newGame.setOnAction(e -> {
                board.newGame();
                update();
        });
    }

    private void styleToolbar(HBox toolbar) {
        toolbar.setAlignment(Pos.CENTER_LEFT);
        toolbar.setFillHeight(true);
        toolbar.setSpacing(15);
        toolbar.setPadding(new Insets(10, 10, 0, 10));
    }

    private void populateGridWithButtons(GridPane grid) {

        buttons = new ToggleButton[board.getRows()][board.getCols()];

        for (int row = 0; row < board.getRows(); row++) {

            for (int col = 0; col < board.getCols(); col++) {

                buttons[row][col] = new ToggleButton();

                styleButtonAsUnrevealed(buttons[row][col]);

                configureListenerForButtonAt(grid, row, col);
            }
        }

    }

    private void configureListenerForButtonAt(GridPane grid, int row, int col) {
        grid.add(buttons[row][col], col, row);
        final int r = row;
        final int c = col;
        buttons[row][col].setOnAction(e -> {
            board.reveal(r, c);
            update();
        });

        buttons[row][col].addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {
                    if (e.getButton() == MouseButton.SECONDARY) {
                        if (board.hasFlag(r, c)) {
                            board.unflag(r, c);
                        } else {
                            board.flag(r, c);
                        }
                        update();
                    }
                    e.consume();
                });
    }

    private void styleButtonAsUnrevealed(ToggleButton toggleButton) {
        toggleButton.setStyle("-fx-base: whitesmoke;");
        toggleButton.setMaxWidth(Double.MAX_VALUE);
        toggleButton.setMaxHeight(Double.MAX_VALUE);
    }

    private GridPane initGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setPadding(new Insets(10, 10, 10, 10));
        return grid;
    }

    private void initGridCols(GridPane grid) {
        for (int col = 0; col < board.getCols(); col++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / board.getCols());
            grid.getColumnConstraints().add(columnConstraints);
        }
    }

    private void initGridRows(GridPane grid) {
        for (int row = 0; row < board.getRows(); row++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPercentHeight(100.0 / board.getRows());
            grid.getRowConstraints().add(rowConstraints);
        }
    }

    /**
     * Starts the application.
     *
     * @param args all arguments are ignored.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
