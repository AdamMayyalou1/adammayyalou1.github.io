// =====================================================================
/**
 * 
 * Polygon is the base class for Chap_9_ShapesPolygon_Interface_ArrayList project.
 * 
 * (1) Write the Polygon_Comparable interface that has 4 abstract methods in it. 
 *     calculateArea() - used for calculating the area.  (returns void)
 *     getMyArea() - used to get the area.  (returns a double)
 *     getMyType() - used to get the type of Shape in the subclasses(returns String)
 *                      Ex. Isosceles, Square, Hexagon, Circle, etc
 *     getMyCategory() - used to get the category of whatever shape. (returns String)
 *                        Ex. Triangles, Quadrilaterals, Agon, Rounds
 *     
 *     (2) This Interface will "extend Comparable" as one Interface "extend" 's another
 *         and does NOT "implement" another. All you have to do to properly implement
 *        the Comparable interface is to write the compareTo() method.  We will do that
 *         via a "default" compareTo() method here that will return the difference between
 *         this.getMyArea() and that.getMyArea().
 *         For information on default methods see:
 *             - https://www.geeksforgeeks.org/default-methods-java/
 *             - https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html
 *     
 */
public interface Polygon_Comparable extends Comparable<Polygon_Comparable>
{
    public abstract void calculateArea();

    public abstract double getMyArea();

    public abstract String getMyType();

    public abstract String getMyCategory();

default public int compareTo(Polygon_Comparable that) { 
        return (int)(this.getMyArea()-that.getMyArea());
    }
}    
