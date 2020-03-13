package sr.unasat.blogger.Entity;

public class Student {

    private int id;
    private String studentNummer;
    private String email;


    public Student(int id, String studentNummer, String email) {
        this.id = id;
        this.studentNummer = studentNummer;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentNummer() {
        return studentNummer;
    }

    public void setStudentNummer(String studentNummer) {
        this.studentNummer = studentNummer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentNummer='" + studentNummer + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
