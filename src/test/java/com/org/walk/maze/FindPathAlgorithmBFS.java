package com.org.walk.maze;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Queue;


class Location {
    int row;
    int col;
    int dist;
    public Location(int row, int col, int dist) {
        this.row = row;
        this.col = col;
        this.dist = dist;
    }
}


public class FindPathAlgorithmBFS {
    static boolean[][] visited;
    static int charNums;
    static int lineNums;
    static String arr[][];
    static int chk[][];

    public static void main(String[] args) {

        maze();

        chk = new int[lineNums][charNums];
        visited = new boolean[lineNums][charNums];

        visited[1][0] = true;
        int answer = findPath(1,0);

        System.out.println(" 정답 : " + answer);
    }

    public static int findPath(int x, int y) {

        Queue<Location> queue = new LinkedList<>();


        int[] xArr = {0,0,-1,1,};
        int[] yArr = {-1,1,0,0,};

        queue.add(new Location(x,y,1));
        visited[x][y] = true;
        while(!queue.isEmpty()) {
            Location location = queue.poll();

            int row = location.row;
            int col = location.col;
            int dist = location.dist;

            if (arr[row][col].equals("G")) {
                System.out.println(" 도착해부렸구만.." + arr[row][col]);

                for (int i = 0 ; i < lineNums; i ++ ) {
                    for (int j = 0 ; j < charNums; j ++ ) {
                        if (visited[j][i]) {
                            arr[j][i] = "*";
                        }
                        System.out.print(arr[j][i]);
                    }
                    System.out.println("");
                }

                return dist;
            }

            for (int i = 0; i < 4; i ++ ) {
                int nextX =  row + xArr[i];
                int nextY =  col + yArr[i];

                if (checkLocation(nextX,nextY) && !visited[nextX][nextY]) {

                    /*
                    https://wikidocs.net/125662
                    너비우선 탐색이 어째서?
                    어째서 최적 경로가 될  수 밖에 없는가?
                    최적경로 보장하기
                    위에서 예를 든 미로탐색과 같은 문제를 BFS를 구현하다보면,
                    누구나 한 번씩은 의구심을 갖게 됩니다. 계속해서 visited를 통해 경로를 찍어 보면서도,
                     "이 경로가 정말 최적 경로가 맞을까?" 또는
                     "혹시 다른 경로를 통해서 가면 최소값을 갖게 되는 경우의 수가 생길 수 도 있지 않을까?"
                     같은 의문이 들게 됩니다.
                   천천히 생각해 보겠습니다.
                    가장 먼저 시작점에 준하는 좌표의 visited 배열에는 출발지를 뜻하는 1을 표기하게 됩니다.
                     그리고 두 번 째로 갈 수 있는 모든 경로에는 2가,
                       세 번만에 도달이 가능한 곳에는 모두 3이 표기됩니다.
                     이렇게 vistied배열에는 모든 순간 갈 수 있는 최선의 경우의 수가 기입되는 것이지요.
                    다른 말로 표현해보면, 내가 이번에 탐색해서 발견한 노드가 이미 이전에 방문한 노드라면,
                    기존에 채워진 값은 새로 채우려는 값보다
                    항상 작거나 같을 수 밖에 없다는 뜻 입니다.
                     따라서 우리는 해당 노드의
                     방문 여부(if(visited[i][j]==0)) 만 확인하게 되는 것이지요.
                     */

                    queue.add(new Location(nextX, nextY, dist+1));
                    visited[nextX][nextY] = true;
                    //arr[row][col]="*";
                }

            }
        }

        return 0;
    }

    public static boolean checkLocation (int nextX, int nextY) {

        if (nextX < 0 || nextX >= charNums || nextY < 0 || nextY >= lineNums || arr[nextX][nextY].equals("#")) {
            return false;
        }
        return true;
    }

