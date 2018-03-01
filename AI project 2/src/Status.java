 //class to store the move status, include its position and win point.
   public  class Status implements Comparable<Status>{
        public int column, row, point;
        public Status(int row, int column, int point) {
            this.column = column;
            this.row = row;
            this.point = point;
        }
        public int compareTo(Status s){
            return s.point-this.point;
        }
    }

