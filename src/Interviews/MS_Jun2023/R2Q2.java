public class R2Q2 {

    static int findBombedBuildings(int[][]city, int bx,int by, int r) {
        int ans = 0;
        int numOfBombedBuildings = 0;
        int leftLimitOfImpact = Math.max(bx - r, 0); //0
        int rightLimitOfImpact = bx+r > city[0].length ? city[0].length-1: bx+r; //4

        int topLimitOfImpact = Math.max(by - r, 0);//0
        int bottomLimitOfImpact = by+r > city.length ? city.length-1: by+r;//4

        for(int i=topLimitOfImpact;i<=bottomLimitOfImpact;i++) {
            for(int j=leftLimitOfImpact;j<=rightLimitOfImpact;j++) {
                if(city[i][j] == 1)
                    ans++;
            }
        }

        return numOfBombedBuildings; //8
    }


    public static void main(String[] args) {

    }
}
