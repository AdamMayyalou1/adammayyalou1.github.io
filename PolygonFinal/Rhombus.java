public class Rhombus extends Parallelogram
{
    public Rhombus()
    {
        this(0,0);
    }
    public Rhombus(double base,double height)
    {
        super(base,height);
    }
    public String getMyType()
    {
        return "Rhombus";
    }
    public void calculateArea()
    {
        super.setMyArea(getMyBase1()*getMyHeight()*0.5);
    }
    public String toString()
    {
        return "Rhombus and I am also a " + super.toString();
    }
}
