public class Hexagon extends Agon{
    public Hexagon()
    {
        this(0);
    }
    public Hexagon(double length)
    {
        super(6,length);
    }
    public String getMyType()
    {
        return "Hexagon";
    }
    public String toString()
    {
        return "Hexagon and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
