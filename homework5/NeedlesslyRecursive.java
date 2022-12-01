package homework5;
import java.math.BigInteger;

//These methods are not ideally recursive, done for practice.

public interface NeedlesslyRecursive {
    public static String binaryString(int n) {
        if (n == 0) {
            return "0";
        } else if (n == 1) {
            return "1";
        } else if (n == -1) {
            return "-1";
        } else {
            if (n % 2 == 0) {
                return binaryString(n / 2) + "0";
            } else  {
                return binaryString(n / 2) + "1";
            }
        }
    }

    static BigInteger power(BigInteger x, int n) {
        BigInteger ans;
        if (n == 0)
            return BigInteger.valueOf(1);
        ans = power(x, n / 2);
        if (n % 2 == 0)
            return ans.multiply(ans);
        else
            return ans.multiply(ans).multiply(x);
    }

    static int log3(int i) {
        return log(3, i);
    }

    static int log(int base, int num ) {
        if (num < base) {
            return 0;
        } else {
            return log(base, num/base)+1;
        }
    }

     static BigInteger a(BigInteger val1, BigInteger val2){
        if(val1 == null || val2 == null  || val1.compareTo(BigInteger.ZERO) < 0 || val2.compareTo(BigInteger.ZERO) < 0){
            throw new IllegalArgumentException();
        }
        else if(val1.equals(BigInteger.ZERO) && val2.compareTo(BigInteger.ZERO) >= 0){
            return val2.add(BigInteger.ONE);
        }
        else if(val1.compareTo(BigInteger.ZERO) > 0 && val2.equals(BigInteger.ZERO)){
            return a(val1.subtract(BigInteger.ONE), BigInteger.ONE);
        }
        else if(val1.compareTo(BigInteger.ZERO) > 0 && val2.compareTo(BigInteger.ZERO) > 0){
            return a(val1.subtract(BigInteger.ONE),a(val1,val2.subtract(BigInteger.ONE)));
        }
        return BigInteger.ZERO;
    }

}
