import java.util.Scanner;

public class MatrixMultiplication {
    public static int[][] multiplyMatrix(int[][] A, int[][] B) {
        int n = A.length;

        
        if (n == 1) {
            int[][] C = new int[1][1];
            C[0][0] = A[0][0] * B[0][0];
            return C;
        }

        int[][] C = new int[n][n];

        
        int newSize = n / 2;
        int[][] A11 = new int[newSize][newSize];
        int[][] A12 = new int[newSize][newSize];
        int[][] A21 = new int[newSize][newSize];
        int[][] A22 = new int[newSize][newSize];
        int[][] B11 = new int[newSize][newSize];
        int[][] B12 = new int[newSize][newSize];
        int[][] B21 = new int[newSize][newSize];
        int[][] B22 = new int[newSize][newSize];

       
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

       
        int[][] m1 = multiplyMatrix(A11, subtractMatrices(B12, B22));
        int[][] m2 = multiplyMatrix(addMatrices(A11, A12), B22);
        int[][] m3 = multiplyMatrix(addMatrices(A21, A22), B11);
        int[][] m4 = multiplyMatrix(A22, subtractMatrices(B21, B11));
        int[][] m5 = multiplyMatrix(addMatrices(A11, A22), addMatrices(B11, B22));
        int[][] m6 = multiplyMatrix(subtractMatrices(A12, A22), addMatrices(B21, B22));
        int[][] m7 = multiplyMatrix(subtractMatrices(A11, A21), addMatrices(B11, B12));

        
        int[][] S11 = addMatrices(subtractMatrices(addMatrices(m5, m4), m2), m6);
        int[][] S12 = addMatrices(m1, m2);
        int[][] S21 = addMatrices(m3, m4);
        int[][] S22 = subtractMatrices(subtractMatrices(addMatrices(m5, m1), m3), m7);

        System.out.println("Product Matrices :");
        System.out.println("M1:");
        displayMatrix(m1);
        System.out.println("M2:");
        displayMatrix(m2);
        System.out.println("M3:");
        displayMatrix(m3);
        System.out.println("M4:");
        displayMatrix(m4);
        System.out.println("M5:");
        displayMatrix(m5);
        System.out.println("M6:");
        displayMatrix(m6);
        System.out.println("M7:");
        displayMatrix(m7);

       
        System.out.println("Sub Matrices :");
        System.out.println("S11:");
        displayMatrix(S11);
        System.out.println("S12:");
        displayMatrix(S12);
        System.out.println("S21:");
        displayMatrix(S21);
        System.out.println("S22:");
        displayMatrix(S22);

        
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                C[i][j] = S11[i][j];
                C[i][j + newSize] = S12[i][j];
                C[i + newSize][j] = S21[i][j];
                C[i + newSize][j + newSize] = S22[i][j];
            }
        }

        return C;
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
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
    public static void displayMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of rows and columns for matrix A: ");
        int rowsA = scanner.nextInt();
        int colsA = scanner.nextInt();
        int[][] A = new int[rowsA][colsA];

        System.out.println("Enter the elements of matrix A:");
        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                A[i][j] = scanner.nextInt();
            }
        }

        System.out.print("Enter the number of rows and columns for matrix B: ");
        int rowsB = scanner.nextInt();
        int colsB = scanner.nextInt();
        int[][] B = new int[rowsB][colsB];

        System.out.println("Enter the elements of matrix B:");
        for (int i = 0; i < rowsB; i++) {
            for (int j = 0; j < colsB; j++) {
                B[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        if (colsA != rowsB) {
            System.out.println("Matrix multiplication is not possible. Number of columns in matrix A must be equal to number of rows in matrix B.");
        } else {
            int[][] C = multiplyMatrix(A, B);

            System.out.println("\nMatrix A:");
            printMatrix(A);

            System.out.println("\nMatrix B:");
            printMatrix(B);

            System.out.println("\nMatrix C (A * B):");
            printMatrix(C);
        }
    }
}
