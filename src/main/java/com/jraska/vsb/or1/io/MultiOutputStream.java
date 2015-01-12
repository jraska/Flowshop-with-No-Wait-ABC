package com.jraska.vsb.or1.io;

import com.jraska.common.ArgumentCheck;

import java.io.IOException;
import java.io.OutputStream;

public class MultiOutputStream extends OutputStream {

  //region Fields

  private final Iterable<OutputStream> _streams;

  //endregion

  //region Constructors

  public MultiOutputStream(Iterable<OutputStream> streams) {
    ArgumentCheck.notNull(streams);

    _streams = streams;
  }

  //endregion

  //region OutputStream impl

  @Override
  public void write(int b) throws IOException {
    for (OutputStream stream : _streams) {
      stream.write(b);
    }
  }

  //endregion
}
