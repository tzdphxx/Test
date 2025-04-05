#include <stdio.h>
#include <stdlib.h>

// 定义树节点结构
typedef struct TreeNode {
    int data;
    struct TreeNode* left;
    struct TreeNode* right;
} TreeNode;

// 栈结构用于非递归遍历
typedef struct Stack {
    TreeNode** array;
    int top;
    int capacity;
} Stack;

// 队列结构用于层序遍历
typedef struct QueueNode {
    TreeNode* data;
    struct QueueNode* next;
} QueueNode;

typedef struct Queue {
    QueueNode* front;
    QueueNode* rear;
} Queue;

// 创建新节点
TreeNode* createNode(int data) {
    TreeNode* newNode = (TreeNode*)malloc(sizeof(TreeNode));
    if (newNode) {
        newNode->data = data;
        newNode->left = newNode->right = NULL;
    }
    return newNode;
}

// 栈操作
Stack* createStack(int capacity) {
    Stack* stack = (Stack*)malloc(sizeof(Stack));
    stack->array = (TreeNode**)malloc(capacity * sizeof(TreeNode*));
    stack->top = -1;
    stack->capacity = capacity;
    return stack;
}

int isStackEmpty(Stack* stack) {
    return stack->top == -1;
}

void push(Stack* stack, TreeNode* node) {
    if (stack->top < stack->capacity - 1) {
        stack->array[++stack->top] = node;
    }
}

TreeNode* pop(Stack* stack) {
    if (!isStackEmpty(stack)) {
        return stack->array[stack->top--];
    }
    return NULL;
}

// 队列操作
void initQueue(Queue* q) {
    q->front = q->rear = NULL;
}

int isQueueEmpty(Queue* q) {
    return q->front == NULL;
}

void enqueue(Queue* q, TreeNode* node) {
    QueueNode* newNode = (QueueNode*)malloc(sizeof(QueueNode));
    newNode->data = node;
    newNode->next = NULL;
    if (isQueueEmpty(q)) {
        q->front = q->rear = newNode;
    }
    else {
        q->rear->next = newNode;
        q->rear = newNode;
    }
}

TreeNode* dequeue(Queue* q) {
    if (isQueueEmpty(q)) return NULL;
    QueueNode* temp = q->front;
    TreeNode* node = temp->data;
    q->front = q->front->next;
    if (q->front == NULL) q->rear = NULL;
    free(temp);
    return node;
}

// 递归插入
TreeNode* insertRecursive(TreeNode* root, int data) {
    if (root == NULL) {
        return createNode(data);
    }
    if (data < root->data) {
        root->left = insertRecursive(root->left, data);
    }
    else if (data > root->data) {
        root->right = insertRecursive(root->right, data);
    }
    return root;
}

// 非递归插入
TreeNode* insertNonRecursive(TreeNode* root, int data) {
    TreeNode* newNode = createNode(data);
    if (root == NULL) return newNode;
    TreeNode* curr = root;
    TreeNode* parent = NULL;
    while (curr != NULL) {
        parent = curr;
        if (data < curr->data) {
            curr = curr->left;
        }
        else if (data > curr->data) {
            curr = curr->right;
        }
        else {
            free(newNode); // 数据已存在
            return root;
        }
    }
    if (data < parent->data) {
        parent->left = newNode;
    }
    else {
        parent->right = newNode;
    }
    return root;
}

// 递归查找
TreeNode* searchRecursive(TreeNode* root, int key) {
    if (root == NULL || root->data == key) {
        return root;
    }
    if (key < root->data) {
        return searchRecursive(root->left, key);
    }
    return searchRecursive(root->right, key);
}

// 非递归查找
TreeNode* searchNonRecursive(TreeNode* root, int key) {
    TreeNode* curr = root;
    while (curr != NULL) {
        if (curr->data == key) return curr;
        if (key < curr->data) curr = curr->left;
        else curr = curr->right;
    }
    return NULL;
}

// 找最小节点
TreeNode* findMin(TreeNode* root) {
    while (root && root->left) root = root->left;
    return root;
}

