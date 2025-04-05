#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>

// 排序算法声明
void insertSort(int arr[], int n);
void mergeSortWrapper(int arr[], int n);
void quickSortWrapper(int arr[], int n);
void countSort(int arr[], int n);
void radixCountSort(int arr[], int n);

// 辅助函数
void swap(int* a, int* b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

// 生成测试数据
void generateTestData(const char* filename, int n) {
    FILE* fp = fopen(filename, "w");
    if (fp == NULL) {
        perror("Failed to open file");
        exit(EXIT_FAILURE);
    }
    srand(time(NULL));
    for (int i = 0; i < n; i++) {
        fprintf(fp, "%d\n", rand() % 10000); // 生成0-9999的随机数
    }
    fclose(fp);
}

// 读取数据
int* readDataFromFile(const char* filename, int* n) {
    FILE* fp = fopen(filename, "r");
    if (fp == NULL) {
        perror("Failed to open file");
        return NULL;
    }

    int capacity = 100;
    int* data = (int*)malloc(capacity * sizeof(int));
    int num, count = 0;

    while (fscanf(fp, "%d", &num) == 1) {
        if (count >= capacity) {
            capacity *= 2;
            data = (int*)realloc(data, capacity * sizeof(int));
        }
        data[count++] = num;
    }

    *n = count;
    fclose(fp);
    return data;
}

// 插入排序
void insertSort(int arr[], int n) {
    for (int i = 1; i < n; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}

// 归并排序实现
void merge(int arr[], int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;
    int* L = (int*)malloc(n1 * sizeof(int));
    int* R = (int*)malloc(n2 * sizeof(int));

    for (int i = 0; i < n1; i++)
        L[i] = arr[l + i];
    for (int j = 0; j < n2; j++)
        R[j] = arr[m + 1 + j];

    int i = 0, j = 0, k = l;
    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) arr[k++] = L[i++];
        else arr[k++] = R[j++];
    }

    while (i < n1) arr[k++] = L[i++];
    while (j < n2) arr[k++] = R[j++];

    free(L);
    free(R);
}

void mergeSort(int arr[], int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;
        mergeSort(arr, l, m);
        mergeSort(arr, m + 1, r);
        merge(arr, l, m, r);
    }
}

void mergeSortWrapper(int arr[], int n) {
    mergeSort(arr, 0, n - 1);
}

// 快速排序实现
int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = low - 1;

    for (int j = low; j <= high - 1; j++) {
        if (arr[j] < pivot) {
            i++;
            swap(&arr[i], &arr[j]);
        }
    }
    swap(&arr[i + 1], &arr[high]);
    return i + 1;
}

