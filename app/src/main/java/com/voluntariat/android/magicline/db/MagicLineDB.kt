package com.voluntariat.android.magicline.db

import android.content.Context
import android.os.AsyncTask
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.voluntariat.android.magicline.data.models.donations.DonationsDBModel
import com.voluntariat.android.magicline.data.models.posts.Converters
import com.voluntariat.android.magicline.data.models.posts.Post
import com.voluntariat.android.magicline.data.models.posts.PostImageItem
import com.voluntariat.android.magicline.data.models.posts.PostsItem
import com.voluntariat.android.magicline.db.dao.DonationsDAO
import com.voluntariat.android.magicline.db.dao.PostDao

@Database(entities = [PostsItem::class, DonationsDBModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MagicLineDB : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun donationsDAO(): DonationsDAO

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
    private val donationsDAO: DonationsDAO = db.donationsDAO()

    override fun doInBackground(vararg params: Void): Void? {
        var post = PostsItem(0, Post(title = "Noticia 1"), ArrayList<PostImageItem>())
        mPostDao.insert(post)

        var donation = DonationsDBModel(0, donationsVal = "1", donationsBcn = "2", donationsBml = "3", donationsMll = "4")
        donationsDAO.insert(donation)
        return null
    }
}
