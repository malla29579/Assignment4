import java.util.*;

public class EmployeeScheduler {

    static Map<String, Map<String, List<String>>> finalSchedule = new HashMap<>();
    static Map<String, Integer> employeeShifts = new HashMap<>();

    public static void main(String[] args) {
        // Initialize schedule structure
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String[] shifts = {"morning", "afternoon", "evening"};
        
        for (String day : days) {
            finalSchedule.put(day, new HashMap<>());
            for (String shift : shifts) {
                finalSchedule.get(day).put(shift, new ArrayList<>());
            }
        }

        // Example employee preferences (could be input dynamically)
        List<Map<String, String>> employeePreferences = new ArrayList<>();
        employeePreferences.add(Map.of("day", "Monday", "name", "John", "shift", "morning"));
        employeePreferences.add(Map.of("day", "Monday", "name", "Alice", "shift", "afternoon"));
        // Add more preferences...

        // Process assignments
        for (Map<String, String> pref : employeePreferences) {
            String day = pref.get("day");
            String employee = pref.get("name");
            String preferredShift = pref.get("shift");

            if (!assignShift(day, preferredShift, employee)) {
                resolveConflict(day, preferredShift, employee);
            }
        }

        // Print the final schedule
        printSchedule();
    }

    public static boolean assignShift(String day, String shift, String employee) {
        if (employeeShifts.getOrDefault(employee, 0) < 5) {
            finalSchedule.get(day).get(shift).add(employee);
            employeeShifts.put(employee, employeeShifts.getOrDefault(employee, 0) + 1);
            return true;
        }
        return false;
    }

    public static void resolveConflict(String day, String shift, String employee) {
        if (finalSchedule.get(day).get(shift).size() < 2) {
            for (String altShift : finalSchedule.get(day).keySet()) {
                if (!altShift.equals(shift) && finalSchedule.get(day).get(altShift).size() < 2) {
                    if (assignShift(day, altShift, employee)) {
                        System.out.println(employee + " assigned to " + altShift + " on " + day + " (original shift conflict)");
                        return;
                    }
                }
            }
        }
    }

    public static void printSchedule() {
        for (String day : finalSchedule.keySet()) {
            System.out.println("\n" + day + ":");
            for (String shift : finalSchedule.get(day).keySet()) {
                System.out.println("  " + shift.substring(0, 1).toUpperCase() + shift.substring(1) + ": " + String.join(", ", finalSchedule.get(day).get(shift)));
            }
        }
    }
}
