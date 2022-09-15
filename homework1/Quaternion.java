package homework1;

import java.util.ArrayList;
import java.util.List;

public record Quaternion(double a, double b, double c, double d) {

    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    public static final Quaternion I = new Quaternion(0, 1, 0, 0);
    public static final Quaternion J = new Quaternion(0, 0, 1, 0);
    public static final Quaternion K = new Quaternion(0, 0, 0, 1);

    public Quaternion {
        if(Double.isNaN(a) || Double.isNaN(b) || Double.isNaN(c) || Double.isNaN(d)) {
            throw new IllegalArgumentException();
        }
    }

    public Quaternion plus(Quaternion q2) {
        Quaternion qPlus = new Quaternion(this.a() + q2.a(), this.b() + q2.b(), this.c() + q2.c(), this.d + q2.d());
        return qPlus;
    }

    public Quaternion minus(Quaternion q2) {
        Quaternion qMnius = new Quaternion(this.a() - q2.a(), this.b() - q2.b(), this.c() - q2.c(), this.d - q2.d());
        return qMnius;
    }

    public Quaternion times(Quaternion q2) {

        double qA = this.a() * q2.a() - this.b()* q2.b() - this.c() * q2.c() - this.d()*q2.d();
        double qB = this.a() * q2.b() + this.b() * q2.a() + this.c() * q2.d() - this.d() * q2.c();
        double qC = this.a()* q2.c() - this.b() * q2.d() + this.c() * q2.a() + this.d() * q2.b();
        double qD = this.a() * q2.d() + this.b()*q2.c() - this.c() * q2.b()  + this.d() * q2.a();

        Quaternion qTimes = new Quaternion(qA, qB, qC, qD);
        return qTimes;
    }

    public double norm() {
        Double qNorm = Math.sqrt(this.a()*this.a() + this.b()*this.b() + this.c()*this.c() + this.d() * this.d());
        return qNorm;
    }

    public Quaternion normalized() {
        Double qNorm = this.norm();
        Quaternion qNormalized = new Quaternion(this.a()/qNorm, this.b()/qNorm, this.c()/qNorm, this.d()/qNorm);
        return qNormalized;
    }

    public Quaternion inverse() {
        Double cal = this.a()*this.a() + this.b()*this.b() + this.c()*this.c() + this.d()*this.d();
        Quaternion qInverse = new Quaternion(this.a()/cal, -(this.b()/cal), -(this.c()/cal), -(this.d()/cal));
        return qInverse;
    }

    public Quaternion conjugate() {
        Quaternion qConjugate = new Quaternion(this.a(), -this.b(), -this.c(), -this.d());
        return qConjugate;
    }

    public List<Double> coefficients() {
        List<Double> coefficientList = new ArrayList<>();
        coefficientList.add(this.a());
        coefficientList.add(this.b());
        coefficientList.add(this.c());
        coefficientList.add(this.d());
        return coefficientList;
    }
}