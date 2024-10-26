
/**
OUTPUT:
Note: Objects with the same value hash to the same number. 

abc.hashCode() = 96354
abc.hashCode() = 96354

bca.hashCode() = 97344
cab.hashCode() = 98244

d1 = 123.0   d2 = 123.0
d1.hashCode() = 1079951360
d2.hashCode() = 1079951360
i1 = 456   i2 = 789
i1.hashCode() = 456
i2.hashCode() = 789

a = new ArrayList<String>() 
a.hashCode() = 1
"Hi".hashCode() = 2337
a.add("Hi")
a.hashCode() w/Hi addded in = 2368
a.get(0) = Hi   a.get(0).hashCode()=2337

Create 5 people objects with 0-4 values for name indexes
Person0:   p.hashCode() = 9510360
Person1:   p.hashCode() = 24631158
Person2:   p.hashCode() = 16851021
Person3:   p.hashCode() = 13349753
Person4:   p.hashCode() = 1576741

o1.hashCode() = 25538551
null: (Object)  p1.hashCode() = 18932994
null: (Person)  p2.hashCode() = 14670628


 */
import java.util.*;

public class ShowHowHashingWorks
{  

    public static void main()
    {    
        System.out.println("Note: Objects with the same value hash to the same number. \n");
        System.out.println("Aa.hashCode() = " + "Aa".hashCode());  
        System.out.println("BB.hashCode() = " + "BB".hashCode());  
        System.out.println("Bb.hashCode() = " + "Bb".hashCode());            
        System.out.println("ABC.hashCode() = " + "ABC".hashCode());  
        System.out.println("abc.hashCode() = " + "abc".hashCode());         
        System.out.println("abc.hashCode() = " + "abc".hashCode()); 
        String s = new String("abc");
        System.out.println("s.hashCode() = " + s.hashCode());          

        System.out.println();
        System.out.println("bca.hashCode() = " + "bca".hashCode()); 
        System.out.println("cab.hashCode() = " + "cab".hashCode());          
        System.out.println();

        Double d1 = 123.0, d2 = 123.0 ;
        Integer i1 = 456;  Integer i2 = 789; Integer i3 = -456;
        System.out.println("d1 = " + d1 + "   d2 = " + d2);      
        System.out.println("d1.hashCode() = " + d1.hashCode());    
        System.out.println("d2.hashCode() = " + d2.hashCode());         
        System.out.println("i1 = " + i1 + "   i2 = " + i2);   
        System.out.println("i1.hashCode() = " + i1.hashCode());
        System.out.println("i2.hashCode() = " + i2.hashCode());    
        System.out.println("i3.hashCode() = " + i3.hashCode());           
        System.out.println();  

        ArrayList<String> a = new ArrayList<String>();
        System.out.println("a = new ArrayList<String>() ");
        System.out.println("a.hashCode() = " + a.hashCode());          
        System.out.println(" \"Hi\".hashCode() = " + "Hi".hashCode());
        System.out.println("a.add(\"Hi\")");
        a.add("Hi");
        System.out.println("a.hashCode() w/Hi addded in = " + a.hashCode());          
        System.out.println("a.get(0) = "+a.get(0)+ "   a.get(0).hashCode()="+a.get(0).hashCode());
        System.out.println();    

        System.out.println("Create 5 people objects with 0-4 values for name indexes");  
        for (int j=0; j<5; j++) {
            Person p = new Person("Person" + j);   //""+j);
            System.out.println(p + ":   p.hashCode() = " + p.hashCode());                 
        }

        Object o1 = new Object();
        System.out.println("o1.hashCode() = " + o1.hashCode());

        Object p1 = new Person();
        System.out.println(p1 + ": (Object)  p1.hashCode() = " + p1.hashCode());         
        Person p2 = new Person();
        System.out.println(p2 + ": (Person)  p2.hashCode() = " + p2.hashCode());           
        Person p3 = new Person();
        System.out.println(p2 + ": (Person)  p3.hashCode() = " + p3.hashCode());           

        //int x=5;
        //  Sytem.out.println(x.hashCode());

    } // main

} // ShowHowHashingWorks
