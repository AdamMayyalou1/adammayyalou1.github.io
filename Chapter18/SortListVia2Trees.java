/*
 *  Write a program, the CLIENT, that creates 2 sorted trees via the ArrayList given below of Person Nodes. 
 *  A Person Node has a name (last, first, middle), and an idNum, all of type String.  Have the first tree 
 *  “nameTreeSort” sort the LinkedList by name (last, first, middle).  Have the second tree “idNumTreeSort” 
 *  sort the LinkedList by idNum.  You MUST utilize the Comparable interface but since we are going to be 
 *  comparing 2 different types of values we have to write out 2 Comparators where upon each will have 
 *  their unique compare( )’s. 
 * 
 * OUTPUT:
 * 
List: [Name: Marques, Paul, The-Great   ID: 123-45-678, Name: Kent, Clark, Kriptonite   ID: 932-00-918, Name: Luthor, Lex, King-of-Evil   ID: 632-95-174, Name: Lane, Lois, Lipstick   ID: 390-29-4945, Name: Bunny, Bugs, Hoppy   ID: 383-92-484, Name: Duck, Daffy, Dippy   ID: 395-10-573, Name: Flintstone, Fred, RockHead   ID: 592-96-285, Name: Ruble, Barney, ShortStone   ID: 116-30-590, Name: Flintstone, Wilma, Mary   ID: 883-95-499, Name: Ruble, Betty, Boop   ID: 501-57-295, Name: Man, Pac, Hungry   ID: 492-95-100, Name: Invader, Space, 1980sVideoGame   ID: 385-10-395, Name: Bert, Q, -   ID: 385-19-305]
ID tree: 
Name: Ruble, Barney, ShortStone   ID: 116-30-590
Name: Marques, Paul, The-Great   ID: 123-45-678
Name: Bunny, Bugs, Hoppy   ID: 383-92-484
Name: Invader, Space, 1980sVideoGame   ID: 385-10-395
Name: Bert, Q, -   ID: 385-19-305
Name: Lane, Lois, Lipstick   ID: 390-29-4945
Name: Duck, Daffy, Dippy   ID: 395-10-573
Name: Man, Pac, Hungry   ID: 492-95-100
Name: Ruble, Betty, Boop   ID: 501-57-295
Name: Flintstone, Fred, RockHead   ID: 592-96-285
Name: Luthor, Lex, King-of-Evil   ID: 632-95-174
Name: Flintstone, Wilma, Mary   ID: 883-95-499
Name: Kent, Clark, Kriptonite   ID: 932-00-918


List: [Name: Marques, Paul, The-Great   ID: 123-45-678, Name: Kent, Clark, Kriptonite   ID: 932-00-918, Name: Luthor, Lex, King-of-Evil   ID: 632-95-174, Name: Lane, Lois, Lipstick   ID: 390-29-4945, Name: Bunny, Bugs, Hoppy   ID: 383-92-484, Name: Duck, Daffy, Dippy   ID: 395-10-573, Name: Flintstone, Fred, RockHead   ID: 592-96-285, Name: Ruble, Barney, ShortStone   ID: 116-30-590, Name: Flintstone, Wilma, Mary   ID: 883-95-499, Name: Ruble, Betty, Boop   ID: 501-57-295, Name: Man, Pac, Hungry   ID: 492-95-100, Name: Invader, Space, 1980sVideoGame   ID: 385-10-395, Name: Bert, Q, -   ID: 385-19-305]
Name tree: 
Name: Bert, Q, -   ID: 385-19-305
Name: Bunny, Bugs, Hoppy   ID: 383-92-484
Name: Duck, Daffy, Dippy   ID: 395-10-573
Name: Flintstone, Fred, RockHead   ID: 592-96-285
Name: Flintstone, Wilma, Mary   ID: 883-95-499
Name: Invader, Space, 1980sVideoGame   ID: 385-10-395
Name: Kent, Clark, Kriptonite   ID: 932-00-918
Name: Lane, Lois, Lipstick   ID: 390-29-4945
Name: Luthor, Lex, King-of-Evil   ID: 632-95-174
Name: Man, Pac, Hungry   ID: 492-95-100
Name: Marques, Paul, The-Great   ID: 123-45-678
Name: Ruble, Barney, ShortStone   ID: 116-30-590
Name: Ruble, Betty, Boop   ID: 501-57-295
 */

