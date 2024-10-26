import java.util.*;
public class TreeNodePerson
{
    private TreeNodePerson left, right;
    private PersonNode person;
    
    public TreeNodePerson(PersonNode n){
        this.person = n;
    }
    public TreeNodePerson(PersonNode n, TreeNodePerson left,TreeNodePerson right){
        person = n;
        this.left = left;
        this.right = right;
    }
    public TreeNodePerson(){
         // Empty   
    }
    public TreeNodePerson getLeft(){
        return left;
    }
    public TreeNodePerson getRight(){
        return right;
    }
    public PersonNode getPerson(){
        return person; 
    }
    public void setperson(PersonNode n){
        person = n;
    }
    public void setLeft(TreeNodePerson n){
        left = n;
    }
    public void setRight(TreeNodePerson n){
        right = n;
    }

} // TreeNodePerson
