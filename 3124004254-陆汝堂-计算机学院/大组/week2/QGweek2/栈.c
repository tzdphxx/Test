#include "comment.h"

// ��ʼ��������ջ
void initNumStack(NumStack* s) {
    s->top = NULL;
}

// ѹ�������
void pushNum(NumStack* s, double val) {
    NumNode* newNode = (NumNode*)malloc(sizeof(NumNode));
    newNode->data = val;
    newNode->next = s->top;
    s->top = newNode;
}

// ����������
double popNum(NumStack* s) {
    if (!s->top) return 0.0;
    NumNode* temp = s->top;
    double val = temp->data;
    s->top = s->top->next;
    free(temp);
    return val;
}

// ��ʼ�������ջ
void initOprtStack(OprtStack* s) {
    s->top = NULL;
}

// ѹ�������
void pushOprt(OprtStack* s, char op) {
    OprtNode* newNode = (OprtNode*)malloc(sizeof(OprtNode));
    newNode->data = op;
    newNode->next = s->top;
    s->top = newNode;
}

// ���������
char popOprt(OprtStack* s) {
    if (!s->top) return '\0';
    OprtNode* temp = s->top;
    char op = temp->data;
    s->top = s->top->next;
    free(temp);
    return op;
}

// ��ȡ���ȼ�
int getPriority(char op) {
    switch (op) {
    case '(': return 0;
    case '+': case '-': return 1;
    case '*': case '/': return 2;
    default: return -1;
    }
}

// ִ�м���
double calculate(double a, double b, char op) {
    switch (op) {
    case '+': return a + b;
    case '-': return a - b;
    case '*': return a * b;
    case '/':
        if (b == 0) {
            printf("���󣺳����쳣\n");
            exit(1);
        }
        return a / b;
    default: return 0.0;
    }
}

// ���ʽ��ֵ
double evaluateExpression(const char* expr) {
    NumStack numStack;
    OprtStack oprtStack;
    initNumStack(&numStack);
    initOprtStack(&oprtStack);
    pushOprt(&oprtStack, '(');

    int i = 0;
    int len = strlen(expr);

    while (i <= len || (oprtStack.top && oprtStack.top->data != '(')) {
        // ��������
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
        // ���������
        else {
            // ����������
            if (i < len && expr[i] == ')') {
                while (oprtStack.top && oprtStack.top->data != '(') {
                    char op = popOprt(&oprtStack);
                    double b = popNum(&numStack);
                    double a = popNum(&numStack);
                    pushNum(&numStack, calculate(a, b, op));
                }
                if (oprtStack.top) popOprt(&oprtStack); // ����������
                i++;
            }
            // �������������
            else {
                char currentOp = (i < len) ? expr[i] : ')'; // ���������Ŵ���ջ����

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
                    i++; // �����ַ�����
                }
            }
        }
    }
    return popNum(&numStack);
}