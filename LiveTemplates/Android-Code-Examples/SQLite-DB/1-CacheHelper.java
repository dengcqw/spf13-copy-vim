public class CacheHelper extends SQLiteOpenHelper {

    /**
     * Version constant to increment when the database should be rebuilt
     */
    private static final int VERSION = 8;

    /**
     * Name of database file
     */
    private static final String NAME = "cache.db";

    /**
     * @param context
     */
    @Inject
    public CacheHelper(final Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL("CREATE TABLE orgs (id INTEGER PRIMARY KEY);");
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT, avatarurl TEXT);");
        db.execSQL("CREATE TABLE repos (id INTEGER PRIMARY KEY, repoId INTEGER, orgId INTEGER, name TEXT, ownerId INTEGER, private INTEGER, fork INTEGER, description TEXT, forks INTEGER, watchers INTEGER, language TEXT, hasIssues INTEGER, mirrorUrl TEXT);");
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion,
            final int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS orgs");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS repos");
        onCreate(db);
    }
}
