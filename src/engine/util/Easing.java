package engine.util;

import engine.util.lambdas.EasingFunction;

public class Easing {

    public static final EasingFunction LINEAR = (a, b, f) -> {
        return a + f * (b - a);
    };

    public static final EasingFunction EASE_IN_OUT_SINE = (a, b, f) -> {
        float factor = (float) -(Math.cos(Math.PI * f) - 1) / 2;
        return a + factor * (b - a);
    };

    private static float easeInOutBounceHelp(float x) {
        float n1 = 7.5625f;
        float d1 = 2.75f;

        if (x < 1 / d1)
            return n1 * x * x;

        if (x < 2 / d1)
            return n1 * (x -= 1.5f / d1) * x + 0.75f;

        if (x < 2.5 / d1)
            return n1 * (x -= 2.25f / d1) * x + 0.9375f;

        return n1 * (x -= 2.625f / d1) * x + 0.984375f;
    }

    public static final EasingFunction EASE_IN_OUT_BOUNCE = (a, b, f) -> {
        float factor = f < 0.5 ? (1 - easeInOutBounceHelp(1 - 2 * f)) / 2f
                : (1 + easeInOutBounceHelp(2 * f - 1)) / 2f;
        return a + factor * (b - a);
    };

    public static final EasingFunction EASE_IN_EXP = (a, b, f) -> {
        float factor = f == 0 ? 0 : (float) Math.pow(2, 10 * f - 10);
        return a + factor * (b - a);
    };
}
