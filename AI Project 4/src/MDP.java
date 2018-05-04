import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;

public class MDP {
    double[][] grid = new double[16][6];
    double[][] res = new double[16][6];
    static final double REWARD = -0.01;
    static final double N_NOISE = 0.6;
    static final double W_NOISE = 0.1;
    static final double E_NOISE = 0.15;
    static final double S_NOISE = 0.15;



    public MDP(){
    }
    //read file
    public void readFile(){
        BufferedReader br=null;
        FileReader fr=null;
        try{
            String txt="grid.txt";
            fr=new FileReader(txt);
            br=new BufferedReader(fr);
            String line;
            int count=0;
            while((line=br.readLine())!=null){
                String[] m=line.split(" ");
                for(int i=0;i<m.length;i++){
                    if(m[i].equals("-")){
                        grid[count][i]= -1;
                    }else if(m[i].equals("0")){
                        grid[count][i] = -0.01;
                    }else
                    grid[count][i]=Double.parseDouble(m[i]);
                }
                count++;
            }
        } catch (IOException e) {
            System.out.println("can't find the file");
        }
    }
    public void update(){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]!=1000 && grid[i][j]!=-800 && grid[i][j]!=-1)
                    grid[i][j] = res[i][j];
            }
        }
    }

    //MDP
    public void mdp(){
        for(int iteration = 0;iteration<99;iteration++){
            for(int i=0;i<grid.length;i++){
                for(int j=0;j<grid[0].length;j++){
                    //is not exit or wall
                    if(grid[i][j]!=1000 && grid[i][j]!=-800 && grid[i][j]!=-1){
                        //north
                        double north = 0;
                        if(i-1>=0 && grid[i-1][j]!=-1){
                            //can go north
                            north += N_NOISE*(REWARD + grid[i-1][j]*0.9);
                            //noise go east
                            if(j+1<grid[0].length && grid[i][j+1]!=-1){
                                north += E_NOISE*(REWARD + grid[i][j+1]*0.9);
                            }else {
                                north += E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go west
                            if(j-1>=0 && grid[i][j-1]!=-1){
                                north += W_NOISE*(REWARD + grid[i][j-1]*0.9);
                            }else {
                                north += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //go south
                            if(i+1<grid.length && grid[i+1][j]!=-1){
                                north += S_NOISE*(REWARD + grid[i+1][j]*0.9);
                            }else {
                                north += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }else {
                            //can't go north, stay
                            north += N_NOISE*(REWARD + grid[i][j]*0.9);
                            //noise go east
                            if(j+1<grid[0].length && grid[i][j+1]!=-1){
                                north += E_NOISE*(REWARD + grid[i][j+1]*0.9);
                            }else {
                                north += E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go west
                            if(j-1>=0 && grid[i][j-1]!=-1){
                                north += W_NOISE*(REWARD + grid[i][j-1]*0.9);
                            }else {
                                north += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //go south
                            if(i+1<grid.length && grid[i+1][j]!=-1){
                                north += S_NOISE*(REWARD + grid[i+1][j]*0.9);
                            }else {
                                north += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }
                        //west
                        double west = 0;
                        if(j-1>=0 && grid[i][j-1]!=-1){
                            //can go east
                            west += N_NOISE*(REWARD + grid[i][j-1]*0.9);
                            //noise go north
                            if(i-1>=0 && grid[i-1][j]!=-1){
                                west += E_NOISE*(REWARD + grid[i-1][j]*0.9);
                            }else {
                                west += E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go south
                            if(i+1<grid.length && grid[i+1][j]!=-1){
                                west += W_NOISE*(REWARD + grid[i+1][j]*0.9);
                            }else {
                                west += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go east
                            if(j+1<grid[0].length && grid[i][j+1]!=-1){
                                west += S_NOISE*(REWARD + grid[i][j+1]*0.9);
                            }else {
                                west += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }else {
                            //can't go east, stay
                            west += N_NOISE*(REWARD + grid[i][j]*0.9);
                            //noise go north
                            if(i-1>=0 && grid[i-1][j]!=-1){
                                west += E_NOISE*(REWARD + grid[i-1][j]*0.9);
                            }else {
                                west += E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go south
                            if(i+1<grid.length && grid[i+1][j]!=-1){
                                west += W_NOISE*(REWARD + grid[i+1][j]*0.9);
                            }else {
                                west += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go east
                            if(j+1<grid[0].length && grid[i][j+1]!=-1){
                                west += S_NOISE*(REWARD + grid[i][j+1]*0.9);
                            }else {
                                west += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }
                        //east
                        double east = 0;
                        if(j+1<grid[0].length && grid[i][j+1]!=-1) {
                            //can go east
                            east += N_NOISE * (REWARD + grid[i][j+1] * 0.9);
                            //noise go north
                            if(i-1>=0 && grid[i-1][j]!=-1){
                                east += W_NOISE*(REWARD + grid[i-1][j]*0.9);
                            }else {
                                east += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go south
                            if(i+1<grid.length && grid[i+1][j]!=-1){
                                east += E_NOISE*(REWARD + grid[i+1][j]*0.9);
                            }else {
                                east += E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go west
                            if(j-1>=0 && grid[i][j-1]!=-1){
                                east += S_NOISE*(REWARD + grid[i][j-1]*0.9);
                            }else {
                                east += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }else {
                            east += E_NOISE * (REWARD + grid[i][j] * 0.9);
                            //noise go north
                            if(i-1>=0 && grid[i-1][j]!=-1){
                                east += W_NOISE*(REWARD + grid[i-1][j]*0.9);
                            }else {
                                east += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go south
                            if(i+1<grid.length && grid[i+1][j]!=-1){
                                east += E_NOISE*(REWARD + grid[i+1][j]*0.9);
                            }else {
                                east += E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go west
                            if(j-1>=0 && grid[i][j-1]!=-1){
                                east += S_NOISE*(REWARD + grid[i][j-1]*0.9);
                            }else {
                                east += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }
                        //south
                        double south = 0;
                        if(i+1<grid.length && grid[i+1][j]!=-1){
                            //can go south
                            south +=N_NOISE*(REWARD + grid[i+1][j]*0.9);
                            //noise go east
                            if(j+1<grid[0].length && grid[i][j+1]!=-1){
                                south += W_NOISE*(REWARD + grid[i][j+1]*0.9);
                            }else {
                                south += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go west
                            if(j-1>=0 && grid[i][j-1]!=-1){
                                south += E_NOISE*(REWARD + grid[i][j-1]*0.9);
                            }else {
                                south += E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go north
                            if(i-1>=0 && grid[i-1][j]!=-1){
                                south += S_NOISE*(REWARD + grid[i-1][j]*0.9);
                            }else {
                                south += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }else {
                            //can't go north, stay
                            south += N_NOISE*(REWARD + grid[i][j]*0.9);
                            //noise go east
                            if(j+1<grid[0].length && grid[i][j+1]!=-1){
                                south += W_NOISE*(REWARD + grid[i][j+1]*0.9);
                            }else {
                                south += W_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go west
                            if(j-1>=0 && grid[i][j-1]!=-1){
                                south += E_NOISE*(REWARD + grid[i][j-1]*0.9);
                            }else {
                                south +=E_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                            //noise go north
                            if(i-1>=0 && grid[i-1][j]!=-1){
                                south += S_NOISE*(REWARD + grid[i-1][j]*0.9);
                            }else {
                                south += S_NOISE*(REWARD + grid[i][j]*0.9);
                            }
                        }
                        //choose max
                        double max = Math.max(north,west);
                        max = Math.max(max,east);
                        max = Math.max(max,south);
                        res[i][j] = max;
                    }
                }
            }
           update();
        }
    }

    public void print(){
        for(int i=0;i<grid.length;i++){
            for(int j=0;j<grid[0].length;j++){
                if(grid[i][j]==-1){
                    System.out.print("------"+"\t\t");
                    if(j==grid[0].length-1) System.out.println();
                } else {
                    double cur = grid[i][j];
                    DecimalFormat df = new DecimalFormat("##0.00");
                    System.out.print(df.format(cur)+"\t\t");
                    if(j==grid[0].length-1) System.out.println();
                }

            }
        }
    }

    public static void main(String[] args){
        MDP test = new MDP();
        test.readFile();
        test.mdp();
        test.print();
    }
}