import java.util.*;

public class SortListVia2Trees
{
    public static void main()
    {
        ArrayList<PersonNode> list=  new ArrayList<PersonNode>( );

        list.add(new PersonNode("Marques","Paul","The-Great","123-45-678"));
        list.add(new PersonNode("Kent","Clark","Kriptonite","932-00-918"));
        list.add(new PersonNode("Luthor","Lex","King-of-Evil","632-95-174"));
        list.add(new PersonNode("Lane","Lois","Lipstick","390-29-4945"));
        list.add(new PersonNode("Bunny","Bugs","Hoppy","383-92-484"));
        list.add(new PersonNode("Duck","Daffy","Dippy","395-10-573"));
        list.add(new PersonNode("Flintstone","Fred","RockHead","592-96-285"));
        list.add(new PersonNode("Ruble","Barney","ShortStone","116-30-590"));
        list.add(new PersonNode("Flintstone","Wilma","Mary","883-95-499"));
        list.add(new PersonNode("Ruble","Betty","Boop","501-57-295"));
        list.add(new PersonNode("Man","Pac","Hungry","492-95-100"));
        list.add(new PersonNode("Invader","Space","1980sVideoGame","385-10-395"));
        list.add(new PersonNode("Bert","Q","-","385-19-305"));

        TreeNodePerson idTree = buildTree(list,new idComparator());
        TreeNodePerson nameTree = buildTree(list,new nameComparator());  

        printTree("ID",idTree,list);
        printTree("Name",nameTree,list);

    } // main

    public static TreeNodePerson buildTree(ArrayList<PersonNode> list,Comparator c)
    {
        TreeNodePerson root = new TreeNodePerson(list.get(0),null,null);   

        for (int i=1;i<list.size(); i++) {
            root = placePersonNodeInTree(root,list.get(i),c);   
        }

        return root;
    }  // buildTree

    public static TreeNodePerson placePersonNodeInTree(TreeNodePerson root, PersonNode n, Comparator<String> comparator) {
        if (root == null) {
            root = n;
        } else if (comparator.compare(n.getPerson(), root.getPerson().toString()) < 0) {
            root.setLeft(placePersonNodeInTree(root.getLeft(), n, comparator));
        } else {
            root.setRight(placePersonNodeInTree(root.getRight(), n, comparator));
        }
        return root;
    }

    public static TreeNodePerson placeIdNodeInTree(TreeNodePerson root, TreeNodePerson n, Comparator<String> comparator) {
        if (root == null) {
            root = n;
        } else if (comparator.compare(n.getPerson().getID(), root.getPerson().getID()) < 0) {
            root.setLeft(placeIdNodeInTree(root.getLeft(), n, comparator));
        } else {
            root.setRight(placeIdNodeInTree(root.getRight(), n, comparator));
        }
        return root;
    }
    
    public static void printTree(String s,TreeNodePerson t,ArrayList<PersonNode> list)
    {
        System.out.println("List: " + list);
        System.out.println(s + " tree: ");
        printTreeSideways(t,1);        
        System.out.println("\n\n");

    }  // printTree

    private static void printTreeSideways(TreeNodePerson root,int level) 
    {
        if (root != null) {
            printTreeSideways(root.getRight(), level + 1);
            for (int i = 0; i < level; i++) {
                System.out.print(" ");
            }
            System.out.println(root.getPerson());
            printTreeSideways(root.getLeft(), level + 1);
        }
    } // printTreeSideways   

} // SortListVia2Trees
