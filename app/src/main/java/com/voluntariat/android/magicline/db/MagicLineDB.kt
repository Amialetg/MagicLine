package com.voluntariat.android.magicline.db

import android.content.Context
import com.voluntariat.android.magicline.db.dao.PostDao
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.annotation.NonNull
import android.os.AsyncTask
import androidx.room.*
import com.voluntariat.android.magicline.data.models.posts.Converters
import com.voluntariat.android.magicline.data.models.posts.Post
import com.voluntariat.android.magicline.data.models.posts.PostImageItem
import com.voluntariat.android.magicline.data.models.posts.PostsItem

@Database(entities = [PostsItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MagicLineDB : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: MagicLineDB

        fun getDatabase(context: Context): MagicLineDB? {
            synchronized(MagicLineDB::class.java) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MagicLineDB::class.java, "magicLine.db")
                        .addCallback(sRoomDatabaseCallback)
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE
        }

        private val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(@NonNull db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

    }

}

private class PopulateDbAsync internal constructor(db: MagicLineDB) : AsyncTask<Void, Void, Void>() {

    private val mPostDao: PostDao = db.postDao()

    override fun doInBackground(vararg params: Void): Void? {
        var post = PostsItem(0, Post(title = "Noticia 1"), ArrayList<PostImageItem>())
        mPostDao.insert(post)
        return null
    }
}
