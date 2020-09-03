package com.javarush.task.task35.task3513;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        JFrame game = new JFrame();

        game.setTitle("2048");
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setSize(450, 500);
        game.setResizable(false);

        game.add(controller.getView());


        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
//            Model model = new Model();
//            model.gameTiles = new Tile[][]{
//                    {new Tile(4), new Tile(4), new Tile(4), new Tile(2)},
//                    {new Tile(0), new Tile(2), new Tile(4), new Tile(2)},
//                    {new Tile(2), new Tile(4), new Tile(2), new Tile(0)},
//                    {new Tile(4), new Tile(0), new Tile(0), new Tile(2)},
//            };
//
//            printTiles(model.gameTiles);
//
//            //model.up();
//            model.rotate();
//            //model.down();
//
//            printTiles(model.gameTiles);
//
//        }
//
//        private static void printTiles(Tile[][] tiles) {
//            for (int i = 0; i < tiles.length; i++) {
//                for (int j = 0; j < tiles.length; j++) {
//                    System.out.print(tiles[i][j].value + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//        }

    }

