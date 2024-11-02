package MySQLDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entity.DiagnosisResult;

public class DatabaseUtil {

    public static List<DiagnosisResult> getDiagnosisResultsByUsername(String username) throws SQLException {
        List<DiagnosisResult> diagnosisResults = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            try {
                connection = DatabaseConnection.initializeDatabase();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            String sql = "SELECT * FROM Images WHERE user_id = (SELECT id FROM Users WHERE username = ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DiagnosisResult result = new DiagnosisResult();
                result.setImageName(resultSet.getString("image_path"));
                result.setDiagnosis(resultSet.getString("diagnosis"));
                diagnosisResults.add(result);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return diagnosisResults;
    }
}