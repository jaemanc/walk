package com.org.walk.maze;

import org.springframework.util.StringUtils;

public class FindPathAlgorithmDfsRecursion {

    static int dx2[] = {1, -1, 0, 0};
    static int dy2[] = {0, 0, 1, -1};
    static int charNums2;
    static int lineNums2;
    static int cnt2=0;
    static String arr2[][];

    public static void main(String[] args) {

        arr2 = maze2();
        boolean[][] visited2 = new boolean[charNums2][lineNums2];

        for (int i = 0 ; i < lineNums2; i ++ ) {
            for (int j = 0 ; j < charNums2; j ++ ) {
                System.out.print(arr2[j][i]);
            }
            System.out.println("");
        }

        dfsRecursion(arr2, visited2, 0, 1);

    }

    static void dfsRecursion(String[][] arr, boolean[][] visited, int x, int y) {

        // 종료조건이 없다면 무한히 돌 수 있다.
        // 적합한 종료조건을 명시해줌이 좋다. 가령 미로에 대한 해답이 없을 수도 있으니까.

        // 찾아가는 경로 확인용.
     /*   for (int i = 0 ; i < lineNums2; i ++ ) {
            for (int j = 0 ; j < charNums2; j ++ ) {
                if (visited[j][i]) {
                    arr2[j][i] = "*";
                }
                System.out.print(arr2[j][i]);
            }
            System.out.println("");
        }*/
        if (arr[x][y].equals("B")) {
            System.out.println("B found...");

//            System.out.println("-------------------------------------------------------------------------------------");
//            for (int i = 0 ; i < lineNums2; i ++ ) {
//                for (int j = 0 ; j < charNums2; j ++ ) {
//                    if (visited[j][i]) {
//                        arr2[j][i] = "*";
//                    }
//                    System.out.print(arr2[j][i]);
//                }
//                System.out.println("");
//            }
//            System.out.println("-------------------------------------------------------------------------------------");
            cnt2++;
            return;
        }

        System.out.println("x : " + x + " / " + y +" = true");
        visited[x][y] = true;

        for (int i=0;i<4;i++) {

            int nx = x + dx2[i];
            int ny = y + dy2[i];

            if (nx >=0 && ny >=0 && nx < charNums2 && ny < lineNums2) {
                if ((!visited[nx][ny] && arr[nx][ny].equals(" ") ) || arr[nx][ny].equals("B")) {
                    dfsRecursion(arr,visited,nx,ny);
                    visited[nx][ny] = false;
                    System.out.println("nx : " + nx + " / " + ny +" = false");
                }
            }
        }
    }

    public static String[][] maze2() {
        String mazes =
                "################################################################\n" +
                "A                                       #                    ###\n" +
                "############# ### ################## ## ################### ## #\n" +
                "#        # ## #                   ## ## #   ##     #      #  # #\n" +
                "#### ### # ## # ################# ## ## ###  ## ## # #### ## # #\n" +
                "#    # # # ## #  ####           # ## ## ########## # ####### # #\n" +
                "# ## # # # ## #       ######### # ## ###           # #       # #\n" +
                "#           # #### # #       # ##### ###### # ###### ###########\n" +
                "# ###########   #  # # #######                #                #\n" +
                "#      ####   # # ## ######  ################ ###### ######### #\n" +
                "# ####     ##                                                  B\n" +
                "################################################################";

        charNums2 = mazes.substring(0,mazes.indexOf("\n")).length();  // 63
        System.out.println(" char nums : " + charNums2);
        lineNums2 = StringUtils.countOccurrencesOf(mazes,"\n")+1;  // 12
        System.out.println("linenums : " + lineNums2);

        mazes = mazes.replace("\n","");


        arr2 = new String[charNums2][lineNums2];

        for (int i=0; i < lineNums2; i ++ ) {
            for (int j=0; j < charNums2; j ++ ) {
                arr2[j][i] = Character.toString(mazes.charAt((i*charNums2)+j));
            }
        }
        return arr2;
    }


}
