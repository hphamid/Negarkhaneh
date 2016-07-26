package ir.abrstudio.negarkhaneh;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class GridImageListActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        GridListFragment result = new GridListFragment();
        ft.replace(R.id.container, result, result.getTag());
        ft.commit();
    }
}
