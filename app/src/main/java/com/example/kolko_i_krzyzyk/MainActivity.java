package com.example.kolko_i_krzyzyk;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button bu1, bu2, bu3, bu4, bu5, bu6, bu7, bu8, bu9;
    private Button reset;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView output = findViewById(R.id.text_view_status);

        // przypisanie afterClick do przycisków
        bu1 = findViewById(R.id.bu1);
        bu1.setOnClickListener(view -> afterClick(0));
        bu2 = findViewById(R.id.bu2);
        bu2.setOnClickListener(view -> afterClick(1));
        bu3 = findViewById(R.id.bu3);
        bu3.setOnClickListener(view -> afterClick(2));
        bu4 = findViewById(R.id.bu4);
        bu4.setOnClickListener(view -> afterClick(3));
        bu5 = findViewById(R.id.bu5);
        bu5.setOnClickListener(view -> afterClick(4));
        bu6 = findViewById(R.id.bu6);
        bu6.setOnClickListener(view -> afterClick(5));
        bu7 = findViewById(R.id.bu7);
        bu7.setOnClickListener(view -> afterClick(6));
        bu8 = findViewById(R.id.bu8);
        bu8.setOnClickListener(view -> afterClick(7));
        bu9 = findViewById(R.id.bu9);
        bu9.setOnClickListener(view -> afterClick(8));

        reset = findViewById(R.id.button_reset);
        reset.setOnClickListener(view -> {
            reset.setText("RESET");

            for(int i = 0; i < 9; i++) {
                fields[i] = " ";
            }

            View H1 = findViewById(R.id.lineH1);
            View H2 = findViewById(R.id.lineH2);
            View H3 = findViewById(R.id.lineH3);
            View V1 = findViewById(R.id.lineV1);
            View V2 = findViewById(R.id.lineV2);
            View V3 = findViewById(R.id.lineV3);
            View X1 = findViewById(R.id.lineX1);
            View X2 = findViewById(R.id.lineX2);

            H1.setVisibility(View.INVISIBLE);
            H2.setVisibility(View.INVISIBLE);
            H3.setVisibility(View.INVISIBLE);
            V1.setVisibility(View.INVISIBLE);
            V2.setVisibility(View.INVISIBLE);
            V3.setVisibility(View.INVISIBLE);
            X1.setVisibility(View.INVISIBLE);
            X2.setVisibility(View.INVISIBLE);

            showBoard();

            Random rand = new Random();
            int firstMove = rand.nextInt(2);

            if (firstMove == 0) {
                output.setText("\nJesteś krzyżykiem\nZaczynasz\n");

            } else {
                output.setText("\nJesteś krzyżykiem\nNie zaczynasz\n");
                opponentMove();
            }

            showBoard();
        }); // restart.onClick
    } // onCrate
    
    private final String[] fields = new String[9];

    // przypisanie wartości pól do przycisków
    private void showBoard() {
        bu1.setText(fields[0]);
        bu2.setText(fields[1]);
        bu3.setText(fields[2]);
        bu4.setText(fields[3]);
        bu5.setText(fields[4]);
        bu6.setText(fields[5]);
        bu7.setText(fields[6]);
        bu8.setText(fields[7]);
        bu9.setText(fields[8]);
    } // showBoard

    @SuppressLint("ResourceAsColor")
    // akcja po naciśnięciu przycisku
    private void afterClick(int n) {
        TextView output = findViewById(R.id.text_view_status);

        if (isFree(n) && gameStatus() == 0) {
            fields[n] = "X"; // Postawienie X
            
            showBoard();

            // Wywołanie ruchu komputera
            if (gameStatus() == 0) {
                opponentMove();
            }

            // Rezultat gry
            if (gameStatus() == 1) {
                output.setText("WYGRAŁEŚ!");
                createLine(gameStatus());
            } else if (gameStatus() == 2) {
                output.setText("PRZEGRAŁEŚ!");
                createLine(gameStatus());
            } else if (gameStatus() == 3) {
                output.setText("REMIS!");
            } // if / rezultat
        } else if(!isFree(n)) {
            Toast.makeText(this, "To pole jest już zajęte", Toast.LENGTH_SHORT).show();
        } // if / całość
    } // afterClick

    @SuppressLint("ResourceAsColor")
    // pokazanie linii
    private void createLine(int T) {
        View H1 = findViewById(R.id.lineH1);
        View H2 = findViewById(R.id.lineH2);
        View H3 = findViewById(R.id.lineH3);
        View V1 = findViewById(R.id.lineV1);
        View V2 = findViewById(R.id.lineV2);
        View V3 = findViewById(R.id.lineV3);
        View X1 = findViewById(R.id.lineX1);
        View X2 = findViewById(R.id.lineX2);

        String symbol = "";

        if (T == 1) { symbol = "X"; }
        if (T == 2) { symbol = "O"; }

        if (fields[0] == symbol && fields[1] == symbol && fields[2] == symbol) {
            H1.setVisibility(View.VISIBLE);
        } else if (fields[3] == symbol && fields[4] == symbol && fields[5] == symbol) {
            H2.setVisibility(View.VISIBLE);
        }else if (fields[6] == symbol && fields[7] == symbol && fields[8] == symbol) {
            H3.setVisibility(View.VISIBLE);
        } else if (fields[0] == symbol && fields[3] == symbol && fields[6] == symbol) {
            V1.setVisibility(View.VISIBLE);
        } else if (fields[1] == symbol && fields[4] == symbol && fields[7] == symbol) {
            V2.setVisibility(View.VISIBLE);
        } else if (fields[2] == symbol && fields[5] == symbol && fields[8] == symbol) {
            V3.setVisibility(View.VISIBLE);
        } else if (fields[0] == symbol && fields[4] == symbol && fields[8] == symbol) {
            X1.setVisibility(View.VISIBLE);
        } else if (fields[2] == symbol && fields[4] == symbol && fields[6] == symbol) {
            X2.setVisibility(View.VISIBLE);
        }
    } // createLine

    // sprawdza zwycięstwo
    private boolean win(String symbol) {
        return  (fields[0] == symbol && fields[1] == symbol && fields[2] == symbol) ||
                (fields[3] == symbol && fields[4] == symbol && fields[5] == symbol) ||
                (fields[6] == symbol && fields[7] == symbol && fields[8] == symbol) ||
                (fields[0] == symbol && fields[3] == symbol && fields[6] == symbol) ||
                (fields[1] == symbol && fields[4] == symbol && fields[7] == symbol) ||
                (fields[2] == symbol && fields[5] == symbol && fields[8] == symbol) ||
                (fields[0] == symbol && fields[4] == symbol && fields[8] == symbol) ||
                (fields[2] == symbol && fields[4] == symbol && fields[6] == symbol);
    } // win

    // określenie statusu gry
    private int gameStatus() {
        // 0 - gra trwa dalej | 1 - gracz wygrywa | 2 - komputer wygrywa | 3 - remis
        if (win("X")) {
            return 1;
        } else if (win("O")) {
            return 2;
        } else if (freeFields() == 0) {
            return 3;
        } else {
            return 0;
        }
    } // gameStatus

     /*--------------------------
    FUNKCJE PRZECIWNIKA
    --------------------------*/

    // ruch komputera
    private void opponentMove() {
        System.out.println("\nRUCH PRZECIWNIKA");

        // kończenie tury
        if ((finnish(1, 2) || finnish(3, 6)
                || finnish(4, 8)) && isFree(0)) {
            setO(0);
        } else if ((finnish(0, 2) || finnish(4, 7)) && isFree(1)) {
            setO(1);
        } else if ((finnish(0,1) || finnish(5,8)
                || finnish(6,4)) && isFree(2)) {
            setO(2);
        } else if ((finnish(0,6) || finnish(4,5)) && isFree(3)) {
            setO(3);
        } else if ((finnish(0, 8) || finnish(1, 7) || finnish(2, 6)
                || finnish(3, 5)) && isFree(4)) {
            setO(4);
        } else if ((finnish(3,4) || finnish(2,8)) && isFree(5)) {
            setO(5);
        } else if ((finnish(0,3) || finnish(2,4)
                || finnish(7,8)) && isFree(6)) {
            setO(6);
        } else if ((finnish(1,4) || finnish(6,8)) && isFree(7)) {
            setO(7);
        } else if ((finnish(0,4) || finnish(6,7)
                || finnish(2,5)) && isFree(8)) {
            setO(8);
        }

        // blokowanie gracza
        else if ((block(1, 2) || block(3, 6) || block(4, 8)) && isFree(0)) {
            setO(0);
        } else if ((block(0, 2) || block(4, 7)) && isFree(1)) {
            setO(1);
        } else if ((block(0,1) || block(5,8) || block(6,4)) && isFree(2)) {
            setO(2);
        } else if ((block(0,6) || block(4,5)) && isFree(3)) {
            setO(3);
        } else if ((block(0, 8) || block(1, 7) || block(2, 6)
                || block(3, 5)) && isFree(4)) {
            setO(4);
        } else if ((block(3,4) || block(2,8)) && isFree(5)) {
            setO(5);
        } else if ((block(0,3) || block(2,4) || block(7,8)) && isFree(6)) {
            setO(6);
        } else if ((block(1,4) || block(6,8)) && isFree(7)) {
            setO(7);
        } else if ((block(0,4) || block(6,7) || block(2,5)) && isFree(8)) {
            setO(8);
        }

        // funkcje specjalne
        else if(freeFields() == 7 && !isFree(4)) {
            setO(or2(cornerHorseMoveR(), cornerHorseMoveL())); // do problemu 4 i 5
        } else if(freeFields() == 8 && freeCorners() == 3) {
            setO(4);
        } else if(freeFields() == 6 && freeCorners() == 2 && !isFree(4)
                && Objects.equals(fields[4], "O")) {
            attackSide();
        } else if((freeFields() == 5) && freeCorners() == 2 && freeSides() == 2) {
            setO(or2(cornerOrMiddleTemp(), 4));  // problem 1
        } else if ((freeFields() == 7) && freeSides() == 3 && cornerOrMiddleTemp() != -1) {
            setO(or2(cornerOrMiddleTemp(), 4)); // problem 2
        } else if ((freeFields() == 7) && freeSides() == 3 && cornerOrMiddleTemp() == -1) {
            setO(or2(rookMoveR(), rookMoveL())); // problem 3
        } else if (freeFields() == 3 && freeCorners() == 3) {
            setO(oppositeCorner()); // problem 4
        }else if (freeFields() == 5 && freeCorners() == 3
                && freeSides() == 2 && !isFree(cornerHorseMoveR())) {
            setO(rookMoveR()); // problem 5
        } else if (freeFields() == 5 && freeCorners() == 3
                && freeSides() == 2 && !isFree(cornerHorseMoveL())) {
            setO(rookMoveL()); // problem 5
        } else if (freeFields() == 6 && freeCorners() == 3 && freeSides() == 3
                && Objects.equals(fields[4], "O") && !isFree(cornerHorseMoveR())){
            setO(or2(oppositeCorner(), rookMoveR())); // problem 6
        } else if (freeFields() == 6 && freeCorners() == 3 && freeSides() == 3
                && Objects.equals(fields[4], "O") && !isFree(cornerHorseMoveL())){
            setO(or2(oppositeCorner(), rookMoveL())); // problem 6
        } else if (freeFields() == 8 && freeSides() == 3) {
            setO(closeCornerL()); // problem 7
        } else if (freeFields() == 6 && freeSides() == 3 && !isFree(closeCornerR())
                && !isFree(closeCornerL()) && Objects.equals(fields[closeCornerL()], "0")) {
            setO(ORookMoveL()); // problem 8
        } else if (freeFields() == 6 && freeSides() == 3 && !isFree(closeCornerR())
                && !isFree(closeCornerL()) && Objects.equals(fields[closeCornerR()], "0")) {
            setO(ORookMoveR()); // problem 8
        } else if (freeFields() == 6 && freeSides() == 2 && freeCorners() == 3
                && isFree(cornerHorseMoveR()) && isFree(cornerHorseMoveL())) {
            setO(or2(cornerHorseMoveL(), cornerHorseMoveR())); // problem 9
        } else if (freeFields() == 6 && freeSides() == 3 && freeCorners() == 2
                && Objects.equals(fields[closeCornerL()], "O")
                && Objects.equals(fields[sideHorseMoveL()], "X")) {
            setO(closeCornerR()); // problem 10
        } else if (freeFields() == 6 && freeSides() == 3 && freeCorners() == 2
                && Objects.equals(fields[closeCornerR()], "O")
                && Objects.equals(fields[sideHorseMoveR()], "X")) {
            setO(closeCornerL()); // problem 10
        } else if (freeFields() == 6 && freeSides() == 3 && freeCorners() == 2
                && Objects.equals(fields[closeCornerL()], "O")
                && Objects.equals(fields[sideHorseMoveR()], "X")) {
            setO(4); // problem 11
        } else if (freeFields() == 6 && freeSides() == 3 && freeCorners() == 2
                && Objects.equals(fields[closeCornerR()], "O")
                && Objects.equals(fields[sideHorseMoveL()], "X")) {
            setO(4); // problem 11
        } else if (freeFields() == 6 && freeCorners() == 3 && freeSides() == 2
                && Objects.equals(fields[pawnMoveR()], "X")
                && Objects.equals(fields[cornerHorseMoveR()], "X")) {
            setO(cornerHorseMoveL()); // problem 12
        } else if (freeFields() == 6 && freeCorners() == 3 && freeSides() == 2
                && Objects.equals(fields[pawnMoveL()], "X")
                && Objects.equals(fields[cornerHorseMoveL()], "X")) {
            setO(cornerHorseMoveR()); // problem 12
        }

        // funkcje naprawcze
        else if (freeFields() == 6 && freeSides() == 3
                && freeCorners() == 2 && !isFree(closeCornerR())) {
            setO(sideHorseMoveL());
        } else if (xSides() == 2 && isFree(4)) {
            setO(4);
        }

        // funkcje domyślne
        else if(isAnyCornerFree()) {
            attackCorner();
        } else if (isFree(4)) {
            setO(4);
        } else if (isAnySideFree()) {
            attackSide();
        }
        showBoard();
    } // opponentMove

    // ustawienie O na danym polu
    private void setO(int n) {
        TextView output = findViewById(R.id.text_view_status);
        if (isFree(n)) {
            fields[n] = "O";
        } else {
            output.setText("BŁĄD!!!\nProgram chce postawić O na zajętym polu");
        } // if isFree
    } // setO

    // sprawdzenie czy pole jest dostępne
    private boolean isFree(int n) {
        return Objects.equals(fields[n], " ");
    } // isFree

    // sprawdzenie czy jest dostępny jakikolwiek róg
    private boolean isAnyCornerFree() {
        return (isFree(0) ||
                isFree(2) ||
                isFree(6) ||
                isFree(8));
    } // isAnyCornerFree

    // sprawdzenie czy jest dostępny jakikolwiek bok
    private boolean isAnySideFree() {
        return (isFree(1) ||
                isFree(3) ||
                isFree(5) ||
                isFree(7));
    } // isAnySideFree

    private int xSides() {
        int sum = 0;

        if (Objects.equals(fields[1], "X")) { sum++; }
        if (Objects.equals(fields[3], "X")) { sum++; }
        if (Objects.equals(fields[5], "X")) { sum++; }
        if (Objects.equals(fields[7], "X")) { sum++; }

        return sum;
    } // xSides

    // liczba wolnych pól
    private int freeFields() {
        int freeFields = 0;
        for (int i = 0; i < 9; i++) {
            if (isFree(i)) { freeFields++; }
        }
        return freeFields;
    } // freeFields

    // liczba wolnych rogów
    private int freeCorners() {
        int sum = 0;

        if (isFree(0)) { sum++; }
        if (isFree(2)) { sum++; }
        if (isFree(6)) { sum++; }
        if (isFree(8)) { sum++; }

        return sum;
    } // freeCorners

    //liczzba wonych boków
    private int freeSides() {
        int sum = 0;

        if (isFree(1)) { sum++; }
        if (isFree(3)) { sum++; }
        if (isFree(5)) { sum++; }
        if (isFree(7)) { sum++; }

        return sum;
    } // freeSides

    // róg po prawej stronie od zajętego boku
    private int closeCornerR() {
        if (!isFree(1)) { return 0;} else
        if (!isFree(3)) { return 6;} else
        if (!isFree(5)) { return 2;} else
        if (!isFree(7)) { return 8;} else
            return -1;
    } // closeCornerR

    // róg po lewej stronie od zajętego boku
    private int closeCornerL() {
        if (!isFree(1)) { return 2;} else
        if (!isFree(3)) { return 0;} else
        if (!isFree(5)) { return 8;} else
        if (!isFree(7)) { return 6;} else
            return -1;
    } // closeCornerL

    // bok po prawej stronie zajętego rogu
    private int pawnMoveR() {
        if (!isFree(0)) { return 3;} else
        if (!isFree(3)) { return 2;} else
        if (!isFree(6)) { return 7;} else
        if (!isFree(8)) { return 5;} else
            return -1;
    } // pawnMoveR

    // bok po lewej stronie zajętego rogu
    private int pawnMoveL() {
        if (!isFree(0)) { return 1;} else
        if (!isFree(3)) { return 5;} else
        if (!isFree(6)) { return 3;} else
        if (!isFree(8)) { return 7;} else
            return -1;
    } // pawnMoveL

    // róg po prawej stronie zajętego rogu
    private int rookMoveR() {
        // tylko kiesdy zajęty jest jeden róg
        if (!isFree(0)) { return 6; } else
        if (!isFree(2)) { return 0; } else
        if (!isFree(6)) { return 8; } else
        if (!isFree(8)) { return 2; } else
        { return -1;}
    } // rookMoveR

    // róg po lewej stronie zajętego rogu
    private int rookMoveL() {
        // tylko kiesdy zajęty jest jeden róg
        if (!isFree(0)) { return 2; } else
        if (!isFree(2)) { return 8; } else
        if (!isFree(6)) { return 0; } else
        if (!isFree(8)) { return 6; } else
        { return -1;}
    } // rookMoveL

    // róg po prawej stronie zajętego przez O rogu
    private int ORookMoveR() {
        // tylko kiesdy zajęty jest jeden róg
        if (fields[0] == "O") { return 6; } else
        if (fields[2] == "O") { return 0; } else
        if (fields[6] == "O") { return 8; } else
        if (fields[8] == "O") { return 2; } else
        { return -1;}
    } // ORookMoveR

    // róg po lewej stronie zajętego przez O rogu
    private int ORookMoveL() {
        // tylko kiesdy zajęty jest jeden róg
        if (fields[0] == "O") { return 2; } else
        if (fields[2] == "O") { return 8; } else
        if (fields[6] == "O") { return 0; } else
        if (fields[8] == "O") { return 6; } else
        { return -1;}
    } // ORookMoveL

    // bok naprzeciwko zajętego rogu po prawej stronie
    private int cornerHorseMoveR() {
        // tylko kiesdy zajęty jest jeden róg
        if (!isFree(0)) { return 7; } else
        if (!isFree(2)) { return 3; } else
        if (!isFree(6)) { return 5; } else
        if (!isFree(8)) { return 1; } else
        { return -1;}
    } // cornerHorseMoveR

    // bok naprzeciwko zajętego rogu po lewej stronie
    private int cornerHorseMoveL() {
        // tylko kiesdy zajęty jest jeden róg
        if (!isFree(0)) { return 5; } else
        if (!isFree(2)) { return 7; } else
        if (!isFree(6)) { return 1; } else
        if (!isFree(8)) { return 3; } else
        { return -1;}
    } // cornerHorseMoveL

    // róg naprzeciwko zajętego boku po prawej stronie
    private int sideHorseMoveR() {
        // tylko kiesdy zajęty jest jeden bok
        if (!isFree(5)) { return 0; } else
        if (!isFree(7)) { return 2; } else
        if (!isFree(1)) { return 6; } else
        if (!isFree(3)) { return 8; } else
        { return -1;}
    } // sideHorseMoveR

    // róg naprzeciwko zajętego boku po lewej stronie
    private int sideHorseMoveL() {
        // tylko kiesdy zajęty jest jeden bok
        if (!isFree(7)) { return 0; } else
        if (!isFree(3)) { return 2; } else
        if (!isFree(5)) { return 6; } else
        if (!isFree(1)) { return 8; } else
        { return -1;}
    } // sideHorseMoveL

    private int cornerOrMiddleTemp() {
        int corner = -1;

        if (checkOX(6, 5) || checkOX(2, 7)) { corner = 0;
        } else if (checkOX(0, 7) || checkOX(8, 3)) { corner = 2;
        } else if (checkOX(2, 3) || checkOX(6, 1)) { corner = 8;
        } else if (checkOX(8, 1) || checkOX(0, 5)) { corner = 6;
        }
        return corner;
    } // cornerOrMiddleTemp

    // losowo wybiera spośród dwóch pól
    private int or2(int a, int b) {
        Random rand = new Random();

        if (rand.nextInt(2) == 0) {
            return a;
        } else { return b; }
    } // or2

    // sprawdzenie dwóch pól
    private boolean checkOX(int O, int X) {
        return (Objects.equals(fields[O], "O") && Objects.equals(fields[X], "X"));
    } // checkOX

    // róg po przeciwnej stronie zajętego rogu
    private int oppositeCorner() {
        // tylko kiesdy zajęty jest jeden róg
        // 0-8 2-6
        if (!isFree(0)) { return 8; } else
        if (!isFree(2)) { return 6; } else
        if (!isFree(6)) { return 2; } else
        if (!isFree(8)) { return 0; } else
        { return -1;}
    } // oppositeCorner

    // blokowanie gracza
    private boolean block(int a, int b) {
        return (Objects.equals(fields[a], "X") && Objects.equals(fields[b], "X"));
    } // block

    // kończenie linii
    private boolean finnish(int a, int b) {
        return (Objects.equals(fields[a], "O") && Objects.equals(fields[b], "O"));
    } // finnish

    // postawienie symbolu w losowym rogu planszy
    private void attackCorner() {
        Random rand = new Random();
        for (int i = 0; i < 1;) {
            switch (rand.nextInt(4)) {
                case 0 :
                    if (isFree(0)) {
                        setO(0);
                        i++;
                    }
                break;
                case 1 :
                    if (isFree(2)) {
                        setO(2);
                        i++;
                    }
                break;
                case 2 :
                    if (isFree(6)) {
                        setO(6);
                        i++;
                    }
                break;
                case 3 :
                    if (isFree(8)) {
                        setO(8);
                        i++;
                    }
                break;
            } // switch
        } // for loop
    } // attackCorner

    // postawienie symbolu w losowym boku planszy
    private void attackSide() {
        Random rand = new Random();

        for (int i = 0; i < 1; ) {
            switch (rand.nextInt(4)) {
                case 0 :
                    if (isFree(1)) {
                        setO(1);
                        i++;
                    }
                break;
                case 1 :
                    if (isFree(3)) {
                        setO(3);
                        i++;
                    }
                break;
                case 2 :
                    if (isFree(5)) {
                        setO(5);
                        i++;
                    }
                break;
                case 3 :
                    if (isFree(7)) {
                        setO(7);
                        i++;
                    }
                break;
            } // switch
        } // for loop
    } // attackSide
} // MainActivity