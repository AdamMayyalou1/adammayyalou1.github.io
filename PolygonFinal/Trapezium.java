public class Trapezium extends Quadrilaterals
{
    public Trapezium()
    {
        this(0,0);
    }
    public Trapezium(double base1,double height)
    {
        super(base1,base1,height);
    }
    public String getMyType()
    {
        return "Trapezium";
    }
    public String toString()
    {
        return "Trapezium and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}  
