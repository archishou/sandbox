package BezierCurveGenerator.Generator;

import java.util.ArrayList;
import java.util.List;

public class Generator {
    List<Integer> xKnots = new ArrayList<>();
    List<Integer> yKnots = new ArrayList<>();

    public void generateCurve() {

    }

    public void addKnot(int x, int y) {
        xKnots.add(x);
        yKnots.add(y);
    }

    public int[] getXYForT(int t) {
        return new int[]{0, 0};
    }
}
