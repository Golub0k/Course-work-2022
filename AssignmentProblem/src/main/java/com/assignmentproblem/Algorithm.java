package com.assignmentproblem;


import java.util.ArrayList;
import java.util.Collections;

public class Algorithm {
    public static /*ObservableList<ObservableList<Integer>>*/ int solveProblem(ArrayList<Agent> agents) {
        int agentsNumber = agents.size();
        int tasksNumber = agents.get(0).getTaskCost().size();
        // массив паросочетания, индекс -- столбец, значение -- строка
        ArrayList<Integer> matching = new ArrayList<>(Collections.nCopies(tasksNumber + 1, -1));
        // потенциалы строк и столбцов
        ArrayList<Integer> rowPotential = new ArrayList<>(Collections.nCopies(agentsNumber, 0));
        ArrayList<Integer> columnPotential = new ArrayList<>(Collections.nCopies(tasksNumber + 1, 0));
        // список для восстановления пути, индекс -- столбец, значение -- предшествующий столбец
        ArrayList<Integer> way = new ArrayList<>(Collections.nCopies(tasksNumber, -1));

        for (int currentRow = 0; currentRow < agentsNumber; ++currentRow) {
            // вспомогательные минимумы
            ArrayList<Integer> columnMinimum = new ArrayList<>(Collections.nCopies(tasksNumber, Integer.MAX_VALUE));
            ArrayList<Boolean> visitedColumn = new ArrayList<>(Collections.nCopies(tasksNumber + 1, false));
            matching.set(tasksNumber, currentRow);
            int currentColumn = tasksNumber; // фиктивный столбец (из него начинает работу алгоритм)
            do {
                visitedColumn.set(currentColumn, true);
                int foundRow = matching.get(currentColumn);
                int delta = Integer.MAX_VALUE;
                int newColumn = -1;
                for (int column = 0; column < tasksNumber; ++column) {
                    // пытаемся найти лучшее (минимальное) новое ребро из посещённой строки в непосещённые столбцы
                    if (!visitedColumn.get(column)) {
                        int newValue = DataMatrix.getMatrix().get(foundRow).getTaskCost().get(column) - rowPotential.get(foundRow) - columnPotential.get(column);
                        if (newValue < columnMinimum.get(column)) {
                            columnMinimum.set(column, newValue);
                            way.set(column, currentColumn);
                        }
                        if (columnMinimum.get(column) < delta) {
                            delta = columnMinimum.get(column);
                            newColumn = column;
                        }
                    }
                }
                // пересчитываем потенциал
                for (int column = 0; column <= tasksNumber; ++column) {
                    if (visitedColumn.get(column)) {
                        columnPotential.set(column, columnPotential.get(column) - delta);
                        rowPotential.set(matching.get(column), rowPotential.get(matching.get(column)) - delta);
                    } else {
                        columnMinimum.set(column, columnMinimum.get(column) - delta);
                    }
                }
                currentColumn = newColumn;
            } while (matching.get(currentColumn) != -1);
            // чередуем рёбра вдоль найденной увеличивающей цепи
            do {
                int previousColumn = way.get(currentColumn);
                matching.set(currentColumn, matching.get(previousColumn));
                currentColumn = previousColumn;
            } while (currentColumn != tasksNumber);
        }
        //TODO
        // взаимодействие с контроллером
        // поиск багов
        return -columnPotential.get(tasksNumber);
    }
}