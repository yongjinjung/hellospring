package lotto;

import java.util.Arrays;

public class LottoGenneratorMain {
    public static void main(String[] args) {
        LottoGenerator lotto = new LottoGenerator();

        for(int i = 0; i < 1000; i++){
            int[] generate = lotto.gennerate();
            System.out.println(Arrays.toString(generate));
        }
    }
}
