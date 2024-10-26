/**
 * Mr. Marques
 * Polygon_Comparable CopyOfCLIENT has an ArrayList of Polygons called PolygonShapesList that 'adds' to the end of it,
 * 'adds at an index specified', 'removes' from it at an index specified, 'sets' at an index specified, and
 * 'gets' a Polygon and finds its area.  It also finds the Polygon with the largest and smallest area.
 */

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Polygon_Comparable_CLIENT
{
    // **************************************************************************************************
    // ***************************************** init() (MAIN) ******************************************
    // **************************************************************************************************    
    public static void main()
    {
        // Declare + define ArrayList 'polygonShapesList' of type Polygon_Comparable
        ArrayList<Polygon_Comparable> polygonShapesList = new ArrayList();

        String polygonShapesListItems="";
        int mainMenuSelection;

        do { 

            polygonShapesListItems="";
            for (int i = 0; i<polygonShapesList.size(); i++)
                polygonShapesListItems += "(" + i + ") " + polygonShapesList.get(i) + "\n";

            mainMenuSelection = Integer.parseInt( JOptionPane.showInputDialog(" Enter your menu selection: \n " +
                    polygonShapesListItems + "\n\n\n" +
                    "(0) Quit. \n" +
                    "(1) Add a new Polygon at the end of the list. \n" +
                    "(2) Add a new Polygon at an index 'i' in the list. \n" +
                    "(3) Remove a Polygon at an index 'i' in the list. \n" +
                    "(4) Set a new Polygon to an index 'i' in the list. \n" +
                    "(5) Get a Polygon at an index 'i' in the list and find the Area of it. \n" +
                    "(6) Find the Polygon with the largest area. \n" +
                    "(7) Find the Polygon with the smallest area.\n"));

            switch (mainMenuSelection) {
                case 0 : JOptionPane.showMessageDialog(null,"Good Bye!");  System.exit(0); break;
                case 1 : addPolygonToEndOfList(polygonShapesList); break;
                case 2 : addPolygonAtAnIndex(polygonShapesList); break;            
                case 3 : removePolygonAtAnIndex(polygonShapesList); break;
                case 4 : setPolygonAtAnIndex(polygonShapesList); break;            
                case 5 : getAndFindAreaOfPolygonAtAnIndex(polygonShapesList); break;
                case 6 : findPolygonWithLargestArea(polygonShapesList); break;
                case 7 : findPolygonWithSmallestArea(polygonShapesList); break;
                default:
                JOptionPane.showMessageDialog(null," Thanks for playing PolygonShapesList game! \n Have a good Day!!");
                System.exit(0);
                break;       
            }

        } while (1==1);
    }

    // **************************************************************************************************
    // ******************************** addPolygon section ********************************************    
    // **************************************************************************************************    
    public static void addPolygonToEndOfList(ArrayList<Polygon_Comparable> pList)
    {
        int polygonSelection;
        boolean badSelection;
        int pListEndPosition;

        pListEndPosition = ( (pList.size() == 0) ? 0 : pList.size());

        do {
            polygonSelection = Integer.parseInt (JOptionPane.showInputDialog(" Enter your menu selection from these categories: \n " +
                    "(1) Triangle  (2) Quadrilateral  (3) Agon  (4) Rounds \n") ); //Polygon.showCategories()); 
            badSelection=false;
            switch (polygonSelection) {
                case 1 : addTriangleAtAnIndex(pList,pListEndPosition);  break;
                case 2 : addQuadrilateralAtAnIndex(pList,pListEndPosition);  break;
                case 3 : addAgonAtAnIndex(pList,pListEndPosition);  break;
                case 4 : addRoundAtAnIndex(pList,pListEndPosition);  break;                                    
                default: 
                JOptionPane.showMessageDialog(null," ERROR! \n Please choose only from the listed categories");
                badSelection = true;
                break;
            }

        } while (badSelection == true);

    }    
    // ***********************************************************************************************
    public static void addPolygonAtAnIndex(List<Polygon_Comparable> pList)
    {
        int polygonSelection;
        boolean badSelection;
        int indexSelection;

        do {
            indexSelection = Integer.parseInt(JOptionPane.showInputDialog("Enter an index number 0 - " + (pList.size()-1)));
            if ( !( 0 <= indexSelection && indexSelection <= pList.size()-1))
                JOptionPane.showMessageDialog(null,"Enter ONLY numbers 0 - " + (pList.size()-1),"Warning",JOptionPane.PLAIN_MESSAGE);  // "Enter ONLY numbers 0 - " + pList.size()-1, "Warning!!",JOptionPane.WARNING_MESSAGE);
        } while (! (0 <= indexSelection && indexSelection <= pList.size()-1) );

        do {
            polygonSelection = Integer.parseInt (JOptionPane.showInputDialog(" Enter your menu selection from these categories: \n " +
                    "(1) Triangle  (2) Quadrilateral  (3) Agon  (4) Rounds \n") ); //Polygon.showCategories()); 
            badSelection=false;
            switch (polygonSelection) {
                case 1 : addTriangleAtAnIndex(pList,indexSelection);  break;
                case 2 : addQuadrilateralAtAnIndex(pList,indexSelection);  break;
                case 3 : addAgonAtAnIndex(pList,indexSelection);  break;
                case 4 : addRoundAtAnIndex(pList,indexSelection);  break;                                    
                default: 
                JOptionPane.showMessageDialog(null," ERROR! \n Please choose only from the listed categories");
                badSelection = true;
                break;
            }

        } while (badSelection == true);          

    }
    // --------------------------------------------------------------------------------------------
    public static void addTriangleAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double b, h;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Triangle from the following list: \n"
                + " (1) Scalene  (2) Isosceles  (3) Equilateral ") );  //   + Triangles.typesAvailable()) );  
        b = Double.parseDouble( JOptionPane.showInputDialog(null, "Please type in the base of the Triangle: \n") );    
        h = Double.parseDouble( JOptionPane.showInputDialog(null, "Please type in the height of the Triangle: \n") );          

        switch (choice) {
            case 1: pList.add(index,new Scalene(b,h)); break;
            case 2: pList.add(index,new Isosceles(b,h)); break;
            case 3: pList.add(index,new Equilateral(b,h)); break;            
        }
    }
    // --------------------------------------------------------------------------------------------
    public static void addQuadrilateralAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double b1,b2,h;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Quadrilateral from the following list: \n"
                + " (1) Trapezium (2) Trapezoid (3) Parallelogram  (4) Rhombus (5) Rectangle (6) Square   ") );

        b1 = Double.parseDouble( JOptionPane.showInputDialog(null," Enter the base: \n") );        
        h = Double.parseDouble( JOptionPane.showInputDialog(null," Enter the height: \n") );        
        switch (choice) {
            case 1: pList.add(index,new Trapezium(b1,h)); break;
            case 2: b2 = Double.parseDouble(JOptionPane.showInputDialog(null," Enter 2nd base: \n") );
            pList.add(index,new Trapezoid(b1,b2,h)); break;
            case 3:  pList.add(index,new Parallelogram(b1,h)); break;
            case 4:  pList.add(index,new Rhombus(b1,h)); break;
            case 5:  pList.add(index,new Rectangle(b1,h)); break;
            case 6:  b1 = Double.parseDouble( JOptionPane.showInputDialog(null," Enter the base: \n") ); 
                    pList.add(index,new Square(b1)); break;               
        }            

    }    
    // --------------------------------------------------------------------------------------------    
    public static void addAgonAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double side;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Agon from the following list: \n"
                + " (1)Pentagon (2) Hexagon (3) Heptagon (4) Octagon (5) Nonagon (6) Decagon (7) Dodecagon \n") );
        side = Double.parseDouble( JOptionPane.showInputDialog(null, "Please enter the length of the side: \n" ) );
        switch (choice) {
            case 1: pList.add(index,new Hexagon(side)); break;
            case 2: pList.add(index,new Hexagon(side)); break;
            case 3: pList.add(index,new Heptagon(side)); break;             
            case 4: pList.add(index,new Octagon(side)); break;  
            case 5: pList.add(index,new Nonagon(side)); break;
            case 6: pList.add(index,new Decagon(side)); break;
            case 7: pList.add(index,new Dodecagon(side)); break;           
        }            

    }    
    // --------------------------------------------------------------------------------------------    
    public static void addRoundAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double radius1, radius2;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Round from the following list: \n"
                + " (1) Circle + \n (2) SemiCircle + \n (3) Ellipse + \n  ") );
        radius1 = Double.parseDouble( JOptionPane.showInputDialog(null, "Please enter the length of the radius: \n" ) );

        switch (choice) {
            case 1: pList.add(index,new Circles(radius1)); break;
            case 2: pList.add(index,new SemiCircle(radius1)); break;
            case 3: radius2 = Double.parseDouble( JOptionPane.showInputDialog(null, "Please enter the length of the radius2: \n" ) );
            pList.add(index,new Ellipse(radius1,radius2));
            break;
        }            

    }    

    // **************************************************************************************************    
    // *************************** removePolygon section ********************************************     
    // **************************************************************************************************
    public static void removePolygonAtAnIndex(ArrayList<Polygon_Comparable> pList)
    {
        int i;
        String output="";
        int indexSelection;

        for (i=0; i<pList.size(); i++)
            output += "(" + i +") " + (Polygon_Comparable)pList.get(i) +"\n";

        do {
            indexSelection = Integer.parseInt( JOptionPane.showInputDialog("Please enter a selection for Deletion from the list: \n" + output));
            if ( !( 0 <= indexSelection && indexSelection <= pList.size()-1))
                JOptionPane.showMessageDialog(null,"Enter ONLY numbers 0 - " + (pList.size()-1),"Warning",JOptionPane.PLAIN_MESSAGE);  // "Enter ONLY numbers 0 - " + pList.size()-1, "Warning!!",JOptionPane.WARNING_MESSAGE);
        } while ( !(0 <= indexSelection && indexSelection <= pList.size()-1));

        // delete selected
        pList.remove(indexSelection);

        output="";
        for (i=0; i<pList.size(); i++)
            output += "(" + i +") " + (Polygon_Comparable)pList.get(i) +"\n";
        JOptionPane.showMessageDialog(null," Remove is successful.  The new list is: \n" + output);

    }

    // **************************************************************************************************    
    // ************************************* setPolygonAtAnIndex ******************************************
    // **************************************************************************************************
    public static void setPolygonAtAnIndex(List<Polygon_Comparable> pList)
    {
        int polygonSelection;
        boolean badSelection;
        int indexSelection;

        do {
            indexSelection = Integer.parseInt(JOptionPane.showInputDialog("Enter an index number 0 - " + pList.size()));
            if ( !( 0 <= indexSelection && indexSelection <= pList.size()-1))
                JOptionPane.showMessageDialog(null, "Enter ONLY numbers 0 - " + (pList.size()-1), "Warning!!",JOptionPane.WARNING_MESSAGE);
        } while ( !( 0 <= indexSelection && indexSelection <= pList.size()-1));

        do {
            polygonSelection = Integer.parseInt (JOptionPane.showInputDialog(" Enter your menu selection from these categories: \n " +
                    "(1) Triangle  (2) Quadrilateral  (3) Agon  (4) Rounds \n") ); //Polygon.showCategories()); 
            badSelection=false;
            switch (polygonSelection) {
                case 1 : setTriangleAtAnIndex(pList,indexSelection);  break;
                case 2 : setQuadrilateralAtAnIndex(pList,indexSelection);  break;
                case 3 : setAgonAtAnIndex(pList,indexSelection);  break;
                case 4 : setRoundAtAnIndex(pList,indexSelection);  break;                                    
                default: 
                JOptionPane.showMessageDialog(null," ERROR! \n Please choose only from the listed categories");
                badSelection = true;
                break;
            }

        } while (badSelection == true);

    }
    // --------------------------------------------------------------------------------------------
    public static void setTriangleAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double b, h;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Triangle from the following list: \n"
                + " (1) Scalene  (2) Isosceles  (3) Equilateral ") );  //   + Triangles.typesAvailable()) );  
        b = Double.parseDouble( JOptionPane.showInputDialog(null, "Please type in the base of the Triangle: \n") );    
        h = Double.parseDouble( JOptionPane.showInputDialog(null, "Please type in the height of the Triangle: \n") );          

        switch (choice) {
            case 1:  pList.set(index,new Scalene(b,h));break;
            case 2:  pList.set(index,new Isosceles(b,h)); break;
            case 3:  pList.set(index,new Equilateral(b,h)); break;             
        }
    }
    // --------------------------------------------------------------------------------------------
    public static void setQuadrilateralAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double b1,b2,h;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Quadrilateral from the following list: \n"
                + " (1) Trapezium (2) Trapezoid (3) Parallelogram  (4) Rhombus (5) Rectangle (6) Square   ") );

        b1 = Double.parseDouble( JOptionPane.showInputDialog(null," Enter the base: \n") );        
        h = Double.parseDouble( JOptionPane.showInputDialog(null," Enter the height: \n") );        
        switch (choice) {
            case 1:  pList.set(index,new Trapezium(b1,h)); break;
            case 2: b2 = Double.parseDouble( JOptionPane.showInputDialog(null," Enter 2nd base: \n") );
            pList.set(index,new Trapezoid(b1,b2,h)); break;
            case 3:  pList.set(index,new Parallelogram(b1,h)); break;
            case 4:  pList.set(index,new Rhombus(b1,h)); break;
            case 5:  pList.set(index,new Rectangle(b1,h)); break;
            case 6:  b1 = Double.parseDouble( JOptionPane.showInputDialog(null," Enter the base: \n") );       
                    pList.set(index,new Square(b1)); break;              
        }            

    }    
    // --------------------------------------------------------------------------------------------    
    public static void setAgonAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double side;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Agon from the following list: \n"
                + " (1)Pentagon (2) Hexagon (3) Heptagon (4) Octagon (5) Nonagon (6) Decagon (7) Dodecagon \n") );
        side = Double.parseDouble( JOptionPane.showInputDialog(null, "Please enter the length of the side: \n" ) );
        switch (choice) {
            case 1:  pList.set(index,new Pentagon(side));break;
            case 2:  pList.set(index,new Hexagon(side));break;
            case 3:  pList.set(index,new Heptagon(side));break;             
            case 4:  pList.set(index,new Octagon(side));break;  
            case 5:  pList.set(index,new Nonagon(side));break;
            case 6:  pList.set(index,new Decagon(side));break;
            case 7:  pList.set(index,new Dodecagon(side));break;           
        }            

    }    
    // --------------------------------------------------------------------------------------------    
    public static void setRoundAtAnIndex(List<Polygon_Comparable> pList,int index)
    {
        int choice;
        double radius1, radius2,radius;

        choice = Integer.parseInt( JOptionPane.showInputDialog(null, "Please select a Round from the following list: \n"
                + " (1) Circle + \n (2) SemiCircle + \n (3) Ellipse + \n  ") );
        radius = Double.parseDouble( JOptionPane.showInputDialog(null, "Please enter the length of the radius: \n" ) );

        switch (choice) {
            case 1: pList.set(index,new Circles(radius)); break;
            case 2: pList.set(index, new SemiCircle(radius));break;
            case 3: radius1 = Double.parseDouble( JOptionPane.showInputDialog(null, "Please enter the length of the radius2: \n" ) );
                    radius2 = Double.parseDouble( JOptionPane.showInputDialog(null, "Please enter the length of the radius2: \n" ) );
            pList.set(index,new Ellipse(radius1,radius2));
            break;
        }            

    }        

    // **************************************************************************************************
    // *************************** findAreaOfPolygon section ******************************************** 
    // **************************************************************************************************
    public static void getAndFindAreaOfPolygonAtAnIndex(ArrayList pList)
    {
        int i;
        String output="";
        int areaSelection;
        Polygon_Comparable p;

        for (i=0; i<pList.size(); i++){
            output += "(" + i +") " + (Polygon_Comparable) pList.get(i) +"\n";
        }
        areaSelection = Integer.parseInt( JOptionPane.showInputDialog("Please enter a selection for Area from the list: \n" + output));

        // get Polygon and find area
        p = (Polygon_Comparable) pList.get(areaSelection);           
        JOptionPane.showMessageDialog(null, "Area of the " + p.getMyType() + " " + p.getMyCategory() + " = " + p.getMyArea());

    }    

    // **************************************************************************************************
    // ******************** find Largest/Smallest Area Of Polygon section ******************************* 
    // **************************************************************************************************
    public static void findPolygonWithLargestArea(ArrayList pList)
    {
        if(pList.isEmpty())
        {
            return;
        }
        String output ="";
        int indexLargest=0;
        Polygon_Comparable p_largest= (Polygon_Comparable)pList.get(0);
        for(int i=1;i<pList.size();i++){
            Polygon_Comparable p_another = (Polygon_Comparable)pList.get(i);
            if(p_largest.compareTo(p_another)<0)
            {
                p_largest = p_another;
                indexLargest = i;
            }
        }
        JOptionPane.showMessageDialog(null,"Largest Area of the list is"+ p_largest.getMyType()+ " "+p_largest.getMyCategory() + " = "+p_largest.getMyArea()+" at index "+indexLargest);
    }

    public static void findPolygonWithSmallestArea(ArrayList pList)
    {
        if(pList.isEmpty())
        {
            return;
        }
        String output ="";
        int indexSmallest=0;
        Polygon_Comparable p_smallest= (Polygon_Comparable)pList.get(0);
        for(int i=1;i<pList.size();i++){
            Polygon_Comparable p_another = (Polygon_Comparable)pList.get(i);
            if(p_smallest.compareTo(p_another)>0)
            {
                p_smallest = p_another;
                indexSmallest = i;
            }
        }
        JOptionPane.showMessageDialog(null,"Smallest Area of the list is"+ p_smallest.getMyType()+ " "+p_smallest.getMyCategory() + " = "+p_smallest.getMyArea()+" at index "+indexSmallest);
    }    

} // CLIENT
