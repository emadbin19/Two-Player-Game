// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TwoPlayerGameOfLife {
   public static int rows;
   public static int columns;
   public static int generation = 0;
   public static int Player1 = 0;
   public static int Player2 = 0;
   static String[][] grid;
   static String[][] temp;
   static String[][] toCheck;

   public TwoPlayerGameOfLife() {
   }


   public static void gameStart() {
      copyGridTocheck();
      runGame();

      while(comparegrids()) {
         copyGrid();
         runGame();
      }

      if (Player1 < Player2) {
         System.out.println("Congratulations, Player 2 Wins! with " + Player2 + " live cells");
      } else if (Player1 == Player2) {
         System.out.println("The Game Is Tied! The number of live cells is identical for both participants :" + Player1);
      } else if (Player1 > Player2) {
         System.out.println("Congratulations, Player 1 Wins! with " + Player1 + " live cells");
      }

      System.out.println("Game Over!!");
   }

   public static void copyGridTocheck() {
      toCheck = new String[grid.length][grid.length];

      for(int row = 0; row < grid.length; row++) {
         for(int col = 0; col < grid.length; col++) {
            toCheck[row][col] = grid[row][col];
         }
      }

   }

   public static void copyGrid() {
      for(int row = 0; row < grid.length; row++) {
         for(int col = 0; col < grid.length; col++) {
            grid[row][col] = toCheck[row][col];
         }
      }


   }

   public static boolean comparegrids() {
      if (Player1 != 0 && Player2 != 0) {
         for(int row = 0; row < grid.length; row++) {
            for(int col = 0; col < grid.length; col++) {
               if (!grid[row][col].equals(toCheck[row][col])) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public static void readInitialGrid() throws IOException {
      BufferedReader br = new BufferedReader(new FileReader("Generation0.txt"));

      try {
         String line = br.readLine();
         String[] rc = line.split(" ");
         rows = Integer.parseInt(rc[0]);
         columns = Integer.parseInt(rc[1]);
         grid = new String[rows][columns];
         temp = new String[rows][columns];
         int r = 0;

         for(line = br.readLine(); line != null; ++r) {
            String[] str_array = new String[line.length()];

            int i;
            for(i = 0; i < line.length(); ++i) {
               String ch = line.substring(i, i + 1);
               str_array[i] = ch;
            }

            for(i = 0; i < str_array.length; ++i) {
               grid[r][i] = str_array[i];
               if (grid[r][i].equals("1")) {
                  ++Player1;
               } else if (grid[r][i].equals("2")) {
                  ++Player2;
               }
            }

            line = br.readLine();
         }
      } catch (Exception var10) {
         System.out.println("Exception in File reading");
         throw var10;
      } finally {
         br.close();
      }

   }

   public static void printGrid(String[][] toprint) {
      int p1 = 0;
      int p2 = 0;

      int i;
      int j;
      for(i = 0; i < grid.length; ++i) {
         for(j = 0; j < grid.length; ++j) {
            if (grid[i][j].equals("1")) {
               ++p1;
            } else if (grid[i][j].equals("2")) {
               ++p2;
            }
         }
      }

      Player1 = p1;
      Player2 = p2;
      System.out.println("Generation #" + generation);
      System.out.println("P1 Cells - " + p1);
      System.out.println("P2 Cells - " + p2);

      for(i = 0; i < rows; ++i) {
         for(j = 0; j < columns; ++j) {
            String character = toprint[i][j];
            System.out.print(character);
         }

         System.out.println();
      }

   }

   public static void playGame(String[] checker, String key, int i, int j) {
      int zero = 0;
      int one = 0;
      int two = 0;

      for(int k = 0; k < checker.length; ++k) {
         if (checker[k].equals(".")) {
            ++zero;
         } else if (checker[k].equals("1")) {
            ++one;
         } else {
            ++two;
         }
      }

      if (!grid[i][j].equals(".") && one + two < 2) {
         toCheck[i][j] = ".";
      } else if (!grid[i][j].equals(".") && one + two > 3) {
         toCheck[i][j] = ".";
      } else if (!grid[i][j].equals(".") && (one + two == 3 || one + two == 2)) {
         toCheck[i][j] = grid[i][j];
      } else if (grid[i][j].equals(".") && one + two == 3) {
         if (one > two) {
            toCheck[i][j] = "1";
         } else if (one < two) {
            toCheck[i][j] = "2";
         } else {
            toCheck[i][j] = "1";
         }
      }

   }

   public static void runGame() {
      printGrid(grid);

      for(int i = 0; i < grid.length; ++i) {
         for(int j = 0; j < grid.length; ++j) {
            if (i == 0 && j == 0) {
               check_gridA(i, j, grid[i][j]);
            } else if (i == 0 && j != grid.length - 1) {
               check_gridT(i, j, grid[i][j]);
            } else if (j == 0 && i != grid.length - 1) {
               check_gridL(i, j, grid[i][j]);
            } else if (i == grid.length - 1 && j == grid.length - 1) {
               check_gridZ(i, j, grid[i][j]);
            } else if (i == 0 && j == grid.length - 1) {
               check_gridTR(i, j, grid[i][j]);
            } else if (j == 0 && i == grid.length - 1) {
               check_gridBL(i, j, grid[i][j]);
            } else if ((i == 0 || j != grid.length - 1) && (j != grid.length - 1 || i == grid.length - 1)) {
               if ((j == 0 || i != grid.length - 1) && (i != grid.length - 1 || j == grid.length - 1)) {
                  check_grid(i, j, grid[i][j]);
               } else {
                  check_gridB(i, j, grid[i][j]);
               }
            } else {
               check_gridR(i, j, grid[i][j]);
            }
         }
      }

      ++generation;
   }

   public static void check_grid(int i, int j, String val) {
      String[] array = new String[]{grid[i - 1][j - 1], grid[i - 1][j], grid[i - 1][j + 1], grid[i][j - 1], grid[i][j + 1], grid[i + 1][j - 1], grid[i + 1][j], grid[i + 1][j + 1]};
      playGame(array, val, i, j);
   }

   public static void check_gridT(int i, int j, String val) {
      String[] array = new String[]{grid[i][j - 1], grid[i][j + 1], grid[i + 1][j - 1], grid[i + 1][j], grid[i + 1][j + 1]};
      playGame(array, val, i, j);
   }

   public static void check_gridL(int i, int j, String val) {
      String[] array = new String[]{grid[i - 1][j], grid[i - 1][j + 1], grid[i][j + 1], grid[i + 1][j], grid[i + 1][j + 1]};
      playGame(array, val, i, j);
   }

   public static void check_gridA(int i, int j, String val) {
      String[] array = new String[]{grid[i][j + 1], grid[i + 1][j], grid[i + 1][j + 1]};
      playGame(array, val, i, j);
   }

   public static void check_gridZ(int i, int j, String val) {
      String[] array = new String[]{grid[i][j - 1], grid[i - 1][j], grid[i - 1][j - 1]};
      playGame(array, val, i, j);
   }

   public static void check_gridTR(int i, int j, String val) {
      String[] array = new String[]{grid[i][j - 1], grid[i + 1][j - 1], grid[i + 1][j]};
      playGame(array, val, i, j);
   }

   public static void check_gridBL(int i, int j, String val) {
      String[] array = new String[]{grid[i][j + 1], grid[i - 1][j], grid[i - 1][j + 1]};
      playGame(array, val, i, j);
   }

   public static void check_gridR(int i, int j, String val) {
      String[] array = new String[]{grid[i - 1][j - 1], grid[i - 1][j], grid[i][j - 1], grid[i + 1][j - 1], grid[i + 1][j]};
      playGame(array, val, i, j);
   }

   public static void check_gridB(int i, int j, String val) {
      String[] array = new String[]{grid[i][j - 1], grid[i - 1][j - 1], grid[i - 1][j], grid[i - 1][j + 1], grid[i][j + 1]};
      playGame(array, val, i, j);
   }
}
