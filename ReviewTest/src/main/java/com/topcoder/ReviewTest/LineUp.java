import java.io.*;

public class LineUp {
  
public int[] getLineup(int[] heights, int[] arrangements) {
  int[] ret = new int[arrangements.length];
  for (int i = 0; i < ret.length; i++) ret[i] = i % heights.length;
  return ret;
}

public static void main(String[] args) {
  try {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int[] heights = new int[Integer.parseInt(br.readLine())];
    for (int i = 0; i < heights.length; ++i) {
      heights[i] = Integer.parseInt(br.readLine());
    }
    int[] arrangements = new int[Integer.parseInt(br.readLine())];
    for (int i = 0; i < arrangements.length; ++i) {
      arrangements[i] = Integer.parseInt(br.readLine());
    }
    LineUp sol = new LineUp();
    int[] ret = sol.getLineup(heights, arrangements);
    System.out.println(ret.length);
    for (int i = 0; i < ret.length; i++) {
      System.out.println(ret[i]);
    }
    System.out.flush();
  } catch (Throwable e) {
    e.printStackTrace();
  }
}

}