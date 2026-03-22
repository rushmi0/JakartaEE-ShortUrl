package win.rushmi0.extension.shorturl

import javax.naming.InitialContext
import javax.sql.DataSource

class ShortUrlService {

    private fun ds(): DataSource =
        InitialContext().lookup("jdbc/TalonDb") as DataSource

    fun resolve(code: String): String? =
        ds().connection.use { conn ->
            conn.prepareStatement(
                "SELECT TARGET_URL FROM SHORT_URL WHERE URL_CODE = ? AND ACTIVE = '1'"
            ).use { stmt ->
                stmt.setString(1, code)
                stmt.executeQuery().use { rs ->
                    if (rs.next()) rs.getString("TARGET_URL") else null
                }
            }
        }

}