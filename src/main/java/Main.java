public class Main {
    public static void main(String... args) {
        MasqPolynomial x = new MasqPolynomial();
        // This should be f(x) = x + 4
        x.setCoeff(1, 1);
        x.setCoeff(2, 4);
        // This should output f(9)
        System.out.println(x.getOutput(9));

        // This should be f(x) = x^3 + x^2 + x^1 + x + 1
        x.setCoeff(1, 1);
        x.setCoeff(2, 1);
        x.setCoeff(3, 1);
        x.setCoeff(4, 1);
        // This should output f(9)
        System.out.println(x.getOutput(9));

        // This should be f(x) = x^2
        x.setCoeff(1, 1);
        x.setCoeff(2, 0);
        x.setCoeff(3, 0);
        // This should output f(9)
        System.out.println(x.getOutput(9));

        // This should be f(x) = x^2 + x + 4
        x.setCoeff(1, 1);
        x.setCoeff(2, 1);
        x.setCoeff(3, 4);
        // This should output f(9)
        System.out.println(x.getOutput(9));
    }
}
