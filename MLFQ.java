import java.util.Scanner;

public class MLFQ {
    class Queue {
        private static final int MAX_VALUE = 100;
        private int[] queue = new int[MAX_VALUE];
        private int front = -1;
        private int rear = -1;

        public void push(int val) {
            if (rear == MAX_VALUE - 1) {
                System.out.println("Queue is Overflow");
            } else {
                rear++;
                queue[rear] = val;
                if (front == -1) {
                    front = 0;
                }
            }
        }

        public int pop() {
            if (front == -1) {
                System.out.println("Queue is Empty");
                return -1;
            }
            int value = queue[front];
            front++;
            if (front > rear) {
                front = rear = -1;
            }
            return value;
        }

        public boolean isEmpty() {
            return front == -1;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of levels:");
        int numLevels = scanner.nextInt();
        System.out.println("Enter the number of processes:");
        int numProcesses = scanner.nextInt();

        MLFQ mlq = new MLFQ();
        Queue[] queues = new Queue[numLevels];
        int[] numProcessesInQueue = new int[numLevels];
        int[][] arr_time = new int[numLevels][100];
        int[][] burst_time = new int[numLevels][100];
        int[][] originalBurst = new int[numLevels][100];
        int[][] com_time = new int[numLevels][100];
        int[][] tat = new int[numLevels][100];
        int[][] wt = new int[numLevels][100];
        int[] timeLimit = new int[numLevels];

        for (int i = 0; i < numLevels; i++) {
            queues[i] = mlq.new Queue();
            System.out.println("Enter the time limit for level " + (i + 1) + ":");
            timeLimit[i] = scanner.nextInt();
        }

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter the Queue number of " + (i + 1) + " process");
            int queueNumber = scanner.nextInt();
            numProcessesInQueue[queueNumber - 1]++;
            System.out.println("Enter the arrival time and burst time for process " + (i + 1) + ":");
            int arrTime = scanner.nextInt();
            int bTime = scanner.nextInt();
            arr_time[queueNumber - 1][i] = arrTime;
            burst_time[queueNumber - 1][i] = bTime;
            originalBurst[queueNumber - 1][i] = bTime;
            com_time[queueNumber - 1][i] = -1;
        }
      
        System.out.println("+----------------------+---------------------+");
        System.out.println("|    Arrival Time      |     Burst Time      |");
        System.out.println("+----------------------+---------------------+");
        
        for (int k = 0; k < numLevels; k++) {
            System.out.println("| Level " + (k + 1) + "               |                     |");
            
            for (int j = 0; j < numProcesses; j++) {
                if (arr_time[k][j] != 0 || originalBurst[k][j] != 0) {
                    System.out.printf("|      %-15d |      %-15d |\n", arr_time[k][j], originalBurst[k][j]);
                }
            }
            
            System.out.println("+-----------------------+---------------------+");
        }
        
        // Initialize processes in queues based on arrival time
        for (int i = 0; i < numLevels; i++) {
            for (int j = 0; j < numProcessesInQueue[i]; j++) {
                if (arr_time[i][j] == 0) {
                    queues[i].push(j);
                }
            }
        }
        

        int time = 0;

while (true) {
    boolean isExecuted = false;

    // Check for process arrival and add them to the respective queues
    for (int i = 0; i < numLevels; i++) {
        for (int j = 0; j < numProcesses; j++) {
            if (arr_time[i][j] == time && burst_time[i][j] == originalBurst[i][j]) {
                queues[i].push(j);
            }
        }
    }

    // Process execution
    for (int i = 0; i < numLevels; i++) {
        if (!queues[i].isEmpty()) {
            isExecuted = true;
            
            int processIndex = queues[i].pop();
            
            // Skip if the process is already completed
            if (burst_time[i][processIndex] <= 0) continue;

            // Calculate execution time based on remaining burst and time limit of the queue
            int executionTime = Math.min(burst_time[i][processIndex], timeLimit[i]);
            System.out.println("Executing process " + (processIndex + 1) + " in queue " + (i + 1) + " at time " + time);

            // Update timings
            burst_time[i][processIndex] -= executionTime;
            time += executionTime;

            // If process is completed
            if (burst_time[i][processIndex] == 0) {
                com_time[i][processIndex] = time;
                tat[i][processIndex] = time - arr_time[i][processIndex];
                wt[i][processIndex] = tat[i][processIndex] - originalBurst[i][processIndex];
            } 
            // If process is not completed, add it to the next lower priority queue
            else {
                int nextQueue = Math.min(i + 1, numLevels - 1);
                queues[nextQueue].push(processIndex);
            }

            // break;
        }
    }

    // If no process was executed in this time slice, check if all processes are completed
    if (!isExecuted) {
        boolean allCompleted = true;
        for (int i = 0; i < numLevels; i++) {
            for (int j = 0; j < numProcesses; j++) {
                if (burst_time[i][j] > 0) {
                    allCompleted = false;
                    break;
                }
            }
        }
        if (allCompleted) break;
        else time++;  // Increment the time if not all processes are completed
    }
}

        System.out.println("+-------------------------+------------------+------------------+");
        System.out.println("|     Completion Time     |  Turnaround Time |   Waiting Time   |");
        System.out.println("+-------------------------+------------------+------------------+");
        int tatTotal = 0;
        int wtTotal = 0;
        for (int i = 0; i < numLevels; i++) {
            System.out.println("| Level " + (i + 1) + "                                   |");
            for (int j = 0; j < numProcessesInQueue[i]; j++) {
                System.out.printf("|      %-18d |      %-12d |      %-12d |\n", com_time[i][j], tat[i][j], wt[i][j]);
                tatTotal += tat[i][j];
                wtTotal += wt[i][j];
            }
            System.out.println("+-------------------------+------------------+------------------+");
        }

        int totalProcess = 0;
        for (int i = 0; i < numLevels; i++) {
            totalProcess += numProcessesInQueue[i];
        }
        System.out.println("Total number of processes: " + totalProcess);
        System.out.println("Average Turnaround Time: " + ((double) tatTotal / totalProcess));
        System.out.println("Average Waiting Time: " + ((double) wtTotal / totalProcess));

        scanner.close();
    }
}