import tester.*;
import java.util.function.Predicate;

//represents a generic list
interface IList<T> {

  // Checks if the list contains the given item
  boolean contains(T item);

  // Counts how many items satisfy the predicate
  int countMatches(Predicate<T> pred);

  // Determines whether there are any matches that satisfy the predicate
  boolean anyMatch(Predicate<T> pred);
}

//represents a generic empty list
class MtList<T> implements IList<T> {

  // Constructor for MtList
  MtList() {
  }

  // An empty list has no items, return false
  public boolean contains(T item) {
    return false;
  }

  // An empty list has no items to match, return 0
  public int countMatches(Predicate<T> pred) {
    return 0;
  }

  // An empty list has no items to match, return false
  public boolean anyMatch(Predicate<T> pred) {
    return false;
  }
}

//represents a generic non-empty list
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  // Constructor for ConsList
  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // Determines whether this list contains the given item within
  // any of its elements
  public boolean contains(T item) {
    return this.first.equals(item) || this.rest.contains(item);
  }

  // Counts each instance where the predicate is satisfied by the list
  public int countMatches(Predicate<T> pred) {
    int count = 0;
    if (pred.test(this.first)) {
      count = 1;
    }
    return count + this.rest.countMatches(pred);
  }

  // Determines if there is any instance where the predicate is
  // satisfied by the list
  public boolean anyMatch(Predicate<T> pred) {
    return pred.test(this.first) || this.rest.anyMatch(pred);
  }
}

//Represents a course's information
class Course {
  String name;
  Instructor prof;
  IList<Student> students;

  // Constructor for course
  Course(String name, Instructor prof) {
    this.name = name;
    this.prof = prof;
    this.students = new MtList<>();
    prof.addCourse(this);
  }

  // Constructor for testing
  Course(String name, Instructor prof, IList<Student> students) {
    this.name = name;
    this.prof = prof;
    this.students = students;
  }

  // Adds the student to this course's list of students
  void addStudent(Student student) {
    this.students = new ConsList<>(student, this.students);
  }

  // Checks if this course has the given student
  boolean hasStudent(Student student) {
    return this.students.contains(student);
  }
}

//Represents an instructor's information
class Instructor {
  String name;
  IList<Course> courses;

  // Constructor for instructor
  Instructor(String name) {
    this.name = name;
    this.courses = new MtList<Course>();
  }

  // Constructor for testing
  Instructor(String name, IList<Course> courses) {
    this.name = name;
    this.courses = courses;
  }

  // Adds the given course to this instructor's list of courses
  void addCourse(Course course) {
    this.courses = new ConsList<>(course, this.courses);
  }

  // Determines whether the given student is taking more than one of
  // the instructor's classes
  public boolean dejavu(Student student) {
    return this.courses.countMatches(course -> course.hasStudent(student)) > 1;
  }
}

// Represents a student's information
class Student {
  String name;
  int id;
  IList<Course> courses;

  // Constructs a student with an empty course load
  Student(String name, int id) {
    this.name = name;
    this.id = id;
    this.courses = new MtList<Course>();
  }

  // Enrolls the student in the given course
  public void enroll(Course course) {
    if (!this.isEnrolled(course)) {
      this.courses = new ConsList<>(course, this.courses);
      course.addStudent(this);
    }
  }

  // Determines whether the student is enrolled in the given course
  boolean isEnrolled(Course course) {
    return this.courses.contains(course);
  }

  // Determines whether this student and that student share any classes
  public boolean classmates(Student other) {
    return this.courses.anyMatch(course -> other.isEnrolled(course));
  }
}

class ExamplesRegistrar {
  Student carol = new Student("Carol", 45);
  Student dany = new Student("Dany", 87);
  Student anthony = new Student("Anthony", 19);
  Student julia = new Student("Julia", 30);
  Student gabriel = new Student("Gabriel", 51);

  Predicate<Course> pred1 = course -> course.hasStudent(julia);
  Predicate<Course> pred2 = course -> course.hasStudent(carol);
  Predicate<Course> pred3 = course -> gabriel.isEnrolled(course);
  Predicate<Course> pred4 = course -> anthony.isEnrolled(course);

  MtList<Student> empty = new MtList<Student>();
  IList<Student> listOfStudents1 = new ConsList<Student>(anthony,
      new ConsList<Student>(julia, new MtList<Student>()));

  IList<Student> listOfStudents2 = new ConsList<Student>(dany,
      new ConsList<Student>(gabriel, new MtList<Student>()));

