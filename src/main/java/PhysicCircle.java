import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import static java.lang.Math.*;

public class PhysicCircle extends Circle {

    private final double weight;
    private final Pane pane;

    private final double MINIMAL_DISTANCE = 15;

    public PhysicCircle(double centerX, double centerY, double radius, Paint fill, double weight, Pane pane) {
        super(centerX, centerY, radius, fill);
        this.weight = weight;
        this.pane = pane;
    }

    public double getWeight() {
        return weight;
    }

    public PhysicCircle interact(PhysicCircle particle) {
        double distance = sqrt(pow((this.getCenterX() - particle.getCenterX()), 2) +
                pow((this.getCenterY() - particle.getCenterY()), 2));

        if (distance < MINIMAL_DISTANCE) {
            final double newWeight = this.getWeight() + particle.getWeight();
            final double newRadius = newWeight * 1.5;
            String color = Color.values()[(int) (Math.random() * Color.values().length)].getColor();
            return new PhysicCircle(this.getCenterX(), this.getCenterY(), newRadius, Paint.valueOf(color), newWeight, pane);
        }

        final double G = 1000;
        final double F = G * ((this.getWeight() * particle.getWeight()) / (distance*distance * 1.0));
        final double a = F / this.getWeight();
        final double T = 0.5;
        final double V = a * T;
        final double timeQuantumDistance = V * T;

        final double diffX = this.getCenterX() - particle.getCenterX();
        final double diffY = this.getCenterY() - particle.getCenterY();

        Platform.runLater(() -> {
            final double angle = atan2(diffY, diffX);
            this.setCenterX(this.getCenterX() - timeQuantumDistance * Math.cos(angle));
            this.setCenterY(this.getCenterY() - timeQuantumDistance * Math.sin(angle));
        });

        return null;
    }
}