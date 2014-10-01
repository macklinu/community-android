package com.detroitlabs.community.fragments;

import android.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.activities.NavigationActivity;
import com.detroitlabs.community.adapters.ProblemListAdapter;
import com.detroitlabs.community.model.Problem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bobbake4 on 10/1/14.
 */
@EFragment(R.layout.fragment_problem_list)
public class ProblemListFragment extends Fragment implements AdapterView.OnItemClickListener {

    @ViewById
    protected ListView problemListView;

    @FragmentArg
    protected ArrayList<Problem> problems = new ArrayList<Problem>();

    private ProblemListAdapter problemListAdapter;

    public void updateProblemList(List<Problem> response) {
        problemListAdapter.clear();
        problemListAdapter.addAll(response);
        problemListAdapter.notifyDataSetChanged();
    }

    @AfterViews
    protected void afterViews() {
        problemListAdapter = new ProblemListAdapter(getActivity(), new ArrayList<Problem>());
        problemListView.setOnItemClickListener(this);
        problemListView.setAdapter(problemListAdapter);
        updateProblemList(problems);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Problem problem = (Problem) parent.getAdapter().getItem(position);
        ProblemFragment pf = new ProblemFragment_();
        pf.setProblem(problem);
        ((NavigationActivity) getActivity()).changeFragment(pf, true);
    }
}
