package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

//    public static void main(String[] args) {
//        int[] data = new int[81];
//        data[0] = 2;
//        data[2] = 9;
//        data[23] = 4;
//        data[15] = 1;
//        data[25] = 5;
//        data[36] = 4;
//        data[37] = 1;
//        data[30] = 9;
//        data[31] = 2;
//        data[49] = 8;
//        data[33] = 6;
//        data[43] = 3;
//        data[55] =5;
//        data[64] = 3;
//        data[75] =7;
//        data[68] = 5;
//        data[80] = 2;
//
//        SukoduSolver solver = new SukoduSolver(data);
//        solver.solve();
//    }


    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 9; i++) {
            System.out.println(String.format("请输入第%s行数据:", i));
            String line = scanner.nextLine();
            for (char c : line.toCharArray()) {
                if (c == ' ') {
                    continue;
                } else if (Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9').contains(c)) {
                    integerList.add(Integer.valueOf(c+""));
                } else {
                    integerList.add(0);
                }
            }
        }
        int[] data = integerList.stream().mapToInt(Integer::intValue).toArray();
        SukoduSolver solver = new SukoduSolver(data);
        solver.solve();
    }
}
