public class Nonagon extends Agon
{
    public Nonagon()
    {
        this(0);
    }
    public Nonagon(double length)
    {
        super(9,length);
    }
    public String getMyType()
    {
        return "Nonagon";
    }
    public String toString()
    {
        return "Nonagon and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
