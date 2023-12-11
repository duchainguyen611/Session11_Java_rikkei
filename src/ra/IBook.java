package ra;

import java.io.Serializable;
import java.util.Scanner;

public interface IBook extends Serializable {
    void inputData(Scanner scanner);
    void displayData();
}
