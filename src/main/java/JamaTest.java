import Jama.Matrix;

public class JamaTest {
    static Matrix coeffs = new Matrix(
            new double[][] {
                    {5, 10},
                    {10, 3}
            }
    );
    static Matrix sol = new Matrix(
            new double[][]{
                    {10},
                    {3}
            }
    );

    public static void main(String[] args) {
        // Passes as of 1/19/20
        System.out.println("X: " + coeffs.solve(sol).get(0, 0));
        System.out.println("Y: " + coeffs.solve(sol).get(1, 0));
    }
}
