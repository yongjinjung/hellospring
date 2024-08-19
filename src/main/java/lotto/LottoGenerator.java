package lotto;

import java.util.Arrays;
import java.util.Random;

public class LottoGenerator {
    private final Random random = new Random();
    private int[] lottoNumbers;
    private int count;

    public int[] gennerate(){
        
        lottoNumbers = new int[6];
        count = 0;
        
        while(count < 6){
            //1부터 45 사이의 숫자 생성
            int number = random.nextInt(45) + 1;
            //
        }

        return Arrays.stream(lottoNumbers).sorted().toArray();

    }
}
