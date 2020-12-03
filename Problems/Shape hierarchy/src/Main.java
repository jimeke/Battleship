abstract class Shape {

    abstract double getPerimeter();

    abstract double getArea();
}
class Triangle extends Shape {
    protected double a;
    protected double b;
    protected double c;

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getArea() {
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double getPerimeter() {
        return a + b + c;
    }

}
class Rectangle extends Shape {
    protected double a;
    protected double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double getArea() {
        return a * b;
    }

    @Override
    public double getPerimeter() {
        return (a + b) * 2;
    }
}
class Circle extends Shape {
    protected double r;

    public Circle(double r) {
        this.r = r;
    }

    @Override
    public double getArea() {
        return Math.PI * (r * r);
    }

    @Override
    public double getPerimeter() {
        return r * 2 * Math.PI;
    }
}