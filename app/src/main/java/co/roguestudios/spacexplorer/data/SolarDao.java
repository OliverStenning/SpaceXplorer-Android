package co.roguestudios.spacexplorer.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import co.roguestudios.spacexplorer.datatypes.Solar;

@Dao
public interface SolarDao {

    @Query("SELECT * FROM Solar")
    List<Solar> getSystems();

    @Query("SELECT * FROM Solar WHERE systemName LIKE :systemName")
    Solar getSystem(String systemName);

    @Query("SELECT * FROM Solar WHERE systemName LIKE :systemName")
    LiveData<Solar> getSolarSystemLive(String systemName);

    @Query("SELECT balance FROM Solar WHERE systemName LIKE :systemName")
    Double getBalance(String systemName);

    @Query("SELECT balance FROM Solar WHERE systemName LIKE :systemName")
    LiveData<Double> getBalanceLive(String systemName);

    @Query("UPDATE Solar SET balance = :balance WHERE systemName LIKE :systemName")
    void setBalance(String systemName, double balance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void setSystem(Solar solar);

    @Insert
    void insertAll(Solar... systems);

    @Delete
    void delete(Solar system);

}
