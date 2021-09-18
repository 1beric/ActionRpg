package engine.util.lambdas;

@FunctionalInterface
public interface EasingFunction {

    public float ease(float a, float b, float factor);

}
