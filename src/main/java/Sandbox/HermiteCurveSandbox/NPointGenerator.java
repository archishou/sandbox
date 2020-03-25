package Sandbox.HermiteCurveSandbox;

import BezierCurveGenerator.Point;
import BezierCurveGenerator.Segment;
import Jama.Matrix;

import java.util.ArrayList;
import java.util.List;

public class NPointGenerator {
    private static List<Point> knots = new ArrayList<>();

    public static void main(String[] args) {
        knots.add(new Point(0, 0));
        knots.add(new Point(20, 50));
        knots.add(new Point(0, 50));

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
        // segments = knots - 1
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
        /*
            x(t) = (n3*x^3)+(n2+x^2)+(n1*x)+n0
            {1, 0, 0, 0, 0, 0, 0, 0}, | a0
            {1, 1, 1, 1, 0, 0, 0, 0}, | a1
            {0, 1, 0, 0, 0, 0, 0, 0}, |	a2
            {0, 0, 0, 0, 1, 0, 0, 0}, |	a3
            {0, 0, 0, 0, 1, 1, 1, 1}, |	b0
            {0, 0, 0, 0, 0, 1, 2, 3}, |	b1
            {0, 1, 2, 3, 0, -1, 0, 0},|	b2
            {0, 0, 2, 6, 0, 0, -2, 0} | b3
        */

        // Each point apart from the initial point, requires 4 constraints. N represents the number of constraints.
        int numPoints = ns.size();
        int n = (numPoints - 1) * 4;
        double matrix[][] = new double[n+1][n+1];
        if (numPoints == 1) return null;
        // Initial velocity = 0
        matrix[0][1] = 1;
        // Final velocity = 0
        matrix[1][n] = 3;
        matrix[1][n-1] = 2;
        matrix[1][n-2] = 1;

        // We filled rows 0 & 1 with initial and final velocity constraint.
        int index = 2;

        while (index < n) {
            // Start of segment is equal to initial point.
            int segmentIndexStart =
            matrix[index][segmentIndexStart] = 1;
            index++;
            // End of segment is equal to destination.
            int segmentCoeffIndex = 0;
            while (segmentCoeffIndex < 4) {
                System.out.printf("Segment Coeff Index %d \n", segmentCoeffIndex);
                matrix[index][segmentCoeffIndex + segmentIndexStart] = 1;
                segmentCoeffIndex++;
            }
            index++;
        }
        printMatrix(matrix);
        return new Matrix(matrix);
    }

    private static Matrix generateSolutionsMatrix(List<Double> ns) {
        return new Matrix(new double[][]{
                {0},
                {0},
                {ns.get(0)},
                {ns.get(1)},
                {ns.get(1)},
                {ns.get(2)},
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

    private static void printMatrix(double[][] matrix) {
        for (double[] aMatrix : matrix) for (double anAMatrix : aMatrix) System.out.print(anAMatrix + " ");
    }
}
