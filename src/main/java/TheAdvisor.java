import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TheAdvisor {
    public static void main(String[] args) throws IOException, TheAdvisorException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String intro = "Hello, I am The Advisor. The one and only advisor you will ever need in your investing " +
                "journey. What can I do for you?";
        System.out.println(intro + "\n");

        // An ArrayList that stores the tasks to be done
        ArrayList<Task> taskList = new ArrayList<>();

        while (true) {
            try {
                processInput(br.readLine(), taskList);
            } catch (TheAdvisorException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void processInput(String str, ArrayList<Task> taskList) throws TheAdvisorException {
        String[] strings = str.split(" ");
        if (str.equals("bye")) {
            System.out.println("     Goodbye. Thank you for using TheAdvisor chatbox and I hope that my advice has managed" +
                    "to help you in your investing journey!");

            // Exit the program
            System.exit(0);
        } else if (str.equals("list")){
            int counter = 1;
            System.out.println("     Here are the tasks in your list:");
            for (int i = 0; i < taskList.size(); i++) {
                System.out.println("     " + counter + ". " + taskList.get(i).toString());
                counter++;
            }
        } else if (strings[0].equals("mark")) {
            checkArrayLength(strings, 2, "Invalid format. Make sure that the format is: "
            + "mark + (number) to mark something on the list as completed.");
            // 1-based indexing on input
            int number = Integer.parseInt(strings[1]);
            checkIndex(number, taskList.size());
            Task temp = taskList.get(number - 1);
            temp.markDone();
            System.out.println("     Nice! I've marked this task as done:\n" + "       " +
                    temp.toString());
        } else if (strings[0].equals("unmark")) {
            checkArrayLength(strings, 2, "Invalid format. Make sure that the format is: "
                    + "unmark + (number) to unmark something on the list.");
            // 1-based indexing on input
            int number = Integer.parseInt(strings[1]);
            checkIndex(number, taskList.size());
            Task temp = taskList.get(number - 1);
            temp.unmark();
            System.out.println("     OK, I've marked this task as not done yet:\n" + "       " +
                    temp.toString());
        } else {
            String type = strings[0];
            if (type.equals("todo")) {
                String task = str.substring(4);
                checkEmptyDescription(task, "The description for todo cannot be empty. Please try again.");
                ToDos toDos = new ToDos(task);
                taskList.add(toDos);
                System.out.println("     Got it. I've added this task:\n" +
                        "       " + toDos.toString() + "\n" +
                        "     Now you have " + taskList.size() +
                        " tasks in the list.");
            } else if (type.equals("deadline")) {
                String task = str.substring(8);
                checkEmptyDescription(task, "The description for deadline cannot be empty. Please try again.");
                String[] arrTask = task.split(" /by ");
                checkArrayLength(arrTask, 2, "Invalid deadline format" +
                        "Please use the correct format: deadline + description + /by + date/day");
                Deadline deadline = new Deadline(arrTask[0], arrTask[1]);
                taskList.add(deadline);
                System.out.println("     Got it. I've added this task:\n" +
                        "       " + deadline.toString() + "\n" +
                        "     Now you have " + taskList.size() +
                        " tasks in the list.");
            } else if (type.equals("event")) {
                String task = str.substring(5);
                checkEmptyDescription(task, "The description for event cannot be empty. Please try again.");
                String[] arrTask = task.split(" /from ");
                checkArrayLength(arrTask, 2, "Invalid event format" +
                        "Please use the correct format: event + description + /from + date/day + /to +date/time");
                String[] timings = arrTask[1].split(" /to");
                checkArrayLength(arrTask, 2, "Invalid event format" +
                        "Please use the correct format: event + description + /from + date/day + /to +date/time");
                Events events = new Events(arrTask[0], timings[0], timings[1]);
                taskList.add(events);
                System.out.println("     Got it. I've added this task:\n" +
                        "       " + events.toString() + "\n" +
                        "     Now you have " + taskList.size() +
                        " tasks in the list.");
            } else {
                throw new TheAdvisorException("Incorrect input, please try again with the correct input of either: "
                        + "todo, event, mark...etc");
            }
        }
    }

    private static void checkIndex(int index, int size) throws TheAdvisorException {
        if (index <= 0) {
            throw new TheAdvisorException("We use 1-indexing for marking. Please try again.");
        } else if (index > size) {
            throw new TheAdvisorException("Out of bounds. Please try again.");
        }
    }

    private static void checkEmptyDescription(String description, String errorMessage) throws TheAdvisorException {
        if (description.isEmpty()) {
            throw new TheAdvisorException(errorMessage);
        }
    }

    private static void checkArrayLength(String[] array, int expectedLength, String errorMessage) throws TheAdvisorException {
        if (array.length != expectedLength) {
            throw new TheAdvisorException(errorMessage);
        }
    }
}
