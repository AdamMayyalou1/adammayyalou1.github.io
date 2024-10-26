public class Trapezoid extends Quadrilaterals
{
    public Trapezoid()
    {
        this(0,0,0);
    }
    public Trapezoid(double base1,double base2,double height)
    {
        super(base1,base2,height);
    }
    public String getMyType()
    {
        return "Trapezoid";
    }
    public String toString()
    {
        return "Trapezoid and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
