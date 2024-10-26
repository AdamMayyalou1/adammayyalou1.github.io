public class Rectangle extends Parallelogram
{
    public Rectangle()
    {
        this(0,0);
    }
    public Rectangle(double base,double height)
    {
        super(base,height);
    }
    public String getMyType()
    {
        return "Rectangle";
    }
    public String toString()
    {
        return "Rectangle and I am also a " + super.toString();
    }
}
