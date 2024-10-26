public class PersonNode 
{                    
private String lastName;
private String firstName;
private String middleName;
private String idNum;
    
    public PersonNode()
    {
       this("","","","");
    }
    public PersonNode(String l, String f, String m, String i)
    {
        this.lastName=l;
        this.firstName=f;
        this.middleName=m;
        this.idNum=i;
    }
    public String getFN()
    {
        return firstName;
    }
    public String getLN()
    {
        return lastName;
    }
    public String getMN()
    {
        return middleName;
    }
    public String getID()
    {
        return idNum;
    }
    public String toString()
    {
        return "Name: " + lastName + ", " + firstName + ", " + middleName + "   ID: " + idNum;        
    }

}  // PersonNode
