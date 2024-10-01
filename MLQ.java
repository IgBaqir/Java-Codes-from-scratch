import java.util.Scanner;

public class MLQ {

    class Queue {
        private int MAX_VALUE = 100;
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
            if (front > rear)
                front = rear = -1;
            return value;
        }

        public boolean isEmpty() {
            return front == -1;
        }
    };

    public static void sorti(int[] priority, Queue[] queue, int n, int[][] arr_time, int[][] burst_time, int[] numProcesses) {
        // 1. Sort levels based on priority
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (priority[j] > priority[j + 1]) {
                    int temp = priority[j];
                    priority[j] = priority[j + 1];
                    priority[j + 1] = temp;
    
                    Queue tempQ = queue[j];
                    queue[j] = queue[j + 1];
                    queue[j + 1] = tempQ;
    
                    int[] tempArrTime = arr_time[j];
                    arr_time[j] = arr_time[j + 1];
                    arr_time[j + 1] = tempArrTime;
    
                    int[] tempBurstTime = burst_time[j];
                    burst_time[j] = burst_time[j + 1];
                    burst_time[j + 1] = tempBurstTime;
                }
            }
        }
        
        // 2. Sort processes within each level based on arrival time
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < numProcesses[i] - 1; j++) {
                for (int k = 0; k < numProcesses[i] - j - 1; k++) {
                    if (arr_time[i][k] > arr_time[i][k + 1]) {
                        int temp = arr_time[i][k];
                        arr_time[i][k] = arr_time[i][k + 1];
                        arr_time[i][k + 1] = temp;
    
                        int tempb = burst_time[i][k];
                        burst_time[i][k] = burst_time[i][k + 1];
                        burst_time[i][k + 1] = tempb;
                    }
                }
            }
        }
    }
    
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of levels:");
        int numLevels = scanner.nextInt();
        System.out.println("Enter the number of process:");
        int num_process=scanner.nextInt();



        MLQ mlq = new MLQ();
        Queue[] queues = new Queue[numLevels];
        int[] priority = new int[numLevels];
         int[] numProcesses = new int[numLevels];
        int[][] arr_time = new int[numLevels][100];
        int[][] burst_time = new int[numLevels][100];
        int[][]com_time=new int[numLevels][100];
        int[][]tat=new int[numLevels][100];
        int[][]wt=new int[numLevels][100];
        int[]priority_process=new int[num_process];
        int[] currentQueueIndices = new int[numLevels];

        int tat_toatal=0;
        int wt_total=0;
       
        
        for(int i=0;i<num_process;i++){
            System.out.println("Enter the Queue number of "+(i+1)+" process");
            priority_process[i]=scanner.nextInt();
        }
    
        
        for (int i = 0; i < numLevels; i++) {
            queues[i] = mlq.new Queue();
        }
        
         for (int k = 0; k < numLevels; k++) {
            System.out.println("Enter the priority of level " + (k + 1) + ":");
            priority[k] = scanner.nextInt();
        }
        for(int i=0;i<num_process;i++){
            numProcesses[priority_process[i]-1]++;
        }
        
        for (int i = 0; i < num_process; i++) {
            System.out.println("Enter the arrival time and burst time for process " + (i + 1) + " in queue " + priority_process[i]);
            int arrTime = scanner.nextInt();
            int bTime = scanner.nextInt();
            
            int queueIndex = priority_process[i] - 1;
            arr_time[queueIndex][currentQueueIndices[queueIndex]] = arrTime;
            burst_time[queueIndex][currentQueueIndices[queueIndex]] = bTime;
            
            currentQueueIndices[queueIndex]++;  
        }
        
           
        sorti(priority,queues,numLevels,arr_time,burst_time,numProcesses);

       

       
        System.out.println("+-----------------------+---------------------+");
        System.out.println("|    Arrival Time      |     Burst Time      |");
        System.out.println("+-----------------------+---------------------+");

        for (int k = 0; k < numLevels; k++) {
            System.out.println("| Level " + (k + 1) + "               |                     |");
            for (int j = 0; j < numProcesses[k]; j++) {
                System.out.printf("|      %-15d |      %-15d |\n", arr_time[k][j], burst_time[k][j]);
            }
            System.out.println("+-----------------------+---------------------+");
        }

        int time = 0;
        boolean allProcessed;
        
        do {
            allProcessed = true;
            for (int k = 0; k < numLevels; k++) {
                int j = 0;
                while (j < numProcesses[k]) {
                    if (arr_time[k][j] <= time && com_time[k][j] == 0) {
                        queues[k].push(j);
                        time += burst_time[k][j];
                        com_time[k][j] = time;
                        tat[k][j] = com_time[k][j] - arr_time[k][j];
                        wt[k][j] = tat[k][j] - burst_time[k][j];
                        System.out.println("Executed process " + (j + 1) + " from level " + (k + 1) + " at time " + time);
                        j++;
                        allProcessed = false; 
                    } else {
                        j++;
                    }
                }
            }
        } while (!allProcessed); 
        
         
       

        
        System.out.println("+-------------------------+------------------+------------------+");
        System.out.println("|     Completion Time     |  Turnaround Time |   Waiting Time   |");
        System.out.println("+-------------------------+------------------+------------------+");
        for (int k = 0; k < numLevels; k++) {
            System.out.println("| Level " + (k + 1) + "                                   |");
            for (int j = 0; j < numProcesses[k]; j++) {
                System.out.printf("|      %-18d |      %-12d |      %-12d |\n", com_time[k][j], tat[k][j], wt[k][j]);
                tat_toatal += tat[k][j];
                wt_total += wt[k][j];
            }
            System.out.println("+-------------------------+------------------+------------------+");
        }
        int total_process=0;
        for(int i=0;i<numLevels;i++){
            total_process+=numProcesses[i];
        }
        System.out.println("Toatal num of process int "+total_process);

        System.out.println("Average Turnaround Time: " + ((double)tat_toatal / total_process));
        System.out.println("Average Waiting Time: " + ((double)wt_total / total_process));


        scanner.close();
    }
}



    

