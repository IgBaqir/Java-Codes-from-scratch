import java.util.Scanner;

public class strassens {

    public static int[][] strassenMultiply(int[][] A, int[][] B) {
        int n = A.length;

        if (n <= 2) {
            return multiplyMatrices(A, B);
        }

        int newSize = n / 2;

        int[][] a11 = new int[newSize][newSize];
        int[][] a12 = new int[newSize][newSize];
        int[][] a21 = new int[newSize][newSize];
        int[][] a22 = new int[newSize][newSize];
        int[][] b11 = new int[newSize][newSize];
        int[][] b12 = new int[newSize][newSize];
        int[][] b21 = new int[newSize][newSize];
        int[][] b22 = new int[newSize][newSize];

        // Divide matrices into submatrices
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                a11[i][j] = A[i][j];
                a12[i][j] = A[i][j + newSize];
                a21[i][j] = A[i + newSize][j];
                a22[i][j] = A[i + newSize][j + newSize];

                b11[i][j] = B[i][j];
                b12[i][j] = B[i][j + newSize];
                b21[i][j] = B[i + newSize][j];
                b22[i][j] = B[i + newSize][j + newSize];
            }
        }

        // Calculate intermediate matrices
        int[][] p1 = strassenMultiply(addMatrices(a11, a22), addMatrices(b11, b22));
        int[][] p2 = strassenMultiply(addMatrices(a21, a22), b11);
        int[][] p3 = strassenMultiply(a11, subtractMatrices(b12, b22));
        int[][] p4 = strassenMultiply(a22, subtractMatrices(b21, b11));
        int[][] p5 = strassenMultiply(addMatrices(a11, a12), b22);
        int[][] p6 = strassenMultiply(subtractMatrices(a21, a11), addMatrices(b11, b12));
        int[][] p7 = strassenMultiply(subtractMatrices(a12, a22), addMatrices(b21, b22));

        // Calculate result submatrices
        int[][] c11 = addMatrices(subtractMatrices(addMatrices(p1, p4), p5), p7);
        int[][] c12 = addMatrices(p3, p5);
        int[][] c21 = addMatrices(p2, p4);
        int[][] c22 = subtractMatrices(subtractMatrices(addMatrices(p1, p5), p3), p6);

        // Combine result submatrices
        int[][] result = new int[n][n];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                result[i][j] = c11[i][j];
                result[i][j + newSize] = c12[i][j];
                result[i + newSize][j] = c21[i][j];
                result[i + newSize][j + newSize] = c22[i][j];
            }
        }

        return result;
    }

    public static int[][] addMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    public static int[][] subtractMatrices(int[][] A, int[][] B) {
        int n = A.length;
        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }

    public static int[][] multiplyMatrices(int[][] A, int[][] B) {
        int numRowsA = A.length;
        int numColsA = A[0].length;
        int numRowsB = B.length;
        int numColsB = B[0].length;

        if (numColsA != numRowsB) {
            throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication.");
        }

        int[][] result = new int[numRowsA][numColsB];

        for (int i = 0; i < numRowsA; i++) {
            for (int j = 0; j < numColsB; j++) {
                int sum = 0;
                for (int k = 0; k < numColsA; k++) {
                    sum += A[i][k] * B[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }

    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows for matrix A: ");
        int numRowsA = scanner.nextInt();
        System.out.print("Enter the number of columns for matrix A: ");
        int numColsA = scanner.nextInt();

        System.out.println("Enter matrix A elements:");
        int[][] A = new int[numRowsA][numColsA];
        for (int i = 0; i < numRowsA; i++) {
            for (int j = 0; j < numColsA; j++) {
                A[i][j] = scanner.nextInt();
            }
        }
        System.out.print("Enter the number of rows for matrix B: ");
        int numRowsB = scanner.nextInt();
        System.out.print("Enter the number of columns for matrix B: ");
        int numColsB = scanner.nextInt();

        System.out.println("Enter matrix B elements:");
        int[][] B = new int[numRowsB][numColsB];
        for (int i = 0; i < numRowsB; i++) {
            for (int j = 0; j < numColsB; j++) {
                B[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        System.out.println("Matrix A:");
        printMatrix(A);

        System.out.println("Matrix B:");
        printMatrix(B);

        int[][] result = strassenMultiply(A, B);

        System.out.println("Result of matrix multiplication:");
        printMatrix(result);
    }
}