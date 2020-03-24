package Sandbox.HermiteCurveSandbox;

import BezierCurveGenerator.Point;
import BezierCurveGenerator.Segment;
import Jama.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Sanbox {
    static Matrix constraintMatrix = new Matrix(new double[][]{
            {1, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 0, 1, 2, 3},
            {0, 1, 2, 3, 0, -1, 0, 0},
            {0, 0, 2, 6, 0, 0, -2, 0}
    });

    static Point p0 = new Point(0, 0);
    static Point p1 = new Point(0, 50);
    static Point p2 = new Point(50, 50);

    static Matrix solutionsX = new Matrix(new double[][]{
            {p0.getX()},
            {p1.getX()},
            {0},
            {p1.getX()},
            {p2.getX()},
            {0},
            {0},
            {0}
    });

    static Matrix solutionsY = new Matrix(new double[][]{
            {p0.getY()},
            {p1.getY()},
            {0},
            {p1.getY()},
            {p2.getY()},
            {0},
            {0},
            {0}
    });


    public static void main(String[] args) {
        Matrix xCoeffs = constraintMatrix.solve(solutionsX);
        Matrix yCoeffs = constraintMatrix.solve(solutionsY);
        List<Point> points = new ArrayList<>();

        Segment seg1X = new Segment(xCoeffs.get(3, 0), xCoeffs.get(2, 0), xCoeffs.get(1, 0), xCoeffs.get(0, 0));
        Segment seg2X = new Segment(xCoeffs.get(7, 0), xCoeffs.get(6, 0), xCoeffs.get(5, 0), xCoeffs.get(4, 0));

        Segment seg1Y = new Segment(yCoeffs.get(3, 0), yCoeffs.get(2, 0), yCoeffs.get(1, 0), yCoeffs.get(0, 0));
        Segment seg2Y = new Segment(yCoeffs.get(7, 0), yCoeffs.get(6, 0), yCoeffs.get(5, 0), yCoeffs.get(4, 0));

        double t = 0;
        double resolution = 0.1;

        while (t < 1) {
            points.add(new Point(seg1X.compute(t), seg1Y.compute(t)));
            t += resolution;
        }
        t = 0;
        while (t < 1) {
            points.add(new Point(seg2X.compute(t), seg2Y.compute(t)));
            t += resolution;
        }

        for (Point p : points) {
            System.out.printf("(%f, %f), ", p.getX(), p.getY());
        }

    }
}
