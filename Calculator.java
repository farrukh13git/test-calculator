
package calculator;
import java.util.Scanner;

public class Calculator {    
    public static class MyException extends Exception {    
    // создаем свой класс исключений путем наследования
        MyException(){}
        MyException(String msg){
            super(msg);
        }
    }
    private static int calculate(int num1, int num2, char operator) throws MyException {    
    // вычисляет результат выражения с числами num1, num2, операцией operator и возвращает результат
        int result;
        if (num1<0 || num1>9 || num2<0 || num2>9)
            throw new MyException("Ошибка: Неверный диапазон числа!");
        if (num2==0 && operator=='/')
            throw new MyException("Ошибка: Деление на ноль!");
        switch (operator) {
            case '+' : result=num1+num2;
            break;
            case '-' : result=num1-num2;
            break;
            case '*' : result=num1*num2;
            break;
            case '/' : result=num1/num2;
            break;
            default: throw new MyException("Ошибка: Неверная операция!");
        }  
        return result;
    }
    
    static void printArabic(int result){        
    // печатает результат на консоль арабскими цифрами
        System.out.println(result);
    }
    
    static void printRoman(int result){
    // печатает результат на консоль римскими цифрами
        int numbers[]={1, 4, 5, 9, 10, 50, 100, 500, 1000};
        String letters[]={"I", "IV", "V", "IX", "X", "L", "C", "D", "M"};
        String roman="";
        while (result>0) {
            for (int i=0; i<numbers.length; i++) {
                if (result < numbers[i]) {
                    result=result-numbers[i-1];
                    roman=roman+letters[i-1];
                    break;
                }
            }
        }      
        System.out.println(roman);
    }
    
    static int romanToInt(String s) throws MyException {
    // приведение в числовой вид строки с римскими цифрами от 1го до 9ти. Цифра 0 в римских цифрах отсутствует.
        int num;
        switch (s) {
            case "I": num=1;
            break;
            case "II": num=2;
            break;
            case "III": num=3;
            break;
            case "IV": num=4;
            break;
            case "V": num=5;
            break;
            case "VI": num=6;
            break;
            case "VII": num=7;
            break;
            case "VIII": num=8;
            break;
            case "IX": num=9;
            break;
            default: throw new MyException("Ошибка: Неверный формат числа!");
        }
        return num;
    }
    
    static char identify(String s) throws MyException {
    // Распознавание выражения из введенной строки
        try (Scanner sc = new Scanner(s)) { // использование try-with-resources
            String temp;
            int result, num1, num2;
            char operator;
            if (sc.hasNextInt()) {
                num1=sc.nextInt();
                if (!sc.hasNext())
                    throw new MyException("Ошибка: Неверный формат выражения!");
                temp=sc.next();
                if (!"+".equals(temp) && !"-".equals(temp) && !"*".equals(temp) && !"/".equals(temp))
                    throw new MyException("Ошибка: Неверная операция!");
                else
                    operator=temp.charAt(0);
                if (sc.hasNextInt())
                    num2=sc.nextInt();
                else
                    throw new MyException("Ошибка: Неверный формат выражения!");
                result=calculate(num1, num2, operator);
                printArabic(result);                   
            }
            else {
                if (!sc.hasNext())
                  return '0';
                temp=sc.next();
                if ("q".equals(temp))
                    return 'q';
                num1=romanToInt(temp);
                if (!sc.hasNext())
                    throw new MyException("Ошибка: Неверный формат выражения!");
                temp=sc.next();                   
                if (!"+".equals(temp) && !"-".equals(temp) && !"*".equals(temp) && !"/".equals(temp))
                    throw new MyException("Ошибка: Неверная операция!");
                else
                    operator=temp.charAt(0);
                if (!sc.hasNext())
                    throw new MyException("Ошибка: Неверный формат выражения!");
                temp=sc.next();
                num2=romanToInt(temp);
                result=calculate(num1, num2, operator);
                printRoman(result);
            }
        }
        return '1';
    }
    
    static void greeting() {        // Приветствие и инструкция
        System.out.println("Вас приветствует программа Калькулятор:");
        System.out.print("Этот калькулятор умеет выполнять четыре арифметических(+,-,*,/) действия над\nцелыми числами, и работает как с арабскими так и с римскими цифрами в диапазоне\nот 0 до 9ти\n");
        System.out.print("Пример ввода: 1 + 6; 9 * 2; 6 / 3; 8 - 4. Нужно разделять цифры и операции\nпробелами.\n");
        System.out.println("Введите выражение(для выхода 'q'):");
    }
    
    public static void main(String[] args) throws MyException {
    // Программа начинается здесь
        try {
            try (Scanner scanner = new Scanner(System.in)) {
                char ch;
                String str;
                greeting();
                while (true) {
                    str=scanner.nextLine();
                    ch=identify(str);
                    if (ch=='q'){
                        System.out.println("Программа завершена.");
                        break;
                    }
                }
            }
        }
        catch (MyException m) {
            System.out.println(m.getMessage());
        }
    }     
}