package win.rushmi0.extension.shorturl

import jakarta.servlet.ServletContextEvent
import jakarta.servlet.ServletContextListener
import javax.naming.InitialContext
import javax.sql.DataSource

class ShortUrlInit : ServletContextListener {

    override fun contextInitialized(sce: ServletContextEvent?) {
        val ctx = sce?.servletContext
        ctx?.log("""
            
            =======================================================
               New Module : shorturl-extension-v1.0
               Feature    : Short URL redirect
            =======================================================
               
        """.trimIndent())

        try {
            val ds = InitialContext().lookup("jdbc/TalonDb") as DataSource
            ctx?.log("[ShortUrl] connecting to database...")

            ds.connection.use { conn ->
                conn.createStatement().use { stmt ->
                    stmt.execute("""
                        IF OBJECT_ID('dbo.T_SHORT_URL', 'U') IS NULL
                        CREATE TABLE dbo.SHORT_URL (
                            URL_CODE    NVARCHAR(50)    NOT NULL PRIMARY KEY,
                            TARGET_URL  NVARCHAR(2000)  NOT NULL,
                            ACTIVE      NVARCHAR(1)     NOT NULL DEFAULT '1',
                            CREATE_DATE DATETIME        NOT NULL DEFAULT GETDATE(),
                            CREATE_BY   NVARCHAR(50)    NOT NULL
                        )
                    """.trimIndent())
                }
            }

        } catch (e: Exception) {
            ctx?.log("[ShortUrl] ERROR during initialization: ${e.message}")
        }
    }

    override fun contextDestroyed(sce: ServletContextEvent?) {
        sce?.servletContext?.log("[ShortUrl] plugin stopped")
    }

}