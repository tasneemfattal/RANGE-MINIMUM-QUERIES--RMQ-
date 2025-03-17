package com.mycompany.tasnimfattalhw1;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] arraySizes = {10, 100, 1000, 10000}; // 1. Kısım: Scalability Analizi boyutları
        int numQueries = 100; // Rastgele sorgu sayısı

        // 1. Kısım: Scalability Analizi (Random Array)
        for (int size : arraySizes) {
            System.out.println("\n*** Scalability Analysis - Array Size: " + size + " ***");
            int[] randomArray = generateRandomArray(size);
            testAlgorithms(randomArray, numQueries);
        }

        // 2. Kısım: Sabit Boyutta Farklı Dizi Türleri Testi
        int fixedSize = 1000; // Sabit dizi boyutu
        System.out.println("\n*** Fixed Size Tests - Array Size: " + fixedSize + " ***");

        // Rastgele Dizi (Random Array)
        System.out.println("\n--- Testing Random Array ---");
        int[] randomArray = generateRandomArray(fixedSize);
        testAlgorithms(randomArray, numQueries);

        // Sıralı Dizi (Sorted Array)
        System.out.println("\n--- Testing Sorted Array ---");
        int[] sortedArray = generateSortedArray(fixedSize);
        testAlgorithms(sortedArray, numQueries);

        // Ters Sıralı Dizi (Reversed Array)
        System.out.println("\n--- Testing Reversed Sorted Array ---");
        int[] reversedArray = generateReversedSortedArray(fixedSize);
        testAlgorithms(reversedArray, numQueries);
    }

    // Rastgele dizi oluştur
    public static int[] generateRandomArray(int size) {
        Random random = new Random(12345L);// sabit 
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000); // 0 ile 1.000.000 arasında rastgele değerler
        }
        return array;
    }

    // Sıralı dizi oluştur
    public static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    // Ters sıralı dizi oluştur
    public static int[] generateReversedSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = size - i; // Büyükten küçüğe sıralı
        }
        return array;
    }

    // Algoritmaları test et
    public static void testAlgorithms(int[] array, int numQueries) {
        int[][] queries = generateRandomQueries(array.length, numQueries, 12345L);

        // PrecomputeAll
        testAlgorithm("Precompute All", () -> new PrecomputeAll(array), queries);

        // PrecomputeNone
        testAlgorithm("Precompute None", () -> new PrecomputeNone(array), queries);

        // SparseTable
        testAlgorithm("Sparse Table", () -> new SparseTable(array), queries);

        // Blocking (Block Size 10)
        testAlgorithm("Blocking (Block Size 10)", () -> new Blocking(array, 10), queries);
    }

    // Algoritma testi ve zaman ölçümü
    public static void testAlgorithm(String name, Runnable algorithmConstructor, int[][] queries) {
        long preprocessStart = System.nanoTime();
        Object algorithm = createAlgorithm(algorithmConstructor);
        long preprocessTime = System.nanoTime() - preprocessStart;

        long queryStart = System.nanoTime();
        measureQueryTime(algorithm, queries);
        long queryTime = System.nanoTime() - queryStart;

        System.out.printf("%s Results:\nPreprocess Time: %d ns\nTotal Query Time: %d ns\n\n",
                name, preprocessTime, queryTime);
    }

    // Algoritmayı çalıştır
    public static Object createAlgorithm(Runnable algorithmConstructor) {
        algorithmConstructor.run();
        return algorithmConstructor;
    }

    // Rastgele sorgular oluştur
    public static int[][] generateRandomQueries(int size, int numQueries, long seed) {// sabit seed
        Random random = new Random(seed);
        int[][] queries = new int[numQueries][2];
        for (int i = 0; i < numQueries; i++) {
            int left = random.nextInt(size);
            int right = random.nextInt(size - left) + left;
            queries[i][0] = left;
            queries[i][1] = right;
        }
        return queries;
    }

    // Sorgu süresini ölç
    public static void measureQueryTime(Object algorithm, int[][] queries) {
        for (int[] query : queries) {
            if (algorithm instanceof PrecomputeAll) {
                ((PrecomputeAll) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof SparseTable) {
                ((SparseTable) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof Blocking) {
                ((Blocking) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof PrecomputeNone) {
                ((PrecomputeNone) algorithm).query(query[0], query[1]);
            }
        }
    }
}

/*public class Main {
    public static void main(String[] args) {
        int[] arraySizes = {10,100, 1000}; // Test edilecek dizi boyutları
        int numQueries = 100; // Her dizi için rastgele 100 sorgu

        for (int size : arraySizes) {
            System.out.println("\n*** Array Size: " + size + " ***");

            // Without Duplicates
            System.out.println("\n--- Testing Array without Duplicates ---");

            // Unsorted Array
            System.out.println("\nTesting Unsorted Array:");
            int[] unsortedArray = generateRandomArray(size);
            testAlgorithms(unsortedArray, numQueries);

            // Sorted Array
            System.out.println("\nTesting Sorted Array:");
            int[] sortedArray = generateSortedArray(size);
            testAlgorithms(sortedArray, numQueries);

            // With Duplicates
            System.out.println("\n--- Testing Array with Duplicates ---");

            // Unsorted Array
            System.out.println("\nTesting Unsorted Array:");
            int[] duplicateUnsortedArray = generateDuplicateArray(size);
            testAlgorithms(duplicateUnsortedArray, numQueries);

            // Sorted Array
            System.out.println("\nTesting Sorted Array:");
            int[] duplicateSortedArray = generateDuplicateArray(size);
            testAlgorithms(duplicateSortedArray, numQueries);
        }
    }

    // Rastgele dizi oluştur
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000); // 0 ile 1.000.000 arasında rastgele değerler
        }
        return array;
    }

    // Sıralı dizi oluştur
    public static int[] generateSortedArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        return array;
    }

    // Yinelenen değerler içeren bir dizi oluştur
    public static int[] generateDuplicateArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100); // 0-99 arasında değerler
        }
        return array;
    }

    // Algoritmaları test et
    public static void testAlgorithms(int[] array, int numQueries) {
        int[][] queries = generateRandomQueries(array.length, numQueries, 12345L);

        // PrecomputeAll
        testAlgorithm("Precompute All", () -> new PrecomputeAll(array), queries);

        // PrecomputeNone
        testAlgorithm("Precompute None", () -> new PrecomputeNone(array), queries);

        // SparseTable
        testAlgorithm("Sparse Table", () -> new SparseTable(array), queries);

        // Blocking (Block Size 10)
        testAlgorithm("Blocking (Block Size 10)", () -> new Blocking(array, 10), queries);
    }

    // Algoritma testi ve zaman ölçümü
    public static void testAlgorithm(String name, Runnable algorithmConstructor, int[][] queries) {
        long preprocessStart = System.nanoTime();
        Object algorithm = createAlgorithm(algorithmConstructor);
        long preprocessTime = System.nanoTime() - preprocessStart;

        long queryStart = System.nanoTime();
        measureQueryTime(algorithm, queries);
        long queryTime = System.nanoTime() - queryStart;

        System.out.printf("%s Results:\nPreprocess Time: %d ns\nTotal Query Time: %d ns\n\n",
                name, preprocessTime, queryTime);
    }

    // Algoritmayı çalıştır
    public static Object createAlgorithm(Runnable algorithmConstructor) {
        algorithmConstructor.run();
        return algorithmConstructor;
    }

    // Rastgele sorgular oluştur
    public static int[][] generateRandomQueries(int size, int numQueries, long seed) {
        Random random = new Random(seed);
        int[][] queries = new int[numQueries][2];
        for (int i = 0; i < numQueries; i++) {
            int left = random.nextInt(size);
            int right = random.nextInt(size - left) + left;
            queries[i][0] = left;
            queries[i][1] = right;
        }
        return queries;
    }

    // Sorgu süresini ölç
    public static void measureQueryTime(Object algorithm, int[][] queries) {
        for (int[] query : queries) {
            if (algorithm instanceof PrecomputeAll) {
                ((PrecomputeAll) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof SparseTable) {
                ((SparseTable) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof Blocking) {
                ((Blocking) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof PrecomputeNone) {
                ((PrecomputeNone) algorithm).query(query[0], query[1]);
            }
        }
    }
}*/
/*public class Main {
    public static void main(String[] args) {
        // Dizi boyutları ve sorgu sayısı
        int[] sizes = {50, 100, 500, 1000};
        int numQueries = 100; // Her dizi için rastgele 100 sorgu

        System.out.printf("%-15s %-12s %-15s %-15s%n", "Algorithm", "Array Size", "Build Time (ms)", "Query Time (ms)");
        System.out.println("=".repeat(60));

        for (int size : sizes) {
            // Rastgele dizi oluştur
            int[] array = generateRandomArray(size);

            // Rastgele sorgular oluştur
            int[][] queries = generateRandomQueries(size, numQueries,12345L);

            // PrecomputeAll
            long buildTime = measureBuildTime(() -> new PrecomputeAll(array));
            long queryTime = measureQueryTime(new PrecomputeAll(array), queries);
            System.out.printf("%-15s %-12d %-15.2f %-15.2f%n", "PrecomputeAll", size, buildTime / 1e6, queryTime / 1e6);

            // SparseTable
            buildTime = measureBuildTime(() -> new SparseTable(array));
            queryTime = measureQueryTime(new SparseTable(array), queries);
            System.out.printf("%-15s %-12d %-15.2f %-15.2f%n", "SparseTable", size, buildTime / 1e6, queryTime / 1e6);

            // Blocking
            buildTime = measureBuildTime(() -> new Blocking(array));
            queryTime = measureQueryTime(new Blocking(array), queries);
            System.out.printf("%-15s %-12d %-15.2f %-15.2f%n", "Blocking", size, buildTime / 1e6, queryTime / 1e6);

            // PrecomputeNone
            buildTime = measureBuildTime(() -> new PrecomputeNone(array));
            queryTime = measureQueryTime(new PrecomputeNone(array), queries);
            System.out.printf("%-15s %-12d %-15.2f %-15.2f%n", "PrecomputeNone", size, buildTime / 1e6, queryTime / 1e6);
        }
    }

    // Rastgele bir dizi oluştur
    public static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000000); // 0 ile 1.000.000 arasında rastgele değerler
        }
        return array;
    }

    // Rastgele sorgular oluştur
    public static int[][] generateRandomQueries(int size, int numQueries,long seed) {
        Random random = new Random(seed);
        int[][] queries = new int[numQueries][2];
        for (int i = 0; i < numQueries; i++) {
            int left = random.nextInt(size);
            int right = random.nextInt(size - left) + left;
            queries[i][0] = left;
            queries[i][1] = right;
        }
        return queries;
    }

    // Ön işleme süresini ölç
    public static long measureBuildTime(Runnable algorithmConstructor) {
        long startTime = System.nanoTime();
        algorithmConstructor.run();
        return (System.nanoTime() - startTime); // ns cinsinden
    }

    // Sorgu süresini ölç
    public static long measureQueryTime(Object algorithm, int[][] queries) {
        long startTime = System.nanoTime();
        for (int[] query : queries) {
            if (algorithm instanceof PrecomputeAll) {
                ((PrecomputeAll) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof SparseTable) {
                ((SparseTable) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof Blocking) {
                ((Blocking) algorithm).query(query[0], query[1]);
            } else if (algorithm instanceof PrecomputeNone) {
                ((PrecomputeNone) algorithm).query(query[0], query[1]);
            }
        }
        return (System.nanoTime() - startTime); // ns cinsinden
    }
}*/

/*public class Main {
    public static void main(String[] args) {
        // Sabit bir dizi
        int[] array = {2, 4, 3, 1, 6, 7};

        // Sabit bir sorgu: Min(2, 5)
        int left = 2;
        int right = 5;

        System.out.println("Sabit sorgu Min(" + left + ", " + right + "):");
        
        // PrecomputeAll algoritmasını test et
        long startTime = System.nanoTime();
        PrecomputeAll precomputeAll = new PrecomputeAll(array);
        long precomputeAllBuildTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        int precomputeAllResult = precomputeAll.query(left, right);
        long precomputeAllQueryTime = System.nanoTime() - startTime;

        System.out.println("PrecomputeAll - Min (" + left + ", " + right + "): " + precomputeAllResult);

        // SparseTable algoritmasını test et
        startTime = System.nanoTime();
        SparseTable sparseTable = new SparseTable(array);
        long sparseTableBuildTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        int sparseTableResult = sparseTable.query(left, right);
        long sparseTableQueryTime = System.nanoTime() - startTime;

        System.out.println("SparseTable - Min (" + left + ", " + right + "): " + sparseTableResult);

        // Blocking algoritmasını test et
        startTime = System.nanoTime();
        Blocking blocking = new Blocking(array);
        long blockingBuildTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        int blockingResult = blocking.query(left, right);
        long blockingQueryTime = System.nanoTime() - startTime;

        System.out.println("Blocking - Min (" + left + ", " + right + "): " + blockingResult);

        // PrecomputeNone algoritmasını test et
        startTime = System.nanoTime();
        PrecomputeNone precomputeNone = new PrecomputeNone(array);
        long precomputeNoneBuildTime = System.nanoTime() - startTime;

        startTime = System.nanoTime();
        int precomputeNoneResult = precomputeNone.query(left, right);
        long precomputeNoneQueryTime = System.nanoTime() - startTime;

        System.out.println("PrecomputeNone - Min (" + left + ", " + right + "): " + precomputeNoneResult);

        System.out.println("\nPerformans ölçümleri:");

        // Performans ölçüm verileri
        System.out.println("PrecomputeAll: Build Time = " + precomputeAllBuildTime / 1e6 + " ms, Query Time = " + precomputeAllQueryTime / 1e6 + " ms");
        System.out.println("SparseTable: Build Time = " + sparseTableBuildTime / 1e6 + " ms, Query Time = " + sparseTableQueryTime / 1e6 + " ms");
        System.out.println("Blocking: Build Time = " + blockingBuildTime / 1e6 + " ms, Query Time = " + blockingQueryTime / 1e6 + " ms");
        System.out.println("PrecomputeNone: Build Time = " + precomputeNoneBuildTime / 1e6 + " ms, Query Time = " + precomputeNoneQueryTime / 1e6 + " ms");
    }
}*/
