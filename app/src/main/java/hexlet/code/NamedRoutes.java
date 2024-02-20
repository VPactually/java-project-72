package hexlet.code;

public class NamedRoutes {

    public static String rootPath() {
        return "/";
    }

    public static String urlsPath() {
        return "/urls";
    }

    public static String urlPath(String id) {
        return "/urls/" + id + "/checks";
    }

    public static String urlPath(int id) {
        return urlPath(String.valueOf(id));
    }
//
//    public static String urlsChecksPath(String id) {
//        return "/urls/" + id + "/checks";
//    }
//
//    public static String urlsChecksPath(int id) {
//        return urlsChecksPath(String.valueOf(id));
//    }

}
