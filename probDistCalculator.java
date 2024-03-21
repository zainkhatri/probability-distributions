/* probDistCalculator.java is a program that solves Probability Distribution problems by 
 * asking for the mean and how many trials it takes to solve. The program can solve any distribution
 * from Math 180A, the Binomial, Geometric, Poisson, and Normal distributions. The program is 
 * mistake proof, it will correct you and show you how to get to your answer.
 * 
 * */

import java.util.Scanner;

public class probDistCalculator {
	

    private static void calculateBinomial(Scanner scanner) {
        int n = getIntInput(scanner, "Enter the number of trials (n):");
        int k = getIntInput(scanner, "Enter the number of successful trials (k):");
        double p = getDoubleInput(scanner, "Enter the probability of success on a single trial (p):");

        double binomialProbability = binomialCoefficient(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
		System.out.println("Step 1: Calculate the binomial coefficient (n choose k).");
		System.out.println("Binomial Coefficient (" + n + " choose " + k + ") = " + String.format("%.3f", binomialCoefficient(n, k)));

		System.out.println("Step 2: Calculate the probability of success (p^k) and failure ((1-p)^(n-k)).");
		System.out.println("Probability of success (p^" + k + ") = " + String.format("%.3f", Math.pow(p, k)));
		System.out.println("Probability of failure ((1-p)^(" + n + "-" + k + ")) = " + String.format("%.3f", Math.pow(1 - p, n - k)));

        System.out.println("Binomial Probability: " + String.format("%.3f", binomialProbability));
    }

    private static void calculateGeometric(Scanner scanner) {
        int k = getIntInput(scanner, "Enter the number of trials until the first success (k):");
        double p = getDoubleInput(scanner, "Enter the probability of success on each trial (p):");

        double geometricProbability = Math.pow(1 - p, k - 1) * p;
		System.out.println("Step 1: Calculate the probability of failures in first (k-1) trials.");
		System.out.println("Probability of failure ((1-p)^(" + (k - 1) + ")) = " + String.format("%.3f", Math.pow(1 - p, k - 1)));


		System.out.println("Step 2: Multiply the probability of failure by the probability of success.");
		System.out.println("Probability of success in the kth trial (p) = " + String.format("%.3f", p));

        System.out.println("Geometric Probability: " + String.format("%.3f", geometricProbability));
    }

    private static void calculateNormal(Scanner scanner) {
        double mu = getDoubleInput(scanner, "Enter the mean (μ):");
        double sigma = getDoubleInput(scanner, "Enter the standard deviation (σ):");
        double x = getDoubleInput(scanner, "Enter the value for which you're calculating the probability density (x):");

        double part1 = 1 / (sigma * Math.sqrt(2 * Math.PI));

		System.out.println("Step 1: Calculate the coefficient (1 / (σ * sqrt(2π))).");
		System.out.println("Coefficient = " + String.format("%.3f", (1 / (sigma * Math.sqrt(2 * Math.PI)))));

		System.out.println("Step 2: Calculate the exponent of e, which is -0.5 * ((x - μ) / σ)^2.");
		double exponent = -0.5 * Math.pow((x - mu) / sigma, 2);
        double normalProbabilityDensity = part1 * Math.exp(exponent);
		System.out.println("Exponent = " + String.format("%.3f", exponent));

		System.out.println("Step 3: Calculate the probability density by multiplying the coefficient with e^(exponent).");

        System.out.println("Normal Probability Density: " + String.format("%.3f", normalProbabilityDensity));
    }
    
	private static void calculatePoisson(Scanner scanner) {
    double lambda = getDoubleInput(scanner, "Enter the mean (λ):");
    int k = getIntInput(scanner, "Enter the value of k (number of occurrences):");

    System.out.println("Step 1: Calculate e^(-λ), where e is the base of the natural logarithm and λ is the mean.");
    double eToTheMinusLambda = Math.exp(-lambda);
    System.out.println("e^(-λ) = " + String.format("%.3f", eToTheMinusLambda));

    System.out.println("Step 2: Calculate λ^k, where λ is the mean and k is the number of occurrences.");
    double lambdaToTheK = Math.pow(lambda, k);
    System.out.println("λ^k = " + String.format("%.3f", lambdaToTheK));

    System.out.println("Step 3: Calculate k!, the factorial of k.");
    double kFactorial = factorial(k);
    System.out.println("k! = " + String.format("%.3f", kFactorial));

    double poissonProbability = (eToTheMinusLambda * lambdaToTheK) / kFactorial;
    System.out.println("Step 4: Calculate the Poisson probability using the formula P(X=k) = (e^(-λ) * λ^k) / k!.");
    System.out.println("Poisson Probability: " + String.format("%.3f", poissonProbability));
}


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Which distribution do you want to use? (Poisson/Binomial/Geometric/Normal)");
        String distribution = scanner.nextLine().trim();

        while (!isValidDistribution(distribution)) {
            System.out.println("Invalid input. Please enter a valid distribution (Poisson, Binomial, Geometric, Normal):");
            distribution = scanner.nextLine().trim();
        }

        switch (distribution.toLowerCase()) {
            case "poisson":
                calculatePoisson(scanner);
                break;
            case "binomial":
                calculateBinomial(scanner);
                break;
            case "geometric":
                calculateGeometric(scanner);
                break;
            case "normal":
                calculateNormal(scanner);
                break;
            default:
                System.out.println("Unknown distribution. Exiting now.");
                break;
        }
        scanner.close();
    }

    private static boolean isValidDistribution(String input) {
        return input.matches("(?i)poisson|binomial|geometric|normal");
    }
    

    private static double getDoubleInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                if (input.contains("/")) {
                    String[] parts = input.split("/");
                    if (parts.length == 2) {
                        double numerator = Double.parseDouble(parts[0]);
                        double denominator = Double.parseDouble(parts[1]);
                        if (denominator == 0) throw new ArithmeticException("Denominator can't be zero. Do better.");
                        return numerator / denominator;
                    } else {
                        throw new NumberFormatException("Invalid fraction format.");
                    }
                } else {
                    return Double.parseDouble(input);
                }
            } catch (NumberFormatException | ArithmeticException e) {
                System.out.println("That's an invalid input. Please enter a valid number or fraction (e.g., 5 or 1/2):");
            }
        }
    }

    private static int getIntInput(Scanner scanner, String prompt) {
        System.out.println(prompt);
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("That's an Invalid input. Please enter a valid integer:");
            }
        }
    }

    private static double factorial(int n) {
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static double binomialCoefficient(int n, int k) {
        double result = 1;
        for (int i = 1; i <= k; i++) {
            result *= (n - i + 1);
            result /= i;
        }
        return result;
    }
}
