public class Ellipse extends Rounds
{
    private double radius1,radius2;
    public Ellipse()
    {
        this(0,0);
    }
    public Ellipse(double radius1,double radius2)
    {
        this.radius1 = radius1;
        this.radius2 = radius2;
    }
    public String getMyType()
    {
        return "Ellipse";
    }
    public double getMyRadius1()
    {
        return radius1;
    }
    public double getMyRadius2()
    {
        return radius2;
    }
    public void calculateArea()
    {
        super.setMyArea(Math.PI * getMyRadius1() * getMyRadius2());
    }
    public String toString()
    {
        return "Ellipse and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
