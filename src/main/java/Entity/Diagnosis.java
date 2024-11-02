package Entity;

public class Diagnosis {
    int id;
    String imagePath;
    String diagnosis;

    public Diagnosis(int id, String imagePath, String diagnosis) {
        this.id = id;
        this.imagePath = imagePath;
        this.diagnosis = diagnosis;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDiagnosis() {
        return diagnosis;
    }
}
