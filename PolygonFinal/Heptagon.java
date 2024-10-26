public class Heptagon extends Agon{
    public Heptagon()
    {
        this(0);
    }
    public Heptagon(double length)
    {
        super(7,length);
    }
    public String getMyType()
    {
        return "Heptagon";
    }
    public String toString()
    {
        return "Heptagon and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
