public class Decagon extends Agon
{
    public Decagon()
    {
        this(0);
    }
    public Decagon(double length)
    {
        super(10,length);
    }
    public String getMyType()
    {
        return "Decagon";
    }
    public String toString()
    {
        return "Decagon and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
