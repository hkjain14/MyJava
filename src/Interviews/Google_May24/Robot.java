/**
Imagine you have a robot that sends status messages that humans will read in real time. 
The raw messages are hard to read for a human because there are often many messages produced in short periods of time. 
One idea to make them more readable is to remove the duplicate messages over a 10 second window. 
Design and implement a program to hide duplicates of any message that has already been displayed within the past 10 seconds.

*/

// Store in a set all the values obtained in last 10 secs.
// Update the set after each second => remove the >10 secs values

// for new incoming message, remove the 10 sec older values from set.

// Assumption : Timestamp -> Integer
/* 
Example Messages Received, with Timestamps:

10 solar panel activated
11 low battery warning
12 tire one: low air pressure
13 solar panel activated
14 low battery warning
21 solar panel activated
35 solar panel activated


Example Messages Shown to User:

10 solar panel activated
11 low battery warning
12 tire one: low air pressure
21 solar panel activated
35 solar panel activated
*/


import java.util.*;

public class Robot {
  public int lowerPointer;
  public Set<String> lastTenSecMessagesSet;
  public Map<Integer, List<String>> processedMap;
  
  Robot() {
    lastTenSecMessagesSet = new HashSet<>();
    lowerPointer = 0;
    processedMap = new HashMap<>();
  }
  
  public void processRobotMessages(String inp, int currentTimestamp) {
  
    int tenSecAgo = Math.max(currentTimestamp-10,0);
    
    while(lowerPointer < tenSecAgo) {
      // clear older messagesLog
      if(!processedMap.containsKey(lowerPointer)) {
        lowerPointer++;
        continue;
      }

      for(String el: processedMap.get(lowerPointer)) {
        lastTenSecMessagesSet.remove(el);
      }

      lowerPointer++;
    }
    
    // check whether inp is present in set lastTenSecMessagesSet
        // if yes, don't process it
        // else process it, add it to set.
    if (!lastTenSecMessagesSet.contains(inp)) {
      if(!processedMap.containsKey(currentTimestamp)) {
        processedMap.put(currentTimestamp, new ArrayList<>());
      }
      
      processedMap.get(currentTimestamp).add(inp);
      lastTenSecMessagesSet.add(inp);
    }
  }

  void print() {
    System.out.println(processedMap);
  }

  public static void main(String[] args) {
    Robot r = new Robot();
    r.processRobotMessages("solar panel activated", 10);
    r.processRobotMessages("low battery warning", 11);
    r.processRobotMessages("tire one: low air pressure", 12);
    r.processRobotMessages("low battery warning", 14);
    r.processRobotMessages("solar panel activated", 21);
    r.processRobotMessages("solar panel activated", 35);
    r.print();
  }
}

/* 
space : O(m) : m is the size of last 10 second's messages
time : O(n) : n is the size of timestamp diff from start to end
*/
