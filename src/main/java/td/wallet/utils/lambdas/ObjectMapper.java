package td.wallet.utils.lambdas;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ObjectMapper {
    void map(PreparedStatement ps) throws SQLException;
}
