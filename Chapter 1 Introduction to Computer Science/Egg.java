public class Egg {
    public static void main(String[] args) {
        EggTop();
        EggBottom();
        System.out.println();
        Middle();
        EggTop();
        EggBottom();
        System.out.println();
        Middle();
        EggBottom();
        EggTop();
        Middle();
        EggBottom();
    }
    
    public static void EggTop() {
        System.out.println("  _______");
        System.out.println(" /       \\");
        System.out.println("/         \\");
    }
    
    public static void Middle() {
        System.out.println("-\"-'-\"-'-\"-");
    }
    
    public static void EggBottom() {
        System.out.println("\\         /");
        System.out.println(" \\_______/");
    }
}