import random

# Dictionary to store employee shift preferences
employee_preferences = {
    "Monday": [("John", "morning"), ("Alice", "afternoon"), ("Bob", "evening")],
    "Tuesday": [("John", "evening"), ("Alice", "morning"), ("Bob", "afternoon")],
    "Wednesday": [("John", "morning"), ("Alice", "afternoon"), ("Bob", "evening")],
    # Add for all days of the week...
}

# Final schedule with shifts per day
final_schedule = {
    "Monday": {"morning": [], "afternoon": [], "evening": []},
    "Tuesday": {"morning": [], "afternoon": [], "evening": []},
    "Wednesday": {"morning": [], "afternoon": [], "evening": []},
    "Thursday": {"morning": [], "afternoon": [], "evening": []},
    "Friday": {"morning": [], "afternoon": [], "evening": []},
    "Saturday": {"morning": [], "afternoon": [], "evening": []},
    "Sunday": {"morning": [], "afternoon": [], "evening": []},
}

# Track number of shifts each employee has been assigned to
employee_shifts = {}

def assign_shift(day, shift, employee):
    """Assign an employee to a shift if they haven't worked 5 days yet."""
    if employee_shifts.get(employee, 0) < 5:
        final_schedule[day][shift].append(employee)
        employee_shifts[employee] = employee_shifts.get(employee, 0) + 1
        return True
    return False

def resolve_conflict(day, shift, employee):
    """Resolve shift conflicts by reassigning to another available shift."""
    if len(final_schedule[day][shift]) < 2:
        # Try to find an alternative shift for the employee
        for alt_shift in ["morning", "afternoon", "evening"]:
            if alt_shift != shift and len(final_schedule[day][alt_shift]) < 2:
                if assign_shift(day, alt_shift, employee):
                    print(f"{employee} assigned to {alt_shift} on {day} (original shift conflict)")
                    return True
    return False

# Example of assigning employees based on their preferences
for day, preferences in employee_preferences.items():
    for employee, preferred_shift in preferences:
        if not assign_shift(day, preferred_shift, employee):
            if not resolve_conflict(day, preferred_shift, employee):
                print(f"Could not assign {employee} to any shift on {day}")

# Output the final schedule
def print_schedule():
    for day, shifts in final_schedule.items():
        print(f"\n{day}:")
        for shift, employees in shifts.items():
            print(f"  {shift.capitalize()}: {', '.join(employees)}")

print_schedule()
