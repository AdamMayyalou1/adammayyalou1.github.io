public class Dodecagon extends Agon
{
    public Dodecagon()
    {
        this(0);
    }
    public Dodecagon(double length)
    {
        super(12,length);
    }
    public String getMyType()
    {
        return "Dodecagon";
    }
    public String toString()
    {
        return "Dodecagon and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
