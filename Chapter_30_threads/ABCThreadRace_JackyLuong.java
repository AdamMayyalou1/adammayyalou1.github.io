 //   ABCThreadRace_JackyLuong

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Created by Jacky Luong on 4/8/18.
 * This program will visually display the order that three threads ran (each a total of 650 times).
 */

// Note: For every ten times you run it, I guarantee it'll provide a good result with a lot of
// switching between threads at least once (sometimes it won't even run right but I've no idea
// what's wrong).
public class ABCThreadRace_JackyLuong extends Application {   
    final int timesPerThread = 650;
    private Aux auxiliaryString = new Aux();

     // main() is ONLY needed if running in BlueJ, 
     //    take out if running in a different IDE.
    public static void main(String args[])
    {
        Application.launch(args);
        System.exit(0);
    }
    
    public void start(Stage primaryStage) {
        // Set up the stage
        RectanglePane rA = new RectanglePane(Color.RED, 'A');
        RectanglePane rB = new RectanglePane(Color.YELLOW, 'B');
        RectanglePane rC = new RectanglePane(Color.GREEN, 'C');
        VBox vbox = new VBox(rA, rB, rC);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5);

        // Set up the threads to add the letters to the auxiliary string
        Thread t1 = new Thread(new Conductor('A'));
        Thread t2 = new Thread(new Conductor('B'));
        Thread t3 = new Thread(new Conductor('C'));
        t1.start();
        t2.start();
        t3.start();
        // Wait until all threads are done
        while (auxiliaryString.getText().length() < timesPerThread * 3);

        // Display stage
        Scene scene = new Scene(vbox, 1400, 100, Color.BLACK);
        primaryStage.setTitle("The Great Thread Race");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Execute the visuals
        Thread displayer = new Thread(new RectangleUpdate(rA, rB, rC));
        displayer.start();
    }

    // Runnable object that does the visuals
    public class RectangleUpdate implements Runnable {
        RectanglePane ra, rb, rc;

        // Pass in references to each colored rectangle
        public RectangleUpdate(RectanglePane a, RectanglePane b, RectanglePane c) {
            ra = a;
            rb = b;
            rc = c;
        }

        @Override
        public void run() {
            try {
                String decode = auxiliaryString.getText();
                for (int i = 0; i < decode.length(); i++) {
                    char current = decode.charAt(i);
                    if (current == 'A')
                        ra.update();
                    else if (current == 'B')
                        rb.update();
                    else
                        rc.update();
                    Thread.sleep(8);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // Hidden runnable object that adds characters to the auxiliary string
    public class Conductor implements Runnable {
        char id;

        public Conductor(char id) {
            this.id = id;
        }

        // Adds its char to the aux string, aux string can only be accessed by one thread at a time
        @Override
        public void run() {
            for (int i = 0; i < timesPerThread; i++) {
//                synchronized (auxiliaryString) {
                    auxiliaryString.add(id);
//                }
            }
        }
    }

    // Auxiliary string class that stores the order in which the threads were executed
    public class Aux {
        private String arg = "";

        // Add letter to string
        public synchronized void add(char letter) {
            arg += letter;
        }

        // Getter method
        public String getText() {
            return arg;
        }
    }

    // Colored rectangle class
    class RectanglePane extends StackPane {
        private Rectangle rectangle;
        private int count;

        // Initialization
        public RectanglePane(Color color, char id) {
            rectangle = new Rectangle(25, 25);
            rectangle.setFill(color);
            rectangle.setArcHeight(12);
            rectangle.setArcWidth(12);
            getChildren().add(rectangle);
            getChildren().add(new Label(id + ""));
        }

        // Increment count, update length of rectangle accordingly
        public void update() {
            count++;
            rectangle.setWidth(25 + count * 1300 / timesPerThread);
            if (count == timesPerThread)
                rectangle.setFill(Color.GRAY);
        }
    }
}


