interface ApplyNameExtension {

    /**
     * Application name for only one default localization
     */
    var appName: String
}

internal open class ApplyNameExtensionImpl : ApplyNameExtension {

    override var appName: String = ""
}