package ir.abrstudio.negarkhaneh.list;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;


public abstract class FactoryExtendedEndlessAdapter<DATA> extends
        ExtendedEndlessAdapter implements Observer, AdapterView.OnItemClickListener {

    protected Factory<DATA> dataFactory;
    protected int pageSize;
    public static final int DEFAULT_PAGE_SIZE = 10;

    private List<Object> initdata;
    private final int LoadingViewLayoutId;
    private final int FailedViewLayoutId;

    /**
     * must pass loading and failed layout id if you want to ignore failed view layout
     * you can easily pass 0 as failed layout id;
     */
    public FactoryExtendedEndlessAdapter(Context context,
                                         List<Object> initList, int LoadingViewLayoutId,
                                         int FailedViewLayoutId, Factory<DATA> dataFactory, int pageSize) {
        super(context, initList);
        if (dataFactory == null) {
            throw new IllegalStateException("dataFactory cannot be null!");
        }
        this.setDataFactory(dataFactory);
        this.pageSize = pageSize;
        this.LoadingViewLayoutId = LoadingViewLayoutId;
        this.FailedViewLayoutId = FailedViewLayoutId;
    }

    public FactoryExtendedEndlessAdapter(Context context,
                                         List<Object> initList, int LoadingViewLayoutId,
                                         int FailedViewLayoutId, Factory<DATA> dataFactory) {
        this(context, initList, LoadingViewLayoutId, FailedViewLayoutId, dataFactory,
                DEFAULT_PAGE_SIZE);
    }

    @Override
    public View getCustomViewExtendedEndless(int position, View convertView,
                                             ViewGroup parent) {
        return this.getSimpleView(position, convertView, parent, true);
    }

    @Override
    public View getNormalViewExtendedEndless(int position, View convertView,
                                             ViewGroup parent) {
        return this.getSimpleView(position, convertView, parent, false);
    }

    @Override
    public void initListExtended(List<Object> list) {
        this.CopyList(this.getInitData(), list);
    }

    @Override
    protected void ClearExtendedEndless() {
        this.initdata.clear();
        this.getDataFactory().reset();
    }

    public List<Object> getInitData() {
        if (this.initdata == null) {
            this.initdata = new ArrayList<Object>();
        }
        return this.initdata;
    }

    public List<DATA> getFactoryData() {
        List<DATA> toReturn = this.getDataFactory().getLoadedDataList();
        if (toReturn == null) {
            toReturn = new ArrayList<DATA>();
        }
        return toReturn;
    }

    @Override
    public int getCountEndless() {
        return this.getInitData().size() + this.getDataSize();
    }

    public int getDataSize() {
        return this.getFactoryData().size();
    }

    public Object getDataItem(int location) {
        return this.getFactoryData().get(location);
    }

    @Override
    public Object getItemEndless(int position) {
        if (position < this.getInitList().size()) {
            return this.getInitList().get(position);
        } else {
            return this.getFactoryData().get(
                    position - this.getInitList().size());
        }
    }

    @Override
    public View getLoadingViewEndless(int position, View convertView,
                                      ViewGroup parent) {
        return this.checkViewAndInflateIfNeeded(convertView,
                LoadingViewLayoutId);
    }

    @Override
    public boolean failedViewIsAvailable() {
        Log.d("failed is available", "number " + this.FailedViewLayoutId);
        return this.FailedViewLayoutId > 0;
    }

    @Override
    public View getLoadingFailedViewEndless(int position, View convertView,
                                            ViewGroup parent) {
        return this
                .checkViewAndInflateIfNeeded(convertView, FailedViewLayoutId);
    }

    private View checkViewAndInflateIfNeeded(View convertView, int layoutId) {
        View toReturn = convertView;
        if (toReturn == null) {
            toReturn = this.getInflater().inflate(layoutId, null);
        }
        return toReturn;
    }

    @Override
    public int getItemViewTypeEndless(int position) {
        return this.getItemViewTypeSimple(position, this.isInit(position));
    }

    /*
     * actually isInit is extra information and can be inferred using isInit
     * function! :) CAUTION: do not forget to override this function if you need
     * to return more than one view type!!
     */
    public int getItemViewTypeSimple(int position, boolean isInit) {
        return 0;
    }

    /*
     * call from UI Thread!
     */
    public void setDataFactory(Factory<DATA> dataFactory) {
        this.setDataFactory(dataFactory, true);
    }

    public void setDataFactory(Factory<DATA> dataFactory, boolean notify) {
        if (dataFactory != null) {
            if (this.dataFactory != null) {
                this.dataFactory.deleteObserver(this);
            }
            this.dataFactory = dataFactory;
            this.dataFactory.addObserver(this);
            if (notify) {
                this.notifyDataSetChanged();
            }
        }
    }

    protected int getPagesize() {
        return this.pageSize;
    }

    public Factory<DATA> getDataFactory() {
        return this.dataFactory;
    }

    @Override
    public void getMoreEndless() {
        this.getDataFactory().getMoreOnUiThread(this.getPagesize(),
                new GetDataCallback<List<DATA>>() {

                    @Override
                    public void failed(int code, String message) {
                        Log.d("adapter", "failed  is " + code);
                        FactoryExtendedEndlessAdapter.this.failed();
                        FactoryExtendedEndlessAdapter.this.dataProcessDone();
                    }

                    @Override
                    public void success(List<DATA> data) {
                        if (getDataFactory().isDone()) {
                            FactoryExtendedEndlessAdapter.this.dataDone();
                        }
                        FactoryExtendedEndlessAdapter.this.dataProcessDone();
                    }
                }, this.getContext());
    }

    /**
     * Call this method from ui thread please!
     */
    public void factoryChange() {
        if (!isEndless()) {
            makeEndless();
        }
        this.notifyDataSetChanged();
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        Log.e("observed", this.getClass().getName());
        if (getContext() != null) {
            Handler handle = new Handler(getContext().getMainLooper());
            handle.post(new Runnable() {

                @Override
                public void run() {
                    FactoryExtendedEndlessAdapter.this.factoryChange();
                }
            });
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterViewCompat, View view, int i, long l) {
        if (view.getTag() != null) {
            ListViewItem<?, ?, ?> item = (ListViewItem<?, ?, ?>) view.getTag();
            item.onItemClick(view);
        } else {
            if(this.hasFailed()){
                this.onFailedItemClick(view);
            }else{
                this.onLoadingItemClick(view);
            }
        }
    }

    public void onFailedItemClick(View view){
        Log.d(this.getClass().getName(), "failed view clicked");
        this.resetData();
    }

    public void onLoadingItemClick(View view){
        Log.d(this.getClass().getName(), "loading item clicked");
    }

    public abstract View getSimpleView(int position, View convertView,
                                       ViewGroup parent, boolean isInit);

}
