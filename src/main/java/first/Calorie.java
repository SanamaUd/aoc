package first;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class Calorie {


  public class Elf {

    int elfOrder;
    int calories;

    public Elf() {
    }

    public Elf(int elfOrder, int calories) {
      this.elfOrder = elfOrder;
      this.calories = calories;
    }

    public Elf(Elf elf) {
      this.elfOrder = elf.elfOrder;
      this.calories = elf.calories;
    }

    @Override
    public String toString() {
      return "Elf{" +
          "elfOrder=" + elfOrder +
          ", calories=" + calories +
          '}';
    }
  }

  public List<Elf> parseFile() throws IOException {
    File mainDirectory = new File(".");
    File input = new File(mainDirectory.getAbsolutePath()+"/src/main/java/first/input.text");

    BufferedReader reader = new BufferedReader(new FileReader(input));
    String line = reader.readLine();
    List<Elf> caloriesPerElf = new ArrayList<>();
    Elf elfo = new Elf();
    elfo.elfOrder = 1;
    elfo.calories = 0;
    do {
      if (!Pattern.matches("[0-9]+", line)) {
        caloriesPerElf.add(new Elf(elfo));
        elfo.elfOrder++;
        elfo.calories = 0;
      } else {
        int choco = Integer.parseInt(line);
        int addition = elfo.calories + choco;
        elfo.calories = addition;
      }
      line = reader.readLine();
    }while (line!= null);

    reader.close();
    return caloriesPerElf;
  }

  public Elf getFattest(List<Elf> elves){
    elves.sort(Comparator.comparing(elf -> elf.calories));
    return elves.get(elves.size()-1) ;
  }

  public List<Elf> getTopNFattest(List<Elf> elves, int topN){
    elves.sort(Comparator.comparing(elf -> elf.calories));
    return elves.subList(elves.size() - topN, elves.size());
  }

  public static void main(String[] args) throws IOException {
    Calorie test = new Calorie();
    List<Elf> elves = test.parseFile();
    System.out.println(elves);
    Elf fattest = test.getFattest(elves);
    System.out.println(fattest.calories);
    // top 3
    elves.sort(Comparator.comparing(elf -> elf.calories));
    List<Elf> top3 = test.getTopNFattest(elves, 3);
    System.out.println(top3.stream().mapToInt(elf -> elf.calories).reduce(0, Integer::sum));
  }
}
