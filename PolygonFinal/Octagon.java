public class Octagon extends Agon{
    public Octagon()
    {
        this(0);
    }
    public Octagon(double length)
    {
        super(8,length);
    }
    public String getMyType()
    {
        return "Octagon";
    }
    public String toString()
    {
        return "Octagon and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
