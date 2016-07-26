package ir.abrstudio.negarkhaneh.list;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hamid on 6/3/15.
 *
 */
public class ArrayFactory<DATA> extends Factory<DATA> {
    List<DATA> data;

    public ArrayFactory(Object extra) {
        super(extra);
    }

    private List<DATA> getDataList(){
        if(data == null){
            data = new ArrayList<DATA>();
        }
        return data;
    }

    public void addToList(DATA toAdd){
        if(toAdd != null){
            this.getDataList().add(toAdd);
        }
        if(this.isDone()){
            this.done(false);
        }
        this.changeAndNotifyObservers();
    }


    @Override
    public void getData(int start, int count, GetDataCallback<List<DATA>> callback, Context context) {
        List<DATA> toReturn = null;
        if(start >= 0 && start < getDataList().size()){
            int end = start + count;
            if(end >= getDataList().size()){
                end = getDataList().size();
            }
            toReturn = getDataList().subList(start, end);
        }
        callback.success(toReturn);
    }
}
