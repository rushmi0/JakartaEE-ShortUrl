package win.rushmi0.extension


interface Platform {
    val os: String
    val jvm: String
}

class PlatformInfo: Platform {
    override val os: String
        get() = System.getProperty("os.name");
    override val jvm: String = "Java ${System.getProperty("java.version")}"
}