    public static String[][] maze() {
        String mazes =
                "################################################################\n" +
                "S             ###                                            ###\n" +
                "############# ### ################## ## ################### ## #\n" +
                "#        # ## #                   ## ## #   ##     #      #  # #\n" +
                "#### ### # ## # ################# ## ## ###  ## ## # #### ## # #\n" +
                "#    # # # ## #  ####           # ## ## ########## # ####### # #\n" +
                "# ## # # # ## #       ######### # ## ##            # #       # #\n" +
                "#### # # # ## # #######       # # ## ############### # ####### #\n" +
                "#      # # ## # ## #    # ##### # ##                 # #       #\n" +
                "# ###### # ## # ## # #### ##### # ########## ######### ####### #\n" +
                "# #    # #    # ## # ######   # # #        # #       # #       #\n" +
                "# # ## # #### # ## #  #     # # # # #### ### # # ### # ### #####\n" +
                "# # ## #      #  # #  ##### # # # ###### ### # # # # # ### #####\n" +
                "# # ## ########### ######## # # # #        # # # # # #  #      #\n" +
                "# # #              #     ## ### # # ##### ## # # # # # ####### #\n" +
                "# # ######### #### #            # # ######## # #   # # #       #\n" +
                "# ### ### ###### # #     ######## # # # # ## ####### # # #######\n" +
                "#                # ########       #                  # #       #\n" +
                "################ #          ##### ### # # ## ### ##### ####### #\n" +
                "#              # ################ ########## # # ##### #       #\n" +
                "# ############ #                  ##         # #     # # #######\n" +
                "# # #    ####  ############################### ####### # #     #\n" +
                "# ###  #   #  ####      #                              # #######\n" +
                "#  ## # #    # ### ###### ################### ##########      ##\n" +
                "## ########### #      ### ##            # ### ############### ##\n" +
                "##         ### ###### ### ## ########## # ### ###             ##\n" +
                "####### ## #            # ## #     #### #     ### ###########  #\n" +
                "# ##### ## ############## ## # ### #### ######### #         # ##\n" +
                "#       ## ##           # ## # # # # ##           ###### ## # ##\n" +
                "########## ## ########### ## # # # # ## ######### #       # # ##\n" +
                "##            #         # ## # # # # ## #   #   # ### ## ## # ##\n" +
                "## ###################### ## # # # # ########## # # # ## #  # ##\n" +
                "## #    ###               ## # # # #   #     ## # # # ## #### ##\n" +
                "## # ##     #### ########### # # # ### # ### ######## ## # ## ##\n" +
                "## # ######## ## #####       # # # # # #              ## # #   #\n" +
                "## #           # #     ##### # # # ### ### ############# # ## ##\n" +
                "## ########### ##### ###   # ### # # # ### #           # #     #\n" +
                "#           ## #   # ### # ##### # ###  ## # ######### # # ### #\n" +
                "#### ###### ## # # #                    ## # ######### # # # # #\n" +
                "# ## #    # ## #   # ##################### #        ## # # # # #\n" +
                "# ## # ## # ## ##### #                     # ###### ## # # # # #\n" +
                "# ## # ## # ## ##    # ####### #################### ## # # # # #\n" +
                "# ##   ## # ## ##### # ##### # ##     #####      ##### # # # # #\n" +
                "# ####### # ## #  ## # #     # ###### ##### ####       # # # # #\n" +
                "#         # ## #  ## # ####### #             ########### # # # #\n" +
                "###  ###### ## ####### #       ############# #           # # # #\n" +
                "### ####### ##         # ################### # ########### ### #\n" +
                "#   #        ########### ###                 # #  ##         # #\n" +
                "# ### ###### ##          ### ############# # # #  ## ######### #\n" +
                "# ### #    # ## ##########      ####### ## # # #  ## #         #\n" +
                "# ### # ## # ############# #### ##      ## ### #  ## # ####### #\n" +
                "#     ######               #### ## #### ##     #  ## #      ## #\n" +
                "# ### #    ####################### #  # ############ #### ######\n" +
                "### #   ##                         #  #              #  #      #\n" +
                "# # ################################################### ###### #\n" +
                "# # #            # #             ##########        #### #### # #\n" +
                "# # # ############ # ########### #          ######           # #\n" +
                "# #                # #         # # ################### ####### #\n" +
                "# ################ # # ####### #            #        #         #\n" +
                "#              ### # #       # ############ # ###### ###########\n" +
                "# ############  #  # # #######              # #                #\n" +
                "#      ####  ## # ## ######  ################ ################ #\n" +
                "# ####      ##                                               # G\n" +
                "################################################################";

        charNums = mazes.substring(0,mazes.indexOf("\n")).length();
        System.out.println(" char nums : " + charNums);
        lineNums = StringUtils.countOccurrencesOf(mazes,"\n")+1;
        System.out.println("linenums : " + lineNums);


        mazes = mazes.replace("\n","");


        arr = new String[lineNums][charNums];


        for (int i=0; i < lineNums; i ++ ) {
            for (int j=0; j < charNums; j ++ ) {
                arr[i][j] = Character.toString(mazes.charAt((i*charNums)+j));
                System.out.print(arr[i][j]);
            }
            System.out.println("");
        }

        return arr;
    }

}
