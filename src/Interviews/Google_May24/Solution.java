/*
You are receiving 2D GPS coordinates in a plane from some service and your task is to write a program that detects if a
given point can create a square with any 3 previously seen points.

Only squares consisting of horizontal and vertical lines are considered.

Let's denote the method as add(x, y) that returns true if (x,y) can be used to create a square, false otherwise.

Example input: (1,2), (3,5), (1,5), (4,2), (4,5)

Solutions:

  add(1,2) returns false
  add(3,5) returns false
  add(1,5) returns false
  add(4,2) returns false
  add(4,5) returns true - there is a square (1,2), (1,5), (4,5), (4,2)
    
    
Map<Integer, List<Integer>> xCordMap : {1: {2,5,8,11}, 3: {5}, 4: {2,3,6,8,11}}

public class Solution {
  Map<Integer, PriorityQueue<Integer>> xCordMap;
  List<Integer> list;
  
  Solution() {
    xCordMap = new HashMap<>();
    list = new ArrayList<Integer>();
  }


  public boolean add(int x, int y) {
    list.add(x);
    if (!xCordMap.contains(x)) {
      xCordMap.add(x, new PriorityQueue<Integer>());
      // Add it in the arrayList
    } 
    xCordMap.get(x).add(y);
    if (xCordMap.size() >=4) {
      for(int i=0;i<list.size();i++) {
        for(int j=i+1;j<list.size();j++) {
          // Get the ith and jth keys of xCordMap
          int n = list.get(i);
          int m = list.get(j);
          int diff = m-n;
          // Get their values
          PriorityQueue<Integer> first = xCordMap.get(n);
          PriorityQueue<Integer> second = xCordMap.get(m);
          int f=0,s=0;
          while(f<first.size() && s<second.size()) {
            if(first.get(f) == second.get(s)) {
              if(first.contains(f+diff) && second.contains(s+diff)) {
                return true;
              }
            }
            else if(first.get(f) < s<second.size()) {
              f++;
            }
            else {
              s++;
            }
          }
          
          
        }
     }
    }
    return false;
    


  }

}


// Map<Integer, List<Integer>> yCordMap : {2: {1,4}, 5: {3,1,4}}


x1: y1,y2   x2: y1,y2

x1,y1
x1,y2
x2,y1
x2,y2


*/