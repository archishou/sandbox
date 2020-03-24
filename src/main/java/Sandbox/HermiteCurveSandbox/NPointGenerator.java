package Sandbox.HermiteCurveSandbox;

import BezierCurveGenerator.Point;
import BezierCurveGenerator.Segment;
import Jama.Matrix;

import java.util.ArrayList;
import java.util.List;

public class NPointGenerator {
    static List<Point> knots = new ArrayList<>();

    public static void main(String[] args) {
        knots.add(new Point(0, 0));
        knots.add(new Point(0, 50));
        knots.add(new Point(50, 50));
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        for (Point p : knots) {
            xs.add(p.getX());
            ys.add(p.getY());
        }

        List<Segment> xSegments = getSegments(xs);
        List<Segment> ySegments = getSegments(ys);
        List<Point> points = new ArrayList<>();

        double t = 0;
        double resolution = 0.1;
        int n = knots.size() - 1;
        int index = 0;
        while (index < n) {
            while (t < 1) {
                points.add(new Point(xSegments.get(index).compute(t), ySegments.get(index).compute(t)));
                t += resolution;
            }
            t = 0;
            index++;
        }
        for (Point p : points) {
            System.out.printf("(%f, %f), ", p.getX(), p.getY());
        }
    }

    private static Matrix generateConstraintMatrix(List<Double> ns) {
        return new Matrix(new double[][]{
                {1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 1, 2, 3},
                {0, 1, 2, 3, 0, -1, 0, 0},
                {0, 0, 2, 6, 0, 0, -2, 0}
        });
    }

    private static Matrix generateSolutionsMatrix(List<Double> ns) {
        return new Matrix(new double[][]{
                {ns.get(0)},
                {ns.get(1)},
                {0},
                {ns.get(1)},
                {ns.get(2)},
                {0},
                {0},
                {0}
        });
    }

    private static List<Segment> getSegments(List<Double> ns) {
        List<Segment> segments = new ArrayList<>();
        Matrix constraints = generateConstraintMatrix(ns);
        Matrix solutions = generateSolutionsMatrix(ns);
        Matrix coeffs = constraints.solve(solutions);
        int n = coeffs.getArray().length;
        int index = 0;
        while (index < n) {
            segments.add(new Segment(coeffs.get(index + 3, 0), coeffs.get(index + 2, 0), coeffs.get(index + 1, 0), coeffs.get(index, 0)));
            index += 4;
        }
        return segments;
    }
}