void quickSort(int arr[], int low, int high) {
    if (low < high) {
        int pi = partition(arr, low, high);
        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

void quickSortWrapper(int arr[], int n) {
    quickSort(arr, 0, n - 1);
}

// 计数排序实现
void countSort(int arr[], int n) {
    if (n <= 0) return;

    int max = arr[0], min = arr[0];
    for (int i = 1; i < n; i++) {
        if (arr[i] > max) max = arr[i];
        if (arr[i] < min) min = arr[i];
    }

    int range = max - min + 1;
    int* count = (int*)calloc(range, sizeof(int));
    int* output = (int*)malloc(n * sizeof(int));

    for (int i = 0; i < n; i++)
        count[arr[i] - min]++;

    for (int i = 1; i < range; i++)
        count[i] += count[i - 1];

    for (int i = n - 1; i >= 0; i--) {
        output[count[arr[i] - min] - 1] = arr[i];
        count[arr[i] - min]--;
    }

    for (int i = 0; i < n; i++)
        arr[i] = output[i];

    free(count);
    free(output);
}

// 基数排序实现
int getMax(int arr[], int n) {
    int max = arr[0];
    for (int i = 1; i < n; i++)
        if (arr[i] > max) max = arr[i];
    return max;
}

void countSortByDigit(int arr[], int n, int exp) {
    int* output = (int*)malloc(n * sizeof(int));
    int count[10] = { 0 };

    for (int i = 0; i < n; i++)
        count[(arr[i] / exp) % 10]++;

    for (int i = 1; i < 10; i++)
        count[i] += count[i - 1];

    for (int i = n - 1; i >= 0; i--) {
        output[count[(arr[i] / exp) % 10] - 1] = arr[i];
        count[(arr[i] / exp) % 10]--;
    }

    for (int i = 0; i < n; i++)
        arr[i] = output[i];

    free(output);
}

void radixCountSort(int arr[], int n) {
    int max = getMax(arr, n);
    for (int exp = 1; max / exp > 0; exp *= 10)
        countSortByDigit(arr, n, exp);
}

// 测试函数
void testSortingAlgorithms(const char* filename, int size) {
    int n;
    int* original_data = readDataFromFile(filename, &n);
    if (!original_data || n != size) {
        printf("Error reading data for size %d\n", size);
        return;
    }

    int* arr = (int*)malloc(size * sizeof(int));
    if (!arr) {
        free(original_data);
        return;
    }

    // 测试插入排序
    memcpy(arr, original_data, size * sizeof(int));
    clock_t start = clock();
    insertSort(arr, size);
    printf("InsertSort[%6d]: %.6f s\n", size, (double)(clock() - start) / CLOCKS_PER_SEC);

    // 测试归并排序
    memcpy(arr, original_data, size * sizeof(int));
    start = clock();
    mergeSortWrapper(arr, size);
    printf("MergeSort[%6d]: %.6f s\n", size, (double)(clock() - start) / CLOCKS_PER_SEC);

    // 测试快速排序
    memcpy(arr, original_data, size * sizeof(int));
    start = clock();
    quickSortWrapper(arr, size);
    printf("QuickSort[%6d]: %.6f s\n", size, (double)(clock() - start) / CLOCKS_PER_SEC);

    // 测试计数排序
    memcpy(arr, original_data, size * sizeof(int));
    start = clock();
    countSort(arr, size);
    printf("CountSort[%6d]: %.6f s\n", size, (double)(clock() - start) / CLOCKS_PER_SEC);

    // 测试基数排序
    memcpy(arr, original_data, size * sizeof(int));
    start = clock();
    radixCountSort(arr, size);
    printf("RadixSort[%6d]: %.6f s\n\n", size, (double)(clock() - start) / CLOCKS_PER_SEC);

    free(arr);
    free(original_data);
}

void testSmallData() {
    const int SIZE = 100;
    const int ITERATIONS = 100000;
    int* original_data = (int*)malloc(SIZE * sizeof(int));
    srand(time(NULL));
    for (int i = 0; i < SIZE; i++) {
        original_data[i] = rand() % 10000;
    }

    int* arr = (int*)malloc(SIZE * sizeof(int));
    clock_t start, end;

    // 测试插入排序
    start = clock();
    for (int i = 0; i < ITERATIONS; i++) {
        memcpy(arr, original_data, SIZE * sizeof(int));
        insertSort(arr, SIZE);
    }
    end = clock();
    printf("InsertSort[100x100k]: %.6f s\n", (double)(end - start) / CLOCKS_PER_SEC);

    // 测试归并排序
    start = clock();
    for (int i = 0; i < ITERATIONS; i++) {
        memcpy(arr, original_data, SIZE * sizeof(int));
        mergeSortWrapper(arr, SIZE);
    }
    end = clock();
    printf("MergeSort[100x100k]: %.6f s\n", (double)(end - start) / CLOCKS_PER_SEC);

    // 测试快速排序
    start = clock();
    for (int i = 0; i < ITERATIONS; i++) {
        memcpy(arr, original_data, SIZE * sizeof(int));
        quickSortWrapper(arr, SIZE);
    }
    end = clock();
    printf("QuickSort[100x100k]: %.6f s\n", (double)(end - start) / CLOCKS_PER_SEC);

    // 测试计数排序
    start = clock();
    for (int i = 0; i < ITERATIONS; i++) {
        memcpy(arr, original_data, SIZE * sizeof(int));
        countSort(arr, SIZE);
    }
    end = clock();
    printf("CountSort[100x100k]: %.6f s\n", (double)(end - start) / CLOCKS_PER_SEC);

    // 测试基数排序
    start = clock();
    for (int i = 0; i < ITERATIONS; i++) {
        memcpy(arr, original_data, SIZE * sizeof(int));
        radixCountSort(arr, SIZE);
    }
    end = clock();
    printf("RadixSort[100x100k]: %.6f s\n\n", (double)(end - start) / CLOCKS_PER_SEC);

    free(arr);
    free(original_data);
}

int main() {
    // 生成测试数据
    const int sizes[] = { 10000, 50000, 200000 };
    for (int i = 0; i < 3; i++) {
        char filename[20];
        sprintf(filename, "data_%d.txt", sizes[i]);
        generateTestData(filename, sizes[i]);
    }

    // 大数据量测试
    for (int i = 0; i < 3; i++) {
        char filename[20];
        sprintf(filename, "data_%d.txt", sizes[i]);
        testSortingAlgorithms(filename, sizes[i]);
    }

    // 小数据量多次测试
    testSmallData();

    return 0;
}