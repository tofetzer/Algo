package Aufgabe7;

class Perm extends Thread {
  private int[] a; // a Arbeitsarray
  private int max; // maximaler Index
  private boolean mayread = false; // Kontrolle

  Perm(int n) { // Konstruktor
    a = new int[n]; // Indices: 0 .. n-1
    max = n - 1; // Maximaler Index
    for (int i = 0; i <= max; i++) a[i] = i+1; // a fuellen mit zahlen von 1-n
    this.start(); // run-Methode beginnt zu laufen
  } // end Konstruktor

  public void run() {// die Arbeits-Methode
    perm(0);
    a = null; // Anzeige, dass fertig
    put(); // ausliefern
  }

  private void perm(int i) { // permutiere ab Index i
    if (i >= max) put(); // eine Permutation fertig
    else {
      for (int j = i; j <= max; j++) { // jedes nach Vorne
        swap(i, j); // vertauschen
        perm(i + 1); // und Rekursion
      }
      int h = a[i]; // restauriere Array
      System.arraycopy(a, i + 1, a, i, max - i); // shift links
      a[max] = h;
    }
  } // end perm

  private void swap(int i, int j) { // tausche a[i] <-> a[j]
    if (i != j) {
      int h = a[i];
      a[i] = a[j];
      a[j] = h;
    }
  }

  synchronized int[] getNext() { // liefert naechste Permutation
    mayread = false; // a darf geaendert werden
    notify(); // wecke anderen Thread
    try {
      while (!mayread) wait(); // non busy waiting
    } catch (InterruptedException e) {
    }
    return a; // liefere Permutationsarray
  } // end getNext

  private synchronized void put() { // uebergebe array
    mayread = true; // a wird gelesen
    notify(); // wecke anderen Thread
    try {
      if (a != null)
        while (mayread) wait(); // non busy waiting
    } catch (InterruptedException e) {
    }
  }
}