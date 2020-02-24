package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SukoduSolver {

    private int[] data;
    private int[] fixedIndexes;
    private int currentIndex;
    private List<int[]> groups;

    private int[] row0 = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
    private int[] row1 = new int[]{9, 10, 11, 12, 13, 14, 15, 16, 17};
    private int[] row2 = new int[]{18, 19, 20, 21, 22, 23, 24, 25, 26};
    private int[] row3 = new int[]{27, 28, 29, 30, 31, 32, 33, 34, 35};
    private int[] row4 = new int[]{36, 37, 38, 39, 40, 41, 42, 43, 44};
    private int[] row5 = new int[]{45, 46, 47, 48, 49, 50, 51, 52, 53};
    private int[] row6 = new int[]{54, 55, 56, 57, 58, 59, 60, 61, 62};
    private int[] row7 = new int[]{63, 64, 65, 66, 67, 68, 69, 70, 71};
    private int[] row8 = new int[]{72, 73, 74, 75, 76, 77, 78, 79, 80};

    private int[] column0 = new int[]{0, 9, 18, 27, 36, 45, 54, 63, 72};
    private int[] column1 = new int[]{1, 10, 19, 28, 37, 46, 55, 64, 73};
    private int[] column2 = new int[]{2, 11, 20, 29, 38, 47, 56, 65, 74};
    private int[] column3 = new int[]{3, 12, 21, 30, 39, 48, 57, 66, 75};
    private int[] column4 = new int[]{4, 13, 22, 31, 40, 49, 58, 67, 76};
    private int[] column5 = new int[]{5, 14, 23, 32, 41, 50, 59, 68, 77};
    private int[] column6 = new int[]{6, 15, 24, 33, 42, 51, 60, 69, 78};
    private int[] column7 = new int[]{7, 16, 25, 34, 43, 52, 61, 70, 79};
    private int[] column8 = new int[]{8, 17, 26, 35, 44, 53, 62, 71, 80};

    private int[] bigCell0 = new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20};
    private int[] bigCell1 = new int[]{3, 4, 5, 12, 13, 14, 21, 22, 23};
    private int[] bigCell2 = new int[]{6, 7, 8, 15, 16, 17, 24, 25, 26};
    private int[] bigCell3 = new int[]{27, 28, 29, 36, 37, 38, 45, 46, 47};
    private int[] bigCell4 = new int[]{30, 31, 32, 39, 40, 41, 48, 49, 50};
    private int[] bigCell5 = new int[]{33, 34, 35, 42, 43, 44, 51, 52, 53};
    private int[] bigCell6 = new int[]{54, 55, 56, 63, 64, 65, 72, 73, 74};
    private int[] bigCell7 = new int[]{57, 58, 59, 66, 67, 68, 75, 76, 77};
    private int[] bigCell8 = new int[]{60, 61, 62, 69, 70, 71, 78, 79, 80};


    public SukoduSolver(int[] data) {
        this.currentIndex = -1;
        this.data = data;
        List<Integer> fixedList = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            if (data[i] != 0) {
                fixedList.add(i);
            }
        }
        fixedIndexes = fixedList.stream().mapToInt(Integer::valueOf).toArray();
        groups = Arrays.asList(row0, row1, row2, row3, row4, row5, row6, row7, row8,
                column0, column1, column2, column3, column4, column5, column6, column7, column8,
                bigCell0, bigCell1, bigCell2, bigCell3, bigCell4, bigCell5, bigCell6, bigCell7, bigCell8);
    }

    public void solve() {
        prettyPrint();
        forward();
    }

    private void forward() {
        if (currentIndex == 80) {
            System.out.println("成功解出");
            prettyPrint();
            System.exit(0);
        }
        do {
            currentIndex++;
        } while (contains(fixedIndexes, currentIndex));
        trySolveCell();
    }

    private void traceBack() {
        if (currentIndex == 0) {
            System.out.println("无解");
            prettyPrint();
            return;
        }
        data[currentIndex] = 0;
        do {
            currentIndex--;
        } while (contains(fixedIndexes, currentIndex));
        trySolveCell();
    }


    private void trySolveCell() {
        if(currentIndex==81){
            System.out.println("成功解出");
            prettyPrint();
            System.exit(0);
        }

        List<int[]> relevantGroups
                = this.groups.stream()
                .filter(group -> contains(group, currentIndex))
                .collect(Collectors.toList());
        for (int v = data[currentIndex]; v < 9; v++) {
            final int testValue = v + 1;
         //   System.out.println(String.format("trying %s @ %s",testValue,currentIndex));
            boolean conflict =
                    relevantGroups.stream()
                            .anyMatch(group -> {
                                for (int k : group) {
                                    if (data[k] == testValue) {
                                        return true;
                                    }
                                }
                                return false;
                            });
            if (!conflict) {
                data[currentIndex] = testValue;
                forward();
                return;
            }
        }
        traceBack();
    }


    private void prettyPrint() {
        System.out.println("打印盘面");
        for (int i = 0; i < 81; i++) {
            if (i % 9 == 0) {
                System.out.print("\n");
            }
            if (i % 27 == 0) {
                System.out.println("----------------------------------------------");
            }
            if (i % 3 == 0) {
                System.out.print("|");
            }
            if (data[i] == 0) {
                System.out.print("[]\t");
            } else {
                System.out.print(data[i] + "\t");
            }


        }
        System.out.print("\n");
        System.out.println("----------------------------------------------");

    }


    private void prettyPrintPosition() {
        for (int i = 0; i < 81; i++) {
            if (i % 9 == 0) {
                System.out.print("\n");
            }
            if (i % 27 == 0) {
                System.out.println("----------------------------------------------");
            }
            if (i % 3 == 0) {
                System.out.print("|");
            }

            System.out.print(i + "\t");

        }
        System.out.print("\n");
        System.out.println("----------------------------------------------");

    }


    public static void main(String[] args) {
        for(int i = 8 ; i < 9; i ++){
            System.out.println("!");
        }
        System.out.println("end");

    }


    private static boolean contains(int[] array, int target) {

        for (int i : array) {
            if (i == target) {
                return true;
            }
        }
        return false;
    }
}
