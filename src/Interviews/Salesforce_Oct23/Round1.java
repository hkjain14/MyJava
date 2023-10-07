package Interviews.Salesforce_Oct23;

import java.util.*;

public class Round1 {
    public static class Point {
        int xCoord;
        int yCoord;
        Point(int x, int y) {
            this.xCoord = x;
            this.yCoord = y;
        }
    }

    public static long minArea(List<Integer> x, List<Integer> y, int k) {
        long ans = Integer.MAX_VALUE;

        List<Point> points = new ArrayList<>();
        for(int i=0;i<x.size();i++) {
            int xCoord = x.get(i);
            int yCoord = y.get(i);
            Point p = new Point(xCoord,yCoord);
            points.add(p);
        }

        List<Point> sortedByX = new ArrayList<>(points);
        Collections.sort(sortedByX, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Integer.compare(p1.xCoord, p2.xCoord);
            }
        });

        List<Point> sortedByY = new ArrayList<>(points);
        Collections.sort(sortedByY, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Integer.compare(p1.yCoord, p2.yCoord);
            }
        });


        for(int i=0;i<x.size()-k+1;i++) {
            for(int j=0;j<y.size()-k+1;j++) {
                Point leftPoint = sortedByX.get(i);
                Point bottomPoint = sortedByY.get(j);
                if(leftPoint.xCoord <= bottomPoint.xCoord &&
                        leftPoint.yCoord >= bottomPoint.yCoord) {

                    int leftSide = leftPoint.xCoord - 1;
                    int bottomSide = bottomPoint.yCoord - 1;
                    Set<Point> hashSet = new HashSet<>(sortedByX.subList(i, x.size()));

                    hashSet.retainAll(new HashSet<Point>(sortedByY.subList(j, y.size())));
                    if(hashSet.size()<k)
                        continue;

                    List<Integer> areaList = new ArrayList<Integer>();

                    for(Point setPoint: hashSet) {
                        int squareS = Math.max(setPoint.xCoord + 1 - leftSide, setPoint.yCoord + 1 - bottomSide);
                        areaList.add(squareS*squareS);
                    }

                    Collections.sort(areaList);
                    if(ans >= areaList.get(k-1)) {
                        ans = areaList.get(k-1);
                    }
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        System.out.println("Hello");
        // Above code clears 8/10 tests : copied from https://ideone.com/139C7A
    }
}
