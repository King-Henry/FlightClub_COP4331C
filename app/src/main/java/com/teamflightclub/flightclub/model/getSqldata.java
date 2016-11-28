package com.teamflightclub.flightclub.model;


/**
 * Created by Gio on 11/23/2016.
 */

import java.util.ArrayList;
import java.util.List;

public class getSqldata  {
    private static final String[] titles = {"Nothingess cannot be defied", "The softest thing cannot be snapped" , "be like water, my friend."};
    private static final int[] icons = {android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_add, android.R.drawable.ic_menu_delete};

/* Get data for the view purchased ticket activity */
public static List<ListItem > getListData()
    {
        List<ListItem > data = new ArrayList<>();
        for (int i=0; i<3; i++) {
            for (int x = 0; x < 3; x++) {
                ListItem item = new ListItem();
                item.setImageResId(icons[x]);
                item.setTitle(titles[x]);
                data.add(item);
            }
        }
        return data;
    }
/* Get data for the MyCart Activity */
    public static List<ListItem> getListData2() {
        List<ListItem > data = new ArrayList<>();
        for (int i=0; i<3; i++) {
            for (int x = 0; x < 3; x++) {
                ListItem item = new ListItem();
                item.setImageResId(icons[x]);
                item.setTitle(titles[x]);
                data.add(item);
            }
        }
        return data;
    }

}
