public class TwoRockets {
    public static void main(String[] Args) {
        drawTriangle();
        drawSquare();
        System.out.println("|United| |United|");
        System.out.println("|States| |States|");
        drawSquare();
        drawTriangle();
    }
    
    public static void drawTriangle() {
        System.out.println("   /\\       /\\");
        System.out.println("  /  \\     /  \\");
        System.out.println(" /    \\   /    \\");
    }
    
    public static void drawSquare() {
        System.out.println("+------+ +------+");
        System.out.println("|      | |      |");
        System.out.println("|      | |      |");
        System.out.println("+------+ +------+");
    }
}