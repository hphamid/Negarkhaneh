package ir.abrstudio.negarkhaneh;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import ir.abrstudio.negarkhaneh.factory.MyImagesFactory;
import ir.abrstudio.negarkhaneh.factory.NewImagesFactory;
import ir.abrstudio.negarkhaneh.factory.TopImagesFactory;


public class GridImageListActivity extends AppCompatActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), getFragmentList()));
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
    }

    public List<FragmentItem> getFragmentList(){
    	List<FragmentItem> toReturn = new ArrayList<FragmentItem>();
    	FragmentItem item1 =  new FragmentItem();
    	Fragment toSend1 = new GridListFragment();
    	Bundle extra1 = new Bundle();
    	extra1.putSerializable("factory", new NewImagesFactory(null));
    	toSend1.setArguments(extra1);
    	item1.setFragment(toSend1);
    	item1.setTitle(getResources().getString(R.string.news));
    	toReturn.add(item1);
    	
    	FragmentItem item2 =  new FragmentItem();
    	Fragment toSend2 = new GridListFragment();
    	Bundle extra2 = new Bundle();
    	extra2.putSerializable("factory", new TopImagesFactory(null));
    	toSend2.setArguments(extra2);
    	item2.setFragment(toSend2);
    	item2.setTitle(getResources().getString(R.string.top));
    	toReturn.add(item2);
    	
    	FragmentItem item3 =  new FragmentItem();
    	Fragment toSend3 = new GridListFragment();
    	Bundle extra3 = new Bundle();
    	extra3.putSerializable("factory", new MyImagesFactory(null));
    	toSend3.setArguments(extra3);
    	item3.setFragment(toSend3);
    	item3.setTitle(getResources().getString(R.string.liked));
    	toReturn.add(item3);
    	
    	return toReturn;
    }
    
    public class FragmentItem{
    	public FragmentItem(){
    		
    	}
    	private String title;
    	private Fragment fragment;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public Fragment getFragment() {
			return fragment;
		}
		public void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}
    }
    public class ViewPageAdapter extends FragmentPagerAdapter{
    	List<FragmentItem> fragments;
		public ViewPageAdapter(FragmentManager fm, List<FragmentItem> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return fragments.get(position).getTitle();
		}
		
		@Override
		public Fragment getItem(int arg0) {
			return this.fragments.get(arg0).fragment;
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
    	
    }
}
