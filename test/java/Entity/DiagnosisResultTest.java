package Entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagnosisResultTest {

    @Test
    public void testGetSetImageName() {
        DiagnosisResult result = new DiagnosisResult();
        result.setImageName("image1.jpg");
        assertEquals("image1.jpg", result.getImageName());
    }

    @Test
    public void testGetSetDiagnosis() {
        DiagnosisResult result = new DiagnosisResult();
        result.setDiagnosis("Healthy");
        assertEquals("Healthy", result.getDiagnosis());
    }
}