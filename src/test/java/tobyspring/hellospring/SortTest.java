package tobyspring.hellospring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {

    Sort sort;

    @BeforeEach
    void beforeEach(){
        sort = new Sort();
    }

    @Test
    @Order(1)
    //@DisplayName("정렬")
    void sort(){
        //준비


        //실행
        List<String> list = sort.sortByLength(Arrays.asList("aa","b"));

        //검증
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));
    }

    @Test
    @Order(2)
    void sort3Items(){
        //준비


        //실행
        List<String> list = sort.sortByLength(Arrays.asList("aa","ccc", "b"));

        //검증
        Assertions.assertThat(list).isEqualTo(List.of("b","aa", "ccc"));
    }

    @Test
    @Order(3)
    void sortAlreadySorted(){
        //준비

        //실행
        List<String> list = sort.sortByLength(Arrays.asList("b","aa", "ccc"));

        //검증
        Assertions.assertThat(list).isEqualTo(List.of("b","aa", "ccc"));
    }
}
