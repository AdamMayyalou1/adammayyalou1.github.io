import java.util.*;
public class nameComparator implements Comparator<PersonNode>
{
    public int compare(PersonNode p, PersonNode n)
    {
        if(p.getLN().compareTo(n.getLN())!=0)
        {
            return p.getLN().compareTo(n.getLN());
        }
        else if (p.getFN().compareTo(n.getFN())!=0)
        {
            return p.getFN().compareTo(n.getFN());
        }
        else
        {
            return p.getMN().compareTo(n.getMN());
        }
    }
}