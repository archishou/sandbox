package BezierCurveGenerator.Generator;

import BezierCurveGenerator.Point;
import BezierCurveGenerator.Segment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    private List<Point> knots = new ArrayList<>();

    public List<Segment> generateSegments() {
        return null;
    }

    public Point getXYForT(int t) {
        return new Point(0, 0, Color.BLACK);
    }

    public void addKnot(Point p) {
        knots.add(p);
    }
}
