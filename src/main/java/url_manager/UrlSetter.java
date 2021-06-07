package url_manager;

public class UrlSetter {

    private static String url;

    public static void setUrl() {
        String TESTS_RUN_ENV = System.getProperty("env");
        if (TESTS_RUN_ENV == null)
            throw new RuntimeException("ERROR: No environment is set");

        url = switch (TESTS_RUN_ENV) {
            case "dev" -> "https://staff.am";
            case "staging" -> "https://stage.staff.am";
            default -> throw new RuntimeException("ERROR: Wrong environment " + TESTS_RUN_ENV);
        };
        //  mvn test -Denv=dev
    }

    public static String getUrl() {
        return url;
    }
}
