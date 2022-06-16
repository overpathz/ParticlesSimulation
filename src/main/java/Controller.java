import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Controller {

    @FXML
    private Pane board;

    @FXML
    private Button createBtn;

    @FXML
    private Button moveBtn;

    private List<PhysicCircle> particles = new ArrayList<>();

    @FXML
    void createObject(ActionEvent event) {
        PhysicCircle circleField1 = new PhysicCircle(200, 100, 10, Paint.valueOf("red"), 10, board);
        PhysicCircle circleField2 = new PhysicCircle(500, 300, 10, Paint.valueOf("red"), 10, board);
//        PhysicCircle circleField3 = new PhysicCircle(300, 300, 10, Paint.valueOf("red"), 10);
        board.getChildren().add(circleField1);
        board.getChildren().add(circleField2);
//        board.getChildren().add(circleField3);
        particles.add(circleField1);
        particles.add(circleField2);
//        particles.add(circleField3);
        enablePhysics();
    }

    private void enablePhysics() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            for (PhysicCircle particle1 : particles) {
                Map<Double, PhysicCircle> circleMap = new HashMap<>();
                for (PhysicCircle particle2 : particles) {
                    if (particle1 != particle2) {
                        double dist = sqrt(pow((particle1.getCenterX() - particle2.getCenterX()), 2)
                                + pow((particle1.getCenterY() - particle2.getCenterY()), 2));
                        circleMap.put(dist, particle2);
                    }
                }
                double lowestDistance = circleMap.keySet().stream().sorted().toList().get(0);
                PhysicCircle nearestParticle = circleMap.get(lowestDistance);
                particle1.interact(nearestParticle);
            }
            System.out.println(board.getChildren().size());
        }, 1, 5, TimeUnit.MILLISECONDS);
    }

    @FXML
    void move() {

    }
}


