import java.util.*;

class Bridge {
    private static class TruckOnBridge {
        int weight;
        int exitTime;
        TruckOnBridge(int weight, int exitTime) {
            this.weight = weight;
            this.exitTime = exitTime;
        }
    }

    private final Deque<TruckOnBridge> trucks = new ArrayDeque<>();
    private final int maxWeight;
    private final int bridgeLength;

    private int currentTime = 0;
    private int currentWeight = 0;

    public Bridge(int bridgeLength, int maxWeight) {
        this.bridgeLength = bridgeLength;
        this.maxWeight = maxWeight;
    }

    public int passAll(int[] truckWeights) {
        int idx = 0;

        while (idx < truckWeights.length) {
            int nextWeight = truckWeights[idx];

            removeExitedTrucks(); // 다리를 빠져나간 트럭 제거

            if (canEnter(nextWeight)) { // 다음 트럭 진입 가능하면
                enterTruck(nextWeight); // 트럭 진입
                idx++;

                //마지막 트럭이 올라가면 바로 종료
                if (idx == truckWeights.length)
                    return trucks.peekLast().exitTime+1;

                currentTime++; // 1초 진행
                continue;
            }

            jumpToNextExit(); //더 못 올라가면 가장 가까운exitTime으로 점프
        }

        return currentTime;
    }

    /**다리를 빠져나간 트럭 제거 */
    private void removeExitedTrucks() {
        while (!trucks.isEmpty() && trucks.peekFirst().exitTime == currentTime) {
            TruckOnBridge exited = trucks.pollFirst();
            currentWeight -= exited.weight;
        }
    }

    /**다음 트럭이 다리에 진입 가능한지 확인 */
    private boolean canEnter(int truckWeight) {
        return trucks.size() < bridgeLength && currentWeight + truckWeight <= maxWeight;
    }

    /** 트럭 진입 */
    private void enterTruck(int truckWeight) {
        int exitTime = currentTime + bridgeLength;
        trucks.addLast(new TruckOnBridge(truckWeight, exitTime));
        currentWeight += truckWeight;
    }

    /** 더 이상 진입할 수 없을 때, 다음 나갈 시각으로 점프 */
    private void jumpToNextExit() {
        if (!trucks.isEmpty()) {
            int nextExit = trucks.peekFirst().exitTime;
            currentTime = nextExit;
            removeExitedTrucks();
        }
    }
}

class Solution {
    public int solution(int bridge_length, int weight, int[] truck_weights) {
        Bridge bridge = new Bridge(bridge_length, weight);
        return bridge.passAll(truck_weights);
    }
}
