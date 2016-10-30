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
        switch (args.getInt("codeSubject")){
            case 100: //English
                tvSubjectTitle.setText("English");
                tvDescription.setText("This is a very cool description about the English subject, which is the best subject among all");
                break;
            case 101: //Mathematics
                tvSubjectTitle.setText("Mathematics");
                tvDescription.setText("This is a very cool description about the Mathematics subject, which is the best subject among all");
                break;
            case 102: //History
                tvSubjectTitle.setText("History");
                tvDescription.setText("This is a very cool description about the History subject, which is the best subject among all");
                break;
            case 103: //Chemistry
                tvSubjectTitle.setText("Chemistry");
                tvDescription.setText("This is a very cool description about the Chemistry subject, which is the best subject among all");
                break;
            case 104: //Physics
                tvSubjectTitle.setText("Physics");
                tvDescription.setText("This is a very cool description about the Physics subject, which is the best subject among all");
                break;
            case 105: //Geography
                tvSubjectTitle.setText("Geography");
                tvDescription.setText("This is a very cool description about the Geography subject, which is the best subject among all");
                break;
        }

        ContentClass content1 = new ContentClass();
        content1.setContent("Today it was taught how to conjugate verbs");
        content1.setDate("21/10/2016");
        contents.add(content1);

        ContentClass content2 = new ContentClass();
        content2.setContent("Today it was taught the past tense verb");
        content2.setDate("17/10/2016");
        contents.add(content2);

        ContentClass content3 = new ContentClass();
        content3.setContent("Today it was taught the present tense");
        content3.setDate("09/10/2016");
        contents.add(content3);

        ContentClass content4 = new ContentClass();
        content4.setContent("Today it was taught the present continuous");
        content4.setDate("25/09/2016");
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
