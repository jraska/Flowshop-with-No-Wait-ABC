package com.jraska.vsb.or1;

import com.jraska.common.StopWatch;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.io.SimpleTextParser;
import com.jraska.vsb.or1.io.ToStringOutputWriter;
import com.jraska.vsb.or1.schedule.*;
import com.jraska.vsb.or1.schedule.abc.*;
import com.jraska.vsb.or1.schedule.summary.SingleRunSummary;
import com.jraska.vsb.or1.schedule.summary.Summary;
import com.jraska.vsb.or1.schedule.summary.SummaryItem;
import com.jraska.vsb.or1.schedule.summary.io.HtmlSummaryWriter;
import com.jraska.vsb.or1.schedule.summary.io.ISummaryWriter;
import com.jraska.vsb.or1.schedule.validation.NoWaitFlowShopValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Program {

  //region Main method

  public static void main(String args[]) throws Exception {
    Program program = new Program();
    program.run(args);
  }

  //endregion

  //region Constants

  public static final int BEES_COUNT = 50;
  public static final int MISS_THRESHOLD = 10;

  //endregion

  //region Fields

  protected final PrintStream _out;

  //endregion

  //region Constructors

  public Program() {
    this(System.out);
  }

  public Program(PrintStream out) {
    _out = out;
  }

  //endregion

  //region methods

  public void run(String args[]) throws Exception {
    StopWatch stopWatch = new StopWatch();

    stopWatch.start();

    Input[] inputs = readInputs(args);

    stopWatch.stop();
    _out.println("- Reading input took " + stopWatch.getElapsedMs() + " ms");
    stopWatch.restart();

    List<SummaryItem> summaryItems = new ArrayList<SummaryItem>();

    for (Input input : inputs) {
      _out.println("--------" + input.getName() + "----------");

      List<SingleRunSummary> singleRunSummaries = new ArrayList<SingleRunSummary>();

      IScheduler scheduler = createScheduler(input);
      StopWatch singleRunStopwatch = new StopWatch();
      boolean valid = true;
      for (int i = 0; i < 30; i++) {
        singleRunStopwatch.start();
        Output output = scheduler.schedule(input);

        singleRunSummaries.add(new SingleRunSummary(output.getMakespan(), singleRunStopwatch.getElapsedMs()));

        NoWaitFlowShopValidator validator = new NoWaitFlowShopValidator();
        List<String> validationResult = validator.validate(output);

        if (!validationResult.isEmpty()) {
          _out.println();
          _out.println("-------------------");
          _out.println("RESULT IS NOT VALID");
          _out.println("-------------------");

          for (String error : validationResult) {
            _out.println(error);
          }

          valid = false;
        }

        singleRunStopwatch.reset();
      }

      stopWatch.stop();
      _out.println("- Scheduling took " + stopWatch.getElapsedMs() + " ms");
      stopWatch.restart();

      Output referenceSchedule = referenceSchedule(input);

      summaryItems.add(new SummaryItem(input.getName(), referenceSchedule.getMakespan(), singleRunSummaries));

      if (valid) {
        _out.println("VALID RESULT");
      } else {
        _out.println("RESULT IS NOT VALID");
      }

      _out.println("-----------------------");

      _out.println();
    }

    Summary summary = new Summary(summaryItems);
    ISummaryWriter writer = new HtmlSummaryWriter();
    writer.write(summary, _out);
  }

  protected void writeFinalOutput(Output output) {
    ToStringOutputWriter toStringOutputWriter = new ToStringOutputWriter();
    toStringOutputWriter.write(output, System.out);
  }

  protected Input[] readInputs(String[] args) throws IOException {
    if (args.length < 1) {
      throw new IllegalArgumentException("There must be arg with file path.");
    }

    File[] files = getFiles(args);
    Input[] inputs = new Input[files.length];

    for (int i = 0; i < files.length; i++) {
      inputs[i] = parseInput(new FileInputStream(files[i]));
      inputs[i].setName(files[i].getName());
    }

    return inputs;
  }

  protected File[] getFiles(String[] args) {
    String path = args[0];

    File[] files = {new File(path)};
    return files;
  }

  protected Input parseInput(InputStream inputStream) throws IOException {
    SimpleTextParser parser = new SimpleTextParser();
    Input input = parser.parse(inputStream);
    inputStream.close();
    return input;
  }

  protected Output referenceSchedule(Input input) {
    SimpleScheduler referenceScheduler = new SimpleScheduler();
    Output simple = referenceScheduler.schedule(input);

    _out.println("Simple Makespan: " + simple.getMakespan());

    return simple;
  }

  protected IScheduler createScheduler(Input input) {
    return createSimpleABCScheduler(input);
  }

  protected IScheduler createSimpleABCScheduler(Input input) {
    MakespanCounter makespanCounter = new MakespanCounter(input.getJobs());
    RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(input.getJobsCount());
    IOnlookerChooser onlookerChooser = new TournamentOnlookerChooser(new RouletteWheelSelection());

    Bee[] bees = new Bee[BEES_COUNT];
    for (int i = 0; i < BEES_COUNT; i++) {
      ILocalSearchStrategy strategy = new SelfAdaptiveSearchStrategy(makespanCounter);
      bees[i] = createBee(strategy, makespanCounter);
    }

    ABCScheduler abcScheduler = new ABCScheduler(bees, randomPositionGenerator, onlookerChooser, getMaxMissThreshold());
    return abcScheduler;
  }

  protected int getMaxMissThreshold() {
    return MISS_THRESHOLD;
  }

  protected Bee createBee(ILocalSearchStrategy strategy, IObjectiveFunction function) {
    return new Bee(strategy, function);
  }

  //endregion
}
