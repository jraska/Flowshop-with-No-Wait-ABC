package com.jraska.vsb.or1;

import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.io.MultiOutputStream;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class AllInDirSchedule extends Program {
  //region Main methods

  public static void main(String[] args) throws Exception {
    PrintStream output = createOutput();

    AllInDirSchedule allInDirSchedule = new AllInDirSchedule(output);
    allInDirSchedule.run(args);

    output.close();
  }

  //endregion

  //region Constructors

  public AllInDirSchedule() {
  }

  public AllInDirSchedule(PrintStream out) {
    super(out);
  }

  //endregion

  //region Program overrides

  @Override
  protected File[] getFiles(String[] args) {
    File dir = new File(args[0]);

    if (!dir.exists()) {
      throw new IllegalArgumentException(String.format("There is no dir on this path: %s", dir.getAbsolutePath()));
    }

    if (dir.isFile()) {
      throw new IllegalArgumentException(String.format("%s is file, no dir", dir.getAbsolutePath()));
    }

    return dir.listFiles(getFileFilter());
  }

  @Override
  protected void writeFinalOutput(Output output) {
    _out.println("Found makespan: " + output.getMakespan());
  }

  //endregion

  //region Methods

  protected FileFilter getFileFilter() {
    return new FileFilter() {
      @Override
      public boolean accept(File pathname) {
        String name = pathname.getName().toLowerCase();

        if (!name.endsWith(".txt")) {
          return false;
        }

        String pureName = name.substring(0, name.length() - 4); //-4 is for txt extension

        //accept only digitally names files
        for (char c : pureName.toCharArray()) {
          if (!Character.isDigit(c)) {
            return false;
          }
        }


        return true;
      }
    };
  }

  protected static PrintStream createOutput() {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(createNewFile());
      OutputStream[] outputs = {fileOutputStream, System.out};
      List<OutputStream> outputStreams = Arrays.asList(outputs);
      MultiOutputStream multiOutputStream = new MultiOutputStream(outputStreams);

      return new PrintStream(multiOutputStream);
    }
    catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  protected static File createNewFile() {
    File file = new File("output.html");
    if (file.exists()) {
      file.delete();
    }

    try {
      file.createNewFile();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }

    return file;
  }

  //endregion
}
