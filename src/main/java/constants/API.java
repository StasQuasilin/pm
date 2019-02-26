package constants;

/**
 * Created by szpt_user045 on 22.02.2019.
 */
public class API {
    public static final String LOGIN = "/a/login";
    public static final String REGISTRATION = "/a/registration";
    public static final String RESTORE = "/a/restore";

    public class INFO {
        public static final String PROJECT_LENGTH = "/api/info/project/length";
        public static final String ADD_MONTH = "/api/info/plus/month";
    }

    public class PROJECT{
        public static final String LIST = "/api/project/list";
        public static final String SAVE = "/api/project/save";
    }

    public class TREE{
        public static final String LIST = "/api/tree/list";
    }
}
