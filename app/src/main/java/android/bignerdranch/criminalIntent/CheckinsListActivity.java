package android.bignerdranch.criminalIntent;

import androidx.fragment.app.Fragment;

public class CheckinsListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CheckinsListFragment();
    }
}
