import java.util.*;

class Solution {
    public int solution(String[][] clothes) {
        int answer = 1;
        
        HashSet cloType = new HashSet(30);
        HashMap cloMap = new HashMap(30);
        
        for (int i=0;i<clothes.length;i++){
            
            //HashSet cSet = new HashSet();;
            int cNum = 1;
            
            String cType = clothes[i][1];
            if (cloMap.containsKey(cType)){
                cNum = (int)cloMap.get(cType)+1;
            }
            cloMap.put(cType, cNum);
        }
        
        Set kSet = cloMap.keySet();
        
        for (Object cType: kSet) {
            int cNum = (Integer)cloMap.get(cType);
            answer*=(cNum+1);
        }
        
        answer--;
        
        return answer;
    }
}