package homework;
//编写程序，将下面的诗歌按列输出
public class _3001 {
    public static void main(String[] args) {
        String[] lines = {"锄禾日当午，", "汗滴禾下土。", "谁知盘中餐，", "粒粒皆辛苦。"};
        int row = lines.length, col = lines[0].length();
        char poem[][] = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                poem[i][j] = lines[i].charAt(j);
            }
        }
        for (int j = 0; j <= col - 1; j++) {
            for (int i = row - 1; i >= 0; i--) {
                System.out.print(poem[i][j] + "  ");
            }
            System.out.println();
        }
    }
}
