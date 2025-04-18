// package rmit.edu.vn.hcmc_metro.generator;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import rmit.edu.vn.hcmc_metro.metro_line.MetroLine;
// import rmit.edu.vn.hcmc_metro.metro_line.MetroLineService;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// @Component
// @Order(3) // Ensures this runs third
// public class MetroLineGenerator implements CommandLineRunner {

//     @Autowired
//     private MetroLineService metroLineService;

//     private Map<String, ArrayList<String>> metroLinesAndStations = new HashMap<>();

//     private List<MetroLine> generateMetroLines() {
//         List<MetroLine> metroLines = new ArrayList<>();

//         metroLinesAndStations.put("Line 1",
//             new ArrayList<>() {
//                 {
//                     add("Ben Thanh Station");
//                     add("Opera House Station");
//                     add("Ba Son Station");
//                     add("Van Thanh Park Station");
//                     add("Tan Cang Station");
//                     add("Thao Dien Station");
//                     add("An Phu Station");
//                     add("Rach Chiec Station");
//                     add("Phuoc Long Station");
//                     add("Binh Thai Station");
//                     add("Thu Duc Station");
//                     add("Hi-Tech Park Station");
//                     add("National University Station");
//                     add("Suoi Tien Terminal Station");
//                 }
//             }
//         );

//         metroLines.add(new MetroLine("Line 1", metroLinesAndStations.get("Line 1"), 30));

//         for (int i = 2; i <= 11; i++) {
//             final int stationCode = i;
//             metroLinesAndStations.put("Line " + i,
//                 new ArrayList<>() {
//                     {
//                         add(String.format("Line %d Station A", stationCode));
//                         add(String.format("Line %d Station B", stationCode));
//                         add(String.format("Line %d Station C", stationCode));
//                         add(String.format("Line %d Station D", stationCode));
//                     }
//                 }
//             );
//             metroLines.add(new MetroLine("Line " + i, metroLinesAndStations.get("Line " + i), 20 + i));
//         }

//         return metroLines;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         List<MetroLine> metroLines = generateMetroLines();
//         metroLineService.addMetroLinesFirst(metroLines);
//         System.out.println("Metro lines added to the repository:");
//         metroLines.forEach(System.out::println);
//     }
// }