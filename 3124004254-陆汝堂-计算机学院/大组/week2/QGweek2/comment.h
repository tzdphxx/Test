#pragma once
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

// ������ջ
typedef struct NumNode {
    double data;
    struct NumNode* next;
} NumNode;

typedef struct {
    NumNode* top;
} NumStack;

// �����ջ
typedef struct OprtNode {
    char data;
    struct OprtNode* next;
} OprtNode;

typedef struct {
    OprtNode* top;
} OprtStack;

// ��������
void initNumStack(NumStack* s);
void pushNum(NumStack* s, double val);
double popNum(NumStack* s);
void initOprtStack(OprtStack* s);
void pushOprt(OprtStack* s, char op);
char popOprt(OprtStack* s);
int getPriority(char op);
double calculate(double a, double b, char op);
double evaluateExpression(const char* expr);