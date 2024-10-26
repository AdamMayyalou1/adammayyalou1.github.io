public class Parallelogram extends Trapezoid
{
    public Parallelogram()
    {
        this(0,0);
    }
    public Parallelogram(double base,double height)
    {
        super(base,base,height);
    }
    public String getMyType()
    {
        return "Parallelogram";
    }
    public void calculateArea()
    {
        super.setMyArea(super.getMyBase1()*super.getMyHeight());
    }
    public String toString()
    {
        return "Parallelogram and I am also a " + super.toString();
    }
}
