package Aufgabe6.Positions.Figures;

import Aufgabe6.Positions.Figure;

import java.util.ArrayList;

public class DominoFigure extends Figure {
  public ArrayList<boolean[][]> figure = new ArrayList<>();


  public DominoFigure(){
    size=2;
    figure.add(new boolean[][]{{true}, {true}});
    figure.add(new boolean[][]{{true, true}});
  }
  @Override
  public boolean[][] rotate(){
    return figure.get(position++);
  }

  @Override
  public String toString() {
    return "Domino";
  }
}