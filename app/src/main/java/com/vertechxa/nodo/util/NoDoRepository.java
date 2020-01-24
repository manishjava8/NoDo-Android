package com.vertechxa.nodo.util;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.vertechxa.nodo.data.NoDoDao;
import com.vertechxa.nodo.data.NoDoRoomDatabase;
import com.vertechxa.nodo.model.NoDo;

import java.util.List;

public class NoDoRepository {

    private NoDoDao noDoDao;

    private LiveData<List<NoDo>> allNoDos;

    public  NoDoRepository(Application application) {
        // Get data from a remote API and then put on deff. list
        NoDoRoomDatabase db = NoDoRoomDatabase.getDatabase(application);
        noDoDao = db.noDoDao();
        allNoDos = noDoDao.getAllNoDo();
    }

    public LiveData<List<NoDo>> getAllNoDos(){
        return  allNoDos;
    }

    public void insert(NoDo noDo) {
        new InsertAsyncTask(noDoDao).execute(noDo);
        noDoDao.insert(noDo);
    }

    private class InsertAsyncTask extends AsyncTask<NoDo, Void, Void> {

        private NoDoDao asyncTaskDao;

        public InsertAsyncTask(NoDoDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(NoDo... params) {
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
