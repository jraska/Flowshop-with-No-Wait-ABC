package com.jraska.vsb.or1.schedule.summary.io;

import com.jraska.vsb.or1.schedule.summary.Summary;

import java.io.OutputStream;

public interface ISummaryWriter {
  //region Methods

  void write(Summary summary, OutputStream outputStream);

  //endregion
}
