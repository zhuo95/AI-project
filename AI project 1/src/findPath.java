import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class findPath {
    int[][] maze=new int[81][81];
    boolean success=false;
    public void dfs(int[][] maze,int sx,int sy,int ex,int ey){
        if(maze[sx][sy]==1) return ;
        if(sx==ex&&sy==ey){
            success=true;
            return;
        }
        maze[sx][sy]=1;
        if(sx!=0&&!success)  dfs(maze,sx-1,sy,ex,ey);
        if(sx!=maze.length-1&&!success)  dfs(maze,sx+1,sy,ex,ey);
        if(sy!=0&&!success)  dfs(maze,sx,sy-1,ex,ey);
        if(sy!=maze[0].length-1&&!success) dfs(maze,sx,sy+1,ex,ey);
        if(success) return;
        }

        public void readFile(){
            BufferedReader br=null;
            FileReader fr=null;
            try{
                String txt="src/maze.txt";
                fr=new FileReader(txt);
                br=new BufferedReader(fr);
                String line;
                int count=0;
                while((line=br.readLine())!=null){
                    String[] m=line.split(" ");
                    for(int i=0;i<m.length;i++){
                        maze[count][i]=Integer.parseInt(m[i]);
                    }
                    count++;
                }
            } catch (IOException e) {
                System.out.println("can't find the file");
            }
        }

        public void canReach(int[][] maze,int sx,int sy,int ex,int ey){
            success=false;
            readFile();
            dfs(maze,sx,sy,ex,ey);
            System.out.print("From"+"("+sx+","+sy+")"+"To"+"("+ex+","+ey+")"+" "+"-->");
            if(success){
                System.out.println("YES");
            }else {
                System.out.println("NO");
            }
        }

    public static void main(String[] args){
        findPath test=new findPath();
        test.canReach(test.maze,1,34,15,47);
        test.canReach(test.maze,1,2,3,39);
        test.canReach(test.maze,0,0,3,77);
        test.canReach(test.maze,1,75,8,79);
        test.canReach(test.maze,1,75,39,40);
    }
}
