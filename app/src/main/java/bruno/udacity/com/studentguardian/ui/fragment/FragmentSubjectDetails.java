package bruno.udacity.com.studentguardian.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bruno.udacity.com.studentguardian.R;
import bruno.udacity.com.studentguardian.adapter.ContentsSubjectAdapter;
import bruno.udacity.com.studentguardian.adapter.SectionedRecyclerViewAdapter;
import bruno.udacity.com.studentguardian.model.ContentClass;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BPardini on 21/10/2016.
 */

public class FragmentSubjectDetails extends Fragment {

    public static final String TAG = "FragmentSubjectDetails";

    @BindView(R.id.tv_subject_title)
    TextView tvSubjectTitle;

    @BindView(R.id.tv_description)
    TextView tvDescription;

    @BindView(R.id.recycler_subject_contents)
    RecyclerView recyclerSubjectContents;

    private Unbinder unbinder;
    private Bundle args;
    private List<ContentClass> contents;
    private ContentsSubjectAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Activity activity = getActivity();
        activity.setTitle(R.string.description_subject_details);

        View view = inflater.inflate(R.layout.fragment_subject_details, container, false);
        unbinder = ButterKnife.bind(this, view);

        args = getArguments();

        contents = new ArrayList<>();

        return view;
    }

    public void onStart(){
        super.onStart();

        addMockData();

        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerSubjectContents.setLayoutManager(manager);
        adapter = new ContentsSubjectAdapter(contents);

        //This is the code to provide a sectioned list
        List<SectionedRecyclerViewAdapter.Section> sections = setUpListHeader(contents);

        //Add your adapter to the sectionAdapter
        SectionedRecyclerViewAdapter.Section[] mSectionArr = new SectionedRecyclerViewAdapter.Section[sections.size()];
        SectionedRecyclerViewAdapter mSectionedAdapter =
                new SectionedRecyclerViewAdapter(getActivity(),R.layout.section,R.id.section_text,adapter);

        mSectionedAdapter.setSections(sections.toArray(mSectionArr));
        recyclerSubjectContents.setAdapter(mSectionedAdapter);
    }

    private void addMockData(){
        switch (args.getInt(getString(R.string.bundle_code_subject))){
            case 100: //English
                tvSubjectTitle.setText(getString(R.string.english));
                tvDescription.setText(getString(R.string.english_description));
                break;
            case 101: //Mathematics
                tvSubjectTitle.setText(getString(R.string.mathematics));
                tvDescription.setText(getString(R.string.mathematics_description));
                break;
            case 102: //History
                tvSubjectTitle.setText(getString(R.string.history));
                tvDescription.setText(getString(R.string.history_description));
                break;
            case 103: //Chemistry
                tvSubjectTitle.setText(getString(R.string.chemistry));
                tvDescription.setText(getString(R.string.chemistry_description));
                break;
            case 104: //Physics
                tvSubjectTitle.setText(getString(R.string.physics));
                tvDescription.setText(getString(R.string.physics_description));
                break;
            case 105: //Geography
                tvSubjectTitle.setText(getString(R.string.geography));
                tvDescription.setText(getString(R.string.geography_description));
                break;
        }

        ContentClass content1 = new ContentClass();
        content1.setContent(getString(R.string.content1));
        content1.setDate(getString(R.string.date1));
        contents.add(content1);

        ContentClass content2 = new ContentClass();
        content2.setContent(getString(R.string.content2));
        content2.setDate(getString(R.string.date2));
        contents.add(content2);

        ContentClass content3 = new ContentClass();
        content3.setContent(getString(R.string.content3));
        content3.setDate(getString(R.string.date3));
        contents.add(content3);

        ContentClass content4 = new ContentClass();
        content4.setContent(getString(R.string.content4));
        content4.setDate(getString(R.string.date4));
        contents.add(content4);
    }

    private List<SectionedRecyclerViewAdapter.Section> setUpListHeader (List<ContentClass> contents) {
        List<SectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

        List<String> listContent = new ArrayList<>();
        int i = 0, j = 0;
        for (ContentClass content : contents) {
            listContent.add(i, content.getContent());
            if (i == 0 || !listContent.get(j).equals(listContent.get(i))) {
                sections.add(new SectionedRecyclerViewAdapter.Section(i, content.getDate()));
            }
            i++;
            j = i-1;
        }
        return sections;
    }

}
