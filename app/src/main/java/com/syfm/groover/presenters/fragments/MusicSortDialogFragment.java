package com.syfm.groover.presenters.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.syfm.groover.R;
import com.syfm.groover.data.network.AppController;
import com.syfm.groover.data.storage.Const;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import de.greenrobot.event.EventBus;
import io.realm.Sort;

/**
 * Created by lycoris on 2016/01/09.
 */
public class MusicSortDialogFragment extends DialogFragment {

    @Bind(R.id.lv_music_sort)
    ListView listview;
    @Bind(R.id.sort_radio_group)
    RadioGroup group;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
        dialog.setTitle("Sort");
        dialog.setContentView(R.layout.dialog_music_sort);

        ButterKnife.bind(this, dialog);

        List<SortType> items = new ArrayList<SortType>();
        items.add(new SortType(android.R.drawable.ic_menu_sort_alphabetically, Const.MUSIC_SORT_MUSIC_NAME));
        items.add(new SortType(android.R.drawable.ic_menu_week, Const.MUSIC_SORT_LAST_PLAYED));
        items.add(new SortType(android.R.drawable.ic_menu_sort_by_size, Const.MUSIC_SORT_PLAY_TIMES));
        items.add(new SortType(android.R.drawable.ic_menu_info_details, Const.MUSIC_SORT_CATEGORY));

        MusicSortDialogAdapter adapter = new MusicSortDialogAdapter(AppController.getInstance(), 0, items);
        listview.setAdapter(adapter);
        return dialog;
    }

    @OnItemClick(R.id.lv_music_sort)
    public void OnItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView listview = (ListView)parent;
        SortType item = (SortType)listview.getItemAtPosition(position);
        List<String> params = new ArrayList<String>();

        params.add(item.getTitle());
        switch(group.getCheckedRadioButtonId()) {
            case R.id.radio_asc:
                params.add("asc");
                break;
            case R.id.radio_desc:
                params.add("desc");
                break;
        }

        EventBus.getDefault().post(params);

        dismiss();
    }

    private class SortType {
        private int image;
        private String title;

        public SortType(int image, String title) {
            this.image = image;
            this.title = title;
        }

        public int getImage() {
            return image;
        }
        public String getTitle() {
            return title;
        }
    }

    public class MusicSortDialogAdapter extends ArrayAdapter<SortType> {
        private LayoutInflater inflater;

        @Bind(R.id.tv_music_sort)
        TextView tv;
        @Bind(R.id.iv_music_sort)
        ImageView iv;

        public MusicSortDialogAdapter(Context context, int viewId, List<SortType> list) {
            super(context, viewId, list);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SortType item = getItem(position);

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.row_music_sort, null);
            }
            ButterKnife.bind(this, convertView);

            iv.setImageResource(item.getImage());
            tv.setText(item.getTitle());

            return convertView;
        }
    }
}