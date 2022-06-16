import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Controller {

    @FXML
    private Pane board;

    @FXML
    private Button createBtn;

    @FXML
    private Button moveBtn;

    private CircleField circle1;
    private CircleField circle2;

    @FXML
    void createObject(ActionEvent event) {
//        for (int i = 0; i < 50; i++) {
//            board.getChildren().add(new CircleField(Math.random() * board.getWidth(),
//                    Math.random() * board.getHeight(), 100, Paint.valueOf("blue"), 100, 100, board));
//        }
        board.getChildren().add(new CircleField(100, 300, 50, Paint.valueOf("blue"), 100, 100, board));
        board.getChildren().add(new CircleField(200, 300, 50, Paint.valueOf("blue"), 100, 100, board));
        enableCheckingBounds();
    }

    private void enableCheckingBounds() {
        ObservableList<Node> children = board.getChildren();
        for (Node child : children) {
            if (child instanceof CircleField) {
                ((CircleField)child).checkBounds();
            }
        }
    }

    @FXML
    void move(ActionEvent event) {

    }

}


