import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Schoolsearch2 {

    // Внутрішній клас для представлення студента
    static class Student {
        String lastName;  // Прізвище студента
        String firstName; // Ім'я студента
        int grade;        // Клас студента
        int classroom;    // Номер класу, в якому навчається студент
        int bus;          // Номер автобуса студента

        // Конструктор для ініціалізації даних студента
        public Student(String lastName, String firstName, int grade, int classroom, int bus) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.grade = grade;
            this.classroom = classroom;
            this.bus = bus;
        }

        // Метод для відображення інформації про студента у вигляді рядка
        @Override
        public String toString() {
            return firstName + " " + lastName + ", Grade: " + grade + ", Class: " + classroom + ", Bus: " + bus;
        }
    }

    // Внутрішній клас для представлення вчителя
    static class Teacher {
        String lastName;  // Прізвище вчителя
        String firstName; // Ім'я вчителя
        int classroom;    // Номер класу, який веде вчитель

        // Конструктор для ініціалізації даних вчителя
        public Teacher(String lastName, String firstName, int classroom) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.classroom = classroom;
        }

        // Метод для відображення інформації про вчителя у вигляді рядка
        @Override
        public String toString() {
            return firstName + " " + lastName + ", Classroom: " + classroom;
        }
    }

    public static void main(String[] args) throws IOException {
        // Завантаження списків студентів і вчителів із файлів
        List<Student> students = readStudentsFromFile("list.txt");
        List<Teacher> teachers = readTeachersFromFile("teachers.txt");

        Scanner scanner = new Scanner(System.in);

        try {
            // Основний цикл роботи програми
            while (true) {
                // Виведення меню
                System.out.println("\nMenu:");
                System.out.println("1. Search by student last name (find class and teacher)");
                System.out.println("2. Search by student last name (find bus route)");
                System.out.println("3. Search by teacher last name (find students)");
                System.out.println("4. Search by bus route (find students)");
                System.out.println("5. Search by grade level (find students)");
                System.out.println("6. Search teacher by classroom number");
                System.out.println("7. Search teacher by last name");
                System.out.println("8. Search teacher by first name");
                System.out.println("9. Exit");

                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt(); // Зчитуємо вибір користувача
                scanner.nextLine(); // Очищаємо буфер

                long startTime;
                long endTime;

                switch (choice) {
                    case 1:
                        // Пошук студента за прізвищем і виведення інформації про його клас і вчителя
                        System.out.print("Enter student last name: ");
                        String lastName1 = scanner.nextLine();
                        startTime = System.currentTimeMillis();
                        searchByLastNameForClassAndTeacher(students, teachers, lastName1);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 2:
                        // Пошук автобуса за прізвищем студента
                        System.out.print("Enter student last name: ");
                        String lastName2 = scanner.nextLine();
                        startTime = System.currentTimeMillis();
                        searchByLastNameForBus(students, lastName2);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 3:
                        // Пошук учнів за прізвищем вчителя
                        System.out.print("Enter teacher last name: ");
                        String teacherLastName = scanner.nextLine();
                        startTime = System.currentTimeMillis();
                        searchByTeacherLastName(students, teachers, teacherLastName);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 4:
                        // Пошук учнів, які їдуть на конкретному автобусі
                        System.out.print("Enter bus route: ");
                        int busRoute = scanner.nextInt();
                        startTime = System.currentTimeMillis();
                        searchByBusRoute(students, busRoute);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 5:
                        // Пошук учнів за рівнем класу
                        System.out.print("Enter grade level: ");
                        int grade = scanner.nextInt();
                        startTime = System.currentTimeMillis();
                        searchByGradeLevel(students, grade);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 6:
                        // Пошук вчителя за номером класу
                        System.out.print("Enter classroom number: ");
                        int classroom = scanner.nextInt();
                        startTime = System.currentTimeMillis();
                        searchTeacherByClassroom(teachers, classroom);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 7:
                        // Пошук вчителя за прізвищем
                        System.out.print("Enter teacher last name: ");
                        String lastName = scanner.nextLine();
                        startTime = System.currentTimeMillis();
                        searchTeacherByLastName(teachers, lastName);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 8:
                        // Пошук вчителя за ім'ям
                        System.out.print("Enter teacher first name: ");
                        String firstName = scanner.nextLine();
                        startTime = System.currentTimeMillis();
                        searchTeacherByFirstName(teachers, firstName);
                        endTime = System.currentTimeMillis();
                        System.out.println("Search time: " + (endTime - startTime) + " ms");
                        break;
                    case 9:
                        // Завершення програми
                        System.out.println("До побачення!");
                        return;
                    default:
                        // Випадок, коли користувач ввів невірну опцію
                        System.out.println("Invalid choice.");
                }
            }
        } finally {
            scanner.close(); // Закриваємо сканер після завершення роботи
        }
    }

    // Метод для читання даних про студентів із файлу
    private static List<Student> readStudentsFromFile(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filename)); // Читаємо всі рядки з файлу

        // Парсимо кожен рядок і створюємо об'єкт Student
        for (String line : lines) {
            String[] parts = line.split(",");
            String lastName = parts[0].trim();
            String firstName = parts[1].trim();
            int grade = Integer.parseInt(parts[2].trim());
            int classroom = Integer.parseInt(parts[3].trim());
            int bus = Integer.parseInt(parts[4].trim());
            students.add(new Student(lastName, firstName, grade, classroom, bus));
        }
        return students;
    }

    // Метод для читання даних про вчителів із файлу
    private static List<Teacher> readTeachersFromFile(String filename) throws IOException {
        List<Teacher> teachers = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filename)); // Читаємо всі рядки з файлу

        // Парсимо кожен рядок і створюємо об'єкт Teacher
        for (String line : lines) {
            String[] parts = line.split(",");
            String lastName = parts[0].trim();
            String firstName = parts[1].trim();
            int classroom = Integer.parseInt(parts[2].trim());
            teachers.add(new Teacher(lastName, firstName, classroom));
        }
        return teachers;
    }

    private static void searchByLastNameForClassAndTeacher(List<Student> students, List<Teacher> teachers, String lastName) {
        List<Student> result = students.stream() //Створюємо потік
                .filter(s -> s.lastName.equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : result) {
                Teacher teacher = findTeacherByClassroom(teachers, s.classroom);
                if (teacher != null) {
                    System.out.println("Student: " + s.firstName + " " + s.lastName + ", Class: " + s.classroom + ", Teacher: " + teacher.firstName + " " + teacher.lastName);
                } else {
                    System.out.println("Student: " + s.firstName + " " + s.lastName + ", Class: " + s.classroom + ", Teacher: Not Found");
                }
            }
        }
    }

    private static void searchByLastNameForBus(List<Student> students, String lastName) {
        List<Student> result = students.stream()
                .filter(s -> s.lastName.equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : result) {
                System.out.println("Student: " + s.firstName + " " + s.lastName + ", Bus: " + s.bus);
            }
        }
    }

    private static void searchByTeacherLastName(List<Student> students, List<Teacher> teachers, String lastName) {
        List<Teacher> result = teachers.stream()
                .filter(t -> t.lastName.equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No teachers found.");
        } else {
            for (Teacher t : result) {
                System.out.println("Teacher: " + t.firstName + " " + t.lastName + ", Classroom: " + t.classroom);
                List<Student> studentsInClass = students.stream()
                        .filter(s -> s.classroom == t.classroom)
                        .collect(Collectors.toList());

                if (studentsInClass.isEmpty()) {
                    System.out.println("No students found for this teacher.");
                } else {
                    for (Student s : studentsInClass) {
                        System.out.println("  " + s.firstName + " " + s.lastName);
                    }
                }
            }
        }
    }

    private static void searchByBusRoute(List<Student> students, int busRoute) {
        List<Student> result = students.stream()
                .filter(s -> s.bus == busRoute)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found for bus route " + busRoute);
        } else {
            System.out.println("Students on bus route " + busRoute + ":");
            for (Student s : result) {
                System.out.println("  " + s.firstName + " " + s.lastName);
            }
        }
    }

    private static void searchByGradeLevel(List<Student> students, int grade) {
        List<Student> result = students.stream()
                .filter(s -> s.grade == grade)
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No students found for grade level " + grade);
        } else {
            System.out.println("Students in grade level " + grade + ":");
            for (Student s : result) {
                System.out.println("  " + s.firstName + " " + s.lastName);
            }
        }
    }

    private static void searchTeacherByClassroom(List<Teacher> teachers, int classroom) {
        Teacher result = findTeacherByClassroom(teachers, classroom);

        if (result == null) {
            System.out.println("No teacher found for classroom " + classroom);
        } else {
            System.out.println("Teacher: " + result.firstName + " " + result.lastName + ", Classroom: " + result.classroom);
        }
    }

    private static void searchTeacherByLastName(List<Teacher> teachers, String lastName) {
        List<Teacher> result = teachers.stream()
                .filter(t -> t.lastName.equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No teachers found with last name " + lastName);
        } else {
            for (Teacher t : result) {
                System.out.println("Teacher: " + t.firstName + " " + t.lastName + ", Classroom: " + t.classroom);
            }
        }
    }

    private static void searchTeacherByFirstName(List<Teacher> teachers, String firstName) {
        List<Teacher> result = teachers.stream()
                .filter(t -> t.firstName.equalsIgnoreCase(firstName))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            System.out.println("No teachers found with first name " + firstName);
        } else {
            for (Teacher t : result) {
                System.out.println("Teacher: " + t.firstName + " " + t.lastName + ", Classroom: " + t.classroom);
            }
        }
    }

    private static Teacher findTeacherByClassroom(List<Teacher> teachers, int classroom) {
        return teachers.stream()
                .filter(t -> t.classroom == classroom)
                .findFirst()
                .orElse(null);
    }
}