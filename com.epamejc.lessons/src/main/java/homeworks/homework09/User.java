package homeworks.homework09;

import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class User {

    public final String name;

    BigDecimal userBalance;

    public User(String name) {
        this.name = name;
        userBalance = new BigDecimal(200_000);
    }

    @SneakyThrows
    public boolean executeOperation(int operation, BigDecimal amount, ATM atm) {
        synchronized (atm) {
            System.out.println(atm.getIdColumnForPrint() + name + " occupied ATM №" + atm.id);
            TimeUnit.MILLISECONDS.sleep(1000 + new Random().nextInt(1000));
            boolean operationResult;
            switch (operation) {
                case 0:
                    operationResult = addToBalance(atm, amount);
                    System.out.println(atm.getIdColumnForPrint() + name + " released ATM №" + atm.id);
                    return operationResult;
                case 1:
                    operationResult = takeFromBalance(atm, amount);
                    System.out.println(atm.getIdColumnForPrint() + name + " released ATM №" + atm.id);
                    return operationResult;
                default:
                    return false;
            }
        }
    }

    public int chooseRandomOperation() {
        return new Random().nextInt(2);
    }

    public BigDecimal chooseRandomAmount() {
        double random = 10000 + new Random().nextDouble() * 50000;
        return new BigDecimal(random);
    }

    public boolean addToBalance(ATM atm, BigDecimal value) {
        if (this.userBalance.compareTo(value) < 0) {
            System.out.println(this.name + " doesn't have much money!");
            return false;
        } else {
            if (atm.addToBalance(value)) {
                userBalance = userBalance.add(value.negate());
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean takeFromBalance(ATM atm, BigDecimal value) {
        if (atm.subtractFromBalance(value)) {
            userBalance = userBalance.add(value);
            return true;
        } else {
            return false;
        }
    }

    public ATM chooseRandomATM() {
        int randomInt = new Random().nextInt(ATM_System.atmList.size());
        return ATM_System.atmList.get(randomInt);
    }


}