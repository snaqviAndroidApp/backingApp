package nanodegree.dfw.perm.bakingapp.data.db;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class FavoritesDao_Impl implements FavoritesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMovieEntries;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMovieEntries;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMovieEntries;

  public FavoritesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMovieEntries = new EntityInsertionAdapter<MovieEntries>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `favorites`(`id_room`,`bfavorite_room`,`updatedAt`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovieEntries value) {
        stmt.bindLong(1, value.getId_room());
        if (value.getBfavorite_room() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBfavorite_room());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimestamp(value.getUpdatedAt());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
      }
    };
    this.__deletionAdapterOfMovieEntries = new EntityDeletionOrUpdateAdapter<MovieEntries>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `favorites` WHERE `id_room` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovieEntries value) {
        stmt.bindLong(1, value.getId_room());
      }
    };
    this.__updateAdapterOfMovieEntries = new EntityDeletionOrUpdateAdapter<MovieEntries>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `favorites` SET `id_room` = ?,`bfavorite_room` = ?,`updatedAt` = ? WHERE `id_room` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MovieEntries value) {
        stmt.bindLong(1, value.getId_room());
        if (value.getBfavorite_room() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getBfavorite_room());
        }
        final Long _tmp;
        _tmp = DateConverter.toTimestamp(value.getUpdatedAt());
        if (_tmp == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindLong(3, _tmp);
        }
        stmt.bindLong(4, value.getId_room());
      }
    };
  }

  @Override
  public void insertFavorites(MovieEntries movieEntries) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfMovieEntries.insert(movieEntries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFavorites(MovieEntries movieEntries) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfMovieEntries.handle(movieEntries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateFavorites(MovieEntries movieEntries) {
    __db.beginTransaction();
    try {
      __updateAdapterOfMovieEntries.handle(movieEntries);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<MovieEntries> loadAllDbFavorite() {
    final String _sql = "SELECT * FROM favorites";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdRoom = _cursor.getColumnIndexOrThrow("id_room");
      final int _cursorIndexOfBfavoriteRoom = _cursor.getColumnIndexOrThrow("bfavorite_room");
      final int _cursorIndexOfUpdatedAt = _cursor.getColumnIndexOrThrow("updatedAt");
      final List<MovieEntries> _result = new ArrayList<MovieEntries>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MovieEntries _item;
        final int _tmpId_room;
        _tmpId_room = _cursor.getInt(_cursorIndexOfIdRoom);
        final String _tmpBfavorite_room;
        _tmpBfavorite_room = _cursor.getString(_cursorIndexOfBfavoriteRoom);
        final Date _tmpUpdatedAt;
        final Long _tmp;
        if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getLong(_cursorIndexOfUpdatedAt);
        }
        _tmpUpdatedAt = DateConverter.toDate(_tmp);
        _item = new MovieEntries(_tmpId_room,_tmpBfavorite_room,_tmpUpdatedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
