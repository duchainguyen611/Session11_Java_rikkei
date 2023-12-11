package ra.run;

import com.sun.tools.javac.Main;
import ra.imp.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookRun implements Serializable {

    public static List<Book> bookList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            bookList = readDataFromFile();
        } catch (Exception ex){
            System.err.println("File not found!");
            bookList = new ArrayList<>();
        }
        menuBook(scanner);
    }

    public static void menuBook(Scanner scanner) {
        Book book = new Book();
        do {
            System.out.println("\n*********************************MENU********************************\n" +
                    "1. Nhập thông tin sách\n" +
                    "2. Hiển thị thông tin sách\n" +
                    "3. Cập nhật thông tin sách theo mã sách\n" +
                    "4. Xóa sách theo mã sách\n" +
                    "5. Sắp xếp sách theo giá bán tăng dần\n" +
                    "6. Thống kê sách theo khoảng giá (a-b nhập từ bàn phím)\n" +
                    "7. Tìm kiếm sách theo tên tác giả\n" +
                    "8. Thoát");
            System.out.print("Nhập lựa chọn:");
            int choice = book.validateInteger(scanner);
            switch (choice) {
                case 1:
                    book.inputData(scanner);
                    break;
                case 2:
                    book.displayData();
                    break;
                case 3:
                    book.updateInforBook(scanner, bookList);
                    break;
                case 4:
                    book.deleteBook(bookList, scanner);
                    break;
                case 5:
                    book.sortBook(bookList);
                    break;
                case 6:
                    book.statisticBook(bookList, scanner);
                    break;
                case 7:
                    book.lookForBook(bookList, scanner);
                    break;
                case 8:
                    writeDataToFile(bookList);
                    System.exit(0);
                default:
                    System.out.println("Hãy nhập từ 1 đến 8! Mời nhập lại!");
                    break;
            }
        } while (true);
    }

    private static void writeDataToFile(List<Book> bookList) {
        File file = new File("books.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(bookList);
            oos.flush();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    private static List<Book> readDataFromFile() {
        List<Book> listBookRead = null;
        File file = new File("books.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listBookRead = (List<Book>) ois.readObject();
            return listBookRead;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return listBookRead;
    }
}
