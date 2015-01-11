package com.jraska.vsb.or1.io;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.data.Output;

import java.io.IOException;
import java.io.OutputStream;

public class ToStringOutputWriter implements IOutputWriter {
  //region IOutputWriter impl

  @Override
  public void write(Output output, OutputStream outputStream) {
    ArgumentCheck.notNull(output);
    ArgumentCheck.notNull(outputStream);

    try {
      outputStream.write("\nFINAL RESULT:\n".getBytes());
      outputStream.write(output.toString().getBytes());
      outputStream.write("\n".getBytes());
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  //endregion
}
