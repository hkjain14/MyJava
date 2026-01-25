package Interviews.Atlassian_Jan26.DSA_Practice;

import java.util.*;

/*
Two inputs: a list of available parts and a list of required parts for each robot.
Output: a list of robot names that can be fully constructed with the available parts.
 */

/*
Time: O(N + U)
Space: O(U)
N = size of available parts list.
U = size of required parts list.
 */

public class RobotParts {
    public static List<String> buildableRobots(List<String> robotParts, Set<String> availableParts) {
        // robot -> SET of required parts
        Map<String, Set<String>> requiredMap = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (String item : robotParts) {
            int idx = item.indexOf('_');
            if (idx == -1 || idx == item.length() - 1) // Invalid format
                continue;

            String robot = item.substring(0, idx);
            String part = item.substring(idx + 1);
            requiredMap.computeIfAbsent(robot, k -> new HashSet<>()).add(part);
        }

        for (Map.Entry<String, Set<String>> entry : requiredMap.entrySet()) {
            String robot = entry.getKey();
            Set<String> requiredParts = entry.getValue();
            boolean canBuild = true;
            for (String part : requiredParts) {
                if (!availableParts.contains(part)) {
                    canBuild = false;
                    break;
                }
            }

            if (canBuild)
                result.add(robot);
        }
        return result;
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList(
                "Rosie_claw",
                "Rosie_sensors",
                "Dustie_case",
                "Optimus_sensors",
                "Rust_sensors",
                "Rosie_case",
                "Rust_case",
                "Optimus_speaker",
                "Rosie_wheels",
                "Dustie_case",
                "Dustie_arms",
                "Rust_claw",
                "Dustie_case",
                "Dustie_speaker",
                "Optimus_case",
                "Optimus_wheels",
                "Optimus_wheels",
                "Rust_legs",
                "Optimus_sensors"
        );
        Set<String> available = new HashSet<>(Arrays.asList("sensors", "case", "speaker", "wheels", "claw"));

        System.out.println(buildableRobots(list, available)); // [Rosie, Optimus]
    }
}
