public class MasqUtils {
    public static double clip(double x, double min, double max) {
        if (x < min) x =  min;
        if (x > max) x = max;
        return x;
    }
    public static double max(double... vals) {
        double max = Double.MIN_VALUE;
        for (double d: vals) if (max < d) max = d;
        return max;
    }
    public static double adjustAngle(double angle) {
        while (angle > 180) angle -= 360;
        while (angle <= -180) angle = 360;
        return angle;
    }
}
