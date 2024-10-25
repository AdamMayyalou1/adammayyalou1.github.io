public class FightSong {
    public static void main(String[] args) {
        Go();
        Space();
        GoWest();
        Space();
        GoWest();
        Space();
        Go();
    }
    
    public static void Go() {
        System.out.println("Go, team, go!");
        System.out.println("You can do it.");
    }
    
    public static void Space() {
        System.out.println();
    }
    
    public static void West() {
        System.out.println("You're the best,");
        System.out.println("In the West.");
    }
    
    public static void GoWest() {
        Go();
        West();
        Go();
    }
}