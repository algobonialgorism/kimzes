class Solution {
    boolean solution(String s) {
        boolean answer = true;
        
        int curInd = 0;
        int openLen = 0;
        
        while (curInd<s.length()) {
            if(s.charAt(curInd)=='('){
                openLen+=1;
                curInd+=1;
                if(curInd==s.length()) {
                    answer=false;
                    break;
                }
            }
            else{
                openLen-=1;
                curInd+=1;
                if (openLen<0||curInd==0){
                    answer=false;
                    break;
                }
            }
        }
        if (openLen>0) {
            answer=false;
        }
        return answer;
    }
}