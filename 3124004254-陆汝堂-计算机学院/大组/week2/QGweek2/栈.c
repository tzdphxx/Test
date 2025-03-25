#include "comment.h"

// 初始化操作数栈
void initNumStack(NumStack* s) {
    s->top = NULL;
}

// 压入操作数
void pushNum(NumStack* s, double val) {
    NumNode* newNode = (NumNode*)malloc(sizeof(NumNode));
    newNode->data = val;
    newNode->next = s->top;
    s->top = newNode;
}

// 弹出操作数
double popNum(NumStack* s) {
    if (!s->top) return 0.0;
    NumNode* temp = s->top;
    double val = temp->data;
    s->top = s->top->next;
    free(temp);
    return val;
}

// 初始化运算符栈
void initOprtStack(OprtStack* s) {
    s->top = NULL;
}

// 压入运算符
void pushOprt(OprtStack* s, char op) {
    OprtNode* newNode = (OprtNode*)malloc(sizeof(OprtNode));
    newNode->data = op;
    newNode->next = s->top;
    s->top = newNode;
}

// 弹出运算符
char popOprt(OprtStack* s) {
    if (!s->top) return '\0';
    OprtNode* temp = s->top;
    char op = temp->data;
    s->top = s->top->next;
    free(temp);
    return op;
}

// 获取优先级
int getPriority(char op) {
    switch (op) {
    case '(': return 0;
    case '+': case '-': return 1;
    case '*': case '/': return 2;
    default: return -1;
    }
}

// 执行计算
double calculate(double a, double b, char op) {
    switch (op) {
    case '+': return a + b;
    case '-': return a - b;
    case '*': return a * b;
    case '/':
        if (b == 0) {
            printf("错误：除零异常\n");
            exit(1);
        }
        return a / b;
    default: return 0.0;
    }
}

// 表达式求值
double evaluateExpression(const char* expr) {
    NumStack numStack;
    OprtStack oprtStack;
    initNumStack(&numStack);
    initOprtStack(&oprtStack);
    pushOprt(&oprtStack, '(');

    int i = 0;
    int len = strlen(expr);

    while (i <= len || (oprtStack.top && oprtStack.top->data != '(')) {
        // 处理数字
        if (i < len && (isdigit(expr[i]) || expr[i] == '.')) {
            double num = 0.0;
            int decimal = 0;
            double fraction = 1.0;

            while (i < len && (isdigit(expr[i]) || expr[i] == '.')) {
                if (expr[i] == '.') {
                    decimal = 1;
                }
                else {
                    if (decimal) {
                        fraction *= 0.1;
                        num += (expr[i] - '0') * fraction;
                    }
                    else {
                        num = num * 10 + (expr[i] - '0');
                    }
                }
                i++;
            }
            pushNum(&numStack, num);
        }
        // 处理运算符
        else {
            // 处理右括号
            if (i < len && expr[i] == ')') {
                while (oprtStack.top && oprtStack.top->data != '(') {
                    char op = popOprt(&oprtStack);
                    double b = popNum(&numStack);
                    double a = popNum(&numStack);
                    pushNum(&numStack, calculate(a, b, op));
                }
                if (oprtStack.top) popOprt(&oprtStack); // 弹出左括号
                i++;
            }
            // 处理其他运算符
            else {
                char currentOp = (i < len) ? expr[i] : ')'; // 虚拟右括号触发栈处理

                while (oprtStack.top && oprtStack.top->data != '(' &&
                    getPriority(currentOp) <= getPriority(oprtStack.top->data)) {
                    char op = popOprt(&oprtStack);
                    double b = popNum(&numStack);
                    double a = popNum(&numStack);
                    pushNum(&numStack, calculate(a, b, op));
                }

                if (i < len) {
                    pushOprt(&oprtStack, currentOp);
                    i++;
                }
                else {
                    i++; // 虚拟字符处理
                }
            }
        }
    }
    return popNum(&numStack);
}