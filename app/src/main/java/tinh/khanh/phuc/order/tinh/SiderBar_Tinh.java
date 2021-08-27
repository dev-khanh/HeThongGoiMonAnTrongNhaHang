package tinh.khanh.phuc.order.tinh;

/**
 * Created by NGUYENTRITINH on 03/08/2017.
 */

public class SiderBar_Tinh {
    private int Image;
    private String Text;

    public SiderBar_Tinh(int image, String text) {
        Image = image;
        Text = text;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
