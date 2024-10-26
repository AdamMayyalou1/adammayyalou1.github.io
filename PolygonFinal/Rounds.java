/**
 * Mr. Marques
 *  Rounds is "abstract" because you can NOT have 'just' a Rounds. It has to
 *  be of a certain type. This is why there are no abstract methods in this abstract
 *  class.
 * 
 */

/* 
(1) Write the abstract 'Rounds' class and have it implement 'Polygon_Comparable'
(2) It has 2 instance variables all of type double: myRadius and myArea.
(3) It has 2 constructors: (a) the default and (b) that takes in 1 double for the radius.
(4) Use set-methods in the constructors to set the variables values.
(5) Write/implement getMyCategory() returns "Rounds"
(6)  NOTE: Write/implement getMyType() in EACH of the subclasses and NOT here!
 */

// Class heading   
public abstract class Rounds implements Polygon_Comparable
{
    private double myRadius,myArea;
    public Rounds()
    {
        this(0);
    }

    public Rounds(double radius)
    {
        setMyRadius(radius);
    }
    // ************ abstract getMyCategory() and getMyType() methods overidem *************
    // >>>>>>>>>>>>>> YOUR CODE HERE <<<<<<<<<<<<<<<<<<<<
    public String getMyCategory()
    {
        return "Rounds";
    }
    // ********************* Assessor and Mutator methods *************************
    public void setMyRadius(double r)
    {
        myRadius = r;
    }

    public void setMyArea(double a)
    {
        myArea = a;    
    }

    public double getMyRadius()
    {
        return myRadius;
    }

    public double getMyArea()
    {
        calculateArea();  // Always calculateArea() first.
        return myArea;
    }    

    // ******* abstract method calculateArea() to be overriden
    public void calculateArea(){
         myArea = Math.PI * getMyRadius() * getMyRadius();
    }
   
    // ************************* compareTo() *************************************
    public int compareTo(Polygon_Comparable other)
    {
        return (int)(this.getMyArea()-other.getMyArea());
    }

    // ************************** toString() ******************************
    public String toString() 
    { 
        return "Round and I am also a Polygon_Comparable";
    }    
    
}

// ====================================================================
