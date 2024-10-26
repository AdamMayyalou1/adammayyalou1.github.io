public class Circles extends Rounds
{
    public Circles()
    {
        super(0);
    }
    public Circles(double radius)
    {
        super(radius);
    }
    public String getMyType()
    {
        return "Circle";
    }
    public String toString()
    {
        return "Circle and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
