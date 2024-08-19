package lotto;

import java.util.Arrays;
import java.util.Random;

public class LottoGenerator {
    private final Random random = new Random();
    private int[] lottoNumbers;

    public int[] gennerate(){
        lottoNumbers = new int[6];
        int count = 0;

        while(count < 6){
            //1부터 45 사이의 숫자 생성
            int number = random.nextInt(45) + 1;
            //중복되지 않은 경우에만 배열에 추가
            if(isUnique(number)){
                lottoNumbers[count] = number;
                count++;
            }
        }
        return Arrays.stream(lottoNumbers).sorted().toArray();
    }

    private boolean isUnique(int number) {
        for(int i = 0 ; i < 6 ; i++){
            if(number == lottoNumbers[i]){
                return false;
            }
        }
        return true;
    }
}