  IList<Student> listOfStudents3 = new ConsList<Student>(carol,
      new ConsList<Student>(dany, new ConsList<Student>(anthony,
          new ConsList<Student>(julia, new ConsList<Student>(gabriel, new MtList<Student>())))));

  Instructor barns = new Instructor("Barns");
  Instructor kiara = new Instructor("Kiara");
  Instructor dan = new Instructor("Dan");
  Instructor emily = new Instructor("Emily");
  Instructor emile = new Instructor("Emile");

  Course psych = new Course("Psychology", kiara);
  Course math = new Course("Mathematics", barns);
  Course chem = new Course("Chemistry", kiara);
  Course phil = new Course("Philosophy", barns);
  Course art = new Course("Art", emile);

  MtList<Course> empty2 = new MtList<Course>();
  IList<Course> listOfCourses1 = new ConsList<Course>(psych,
      new ConsList<Course>(math, new MtList<Course>()));
  IList<Course> listOfCourses2 = new ConsList<Course>(psych, new ConsList<Course>(math,
      new ConsList<Course>(chem, new ConsList<Course>(phil, new MtList<Course>()))));

  // Test methods for Registrar
  void testRegistrarMethods(Tester t) {
    // Tests for enroll and isEnrolled methods
    t.checkExpect(this.carol, new Student("Carol", 45));
    t.checkExpect(this.dany, new Student("Dany", 87));
    t.checkExpect(this.gabriel, new Student("Gabriel", 51));
    this.carol.enroll(chem);
    this.carol.enroll(psych);
    this.dany.enroll(phil);
    this.gabriel.enroll(phil);
    t.checkExpect(this.carol.isEnrolled(chem), true);
    t.checkExpect(this.dany.isEnrolled(chem), false);
    t.checkExpect(this.gabriel.isEnrolled(phil), true);

    // Additional enrollments for further testing
    this.anthony.enroll(math);
    this.anthony.enroll(phil);
    this.julia.enroll(math);

    // Tests for the classmates method
    t.checkExpect(this.dany.classmates(gabriel), true);
    t.checkExpect(this.dany.classmates(carol), false);

    // Tests for the dejavu method
    t.checkExpect(this.kiara.dejavu(carol), true);
    t.checkExpect(this.kiara.dejavu(anthony), false);
    t.checkExpect(this.barns.dejavu(gabriel), false);
    t.checkExpect(this.barns.dejavu(anthony), true);
    t.checkExpect(this.kiara.dejavu(julia), false);

    // Tests for the countMatches method
    t.checkExpect(this.empty2.countMatches(pred1), 0);
    t.checkExpect(this.listOfCourses1.countMatches(pred1), 1);
    t.checkExpect(this.listOfCourses2.countMatches(pred2), 2);

    // Tests for the anyMatch method
    t.checkExpect(this.empty2.anyMatch(pred3), false);
    t.checkExpect(this.listOfCourses1.anyMatch(pred3), false);
    t.checkExpect(this.listOfCourses2.anyMatch(pred4), true);

    // Tests for the hasStudent method
    t.checkExpect(this.psych.hasStudent(anthony), false);
    t.checkExpect(this.math.hasStudent(julia), true);
    t.checkExpect(this.chem.hasStudent(dany), false);
    t.checkExpect(this.phil.hasStudent(gabriel), true);

    // Tests for the addStudent method
    t.checkExpect(this.art, new Course("Art", emile));
    this.art.addStudent(anthony);
    t.checkExpect(this.art,
        new Course("Art", emile, new ConsList<Student>(anthony, new MtList<Student>())));
  }

  // Tests for the contains method
  void testContains(Tester t) {
    t.checkExpect(this.empty.contains(anthony), false);
    t.checkExpect(this.listOfStudents1.contains(gabriel), false);
    t.checkExpect(this.listOfStudents2.contains(dany), true);
    t.checkExpect(this.listOfStudents3.contains(julia), true);
  }

  // Tests for the addCourse method
  void testAddCourse(Tester t) {
    t.checkExpect(this.dan, new Instructor("Dan"));
    t.checkExpect(this.emily, new Instructor("Emily"));
    this.dan.addCourse(math);
    this.emily.addCourse(chem);
    t.checkExpect(this.dan,
        new Instructor("Dan", new ConsList<Course>(math, new MtList<Course>())));
    t.checkExpect(this.emily,
        new Instructor("Emily", new ConsList<Course>(chem, new MtList<Course>())));
  }
}