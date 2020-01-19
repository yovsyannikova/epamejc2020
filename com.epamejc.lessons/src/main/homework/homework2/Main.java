package main.homework.homework2;

import main.homework.homework2.matrix.RandomCharTable;
import main.homework.homework2.matrix.RandomPyramidalMatrix;
import main.homework.homework2.recursion.DigitsCounter;
import main.homework.homework2.recursion.Fibonacci;
import main.homework.homework2.recursion.Pow;

public class Main {
    public static void main(String[] args) {

        Immutable immutable = new Immutable(22);
        System.out.println("Immutable object was created");
        System.out.println("value = " + immutable.immutableField);
        // immutable.immutableField = 66; // immutable!
        immutable = new Immutable(66); // replaced with entire new instance
        System.out.println("value = " + immutable.immutableField);

        RandomPyramidalMatrix pyramidalArray = new RandomPyramidalMatrix(10);
        pyramidalArray.print();
        System.out.println(pyramidalArray.toStringStrategyA());
        System.out.println(pyramidalArray.toStringStrategyB());

        RandomCharTable twoDimensionalArray = new RandomCharTable(10, 10);
        twoDimensionalArray.print();
        System.out.println(twoDimensionalArray.toStringStrategyA());
        System.out.println(twoDimensionalArray.toStringStrategyB());

        String everyOtherCharsString = StringGenerator.createOfEveryOtherChars("String", true);
        System.out.println(everyOtherCharsString);

        String chosenCharsString = StringGenerator.createOfChars("Hello world", 0, 4, 6);
        System.out.println(chosenCharsString);

        String swappedSymbolsString = StringGenerator.swapSymbols("Hello World", 0, 4);
        System.out.println(swappedSymbolsString);

        String reversedWordsString = StringGenerator.reverseWords("Hello World");
        System.out.println(reversedWordsString);

        System.out.println(Fibonacci.getFibonacci(4));

        System.out.println(Pow.getPow(4, 3));

        System.out.println(DigitsCounter.calculateDigitsNumber(-111));

    }
}