package android.bignerdranch.criminalIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.bignerdranch.criminalIntent.CheckinsActivity.newIntent;

public class CheckinsListFragment extends Fragment {

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;
    private boolean mSubtitleVisible;
    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimeRecyclerView = (RecyclerView) view
                .findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }

        updateUI();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_crime_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        }else{
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_crime:
                Checkins checkins = new Checkins();
                CheckinsLab.get(getActivity()).addCrime(checkins);
                Intent intent = CheckinsActivity
                        .newIntent(getActivity(), checkins.getId());
                startActivity(intent);
                return true;

            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            case R.id.show_webview:
                Intent i = HelpWebPageActivity
                        .webIntent(getActivity(),"https://www.wikihow.com/Check-In-on-Facebook");
                startActivity(i);
                return true;


                default:
                    return super.onOptionsItemSelected(item);

        }
    }


    private void updateSubtitle(){
        CheckinsLab checkinsLab = CheckinsLab.get(getActivity());
        int crimeCount = checkinsLab.getCrimes().size();
        String subtitle = getString(R.string.subtitle_format, crimeCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        CheckinsLab checkinsLab = CheckinsLab.get(getActivity());
        List<Checkins> checkins = checkinsLab.getCrimes();

        if (mAdapter == null){
            mAdapter = new CrimeAdapter(checkins);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }else{
            mAdapter.setCrimes(checkins);
            mAdapter.notifyDataSetChanged();
        }




        updateSubtitle();

        //mAdapter = new CrimeAdapter(checkins);
        //mCrimeRecyclerView.setAdapter(mAdapter);
    }


    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mPlaceTextView;
        private Checkins mCheckins;
        private ImageView mSolvedImageView;


        public CrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super (inflater.inflate(R.layout.list_item_crime, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mPlaceTextView = (TextView) itemView.findViewById(R.id.crime_place);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        public void bind(Checkins checkins) {
            mCheckins = checkins;
            mTitleTextView.setText(mCheckins.getTitle());
            mDateTextView.setText(mCheckins.getDate().toString());
            mPlaceTextView.setText(mCheckins.getPlace());
            mSolvedImageView.setVisibility(checkins.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
            /*Toast.makeText(getActivity(),
                    mCheckins.getTitle() + "clicked!", Toast.LENGTH_SHORT)
                    .show();*/
            //Intent intent = new Intent(getActivity(), CheckinsActivity.class);
            Intent intent = newIntent(getActivity(), mCheckins.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {
        private List<Checkins> mCheckins;
        public CrimeAdapter(List<Checkins> checkins) {
            mCheckins = checkins;
        }


        @Override
        public CrimeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CrimeHolder holder, int position) {
            Checkins checkins = mCheckins.get(position);
            holder.bind(checkins);

        }

        @Override
        public int getItemCount() {
            return mCheckins.size();
        }

        public void setCrimes(List<Checkins> checkins) {
            mCheckins = checkins;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

}
