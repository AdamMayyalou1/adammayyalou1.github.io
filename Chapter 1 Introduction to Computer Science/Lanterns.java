public class Lanterns {
    public static void main(String[] args) {
        drawTrapezoid();
        System.out.println();
        drawTrapezoid();
        drawWeirdLine();
        draw13();
        drawTrapezoid();
        System.out.println();
        drawTrapezoid();
        draw5();
        drawWeirdLine();
        drawWeirdLine();
        draw5();
        draw5();
    }
    
    public static void drawTrapezoid() {
        draw5();
        draw9();
        draw13();
    }
    
    public static void draw5() {
        System.out.println("    *****");
    }
    
    public static void draw9() {
        System.out.println("  *********");
    }
    
    public static void draw13() {
        System.out.println("*************");
    }
    
    public static void drawWeirdLine() {
        System.out.println("* | | | | | *");
    }
}