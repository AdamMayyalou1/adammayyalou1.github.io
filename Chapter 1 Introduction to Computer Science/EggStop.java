public class EggStop {
    public static void MAIN(String[] args) {
        TopSign();
        BottomSign();
        BottomSign();
        PlusMinus();
        Space();
        TopSign();
        Stop();
        BottomSign();
        Space();
        TopSign();
        LongPlusMinus();
    }
    
    public static void TopSign() {
        System.out.println("  _______  ");
        System.out.println(" /       \\ ");
        System.out.println("/         \\");
    }
    
    public static void BottomSign() {
        System.out.println("\\         /");
        System.out.println(" \\_______/ ");
    }
    
    public static void PlusMinus() {
        System.out.println(" +-------+ ");
    }
    
    public static void Space() {
        System.out.println();
    }
    
    public static void Stop() {
        System.out.println("|   STOP  |");
    }
    
    public static void LongPlusMinus() {
        System.out.println("+---------+");
    }
}    
