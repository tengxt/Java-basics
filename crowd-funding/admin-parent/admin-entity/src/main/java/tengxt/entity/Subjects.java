package tengxt.entity;

public class Subjects {
    private String subjectName;
    private String score;

    public Subjects() {
    }

    public Subjects(String subjectName, String score) {
        this.subjectName = subjectName;
        this.score = score;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Subjects{" +
                "subjectName='" + subjectName + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
