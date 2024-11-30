package backend.academy.fractal.flame;

import com.beust.jcommander.JCommander;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Runner runner = new Runner();
        JCommander commander = JCommander.newBuilder()
            .addObject(runner)
            .build();
        commander.parse(args);

        if (runner.benchmark()) {
            runner.runBenchmark();
        } else if (runner.generate()) {
            runner.generateSingleImage();
        } else {
            commander.usage();
        }
    }
}
