package com.jraska.vsb.or1.schedule.summary.io;

import com.jraska.common.ArgumentCheck;
import com.jraska.vsb.or1.exception.ABCRuntimeException;
import com.jraska.vsb.or1.schedule.summary.Summary;
import com.jraska.vsb.or1.schedule.summary.SummaryItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Stack;

public class HtmlSummaryWriter implements ISummaryWriter {

  //region Fields

  private BufferedWriter _bufferedWriter;
  private final Stack<String> _tags = new Stack<String>();
  private final NumberFormat _numberFormat = new DecimalFormat("#.###");

  //endregion

  //region ISummaryWriter impl

  @Override
  public void write(Summary summary, OutputStream outputStream) {
    ArgumentCheck.notNull(summary);
    ArgumentCheck.notNull(outputStream);

    try {
      writeInternal(summary, outputStream);
    }
    catch (IOException e) {
      throw new ABCRuntimeException(e);
    }
  }


  //endregion

  //region

  protected synchronized void writeInternal(Summary summary, OutputStream outputStream) throws IOException {
    _bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));

    writeSummary(summary);

    _bufferedWriter.flush();
    _bufferedWriter = null;
    _tags.clear();
  }

  protected void writeSummary(Summary summary) throws IOException {
    beginTag("html");

    writeHeadTitle();

    beginTag("body");

    writeSummaryBody(summary);

    endTag(); //body
    endTag(); //html
  }

  private void writeSummaryBody(Summary summary) throws IOException {
    for (SummaryItem item : summary.getItems()) {
      beginTag("h2");
      write("File: " + item.getName());
      endTag();

      beginTag("p");

      writeItemSection(item);

      endTag();
    }
  }

  private void writeItemSection(SummaryItem item) throws IOException {
    writeValuesTable(item);
    writeStatisticValues(item);
  }

  private void writeStatisticValues(SummaryItem item) throws IOException {
    beginTag("table");

    beginTag("tr");

    beginTag("td");
    write("Best result");
    endTag();

    beginTag("td");
    write(item.getBestMakespan());
    endTag();

    endTag(); //tr

    beginTag("tr");

    beginTag("td");
    write("Worst result");
    endTag();

    beginTag("td");
    write(item.getWorstMakespan());
    endTag();

    endTag(); //tr

    beginTag("tr");

    beginTag("td");
    write("Standard deviation");
    endTag();

    beginTag("td");
    write(item.getStandardDeviation());
    endTag();

    endTag(); //tr

    beginTag("tr");

    beginTag("td");
    write("Average time");
    endTag();

    beginTag("td");
    write(item.getAverageTime() / 1000);
    write(" s");
    endTag();

    endTag(); //tr


    endTag(); //table
  }

  private void writeValuesTable(SummaryItem item) throws IOException {
    beginTag("table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"");

    int step = 15;
    int overallIndex = 0;

    for (int stepped = 0; stepped < item.getRunsCount(); stepped += step) {
      beginTag("tr");

      beginTag("td");
      write("Sequence no.");
      endTag();

      for (int i = overallIndex; i < overallIndex + step; i++) {
        beginTag("td");

        write(i + 1);
//        write(".");

        endTag();
      }

      endTag(); //tr

      beginTag("tr");

      beginTag("td");
      write("Makespan");
      endTag();

      for (int i = overallIndex; i < overallIndex + step; i++) {
        beginTag("td");

        write(item.getSingleRuns().get(i).getResult());

        endTag();
      }

      endTag(); //tr

      overallIndex += step;
    }
    endTag(); //table
  }

  private void writeHeadTitle() throws IOException {
    beginTag("head");
    beginTag("title");
    write("Results");
    endTag();
    endTag();
  }

  private void beginTag(String tag) throws IOException {
    _bufferedWriter.append("<").append(tag).append(">");
    _tags.push(tag);
  }

  private void endTag() throws IOException {
    _bufferedWriter.append("</").append(_tags.pop()).append(">");
  }

  private void write(String text) {
    try {
      _bufferedWriter.append(text);
    }
    catch (IOException e) {
      throw new ABCRuntimeException(e);
    }
  }

  private void write(int value) {
    write(String.valueOf(value));
  }

  private void write(double value) {
    write(_numberFormat.format(value));
  }

  //endregion
}