// 递归删除
TreeNode* deleteRecursive(TreeNode* root, int key) {
    if (root == NULL) return NULL;
    if (key < root->data) {
        root->left = deleteRecursive(root->left, key);
    }
    else if (key > root->data) {
        root->right = deleteRecursive(root->right, key);
    }
    else {
        // 节点找到
        if (root->left == NULL) {
            TreeNode* temp = root->right;
            free(root);
            return temp;
        }
        else if (root->right == NULL) {
            TreeNode* temp = root->left;
            free(root);
            return temp;
        }
        else {
            // 有两个子节点，找右子树最小节点
            TreeNode* temp = findMin(root->right);
            root->data = temp->data;
            root->right = deleteRecursive(root->right, temp->data);
        }
    }
    return root;
}

// 递归遍历
void preOrderRecursive(TreeNode* root) {
    if (root) {
        printf("%d ", root->data);
        preOrderRecursive(root->left);
        preOrderRecursive(root->right);
    }
}

void inOrderRecursive(TreeNode* root) {
    if (root) {
        inOrderRecursive(root->left);
        printf("%d ", root->data);
        inOrderRecursive(root->right);
    }
}

void postOrderRecursive(TreeNode* root) {
    if (root) {
        postOrderRecursive(root->left);
        postOrderRecursive(root->right);
        printf("%d ", root->data);
    }
}

// 非递归遍历
void preOrderNonRecursive(TreeNode* root) {
    if (root == NULL) return;
    Stack* stack = createStack(100);
    push(stack, root);
    while (!isStackEmpty(stack)) {
        TreeNode* node = pop(stack);
        printf("%d ", node->data);
        if (node->right) push(stack, node->right);
        if (node->left) push(stack, node->left);
    }
    free(stack->array);
    free(stack);
}

void inOrderNonRecursive(TreeNode* root) {
    Stack* stack = createStack(100);
    TreeNode* curr = root;
    while (curr || !isStackEmpty(stack)) {
        while (curr) {
            push(stack, curr);
            curr = curr->left;
        }
        curr = pop(stack);
        printf("%d ", curr->data);
        curr = curr->right;
    }
    free(stack->array);
    free(stack);
}

void postOrderNonRecursive(TreeNode* root) {
    if (root == NULL) return;
    Stack* s1 = createStack(100);
    Stack* s2 = createStack(100);
    push(s1, root);
    while (!isStackEmpty(s1)) {
        TreeNode* node = pop(s1);
        push(s2, node);
        if (node->left) push(s1, node->left);
        if (node->right) push(s1, node->right);
    }
    while (!isStackEmpty(s2)) {
        printf("%d ", pop(s2)->data);
    }
    free(s1->array);
    free(s1);
    free(s2->array);
    free(s2);
}

// 层序遍历
void levelOrder(TreeNode* root) {
    if (root == NULL) return;
    Queue q;
    initQueue(&q);
    enqueue(&q, root);
    while (!isQueueEmpty(&q)) {
        TreeNode* node = dequeue(&q);
        printf("%d ", node->data);
        if (node->left) enqueue(&q, node->left);
        if (node->right) enqueue(&q, node->right);
    }
}

// 释放树的内存
void freeTree(TreeNode* root) {
    if (root) {
        freeTree(root->left);
        freeTree(root->right);
        free(root);
    }
}

int main() {
    TreeNode* root = NULL;
    root = insertRecursive(root, 5);
    root = insertRecursive(root, 3);
    root = insertRecursive(root, 7);
    root = insertRecursive(root, 2);
    root = insertRecursive(root, 4);
    root = insertRecursive(root, 6);
    root = insertRecursive(root, 8);

    printf("前序遍历（递归）: ");
    preOrderRecursive(root);
    printf("\n前序遍历（非递归）: ");
    preOrderNonRecursive(root);

    printf("\n中序遍历（递归）: ");
    inOrderRecursive(root);
    printf("\n中序遍历（非递归）: ");
    inOrderNonRecursive(root);

    printf("\n后序遍历（递归）: ");
    postOrderRecursive(root);
    printf("\n后序遍历（非递归）: ");
    postOrderNonRecursive(root);

    printf("\n层序遍历: ");
    levelOrder(root);

    root = deleteRecursive(root, 3);
    printf("\n删除节点3后的中序遍历: ");
    inOrderRecursive(root);

    freeTree(root);
    return 0;
}