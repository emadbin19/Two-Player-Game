import java.io.IOException;

 public class Main 
 {
     public static void main(String[] args) throws IOException {
      
         try {
          TwoPlayerGameOfLife.readInitialGrid();
          TwoPlayerGameOfLife.gameStart();
       } catch (Exception var2) {
          System.out.println("Exception in starting the game");
          System.out.println(var2.getMessage());
       }
    }


    
 }
