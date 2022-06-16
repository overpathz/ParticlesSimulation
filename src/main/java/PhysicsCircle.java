import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PhysicsCircle extends Circle {

    private final int weight;

    public PhysicsCircle(double centerX, double centerY, double radius, Paint paint, int weight) {
        super(centerX, centerY, radius, paint);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
