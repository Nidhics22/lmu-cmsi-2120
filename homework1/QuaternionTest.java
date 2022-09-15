package homework1;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuaternionTest {

    @Test
    public void testConstructorErrors() {
        // That that NaNs are detected in each argument
        assertThrows(IllegalArgumentException.class,
                () -> new Quaternion(Double.NaN, 0, 0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Quaternion(0, Double.NaN, 0, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Quaternion(0, 0, Double.NaN, 0));
        assertThrows(IllegalArgumentException.class,
                () -> new Quaternion(0, 0, 0, Double.NaN));
    }

    @Test
    public void testAccessors() {
        // Java records give us these for free
        var q = new Quaternion(3.5, 2.25, -100, -1.25);
        assertEquals(3.5, q.a());
        assertEquals(2.25, q.b());
        assertEquals(-100.0, q.c());
        assertEquals(-1.25, q.d());
    }

    @Test
    public void testBasicArithmetic() {
        var q1 = new Quaternion(1, 3, 5, 2);
        var q2 = new Quaternion(-2, 2, 8, -1);
        var q3 = new Quaternion(-1, 5, 13, 1);
        var q4 = new Quaternion(-46, -25, 5, 9);
        assertEquals(q3, q1.plus(q2));
        assertEquals(q1, q3.minus(q2));
        assertEquals(q4, q1.times(q2));
        assertEquals(Quaternion.K, Quaternion.I.times(Quaternion.J));
    }


    @Test
    public void testNorms() {
        var q1 = new Quaternion(60, -20, 9, 12);
        assertEquals(65.0, q1.norm());
        var q2 = new Quaternion(12, 3, 84, -4);
        assertEquals(85.0, q2.norm());
        var q3 = new Quaternion(1, -1, -1, 1);
        var q4 = new Quaternion(0.5, -0.5, -0.5, 0.5);
        assertEquals(q4, q3.normalized());
    }



    @Test
    public void testConjugates() {
        var q1 = new Quaternion(1, -21, -1, 13);
        var q2 = new Quaternion(1, 21, 1, -13);
        assertEquals(q2, q1.conjugate());
    }

    @Test
    public void testInverses() {
        var q1 = new Quaternion(1, -1, 1, -1);
        var q2 = new Quaternion(0.25, 0.25, -0.25, 0.25);
        assertEquals(q2, q1.inverse());
        assertEquals(q1, q2.inverse());
    }


    @Test
    public void testCoefficients() {
        assertEquals(List.of(0.0, 0.0, 0.0, 0.0),
                new Quaternion(0, 0, 0, 0).coefficients());
        assertEquals(List.of(2.0, 1.5, 10.0, -8.0),
                new Quaternion(2, 1.5, 10, -8).coefficients());
    }



    @Test
    public void testToString() {
        assertEquals("Quaternion[a=0.0, b=0.0, c=0.0, d=0.0]",
                new Quaternion(0, 0, 0, 0).toString());
        assertEquals("Quaternion[a=0.0, b=-1.0, c=0.0, d=2.25]",
                new Quaternion(0, -1, 0, 2.25).toString());
        assertEquals("Quaternion[a=0.0, b=0.0, c=0.0, d=-1.0]",
               Quaternion.ZERO.minus(Quaternion.K).toString());
    }


}