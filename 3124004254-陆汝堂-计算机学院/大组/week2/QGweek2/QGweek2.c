#define _CRT_SECURE_NO_WARNINGS 


#include "comment.h"

int main() 
{
    char expr[100];
    printf("输入表达式（以#结尾，如：(3+4)*5#）：");
    scanf("%99[^#]", expr);

    double result = evaluateExpression(expr);
    printf("计算结果：%.2f\n", result);
    return 0;
}
