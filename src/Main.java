import java.io.*;


class Diff {
  int x, y;
  int dx, dy;
  int flag;
  int count;

  Diff(int x, int y, int dx, int dy, int flag) {
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
    this.flag = flag;
    this.count = 0;
  }
}

public class Main {

  private static int board[][] = {{0, 0, 0},{0, 0, 0}, {0, 0, 0}};

  private static void drawBoard(int board[][]) {
    System.out.println("  0 1 2");
    for (int i = 0; i < 3; i++) {

      System.out.print(i + " ");

      for (int j = 0; j < 3; j++) {
        switch (board[i][j]) {
          case 0:
            System.out.print("- ");
            break;
          case 1:
            System.out.print("◯");
            break;
          case 2:
            System.out.print("☓");
            break;
        }
      }
      System.out.println();
    }

  }

  private  static void whichTurn(int turnFlag) {
    switch (turnFlag) {
      case 1:
        System.out.print("First:");
        break;
      case 2:
        System.out.print("Second:");
        break;
    }
  }

  private static boolean checkMatch(int column, int line, int turnFlag) {
    int dr[][] = {{-1, -1}, {0, -1}, {-1, 0}, {-1, 1}};
    int dl[][] = {{1, 1}, {0, 1}, {1, 0}, {1, -1}};

    for (int n = 0; n < 4; n++) {
      Diff right = new Diff(line + dr[n][0], column + dr[n][1], dr[n][0], dr[n][1], turnFlag);
      Diff left = new Diff(line + dl[n][0], column + dl[n][1], dl[n][0], dl[n][1], turnFlag);

      int count = countCell(right) + countCell(left);

      if (count == 2) {
        switch (turnFlag) {
          case 1:
            System.out.println("\nFirst Win!");
            return true;
          case 2:
            System.out.println("\nSecond Win!");
            return true;
        }
      }
    }
    return false;
  }

  private static int countCell(Diff diff) {
    if (diff.x >= 0 && diff.x <= 2 && diff.y >= 0 && diff.y <= 2 && board[diff.x][diff.y] == diff.flag) {
      diff.count++;
      diff.x += diff.dx;
      diff.y += diff.dy;
      return countCell(diff);
    } else {
      return diff.count;
    }
  }

  public static void main(String[] args) {

    String input[] = new String[2];
    drawBoard(board);
    int count = 0;

    // 先攻：１，後攻：２
    int turnFlag = 1;

    while (true) {

      whichTurn(turnFlag);

      try {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        input = in.readLine().split(" ");

      } catch (IOException e) {
        System.out.println(e);
      }

      if (Integer.parseInt(input[0]) > 3 || Integer.parseInt(input[1]) > 3) {
        System.out.println("\nPlease input number from 0 to 3. \n");
        continue;
      }

      board[Integer.parseInt(input[0])][Integer.parseInt(input[1])] = turnFlag;

      boolean check = checkMatch(Integer.parseInt(input[0]), Integer.parseInt(input[1]), turnFlag);

      System.out.println();

      drawBoard(board);
      turnFlag = turnFlag % 2 + 1;

      if (check) {
        break;
      }
    }
  }
}
