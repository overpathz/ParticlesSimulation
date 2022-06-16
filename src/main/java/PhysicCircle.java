import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static java.lang.Math.*;

public class PhysicCircle extends Circle {

    private final int weight;
    private final Pane pane;

    public PhysicCircle(double centerX, double centerY, double radius, Paint fill, int weight, Pane pane) {
        super(centerX, centerY, radius, fill);
        this.weight = weight;
        this.pane = pane;
    }

    public int getWeight() {
        return weight;
    }

    public void interact(PhysicCircle particle) {
        if ((particle.getCenterX() == this.getCenterX()) && (particle.getCenterY() == this.getCenterY())) {
            ObservableList<Node> children = pane.getChildren();
            children.add(new PhysicCircle(this.getCenterX(), this.getCenterY(), 10, Paint.valueOf("red"), 10, pane));
            children.remove(particle);
            children.remove(this);
        }

        double distance = sqrt(pow((this.getCenterX() - particle.getCenterX()), 2) +
                pow((this.getCenterY() - particle.getCenterY()), 2));

        double G = 3000;
        double F = G * ((this.getWeight() * particle.getWeight()) / (distance*distance * 1.0));
        double a = F / this.getWeight();
        double T = 0.5;
        double V = a * T;
        double timeQuantumDistance = V * T;

        double diffX = this.getCenterX() - particle.getCenterX();
        double diffY = this.getCenterY() - particle.getCenterY();

        if ((diffX < 0) && (diffY < 0)) {
            this.setCenterX(this.getCenterX() + timeQuantumDistance);
            this.setCenterY(this.getCenterY() + timeQuantumDistance);
        } else if ((diffX < 0) && (diffY > 0)) {
            this.setCenterX(this.getCenterX() + timeQuantumDistance);
            this.setCenterY(this.getCenterY() - timeQuantumDistance);
        } else if ((diffX > 0) && (diffY > 0)) {
            this.setCenterX(this.getCenterX() - timeQuantumDistance);
            this.setCenterY(this.getCenterY() - timeQuantumDistance);
        } else if ((diffX < 0) && (diffY > 0)) {
            this.setCenterX(this.getCenterX() - timeQuantumDistance);
            this.setCenterY(this.getCenterY() - timeQuantumDistance);
        }
    }
}
