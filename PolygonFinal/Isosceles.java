public class Isosceles extends Scalene
{  

    // Constructors
    public Isosceles()
    {
        super();
    }

    public Isosceles(double base,double height)
    {
        super(base,height);
    }

    // Overide abstract method    
    public String getMyType()
    {
        return "Isosceles";
    }
    public String toString()
    {
        return "Isosceles and I am also a " + super.toString();
    }
}
