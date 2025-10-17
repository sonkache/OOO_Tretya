package wsuniversallib;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculationTest {

    // ***** 10 тестов низкой сложности *****

    @Test
    void GetQuantityForProduct_ProductType1_MaterialType1_ValidData() {
        int q = Calculation.getQuantityForProduct(1, 1, 1, 33, 33);
        assertEquals(1202, q);
    }

    @Test
    void GetQuantityForProduct_ProductType1_MaterialType2_ValidData() {
        int q = Calculation.getQuantityForProduct(1, 2, 10, 50, 100);
        assertEquals(55067, q);
    }

    @Test
    void GetQuantityForProduct_ProductType2_MaterialType1_ValidData() {
        int q = Calculation.getQuantityForProduct(2, 1, 5, 40, 80);
        assertEquals(40120, q);
    }

    @Test
    void GetQuantityForProduct_ProductType3_MaterialType2_ValidData() {
        int q = Calculation.getQuantityForProduct(3, 2, 1, 20, 30);
        assertEquals(5065, q);
    }

    @Test
    void GetQuantityForProduct_ZeroCount_ReturnsMinusOne() {
        int q = Calculation.getQuantityForProduct(1, 1, 0, 10, 10);
        assertEquals(-1, q);
    }

    @Test
    void GetQuantityForProduct_ZeroWidth_ReturnsMinusOne() {
        int q = Calculation.getQuantityForProduct(1, 1, 5, 0, 10);
        assertEquals(-1, q);
    }

    @Test
    void GetQuantityForProduct_ZeroLength_ReturnsMinusOne() {
        int q = Calculation.getQuantityForProduct(1, 1, 5, 10, 0);
        assertEquals(-1, q);
    }

    @Test
    void GetQuantityForProduct_InvalidProductType_ReturnsMinusOne() {
        int q = Calculation.getQuantityForProduct(0, 1, 10, 10, 10);
        assertEquals(-1, q);
    }

    @Test
    void GetQuantityForProduct_InvalidMaterialType_ReturnsMinusOne() {
        int q = Calculation.getQuantityForProduct(1, 3, 10, 10, 10);
        assertEquals(-1, q);
    }

    @Test
    void GetQuantityForProduct_RoundingEdge_SmallValues() {
        int q = Calculation.getQuantityForProduct(1, 1, 1, 1, 1);
        assertEquals(2, q);
    }

    // ***** 5 тестов высокой сложности *****

    @Test
    void GetQuantityForProduct_ExampleFromSpecification_ValidatesExpectedRange() {
        int q = Calculation.getQuantityForProduct(3, 1, 15, 20, 45);
        assertTrue(q == 114148 || q == 114147);
    }

    @Test
    void GetQuantityForProduct_LargeValues_DoNotOverflowInt() {
        int q = Calculation.getQuantityForProduct(3, 1, 1000, 1000, 1000);
        assertTrue(q > 0);
    }

    @Test
    void GetQuantityForProduct_RoundingAlmostDown_StillRoundsUp() {
        int q = Calculation.getQuantityForProduct(2, 2, 7, 17, 7);
        assertEquals(2085, q);
    }

    @Test
    void GetQuantityForProduct_RoundingAlmostUp_StillRoundsUp() {
        int q = Calculation.getQuantityForProduct(1, 1, 1, 13, 35);
        assertEquals(503, q);
    }

    @Test
    void GetQuantityForProduct_Monotonicity_ByCount_NonDecreasing() {
        for (int pt : new int[]{1, 2, 3}) {
            for (int mt : new int[]{1, 2}) {
                int q1 = Calculation.getQuantityForProduct(pt, mt, 3, 12, 25);
                int q2 = Calculation.getQuantityForProduct(pt, mt, 4, 12, 25);
                assertTrue(q2 >= q1);
            }
        }
    }
}
