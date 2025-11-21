import java.util.TreeMap;

class Solution {

    static class Operation {
        final char op;
        final int value;

        Operation(char op, int value) {
            this.op = op;
            this.value = value;
        }
    }

    static class OperationEngine {
        private final TreeMap<Integer, Integer> map = new TreeMap<>();

        void insert(int x) {
            map.put(x, map.getOrDefault(x, 0) + 1);
        }

        void deleteMin() {
            if (map.isEmpty()) return;
            int key = map.firstKey();
            int cnt = map.get(key);
            if (cnt == 1) map.remove(key);
            else map.put(key, cnt - 1);
        }

        void deleteMax() {
            if (map.isEmpty()) return;
            int key = map.lastKey();
            int cnt = map.get(key);
            if (cnt == 1) map.remove(key);
            else map.put(key, cnt - 1);
        }

        void processOperation(Operation command) {
            if (command.op == 'I') {
                insert(command.value);
            } else if (command.op == 'D') {
                if (command.value == -1) {
                    deleteMin();
                } else if (command.value == 1) {
                    deleteMax();
                }
            }
        }

        void processOperations(String[] operations) {
            for (String opStr : operations) {
                processOperation(parseOperation(opStr));
            }
        }

        Operation parseOperation(String s) {
            String[] parts = s.trim().split("\\s+");
            char op = parts[0].charAt(0);
            int value = Integer.parseInt(parts[1]);
            return new Operation(op, value);
        }

        int[] getResult() {
            if (map.isEmpty()) {
                return new int[]{0, 0};
            }
            return new int[]{map.lastKey(), map.firstKey()};
        }
    }

    public int[] solution(String[] operations) {
        OperationEngine engine = new OperationEngine();
        engine.processOperations(operations);
        return engine.getResult();
    }
}
