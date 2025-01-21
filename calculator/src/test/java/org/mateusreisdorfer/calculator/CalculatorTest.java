package org.mateusreisdorfer.calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class CalculatorTest {
    @Test
    void test() {
        var calculator = new Calculator();

        Assertions.assertEquals(new BigInteger("20"), calculator.startEquation("15 + 3 * 10 - 50 / 2"));
    }
}
