import java.util.*;

class Task {
    private int id;
    private int requestTime;
    private int processingTime;

    public Task(int id, int requestTime, int processingTime) {
        this.id = id;
        this.requestTime = requestTime;
        this.processingTime = processingTime;
    }

    public int getId() { return id; }
    public int getRequestTime() { return requestTime; }
    public int getProcessingTime() { return processingTime; }
}

class DiskController {
    private PriorityQueue<Task> readyQueue;

    public DiskController() {
        this.readyQueue = new PriorityQueue<>(
            Comparator.comparingInt(Task::getProcessingTime)
                      .thenComparingInt(Task::getRequestTime)
                      .thenComparingInt(Task::getId)
        );
    }

    public void add(Task task) {
        readyQueue.offer(task);
    }

    public Task takeNext() {
        return readyQueue.poll();
    }

    public boolean hasWaitingTask() {
        return !readyQueue.isEmpty();
    }
}

class Solution {
    public int solution(int[][] jobs) {
        List<Task> waitingList = new ArrayList<>();
        for (int i = 0; i < jobs.length; i++) {
            waitingList.add(new Task(i, jobs[i][0], jobs[i][1]));
        }

        waitingList.sort(Comparator.comparingInt(Task::getRequestTime));

        DiskController controller = new DiskController();
        
        int currentTime = 0;
        int totalTurnaround = 0;
        int completedCount = 0;
        int listIdx = 0;

        while (completedCount < jobs.length) {
            
            while (listIdx < waitingList.size() && waitingList.get(listIdx).getRequestTime() <= currentTime) {
                controller.add(waitingList.get(listIdx));
                listIdx++;
            }

            if (controller.hasWaitingTask()) {
                Task task = controller.takeNext();
                currentTime += task.getProcessingTime();
                
                totalTurnaround += (currentTime - task.getRequestTime());
                completedCount++;
            } else {
                if (listIdx < waitingList.size()) {
                    currentTime = waitingList.get(listIdx).getRequestTime();
                }
            }
        }
        
        return totalTurnaround / jobs.length;
    }
}