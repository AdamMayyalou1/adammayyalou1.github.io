public class Square extends Rectangle
{
    public Square()
    {
        this(0);
    }
    public Square(double base)
    {
        super(base,base);
    }
    public String getMyType()
    {
        return "Square";
    }
    public String toString()
    {
        return "Square and I am also a " + super.toString();
    }
}
