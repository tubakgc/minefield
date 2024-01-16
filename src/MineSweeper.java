import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private char[][] gameBoard;
    private char[][] mineBoard;
    private int rows;
    private int cols;
    private int totalMines;
    private int revealedCells;

    public MineSweeper(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.totalMines = rows * cols / 4;
        this.gameBoard = new char[rows][cols];
        this.mineBoard = new char[rows][cols];
        this.revealedCells = 0;
        initializeGameBoard();
        initializeMineBoard();
    }

    private void initializeGameBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gameBoard[i][j] = '-';
            }
        }
    }

    private void initializeMineBoard() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < totalMines) {
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (mineBoard[row][col] != '*') {
                mineBoard[row][col] = '*';
                minesPlaced++;
            }
        }
    }

    private void printBoard(char[][] board) {
        System.out.println("Mayın Tarlası Oyuna Hoşgeldiniz !");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("===========================");
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && gameBoard[row][col] == '-';
    }

    private void revealCell(int row, int col) {
        if (!isValidMove(row, col)) {
            System.out.println("Geçersiz koordinatlar. Lütfen geçerli bir koordinat girin.");
            return;
        }

        if (mineBoard[row][col] == '*') {
            System.out.println("Game Over! Mayına bastınız.");
            printBoard(mineBoard);
            System.exit(0);
        } else {
            int minesAround = countMinesAround(row, col);
            gameBoard[row][col] = (char) (minesAround + '0');
            revealedCells++;

            if (revealedCells == rows * cols - totalMines) {
                System.out.println("Tebrikler! Oyunu kazandınız!");
                printBoard(gameBoard);
                System.exit(0);
            }

            if (minesAround == 0) {
                revealNeighbors(row, col);
            }
        }
    }

    private void revealNeighbors(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isValidMove(i, j)) {
                    revealCell(i, j);
                }
            }
        }
    }

    private int countMinesAround(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < rows && j >= 0 && j < cols && mineBoard[i][j] == '*') {
                    count++;
                }
            }
        }
        return count;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard(gameBoard);

            System.out.print("Satır Giriniz: ");
            int row = scanner.nextInt();

            System.out.print("Sütun Giriniz: ");
            int col = scanner.nextInt();

            revealCell(row, col);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Satır Sayısını Giriniz: ");
        int rows = scanner.nextInt();

        System.out.print("Sütun Sayısını Giriniz: ");
        int cols = scanner.nextInt();

        while (rows < 2 || cols < 2) {
            System.out.println("Satır ve sütun sayıları minimum 2 olmalıdır. Tekrar giriniz.");
            System.out.print("Satır Sayısını Giriniz: ");
            rows = scanner.nextInt();

            System.out.print("Sütun Sayısını Giriniz: ");
            cols = scanner.nextInt();
        }

        MineSweeper mineSweeper = new MineSweeper(rows, cols);
        mineSweeper.play();
    }
}