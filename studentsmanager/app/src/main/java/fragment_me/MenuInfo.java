package fragment_me;

import android.graphics.Bitmap;

/**
 * Created by Avecle on 2019/3/17.
 */

public class MenuInfo {
    private Bitmap pic;
    private String menuName;
    private int jiantouId;

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public int getJiantouId() {
        return jiantouId;
    }

    public void setJiantouId(int jiantouId) {
        this.jiantouId = jiantouId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
