package com.gstsgy.sequence;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SequenceApplicationTests {

    @Test
    void contextLoads() {
        System.out.print(SeqUtils.getSeq());
    }

}
