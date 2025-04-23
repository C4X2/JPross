package org.command.line.orch.utils;

public final class PlatformDetectionUtil {
    private PlatformDetectionUtil() {
    }

    public static boolean isWindows() {
        return Platform.WINDOWS.equals(determinePlatform());
    }

    public static boolean isMac() {
        return Platform.MAC.equals(determinePlatform());
    }

    public static boolean isLinux() {
        return Platform.LINUX.equals(determinePlatform());
    }

    public static Platform determinePlatform() {
        String os = System.getProperty("os.name");

        if (os == null || os.isEmpty()) return Platform.UNKNOWN;

        os = os.toLowerCase();

        if (os.contains("win")) {
            return Platform.WINDOWS;
        }

        if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return Platform.LINUX;
        }

        if (os.contains("mac")) {
            return Platform.MAC;
        }

        if (os.contains("sunos")) {
            return Platform.SOLARIS;
        }

        return Platform.UNKNOWN;
    }

}
