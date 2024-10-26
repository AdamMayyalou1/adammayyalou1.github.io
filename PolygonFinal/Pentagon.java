public class Pentagon extends Agon{
    public Pentagon()
    {
        this(0);
    }
    public Pentagon(double length)
    {
        super(5,length);
    }
    public String getMyType()
    {
        return "Pentagon";
    }
    public String toString()
    {
        return "Pentagon and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
