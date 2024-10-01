import java.util.Scanner;

public class StrassensMatrixMultiplication {
    public static int[][] strassenMultiply(int[][] A, int[][] B) {
        int n = A.length;

        if (n <= 2) {
            // Base case: Use standard matrix multiplication
            int[][] C = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    for (int k = 0; k < n; k++) {
                        C[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
            return C;
        }

        // Split matrices into submatrices
        int newSize = n / 2;
        int[][] A11 = new int[newSize][newSize];
        int[][] A12 = new int[newSize][newSize];
        int[][] A21 = new int[newSize][newSize];
        int[][] A22 = new int[newSize][newSize];
        int[][] B11 = new int[newSize][newSize];
        int[][] B12 = new int[newSize][newSize];
        int[][] B21 = new int[newSize][newSize];
        int[][] B22 = new int[newSize][newSize];

        // Initialize submatrices
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                A11[i][j] = A[i][j];
                A12[i][j] = A[i][j + newSize];
                A21[i][j] = A[i + newSize][j];
                A22[i][j] = A[i + newSize][j + newSize];

                B11[i][j] = B[i][j];
                B12[i][j] = B[i][j + newSize];
                B21[i][j] = B[i + newSize][j];
                B22[i][j] = B[i + newSize][j + newSize];
            }
        }

        // Recursive matrix multiplication
        int[][] P1 = strassenMultiply(A11, subtractMatrices(B12, B22));
        int[][] P2 = strassenMultiply(addMatrices(A11, A12), B22);
        int[][] P3 = strassenMultiply(addMatrices(A21, A22), B11);
        int[][] P4 = strassenMultiply(A22, subtractMatrices(B21, B11));
        int[][] P5 = strassenMultiply(addMatrices(A11, A22), addMatrices(B11, B22));
        int[][] P6 = strassenMultiply(subtractMatrices(A12, A22), addMatrices(B21, B22));
        int[][] P7 = strassenMultiply(subtractMatrices(A11, A21), addMatrices(B11, B12));

        // Calculate submatrices of result matrix
        int[][] C11 = subtractMatrices(addMatrices(addMatrices(P5, P4), P6), P2);
        int[][] C12 = addMatrices(P1, P2);
        int[][] C21 = addMatrices(P3, P4);
        int[][] C22 = subtractMatrices(subtractMatrices(addMatrices(P1, P5), P3), P7);

        System.out.println("Matrices P1-P7:");
        System.out.println("P1:");
        displayMatrix(P1);
        System.out.println("P2:");
        displayMatrix(P2);
        System.out.println("P3:");
        displayMatrix(P3);
        System.out.println("P4:");
        displayMatrix(P4);
        System.out.println("P5:");
        displayMatrix(P5);
        System.out.println("P6:");
        displayMatrix(P6);
        System.out.println("P7:");
        displayMatrix(P7);

        // Display matrices C11, C12, C21, and C22
        System.out.println("Matrices C11-C22:");
        System.out.println("C11:");
        displayMatrix(C11);
        System.out.println("C12:");
        displayMatrix(C12);
        System.out.println("C21:");
        displayMatrix(C21);
        System.out.println("C22:");
        displayMatrix(C22);


        // Combine submatrices into result matrix
        int[][] C = new int[n][n];
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                C[i][j] = C11[i][j];
                C[i][j + newSize] = C12[i][j];
                C[i + newSize][j] = C21[i][j];
                C[i + newSize][j + newSize] = C22[i][j];
            }
        }
        return C;
    }
    public static void displayMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
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
    public static int[][] makePowerOfTwo(int[][] matrix) {
        int originalRows = matrix.length;
        int originalCols = matrix[0].length;

        
        int newSize = 1;
        while (newSize < originalRows || newSize < originalCols) {
            newSize *= 2;
        }

        
        int[][] newMatrix = new int[newSize][newSize];

        
        for (int i = 0; i < originalRows; i++) {
            for (int j = 0; j < originalCols; j++) {
                newMatrix[i][j] = matrix[i][j];
            }
        }
        
        return newMatrix;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the dimensions of matrix A (rows columns): ");
        int rowsA = scanner.nextInt();
        int columnsA = scanner.nextInt();

        System.out.println("Enter matrix A:");
        int[][] matrixA = new int[rowsA][columnsA];
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < columnsA; j++) {
                matrixA[i][j] = scanner.nextInt();
            }
        }
        
        matrixA = makePowerOfTwo(matrixA);

        System.out.print("Enter the dimensions of matrix B (rows columns): ");
        int rowsB = scanner.nextInt();
        int columnsB = scanner.nextInt();

        if (columnsA != rowsB) {
            System.out.println("Matrix multiplication is not possible.");
            scanner.close();
            return;
        }

        System.out.println("Enter matrix B:");
        int[][] matrixB = new int[rowsB][columnsB];
        for (int i = 0; i < rowsB; i++) {
            for (int j = 0; j < columnsB; j++) {
                matrixB[i][j] = scanner.nextInt();
            }
        }
        // Make matrixB dimensions a power of 2
        matrixB = makePowerOfTwo(matrixB);
        
        int[][] resultMatrix = strassenMultiply(matrixA, matrixB);

        System.out.println("Resultant matrix:");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < columnsB; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
