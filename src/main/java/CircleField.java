import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.*;

public class CircleField extends Circle {

    private final int weight;
    private final int field;
    private final PhysicsCircle circleObject;
    private final Pane board;
    private final ScheduledExecutorService scheduledExecutorService;

    public CircleField(double centerX, double centerY, double radius, Paint fill, int weight, int field, Pane board) {
        super(centerX, centerY, radius, fill);
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        this.setOpacity(0.1);
        this.board = board;
        this.weight = weight;
        this.field = field;
        this.circleObject = new PhysicsCircle(centerX, centerY, radius / 10, Paint.valueOf("red"), 100);
        this.board.getChildren().add(circleObject);
    }

    public void checkBounds() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            ObservableList<Node> nodes = board.getChildren();
            for (Node node : nodes) {
                if (node instanceof CircleField otherField) {
                    if (otherField != this) {
                        if (this.getBoundsInLocal().intersects(otherField.getBoundsInLocal())) {
                            PhysicsCircle circleObject1 = this.getCircleObject();
                            PhysicsCircle circleObject2 = otherField.getCircleObject();
                            int distance = (int) sqrt(pow((circleObject1.getCenterX() - circleObject2.getCenterX()), 2) +
                                    pow((circleObject1.getCenterY() - circleObject2.getCenterY()), 2));

                            double G = 6.67384 * pow(10, -11);
                            double F = G * ((circleObject1.getWeight() * circleObject2.getWeight()) / (distance*distance * 1.0));
                            double a = F / circleObject1.getWeight();
                            double T = 0.5;
                            double V = a * T;
                            System.out.println(V);

                            boolean isRight = (circleObject1.getCenterX() - circleObject2.getCenterX()) < 0;
                            if (isRight) {
                                circleObject1.setCenterX(circleObject1.getCenterX() + V);
                            } else {
                                circleObject1.setCenterX(circleObject1.getCenterX() - V);
                            }
                        }
                    }
                }
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }

    public PhysicsCircle getCircleObject() {
        return circleObject;
    }
}
