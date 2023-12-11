package ra.imp;

import ra.IBook;
import ra.run.BookRun;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Book implements IBook {

    private int bookId;
    private String bookName;
    private float importPrice;
    private float exportPrice;
    private String author;
    private Date created;
    private String descriptions;

    public Book() {
    }

    public Book(int bookId, String bookName, float importPrice, float exportPrice, String author, Date created, String descriptions) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.author = author;
        this.created = created;
        this.descriptions = descriptions;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(float importPrice) {
        this.importPrice = importPrice;
    }

    public float getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(float exportPrice) {
        this.exportPrice = exportPrice;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public void inputData(Scanner scanner) {
        System.out.print("Nhập số sách cần nhập:");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.printf("Sách thứ %d:\n", BookRun.bookList.size() + 1);
            this.bookId = inputBookId(BookRun.bookList);
            this.bookName = inputBookName(BookRun.bookList, scanner);
            this.importPrice = inputImportPrice(scanner);
            this.exportPrice = inputExportPrice(scanner);
            this.author = inputAuthor(scanner);
            this.created = inputCreated(scanner);
            this.descriptions = inputDescriptions(scanner);
            Book book = new Book(this.bookId, this.bookName, this.importPrice, this.exportPrice, this.author, this.created, this.descriptions);
            BookRun.bookList.add(book);
        }
    }

    @Override
    public void displayData() {
        BookRun.bookList.forEach(System.out::println);
    }

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Mã sách: " + this.bookId + " - Tên sách: " + this.bookName + " - Giá nhập: " + this.importPrice + " - Giá xuất: " + this.exportPrice + "\n" +
                "Tác giả: " + this.author + " - Ngày nhập:" + sdf.format(this.created) + " - Mô tả:" + this.descriptions;
    }

    public int inputBookId(List<Book> bookList) {
        if (bookList.isEmpty()) {
            return 1;
        } else {
            int max = bookList.get(0).getBookId();
            for (int i = 0; i < bookList.size(); i++) {
                if (max < bookList.get(i).getBookId()) {
                    max = bookList.get(i).getBookId();
                }
            }
            return max + 1;
        }
    }

    public String inputBookName(List<Book> bookList, Scanner scanner) {
        System.out.println("Nhập tên sách (duy nhất, gồm 4 ký tự, bắt đầu là B):");
        do {
            this.bookName = lenghthString(3, 4, scanner);
            if (this.bookName.charAt(0) == 'B') {
                boolean isDuplication = false;
                for (int i = 0; i < bookList.size(); i++) {
                    if (this.bookName.equals(bookList.get(i).getBookName())) {
                        isDuplication = true;
                        break;
                    }
                }
                if (!isDuplication) {
                    return this.bookName;
                } else {
                    System.out.println("Tên sách bị trùng! Mời nhập lại");
                }
            } else {
                System.out.println("Ký tự đầu tiên của sách bắt đầu bằng ký tự 'B'");
            }
        } while (true);
    }

    public float inputImportPrice(Scanner scanner) {
        System.out.println("Nhập giá nhập (có giá trị lớn hơn 0):");
        this.importPrice = validateFloat(scanner, 0);
        return this.importPrice;
    }

    public float inputExportPrice(Scanner scanner) {
        System.out.println("Nhập giá xuất (có giá trị lớn hơn giá nhập):");
        this.exportPrice = validateFloat(scanner, this.importPrice);
        return this.importPrice;
    }

    public String inputAuthor(Scanner scanner) {
        System.out.println("Nhập tác giả (có từ 6-50 ký tự):");
        this.author = lenghthString(6, 50, scanner);
        return this.author;
    }

    public Date inputCreated(Scanner scanner) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Nhập ngày nhập sách:");
        do {
            try {
                Date date = sdf.parse(scanner.nextLine());
                return date;
            } catch (ParseException e) {
                System.err.println("Định dạng ngày dd/MM/yyyy, vui lòng nhập lại");
            }
        } while (true);
    }

    public String inputDescriptions(Scanner scanner) {
        System.out.println("Nhập Mô tả sách:");
        this.descriptions = lenghthString(0, 500, scanner);
        return this.descriptions;
    }

    public int validateInteger(Scanner scanner) {
        do {
            try {
                int number = Integer.parseInt(scanner.nextLine());
                if (number > 0) {
                    return number;
                } else {
                    System.err.println("Giá trị số nguyên > 0 vui lòng nhập lại");
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Vui lòng nhập số nguyên");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

        } while (true);
    }

    public float validateFloat(Scanner scanner, float n) {
        do {
            try {
                float number = Float.parseFloat(scanner.nextLine());
                if (number > n) {
                    return number;
                } else {
                    System.err.printf("Giá trị số nguyên > %.0f vui lòng nhập lại!\n", n);
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Vui lòng nhập số thực");
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (true);
    }

    public String lenghthString(int a, int b, Scanner scanner) {
        do {
            try {
                String s = scanner.nextLine();
                if (s.length() > a && s.length() <= b) {
                    return s;
                } else {
                    System.err.println("Nhập đúng số lượng ký tự!");
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } while (true);
    }

    public int checkIdBook(List<Book> bookList, Scanner scanner) {
        boolean isExit = false;
        do {
            System.out.print("Nhập mã sách:");
            int id = validateInteger(scanner);
            for (int i = 0; i < bookList.size(); i++) {
                if (id == bookList.get(i).getBookId()) {
                    return i;
                }
            }
            if (!isExit) {
                System.out.println("Mã sách không tồn tại! Mời nhập lại!");
            }
        } while (true);
    }

    public void updateInforBook(Scanner scanner, List<Book> bookList) {
        System.out.println("Cập nhật thông tin sách theo mã sách");
        int updateIndexBook = checkIdBook(bookList, scanner);
        do {
            System.out.println("*** MENU UPDATE ***");
            System.out.println("1. Tên sách\n" +
                    "2. Giá sách nhập\n" +
                    "3. Giá sách xuất\n" +
                    "4. Tác giả\n" +
                    "5. Ngày nhập sách\n" +
                    "6. Mô tả\n" +
                    "7. Thoát");
            System.out.println("Nhập lựa chọn:");
            int choice = validateInteger(scanner);
            switch (choice) {
                case 1:
                    bookList.get(updateIndexBook).setBookName(inputBookName(bookList, scanner));
                    break;
                case 2:
                    bookList.get(updateIndexBook).setImportPrice(inputImportPrice(scanner));
                    break;
                case 3:
                    bookList.get(updateIndexBook).setExportPrice(inputExportPrice(scanner));
                    break;
                case 4:
                    bookList.get(updateIndexBook).setAuthor(inputAuthor(scanner));
                    break;
                case 5:
                    bookList.get(updateIndexBook).setCreated(inputCreated(scanner));
                    break;
                case 6:
                    bookList.get(updateIndexBook).setDescriptions(inputDescriptions(scanner));
                    break;
                case 7:
                    BookRun.menuBook(scanner);
                    break;
                default:
                    System.out.println("Mời nhập từ 1 đến 7!");
                    break;
            }
        } while (true);
    }

    public void deleteBook(List<Book> bookList, Scanner scanner) {
        System.out.println("Xóa sách theo mã sách");
        int indexDelete = checkIdBook(bookList, scanner);
        bookList.remove(indexDelete);
        System.out.println("Xóa thành công! Ấn 2 để kiểm tra.");
    }

    public void sortBook(List<Book> bookList) {
        System.out.println("Sắp xếp sách theo giá bán tăng dần");
        bookList.sort((o1, o2) -> (int) (o1.getExportPrice() - o2.getExportPrice()));
        System.out.println("Sắp xếp thành công! Ấn 2 để kiểm tra");
    }

    public void statisticBook(List<Book> bookList, Scanner scanner) {
        System.out.println("Thống kê sách theo khoảng giá (a-b nhập từ bàn phím)");
        System.out.println("Nhập a:");
        int a = validateInteger(scanner);
        System.out.println("Nhập b:");
        int b = validateInteger(scanner);
        int count = 0;
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getExportPrice() >= a && bookList.get(i).getExportPrice() <= b) {
                System.out.println(bookList.get(i).toString());
                count++;
            }
        }
        System.out.println("Tổng số sách trong khoảng trên: " + count);
    }

    public void lookForBook(List<Book> bookList, Scanner scanner) {
        System.out.println("Tìm kiếm sách theo tên tác giả");
        System.out.println("Nhập tên tác giả:");
        String lfb = scanner.nextLine();
        boolean isDuplicate = false;
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).author.contains(lfb)) {
                isDuplicate = true;
                System.out.println(bookList.get(i).toString());
            }
        }
        if (!isDuplicate) {
            System.out.println("Không tìm thấy sách theo tên tác giả trên!");
        }
    }
}
