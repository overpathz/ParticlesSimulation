import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Controller {

    @FXML
    private Pane board;

    @FXML
    private Button resetBtn;

    @FXML
    private Button createBtn;

    @FXML
    private TextField param;

    @FXML
    private Button moveBtn;

    private List<PhysicCircle> particles = new ArrayList<>();
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);

    @FXML
    void clearObjects() {
        board.getChildren().clear();
        particles.clear();
    }

    @FXML
    void createObject(ActionEvent event) {

        for (int i = 0; i < Integer.parseInt(param.getText()); i++) {
            String color = Color.values()[(int) (Math.random() * Color.values().length)].getColor();
            double y = Math.random() * board.getHeight();
            double x = Math.random() * board.getHeight();
            double radius = Math.random();
            double weight =  radius * 1.5;
            PhysicCircle circleField = new PhysicCircle(x, y, radius, Paint.valueOf(color), weight, board);
            board.getChildren().add(circleField);
            particles.add(circleField);
        }
        enablePhysics(particles);
    }

    private void enablePhysics(List<PhysicCircle> particles) {
        executorService = Executors.newScheduledThreadPool(1);
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
                PhysicCircle generatedParticle = particle1.interact(nearestParticle);

                if (generatedParticle != null) {
                    List<PhysicCircle> tempList = particles;
                    tempList.add(generatedParticle);
                    Platform.runLater(() -> {
                        board.getChildren().remove(particle1);
                        board.getChildren().remove(nearestParticle);
                        board.getChildren().add(generatedParticle);
                    });
                    tempList.remove(particle1);
                    tempList.remove(nearestParticle);
                    enablePhysics(tempList);
                }
            }
        }, 1, 10, TimeUnit.MILLISECONDS);
    }
}