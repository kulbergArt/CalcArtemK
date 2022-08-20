import java.util.Scanner;

public class Calc {
    public static void main(String[] args) throws CalcException {
        // получаем данные из клавиатуры
        Scanner in = new Scanner(System.in);
        String input = in.nextLine().toUpperCase();

        // разбиваем строку по пробелам и получаем массив строк
        String [] summands = input.split(" ");

        // Проверяем на кол-во операндов и операторов + выбрасываем Exception
        if (summands.length > 3) {
            throw new CalcException("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else if (summands.length < 3) {
            throw new CalcException("Cтрока не является математической операцией");
        }

        int[] arabNum = new int[2]; // Массив с арабскими операндами
        String[] romeNum = new String[2]; // Массив с римскими операндами
        char symbol = '+'; // Оператор

        // Проходимся циклом по массиву summands добавляем данные в нужный словарь
        for(int i = 0; i<summands.length; i++){
            if (i == 0) {
                // Проверка строки на римское число.
                boolean cr = checkRome(summands[0]);
                // Если не римское - делаем строку цифрой
                if (!cr) {
                    arabNum[0] = Integer.parseInt(summands[0]);
                    // Проверяем на размер арабскоое число
                    if (arabNum[0] > 10) {
                        throw new CalcException("Одно из чисел больше 10.");
                    }
                } else {
                    try {
                        RomeNum.valueOf(summands[0]);
                    } catch (IllegalArgumentException e) {
                        throw new CalcException("Ошибка. Число должно быть римским или целым арабским.");
                    }
                    int rNumArab = RomeNum.valueOf(summands[0]).getArabNum();
                    if(rNumArab > 10){
                        throw new CalcException("Одно из чисел больше X.");
                    }
                    // Оставляем римское число - строка
                    romeNum[0] = summands[0];
                }
            } else if (i == 1) {
                symbol = summands[1].charAt(0);

            } else if (i == 2) {
                // Проверка строки на римское число.
                boolean cr = checkRome(summands[2]);
                // Если не римское - делаем строку цифрой
                if (!cr) {
                    arabNum[1] = Integer.parseInt(summands[2]);
                    // Проверяем на размер арабскоое число
                    if (arabNum[1] > 10) {
                        throw new CalcException("Одно из чисел больше 10.");
                    }
                } else {
                    try {
                        RomeNum.valueOf(summands[2]);
                    } catch (IllegalArgumentException e) {
                        throw new CalcException("Ошибка. Число должно быть римским или целым арабским.");
                    }
                    int rNumArab = RomeNum.valueOf(summands[2]).getArabNum();
                    if(rNumArab > 10){
                        throw new CalcException("Одно из чисел больше X.");
                    }
                    // Оставляем римское число - строка
                    romeNum[1] = summands[2];
                }
            }
        }


        if (romeNum[0] == null && romeNum[1] == null){
            // Выводим результат арабской цифрой
            System.out.println(countArab(arabNum[0], symbol, arabNum[1]));
        } else if (romeNum[0] != null && romeNum[1] != null) {
            // Выводим результат римской цифрой
            System.out.println(countRome(romeNum[0], symbol, romeNum[1]));
        } else {
            throw new CalcException("Используются одновременно разные системы счисления.");
        }

        in.close();


    }

    static int countArab(int summand1, char symbol, int summand2) throws CalcException {
        int result;
        switch (symbol){
            case '+' :
                result = summand1 + summand2;
                break;
            case '-' :
                result = summand1 - summand2;
                break;
            case '*' :
                result = summand1 * summand2;
                break;
            case '/' :
                result = summand1 / summand2;
                break;
            default:
                throw new CalcException("Использован неизвестный оператор. Можно использовать только +, -, * и /.");
        }
        return result;
    }

    static String countRome(String summand1, char symbol, String summand2) throws CalcException {
        int rNumArab1 = RomeNum.valueOf(summand1).getArabNum();
        int rNumArab2 = RomeNum.valueOf(summand2).getArabNum();
        int count;
        switch (symbol){
            case '+' :
                count = rNumArab1 + rNumArab2;
                break;
            case '-' :
                count = rNumArab1 - rNumArab2;
                break;
            case '*' :
                count = rNumArab1 * rNumArab2;
                break;
            case '/' :
                count = rNumArab1 / rNumArab2;
                break;
            default:
                throw new CalcException("Использован неизвестный оператор. Можно использовать только +, -, * и /.");
        }
        if (count <= 0){
            throw new CalcException("В римской системе нет отрицательных чисел и нуля.");
        }
        RomeNum romeResult = RomeNum.values()[count-1];

        return romeResult.getRomeNum();
    }

    static boolean checkRome(String number){
        try {
            Integer.parseInt(number);
            return false;
        } catch (NumberFormatException e){
            return true;
        }
    }
}

class CalcException extends Exception {
    public CalcException(String description){
        super(description);
    }
}
