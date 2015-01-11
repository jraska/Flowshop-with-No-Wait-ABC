package com.jraska.vsb.or1;

import com.jraska.common.StopWatch;
import com.jraska.vsb.or1.data.Input;
import com.jraska.vsb.or1.data.Output;
import com.jraska.vsb.or1.io.SimpleTextParser;
import com.jraska.vsb.or1.io.ToStringOutputWriter;
import com.jraska.vsb.or1.schedule.*;
import com.jraska.vsb.or1.schedule.abc.*;
import com.jraska.vsb.or1.schedule.validation.NoWaitFlowShopValidator;

import java.io.*;
import java.util.List;

public class Program {
  //region Main method

  public static void main(String args[]) throws Exception {
    Program program = new Program();
    program.run(args);
  }

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

    for (Input input : inputs) {
      _out.println("--------" + input.getName() + "----------");

      IScheduler scheduler = createScheduler(input);
      Output output = scheduler.schedule(input);

      stopWatch.stop();
      _out.println("- Scheduling took " + stopWatch.getElapsedMs() + " ms");
      stopWatch.restart();

      referenceSchedule(input);

      stopWatch.stop();
      _out.println("- Reference scheduling took " + stopWatch.getElapsedMs() + " ms");
      stopWatch.restart();

      writeFinalOutput(output);

      stopWatch.stop();
      _out.println("- Writing output took " + stopWatch.getElapsedMs() + " ms");
      stopWatch.restart();

      NoWaitFlowShopValidator validator = new NoWaitFlowShopValidator();
      List<String> validationResult = validator.validate(output);

      stopWatch.stop();
      _out.println("- Validation took " + stopWatch.getElapsedMs() + " ms");
      stopWatch.restart();

      if (!validationResult.isEmpty()) {
        _out.println();
        _out.println("-------------------");
        _out.println("RESULT IS NOT VALID");
        _out.println("-------------------");

        for (String error : validationResult) {
          _out.println(error);
        }
      }

      _out.println("-----------------------");
      _out.println();
    }
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

  protected void referenceSchedule(Input input) {
    SimpleScheduler referenceScheduler = new SimpleScheduler();
    Output simple = referenceScheduler.schedule(input);

    _out.println("Simple Makespan: " + simple.getMakespan());
  }

  protected IScheduler createScheduler(Input input) {
    return createSimpleABCScheduler(input);
  }

  protected IScheduler createSimpleABCScheduler(Input input) {
    MakespanCounter makespanCounter = new MakespanCounter(input.getJobs());
    RandomPositionGenerator randomPositionGenerator = new RandomPositionGenerator(input.getJobsCount());
    IOnlookerChooser onlookerChooser = new TournamentOnlookerChooser(new RouletteWheelSelection());

    Bee[] bees = new Bee[20];
    for (int i = 0; i < 20; i++) {
      ILocalSearchStrategy strategy = new SelfAdaptiveSearchStrategy(makespanCounter);
      bees[i] = createBee(strategy, makespanCounter);
    }

    ABCScheduler abcScheduler = new ABCScheduler(bees, randomPositionGenerator, onlookerChooser, getMaxMissThreshold());
    return abcScheduler;
  }

  protected int getMaxMissThreshold() {
    return 10;
  }

  protected Bee createBee(ILocalSearchStrategy strategy, IObjectiveFunction function) {
    return new Bee(strategy, function);
  }

  //endregion
}
