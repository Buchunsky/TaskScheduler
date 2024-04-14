package com.example.taskscheduler;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {
    String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS Users (" +
            "UserId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Username NVARCHAR(255)," +
            "Email NVARCHAR(255)" +
            ");";

    String CREATE_TABLE_BOARDS = "CREATE TABLE IF NOT EXISTS Boards (" +
            "BoardId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Name NVARCHAR(255)," +
            "Description NVARCHAR(1000)," +
            "OwnerId INTEGER," +
            "FOREIGN KEY(OwnerId) REFERENCES Users(UserId)" +
            ");";

    String CREATE_TABLE_TASKS = "CREATE TABLE IF NOT EXISTS Tasks (" +
            "TaskId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "BoardId INTEGER," +
            "Title NVARCHAR(255)," +
            "Description NVARCHAR(1000)," +
            "Status NVARCHAR(50)," +
            "Deadline DATETIME," +
            "AssignedUserId INTEGER," +
            "FOREIGN KEY(BoardId) REFERENCES Boards(BoardId)," +
            "FOREIGN KEY(AssignedUserId) REFERENCES Users(UserId)" +
            ");";

    String CREATE_TABLE_BOARDROLES = "CREATE TABLE IF NOT EXISTS BoardRoles (" +
            "RoleId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "RoleName NVARCHAR(50)" +
            ");";

    String CREATE_TABLE_USERBOARDROLES = "CREATE TABLE IF NOT EXISTS UserBoardRoles (" +
            "UserBoardRoleId INTEGER PRIMARY KEY AUTOINCREMENT," +
            "UserId INTEGER," +
            "BoardId INTEGER," +
            "RoleId INTEGER," +
            "FOREIGN KEY(UserId) REFERENCES Users(UserId)," +
            "FOREIGN KEY(BoardId) REFERENCES Boards(BoardId)," +
            "FOREIGN KEY(RoleId) REFERENCES BoardRoles(RoleId)" +
            ");";

    private SQLiteDatabase DB;
    public DatabaseHelper(SQLiteDatabase db) {
        DB = db;
    }

    public void onCreate() {
        // Create tables
        DB.execSQL(CREATE_TABLE_USERS);
        DB.execSQL(CREATE_TABLE_BOARDS);
        DB.execSQL(CREATE_TABLE_TASKS);
        DB.execSQL(CREATE_TABLE_BOARDROLES);
        DB.execSQL(CREATE_TABLE_USERBOARDROLES);
        seedDatabase();
    }

    public void onUpgrade() {
        // Drop older table if existed
        DB.execSQL("DROP TABLE IF EXISTS Users");
        DB.execSQL("DROP TABLE IF EXISTS Boards");
        DB.execSQL("DROP TABLE IF EXISTS Tasks");
        DB.execSQL("DROP TABLE IF EXISTS BoardRoles");
        DB.execSQL("DROP TABLE IF EXISTS UserBoardRoles");
        // Create tables again
        onCreate();
    }
    public void seedUsers() {

        for (int i = 1; i <= 15; i++) {
            ContentValues values = new ContentValues();
            values.put("Username", "User" + i);
            values.put("Email", "user" + i + "@example.com");

            DB.insert("Users", null, values);
        }
    }

    // Method to seed Boards table
    public void seedBoards() {

        for (int i = 1; i <= 15; i++) {
            ContentValues values = new ContentValues();
            values.put("Name", "Board" + i);
            values.put("Description", "Description for Board " + i);
            values.put("OwnerId", i); // Assuming OwnerId is a valid UserId

            DB.insert("Boards", null, values);
        }
    }

    // Method to seed Tasks table
    public void seedTasks() {

        for (int i = 1; i <= 15; i++) {
            ContentValues values = new ContentValues();
            values.put("BoardId", i); // Assuming BoardId is a valid BoardId
            values.put("Title", "Task" + i);
            values.put("Description", "Description for Task " + i);
            values.put("Status", "Open");
            values.put("Deadline", "2024-12-31"); // Example deadline
            values.put("AssignedUserId", i); // Assuming AssignedUserId is a valid UserId

            DB.insert("Tasks", null, values);
        }
    }

    // Method to seed BoardRoles table
    public void seedBoardRoles() {

        for (int i = 1; i <= 15; i++) {
            ContentValues values = new ContentValues();
            values.put("RoleName", "Role" + i);

            DB.insert("BoardRoles", null, values);
        }
    }

    // Method to seed UserBoardRoles table
    public void seedUserBoardRoles() {

        for (int i = 1; i <= 15; i++) {
            ContentValues values = new ContentValues();
            values.put("UserId", i); // Assuming UserId is a valid UserId
            values.put("BoardId", i); // Assuming BoardId is a valid BoardId
            values.put("RoleId", i); // Assuming RoleId is a valid RoleId

            DB.insert("UserBoardRoles", null, values);
        }
    }

    // Call this method after creating the database to seed all tables
    public void seedDatabase() {
        seedUsers();
        seedBoards();
        seedTasks();
        seedBoardRoles();
        seedUserBoardRoles();
    }
}
