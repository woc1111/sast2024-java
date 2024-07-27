package homework;

import java.util.Arrays;
import java.util.Scanner;

public class LongestSubstringWithoutRepeatingCharacters {
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length(), ans = 0;
        int visitFlag[] = new int[95];      // 0x20-0x7E
        int tmp_longest = 0
    ;
        int startptr=0;
        while(startptr<n){
            for(int i=0;i<95;i++){
                visitFlag[i]=-1;    // -1表示未标记，否则其值代表本组出现的坐标
            }
            int k=0;
            while(true){
                int asciiVal =Integer.valueOf(s.charAt(startptr+k));
                if(visitFlag[asciiVal-0x20]>=0){
                    if(k > tmp_longest){    //此时k为本组的最长子串长度
                        tmp_longest = k;                    
                    }
                    startptr=visitFlag[asciiVal-0x20]+1;
                    break;
                }
                visitFlag[asciiVal-0x20]=startptr+k;
                k++;
                if(startptr+k >= n){
                    startptr = startptr+k;
                    if(k > tmp_longest){
                        tmp_longest = k;
                    }
                    break;
                }
            
            }
        }
        ans = tmp_longest;
        return ans;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the string: ");
        String s = input.nextLine();
        System.out.println(lengthOfLongestSubstring(s));
    }
}
