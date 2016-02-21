package com.ucsdbbs.ucsdbbs;

        import android.support.v4.app.DialogFragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.Toast;

public class SelectForumDialog extends DialogFragment implements
        OnItemClickListener {

    String[] listitems = { "item01", "item02", "item03", "item04","item01", "item02", "item03", "item04","item01", "item02", "item03", "item04","item01", "item02", "item03", "item04" };

    ListView mylist;
    Button cancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.newthread_selectforum, null, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        int width = getResources().getDimensionPixelSize(R.dimen.forum_select_width);
        int height = getResources().getDimensionPixelSize(R.dimen.forum_select_height);
        getDialog().getWindow().setLayout(width, height);

        mylist = (ListView) view.findViewById(R.id.list);
        cancel=(Button)view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, listitems);

        mylist.setAdapter(adapter);

        mylist.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if ((view != null)) {
            Toast.makeText(getActivity(), listitems[position], Toast.LENGTH_SHORT)
                    .show();
            dismiss();
        }

    }

}