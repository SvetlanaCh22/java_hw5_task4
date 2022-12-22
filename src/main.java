// Реализовать алгоритм перевода из инфиксной записи в постфиксную для арифметического выражения.
// Вычислить запись если это возможно.

// Чубченко Светлана

import java.util.LinkedList;
import java.util.List;

public class main {

    public static void main(String[] args) {
        int n = 0;
        // создаем список решений
        List<QueensTestBoard> solutions = new QueensTest(8).getAllSolutions();
        // выводим все имеющиеся варианты размещения 8 ферзей
        // чтобы они не били друг друга
        for(QueensTestBoard board : solutions) {
            System.out.println("Вариация "+ (++n) + "\n" + board.toString());
        }
    }

}

// класс поиска решений
class QueensTest {

    private int size;

    private List<QueensTestBoard> solutions;

    // конструктор - инициализация значений
    public QueensTest(int size) {
        this.size = size;
        this.solutions = new LinkedList<QueensTestBoard>();
        solve(size);
    }

    // поиск решений учитывая размеры доски
    public void solve(int size) {
        QueensTestBoard board = new QueensTestBoard(size);
        QueensLogicalBoard queensLogicalBoard = new QueensLogicalBoard(size);
        solve(board, queensLogicalBoard, 0, size);
    }

    // возвращаем найденные решения
    public List<QueensTestBoard> getAllSolutions() {
        return solutions;
    }

    // рекурсивный поиск решений
    private void solve(QueensTestBoard board, QueensLogicalBoard queensLogicalBoard, int rowNumber, int size) {
        if (rowNumber == size) {
            solutions.add(board);
            return;
        }
        for (int column = 0; column < size; column++) {
            if (queensLogicalBoard.getPossible(rowNumber, column)) {
                QueensTestBoard newBoard = new QueensTestBoard(board);
                newBoard.setHasQueen(rowNumber, column, true);
                QueensLogicalBoard newQueensLogicalBoard = new QueensLogicalBoard(queensLogicalBoard);
                newQueensLogicalBoard.setPossible(rowNumber, column, false);
                updateBoard(rowNumber, column, newQueensLogicalBoard, size);
                solve(newBoard, newQueensLogicalBoard, rowNumber + 1, size);
            }
        }
    }

    private void updateBoard(int row, int col, QueensLogicalBoard queensLogicalBoard, int size) {
        // обновляем строку
        for (int j = 0; j < size; j++) {
            queensLogicalBoard.setPossible(row, j, false);
        }
        // обновляем столбец
        for (int j = 0; j < size; j++) {
            queensLogicalBoard.setPossible(j, col, false);
        }
        // обновляем диагонали
        int r = row;
        int c = col;
        while (r >= 0 && c >= 0) {
            queensLogicalBoard.setPossible(r, c, false);
            r--;
            c--;
        }
        r = row;
        c = col;
        while (r < 8 && c < 8) {
            queensLogicalBoard.setPossible(r, c, false);
            r++;
            c++;
        }
        r = row;
        c = col;
        while (r < 8 && c >= 0) {
            queensLogicalBoard.setPossible(r, c, false);
            r++;
            c--;
        }
        r = row;
        c = col;
        while (r >= 0 && c < 8) {
            queensLogicalBoard.setPossible(r, c, false);
            r--;
            c++;
        }
    }
}

    // класс доски с ферзями
    class QueensTestBoard {

        private boolean[][] board;
        int size;
    
        // конструкторы - инициализация значений
        public QueensTestBoard(int size) {
            this.size = size;
            this.board = new boolean[size][size];
        }
    
        public QueensTestBoard(QueensTestBoard oldBoard) {
            this.size = oldBoard.size;
            this.board = new boolean[size][size];
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    this.board[i][j] = oldBoard.board[i][j];
                }
            }
        }
    
        // установка что по указанным координатам стоит ферзь
        public void setHasQueen(int x, int y, boolean value) {
            board[x][y] = value;
        }
    
        // проверка что по указанным координатам стоит ферзь
        public boolean hasQueen(int x, int y) {
            return board[x][y];
        }
    
        // вывод доски в виде строки
        @Override
        public String toString() {
            StringBuilder result = new StringBuilder();
            result.append("------------------------------\n");
            for (int i = 0; i < size; i++) {
                result.append("|");
                for (int j = 0; j < size; j++) {
                    result.append((board[i][j] ? "Q" : " ") + "|");
                }
                result.append("\n");
            }
            result.append("------------------------------");
            return result.toString();
        }
    
    }
    
    // класс эксперимента доски с ферзями
    class QueensLogicalBoard {
    
        private boolean[][] board;
        int size;
    
        // конструкторы - инициализация значений
        public QueensLogicalBoard(int size) {
            this.size = size;
            board = new boolean[size][size];
            for(int i = 0; i < size; i++) {
                for(int j = 0; j < size; j++) {
                    this.board[i][j] = true;
                }
            }
        }
    
        public QueensLogicalBoard(QueensLogicalBoard oldBoard) {
            this.size = oldBoard.size;
            this.board = new boolean[size][size];
            for(int i = 0; i < size; i++) {
                System.arraycopy(oldBoard.board[i], 0, this.board[i], 0, size);
            }
        }
    
        // установить возможность ферзя по координатам
        public void setPossible(int x, int y, boolean value) {
            board[x][y] = value;
        }
    
        // считать есть ли ферзь по данным координатам
        public boolean getPossible(int x, int y) {
            return board[x][y];
        }
    
    }

