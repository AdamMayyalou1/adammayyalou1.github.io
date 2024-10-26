public class SemiCircle extends Rounds
{
    public SemiCircle()
    {
        super(0);
    }
    public SemiCircle(double radius)
    {
        super(radius);
    }
    public String getMyType()
    {
        return "SemiCircle";
    }
    // ******* abstract method calculateArea() to be overriden
    public void calculateArea()
    {
        super.setMyArea(Math.PI*getMyRadius()*getMyRadius()/2);
    }
    public String toString()
    {
        return "SemiCricle and I am also a " + super.toString() + " AND my area = " + getMyArea();
    }
}
