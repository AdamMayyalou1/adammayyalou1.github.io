public class StarFigures {
    public static void main(String[] args) {
        draw2HL();
        drawX();
        drawDoubleSpace();
        draw2HL();
        drawX();
        draw2HL();
        drawDoubleSpace();
        draw1VL();
        draw2HL();
        drawX();
    }
    
    public static void draw2HL() {
        System.out.println("*****");
        System.out.println("*****");
    }
    
    public static void drawX() {
        System.out.println(" * * ");
        System.out.println("  *  ");
        System.out.println(" * * ");
    }
    
    public static void drawDoubleSpace() {
        System.out.println();
        System.out.println();
    }
    
    public static void draw1VL() {
        System.out.println("  *  ");
        System.out.println("  *  ");
        System.out.println("  *  ");
    }
}