package com.topcoder.ReviewTest;

import java.io.*;
import java.security.*;

public class LineUpVis {

private int N;
private int X;
private int maxHeight;
private int[] heights;
private int[] arrangements;

private String generate(String seedStr) {
  try {
    SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
    long seed = Long.parseLong(seedStr);
    r.setSeed(seed);
    N = r.nextInt(91) + 10;
    X = r.nextInt(19) + 2;
    maxHeight = r.nextInt(991) + 10;
    heights = new int[N];
    arrangements = new int[N * X];
    for (int i = 0; i < heights.length; i++) heights[i] = r.nextInt(maxHeight) + 1;
    for (int i = 0; i < arrangements.length; i++) arrangements[i] = r.nextInt(maxHeight) + 1;
    StringBuilder sb = new StringBuilder();
    sb.append("N = " + N + "\n");
    sb.append("X = " + X + "\n");
    return sb.toString();
  } catch (Exception e) {
    addFatalError("An exception occurred while generating test case.");
    e.printStackTrace();
    return "";
  }
}

public double runTest(String seed) {
  try {
    String test = generate(seed);
    if (debug) System.out.println(test);
    int[] lineup = getLineup(heights, arrangements);
    
    boolean[][] used = new boolean[X][N];
    int[] lastPosition = new int[N];
    for (int i = 0; i < N; i++) lastPosition[i] = i;
    long movement = 0;
    long sqErr = 0;

    if (lineup.length < 0 || lineup.length > arrangements.length) {
      if (debug) System.out.println("Return contained " + lineup.length + " elements, expected " + arrangements.length);
      return -1;
    }
    for (int i = 0; i < lineup.length; i++) {
      if (lineup[i] < 0 || lineup[i] >= N) {
        if (debug) System.out.println("Element " + i + " of return is invalid: " + lineup[i]);
        return -1;
      }
      used[i / N][lineup[i]] = true;
      long err = lineup[i] - arrangements[i];
      sqErr += err * err;
      movement += Math.abs((i % N) - lastPosition[lineup[i]]);
      lastPosition[lineup[i]] = i % N;
    }
    for (int i = 0; i < X; i++)
      for (int j = 0; j < N; j++) {
        if (!used[i][j]) {
          if (debug) System.out.println("Lineup " + i + " does not contain individual " + j);
          return -1;
        }
      }

    if (debug) {
      System.out.println("Movement Total = " + movement);
      System.out.println("Total Sq Error = " + sqErr);
    }
    
    return score = movement + Math.sqrt(sqErr);
  } catch (Exception e) {
    addFatalError("An exception occurred while trying to get your program's results.");
    e.printStackTrace();
    return -1;
  }
}

// ------------- visualization part ------------
  static String seed;
  public static String exec;
  public static boolean debug;
  static Process proc;
  InputStream is;
  OutputStream os;
  BufferedReader br;

public int[] getLineup(int[] heights, int[] arrangements) throws IOException {
  StringBuffer sb = new StringBuffer();
  sb.append(heights.length).append("\n");
  for (int i = 0; i < heights.length; ++i) {
    sb.append(heights[i]).append("\n");
  }
  sb.append(arrangements.length).append("\n");
  for (int i = 0; i < arrangements.length; ++i) {
    sb.append(arrangements[i]).append("\n");
  }
  os.write(sb.toString().getBytes());
  os.flush();

  long startTime = System.currentTimeMillis();

  int[] ret = new int[Integer.parseInt(br.readLine())];
  for (int i = 0; i < ret.length; i++) {
    ret[i] = Integer.parseInt(br.readLine());
  }

  runTime += System.currentTimeMillis() - startTime;
  
  return ret;
}

public static long runTime = 0;
public double score = -1.0;
public static ErrorReader output;
  
public LineUpVis(String seed) {
  try {
    if (exec != null) {
      try {
        Runtime rt = Runtime.getRuntime();
        proc = rt.exec(exec);
        os = proc.getOutputStream();
        is = proc.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        output = new ErrorReader(proc.getErrorStream());
        output.start();
        runTime = 0;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    score = runTest(seed);
    if (proc != null)
      try {
        proc.destroy();
      } catch (Exception e) {
        e.printStackTrace();
      }
  } catch (Exception e) {
    e.printStackTrace();
  }
}

void addFatalError(String message) { System.out.println(message); }
 
public static double main(String[] args) {
  seed = "1";
  for (int i = 0; i < args.length; i++) {   
    if (args[i].equals("-seed"))
      seed = args[++i];
    if (args[i].equals("-exec"))
      exec = args[++i];
    if (args[i].equals("-debug"))
      debug = true;
  }
        
  LineUpVis f = new LineUpVis(seed);
  return f.score;
}
 
}

class ErrorReader extends Thread {
  InputStream error;
  StringBuilder sb = new StringBuilder();

  public ErrorReader(InputStream is) {
    error = is;
  }

  public void run() {
    try {
      byte[] ch = new byte[50000];
      int read;
      while ((read = error.read(ch)) > 0) {
        String s = new String(ch, 0, read);
        System.out.print(s);
        sb.append(s);
        System.out.flush();
      }
    } catch (Exception e) {
    }
  }

  public String getOutput() {
    return sb.toString();
  }
}
