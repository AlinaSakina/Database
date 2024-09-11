import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Schoolsearch {

    // Внутрішній клас, що представляє студента з його атрибутами
    static class Student {
        String lastName;      // Прізвище студента
        String firstName;     // Ім'я студента
        int grade;            // Клас (рівень освіти)
        int classroom;        // Номер класу
        int bus;              // Номер шкільного автобуса
        String teacherLastName;  // Прізвище вчителя
        String teacherFirstName; // Ім'я вчителя

        // Конструктор для ініціалізації об'єкта студента
        public Student(String lastName, String firstName, int grade, int classroom, int bus, String teacherLastName, String teacherFirstName) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.grade = grade;
            this.classroom = classroom;
            this.bus = bus;
            this.teacherLastName = teacherLastName;
            this.teacherFirstName = teacherFirstName;
        }

        // Метод для відображення інформації про студента у вигляді рядка
        @Override
        public String toString() {
            return lastName + ", " + firstName + ", Grade: " + grade + ", Classroom: " + classroom + ", Bus: " + bus + ", Teacher: " + teacherFirstName + " " + teacherLastName;
        }
    }

    // Головний метод програми
    public static void main(String[] args) throws IOException {
        // Зчитуємо список студентів з файлу
        List<Student> students = readStudentsFromFile("students.txt");
        Scanner scanner = new Scanner(System.in);

        // Блок try-finally для забезпечення закриття Scanner
        try {
            // Меню з пошуковими запитами
            while (true) {
                System.out.println("\nMenu:");
                System.out.println("1. Search by student last name (find class and teacher)");
                System.out.println("2. Search by student last name (find bus route)");
                System.out.println("3. Search by teacher (find students)");
                System.out.println("4. Search by bus route (find students)");
                System.out.println("5. Search by grade level (find students)");
                System.out.println("6. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Пропускаємо новий рядок після введення числа

                long startTime;  // Змінна для вимірювання часу початку пошуку
                long endTime;    // Змінна для вимірювання часу закінчення пошуку

                switch (choice) {
                    case 1:
                        // Пошук студентів за прізвищем і виведення їхнього класу та вчителя
                        System.out.print("Enter student last name: ");
                        String lastName1 = scanner.nextLine();
                        startTime = System.nanoTime();  // Вимірювання часу початку пошуку
                        searchByLastNameForClassAndTeacher(students, lastName1);
                        endTime = System.nanoTime();    // Вимірювання часу закінчення пошуку
                        System.out.println("Search time: " + (endTime - startTime) + " ns");
                        break;
                    case 2:
                        // Пошук студентів за прізвищем і виведення їхнього автобуса
                        System.out.print("Enter student last name: ");
                        String lastName2 = scanner.nextLine();
                        startTime = System.nanoTime();
                        searchByLastNameForBus(students, lastName2);
                        endTime = System.nanoTime();
                        System.out.println("Search time: " + (endTime - startTime) + " ns");
                        break;
                    case 3:
                        // Пошук студентів за прізвищем вчителя
                        System.out.print("Enter teacher last name: ");
                        String teacherLastName = scanner.nextLine();
                        startTime = System.nanoTime();
                        searchByTeacher(students, teacherLastName);
                        endTime = System.nanoTime();
                        System.out.println("Search time: " + (endTime - startTime) + " ns");
                        break;
                    case 4:
                        // Пошук студентів за номером автобуса
                        System.out.print("Enter bus route: ");
                        int busRoute = scanner.nextInt();
                        startTime = System.nanoTime();
                        searchByBusRoute(students, busRoute);
                        endTime = System.nanoTime();
                        System.out.println("Search time: " + (endTime - startTime) + " ns");
                        break;
                    case 5:
                        // Пошук студентів за рівнем освіти
                        System.out.print("Enter grade level: ");
                        int grade = scanner.nextInt();
                        startTime = System.nanoTime();
                        searchByGradeLevel(students, grade);
                        endTime = System.nanoTime();
                        System.out.println("Search time: " + (endTime - startTime) + " ns");
                        break;
                    case 6:
                        // Вихід з програми
                        System.out.println("Exiting...");
                        return;
                    default:
                        // Повідомлення про неправильний вибір
                        System.out.println("Invalid choice.");
                }
            }
        } finally {
            // Закриваємо Scanner, щоб уникнути витоку ресурсів
            scanner.close();
        }
    }

    // Метод для зчитування студентів з файлу
    private static List<Student> readStudentsFromFile(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));  // Читаємо всі рядки з файлу

        // Парсимо кожен рядок і створюємо об'єкт студента
        for (String line : lines) {
            String[] parts = line.split(",");
            String lastName = parts[0].trim();
            String firstName = parts[1].trim();
            int grade = Integer.parseInt(parts[2].trim());
            int classroom = Integer.parseInt(parts[3].trim());
            int bus = Integer.parseInt(parts[4].trim());
            String teacherLastName = parts[5].trim();
            String teacherFirstName = parts[6].trim();
            students.add(new Student(lastName, firstName, grade, classroom, bus, teacherLastName, teacherFirstName));
        }

        return students;  // Повертаємо список студентів
    }

    // Пошук студентів за прізвищем та виведення інформації про клас і вчителя
    private static void searchByLastNameForClassAndTeacher(List<Student> students, String lastName) {
        List<Student> result = students.stream()
                .filter(s -> s.lastName.equalsIgnoreCase(lastName))  // Фільтруємо за прізвищем
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            result.forEach(s -> System.out.println("Student: " + s.firstName + " " + s.lastName + ", Class: " + s.classroom + ", Teacher: " + s.teacherFirstName + " " + s.teacherLastName));
        }
    }

    // Пошук студентів за прізвищем та виведення їхнього маршруту автобуса
    private static void searchByLastNameForBus(List<Student> students, String lastName) {
        List<Student> result = students.stream()
                .filter(s -> s.lastName.equalsIgnoreCase(lastName))  // Фільтруємо за прізвищем
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            result.forEach(s -> System.out.println("Student: " + s.firstName + " " + s.lastName + ", Bus Route: " + s.bus));
        }
    }

    // Пошук студентів за прізвищем вчителя
    private static void searchByTeacher(List<Student> students, String teacherLastName) {
        List<Student> result = students.stream()
                .filter(s -> s.teacherLastName.equalsIgnoreCase(teacherLastName))  // Фільтруємо за прізвищем вчителя
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            result.forEach(s -> System.out.println("Student: " + s.firstName + " " + s.lastName));
        }
    }

    // Пошук студентів за номером маршруту автобуса
    private static void searchByBusRoute(List<Student> students, int busRoute) {
        List<Student> result = students.stream()
                .filter(s -> s.bus == busRoute)  // Фільтруємо за маршрутом автобуса
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            result.forEach(s -> System.out.println("Student: " + s.firstName + " " + s.lastName + ", Bus Route: " + s.bus));
        }
    }

    // Пошук студентів за рівнем класу (Grade)
    private static void searchByGradeLevel(List<Student> students, int grade) {
        List<Student> result = students.stream()
                .filter(s -> s.grade == grade)  // Фільтруємо за рівнем класу
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            result.forEach(s -> System.out.println("Student: " + s.firstName + " " + s.lastName + ", Grade: " + s.grade));
        }
    }
}


