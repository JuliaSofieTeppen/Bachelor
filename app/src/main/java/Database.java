import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
    // TODO change class to fit our database
    public class Database extends SQLiteOpenHelper {
        // Table one
        private static String TABLE_FRIEND = "Friend";
        private static String KEY_ID = "_ID";
        private static String KEY_NAME = "Name";
        private static String KEY_PH_NO = "Telephone";
        // Table two
        private static String TABLE_RESTAURANT = "Restaurant";
        private static String KEY_ADDRESS = "Address";
        private static String KEY_TYPE = "Type";
        // Table three
        private static String TABLE_ORDERS = "Orders";
        private static String KEY_DATE = "Date";
        private static String KEY_RES_ID = "Restaurant_id";
        private static String KEY_FRI_ID = "Friend_id";

        // Database specific
        private static int DATABASE_VERSION = 1;
        private static String DATABASE_NAME = "MappeDB";

        Database(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // CREATE TABLE Friend(_ID INTEGER PRIMARY KEY, Name TEXT, Telephone INTEGER)
            String CREATE_TABLE_FRIEND = "CREATE TABLE "+ TABLE_FRIEND + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PH_NO + " INTEGER" + ")";
            // CREATE TABLE Restaurant(_ID INTEGER PRIMARY KEY, Name TEXT, Address TEXT, Telephone INTEGER, Type TEXT)
            String CREATE_TABLE_RESTAURANT = "CREATE TABLE "+ TABLE_RESTAURANT + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PH_NO + " INTEGER, " + KEY_TYPE + " TEXT" + ")";
            // CREATE TABLE Orders(_ID INTEGER PRIMARY KEY, Restaurant_id INTEGER, Friend_id TEXT, Date DATE)
            String CREATE_TABLE_ORDER = "CREATE TABLE "+ TABLE_ORDERS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_RES_ID + " INTEGER," + KEY_FRI_ID + " TEXT," + KEY_DATE + " DATE" + ")";

            db.execSQL(CREATE_TABLE_FRIEND);
            db.execSQL(CREATE_TABLE_RESTAURANT);
            db.execSQL(CREATE_TABLE_ORDER);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
            onCreate(db);
        }
        /*
        void AddFriend(Person friend){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME,friend.getName());
            values.put(KEY_PH_NO,friend.getTelephone());
            db.insert(TABLE_FRIEND,null,values);
            db.close();
        }
        void AddRestaurant(Restaurant restaurant){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME,restaurant.getName());
            values.put(KEY_ADDRESS, restaurant.getAddress());
            values.put(KEY_PH_NO,restaurant.getTelephone());
            values.put(KEY_TYPE,restaurant.getType());
            db.insert(TABLE_RESTAURANT,null,values);
            db.close();
        }
        void AddOrders(List<Person> friends, Restaurant restaurant, String date){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            StringBuilder personIDs = new StringBuilder();
            for(Person friend:friends){
                personIDs.append(Long.toString(friend.get_ID()));
                personIDs.append(",");
            }
            values.put(KEY_RES_ID,restaurant.get_ID());
            values.put(KEY_FRI_ID,personIDs.toString());
            values.put(KEY_DATE, date);
            db.insert(TABLE_ORDERS,null,values);
            db.close();
        }
        List<Restaurant> findAllResturants() {
            List<Restaurant> resturants = new ArrayList<>();
            String selectQuery= "SELECT * FROM " + TABLE_RESTAURANT;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor= db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()) {
                do {
                    Restaurant resturant= new Restaurant();
                    //ID,Name,Address,Phone,Type
                    resturant.set_ID(cursor.getLong(0));
                    resturant.setName(cursor.getString(1));
                    resturant.setAddress(cursor.getString(2));
                    resturant.setTelephone(cursor.getString(3));
                    resturant.setType(cursor.getString(4));
                    resturants.add(resturant);
                } while(cursor.moveToNext());
                cursor.close();
                db.close();
            }
            return resturants;
        }
        public List<Person> findAllFriends() {
            List<Person> friends = new ArrayList<>();
            String selectQuery= "SELECT * FROM " + TABLE_FRIEND;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor= db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()) {
                do {
                    Person friend= new Person();
                    friend.set_ID(cursor.getLong(0));
                    friend.setName(cursor.getString(1));
                    friend.setTelephone(cursor.getString(2));
                    friends.add(friend);
                } while(cursor.moveToNext());
                cursor.close();
                db.close();
            }
            return friends;
        }
        /**
         * Method for deleting people
         * @param inn_id id of person to be deleted
         * @return returns true if person was deleted else false.
         *
        public boolean deleteFriend(long inn_id){
            List<NotifyOrder> orders = findAllOrders();
            Boolean exists=false;
            for(NotifyOrder notifyOrder : orders){
                List<Person> people = notifyOrder.getPeople();
                for(Person person : people){
                    if(person.get_ID()==inn_id){
                        exists=true;
                    }
                }
            }
            if(!exists){
                SQLiteDatabase db = this.getWritableDatabase();
                db.delete(TABLE_FRIEND, KEY_ID + " =? ", new String[]{Long.toString(inn_id)});
                db.close();
                return true;
            }
            return false;
        }

        public int updateFriend(Person person){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, person.getName());
            values.put(KEY_PH_NO, person.getTelephone());
            int changed = db.update(TABLE_FRIEND, values, KEY_ID + " =? ", new String[]{String.valueOf(person.get_ID())});
            db.close();
            return changed;
        }
        public int updateRestaurant(Restaurant restaurant){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, restaurant.getName());
            values.put(KEY_ADDRESS, restaurant.getAddress());
            values.put(KEY_PH_NO, restaurant.getTelephone());
            values.put(KEY_TYPE, restaurant.getType());
            int changed = db.update(TABLE_RESTAURANT, values, KEY_ID + " =? ", new String[]{String.valueOf(restaurant.get_ID())});
            db.close();
            return changed;
        }
        // TODO do we need?
        public int finnAntallKontakter(){
            String query = "SELECT * FROM " + TABLE_FRIEND;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            int antall = cursor.getCount();
            cursor.close();
            db.close();
            return antall;
        }
        // TODO cant use friends phone number to find id
        public Person findfriendId(Long phone){
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_FRIEND, new String[]{KEY_ID, KEY_NAME, KEY_PH_NO}, KEY_PH_NO + " =? ", new String[]{String.valueOf(phone)},null,null,null,null);
            if(cursor != null){
                cursor.moveToFirst();
                Person person = new Person(Long.parseLong(cursor.getString(0)),cursor.getString(1), cursor.getString(2));
                cursor.close();
                return person;
            }
            db.close();
            return null;
        }
        // TODO change this method cant use phone number as id
        public Restaurant findRestaurantId(Long phone){
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query(TABLE_RESTAURANT, new String[]{KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_PH_NO, KEY_TYPE}, KEY_PH_NO + " =? ", new String[]{String.valueOf(phone)},null,null,null,null);
            if(cursor != null){
                cursor.moveToFirst();
                Restaurant restaurant= new Restaurant(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4));
                cursor.close();
                return restaurant;
            }
            db.close();
            return null;
        }

        public Person findPerson(long inn_id){
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery= "SELECT * FROM " + TABLE_FRIEND + " WHERE " + KEY_ID + " =? ";
            Cursor cursor= db.rawQuery(selectQuery, new String[]{Long.toString(inn_id)});
            Person person = new Person();
            if(cursor.moveToFirst()) {
                person.set_ID(cursor.getLong(0));
                person.setName(cursor.getString(1));
                person.setTelephone(cursor.getString(2));
            }
            return person;
        }public Restaurant findRestaurant(long inn_id){
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery= "SELECT * FROM " + TABLE_RESTAURANT + " WHERE " + KEY_ID + " =? ";
            Cursor cursor= db.rawQuery(selectQuery, new String[]{Long.toString(inn_id)});
            Restaurant restaurant = new Restaurant();
            if(cursor.moveToFirst()) {
                restaurant.set_ID(cursor.getLong(0));
                restaurant.setName(cursor.getString(1));
                restaurant.setAddress(cursor.getString(2));
                restaurant.setTelephone(cursor.getString(3));
                restaurant.setType(cursor.getString(4));
            }
            return restaurant;
        }
        public List<NotifyOrder> findAllOrders(){
            List<NotifyOrder> orders = new ArrayList<>();
            String selectQuery= "SELECT * FROM " + TABLE_ORDERS;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor= db.rawQuery(selectQuery, null);
            if(cursor.moveToFirst()) {
                do {
                    NotifyOrder order = new NotifyOrder();
                    order.set_ID(cursor.getLong(0));
                    order.setRestaurant(findRestaurant(cursor.getLong(1)));
                    order.setPeople(getFriendList(cursor.getString(2)));
                    order.setDate(cursor.getString(3));
                    orders.add(order);
                } while(cursor.moveToNext());
                cursor.close();
                db.close();
            }
            return orders;
        }
        private List<Person> getFriendList(String ids){
            List<Person> people =new ArrayList<>();
            String[] friendId= ids.split(",");
            for (String aFriendId : friendId) {
                Person person = findPerson(Long.parseLong(aFriendId));
                people.add(person);
            }
            return people;
        }
        public NotifyOrder findOrder(long inn_id){
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery= "SELECT * FROM " + TABLE_ORDERS + " WHERE " + KEY_ID + " =? ";
            Cursor cursor= db.rawQuery(selectQuery, new String[]{Long.toString(inn_id)});
            NotifyOrder order = new NotifyOrder();
            if(cursor.moveToFirst()) {
                order.set_ID(cursor.getLong(0));
                order.setRestaurant(findRestaurant(cursor.getLong(1)));
                order.setPeople(getFriendList(cursor.getString(2)));
                order.setDate(cursor.getString(3));
                cursor.close();
                db.close();
            }
            return order;
        }
        public boolean deleteRestaurant(long inn_id){
            List<NotifyOrder> orders = findAllOrders();
            boolean exists = false;
            for(NotifyOrder notifyOrder : orders){
                Restaurant restaurant = notifyOrder.getRestaurant();
                if(restaurant.get_ID()== inn_id){
                    exists = true;
                }
            }
            if(!exists) {
                SQLiteDatabase db = this.getWritableDatabase();
                db.delete(TABLE_RESTAURANT, KEY_ID + " =? ", new String[]{Long.toString(inn_id)});
                db.close();
                return true;
            }
            return false;
        }
        public void deleteOrder(long inn_id){
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_ORDERS, KEY_ID + " =? ", new String[]{Long.toString(inn_id)});
            db.close();
        }
        public int updateOrder(NotifyOrder notifyOrder){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            List<Person> people = notifyOrder.getPeople();
            StringBuilder personIDs = new StringBuilder();
            for(Person person : people){
                personIDs.append(Long.toString(person.get_ID()));
                personIDs.append(",");
            }
            values.put(KEY_RES_ID, notifyOrder.getRestaurant().get_ID());
            values.put(KEY_FRI_ID, personIDs.toString());
            values.put(KEY_DATE, notifyOrder.getDate());
            int changed = db.update(TABLE_ORDERS, values, KEY_ID + " =? ", new String[]{String.valueOf(notifyOrder.get_ID())});
            db.close();
            return changed;
        }
    */
    }


